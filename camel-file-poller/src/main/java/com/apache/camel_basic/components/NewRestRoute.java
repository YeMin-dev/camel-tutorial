package com.apache.camel_basic.components;

import com.apache.camel_basic.beans.InboundRestProcessingBean;
import com.apache.camel_basic.beans.NameAddress;
import com.apache.camel_basic.processor.InboundMessageProcessor;
import org.apache.camel.Exchange;
import org.apache.camel.LoggingLevel;
import org.apache.camel.Predicate;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.model.rest.RestBindingMode;
import org.springframework.stereotype.Component;

import javax.jms.JMSException;
import java.net.ConnectException;

@Component
public class NewRestRoute extends RouteBuilder {

    @Override
    public void configure() throws Exception {

        Predicate isCityAjax = header("userCity").isEqualTo("Ajax");

        onException(JMSException.class, ConnectException.class, RuntimeException.class)
                .routeId("jmsExceptionRouteId")
                .handled(true)
                .log(LoggingLevel.ERROR, "Exception has occurred; handling gracefully");

        restConfiguration()
                .component("jetty")
                .host("localhost")
                .port(8080)
                .bindingMode(RestBindingMode.json)
                .enableCORS(true);

        rest("masterclass")
                .produces("application/json")
                .post("nameAddress").type(NameAddress.class).route()
                .routeId("newRestRouteId")
//                .throwException(new RuntimeException("Intentional exception for testing"))
                .log(LoggingLevel.INFO, "${body}")

                .bean(new InboundRestProcessingBean(), "validate2") // will execute automatically if there is only one method in the class

                // Setup Rule
                // If City = JAAX -> send to MQ
                // Else send to both DB and MQ
                .choice()
                    // .when(simple("${headers.userCity} == 'Ajax'"))
                    .when(isCityAjax) // another way using predicate
                        .to("direct:toActiveMQ")
                    .otherwise()
                        .to("direct:toDB")
                        .to("direct:toActiveMQ")

                // process JSON and write to output file
//                .process(new InboundMessageProcessor())
//                .log(LoggingLevel.INFO, "Transformed Body: ${body}")
//                .convertBodyTo(String.class) // implicitly setting body with fileDataPojo.toString()
//                .to("file:src/data/output?fileName=outputFile.csv&fileExist=append&appendChars=\\n");

                // this will save to db and queue simultaneously
//                .multicast()

                    // save to database
//                    .to("jpa:" + NameAddress.class.getName())

                    // send to queue
//                    .to("activemq:queue:my-activemq-queue?exchangePattern=InOnly")

                // sending to another sub routes (direct = synchronize, seda = synchronize)
                // can use wireTap instead of 'to' to send data in separate thread
                .log(LoggingLevel.INFO, ">>> Sending to DB endpoint")
                .to("direct:toDB")
                .log(LoggingLevel.INFO, ">>> Sending to AMQ endpoint")
                .to("direct:toActiveMQ")

                .setHeader(Exchange.HTTP_RESPONSE_CODE, constant(200))
                .transform().simple("Message Processed and Result Generated with Body: ${body}")
                .endRest();

        from("direct:toDB")
                .routeId("toDBId")
                .log(LoggingLevel.INFO, ">>> In DB endpoint")
                .to("jpa:" + NameAddress.class.getName());

        from("direct:toActiveMQ")
                .routeId("toActiveMQId")
                .log(LoggingLevel.INFO, ">>> In AMQ endpoint")
                .to("activemq:queue:my-activemq-queue?exchangePattern=InOnly");
    }
}
