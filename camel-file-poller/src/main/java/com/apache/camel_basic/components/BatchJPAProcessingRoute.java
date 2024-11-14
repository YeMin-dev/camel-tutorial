package com.apache.camel_basic.components;

import com.apache.camel_basic.beans.NameAddress;
import com.apache.camel_basic.processor.InboundMessageProcessor;
import org.apache.camel.LoggingLevel;
import org.apache.camel.builder.RouteBuilder;
import org.springframework.stereotype.Component;

//@Component
//public class BatchJPAProcessingRoute extends RouteBuilder {
//
//    @Override
//    public void configure() throws Exception {
//
//        from("timer:readDB?period=5000")
//                .routeId("readDBId")
//                .to("jpa:" + NameAddress.class.getName() + "?namedQuery=fetchAllRows")
//                .split(body())
//                    .process(new InboundMessageProcessor())
//                    .log(LoggingLevel.INFO, "Transformed Body: ${body}")
//                    .convertBodyTo(String.class) // implicitly setting body with fileDataPojo.toString()
//                    .to("file:src/data/output?fileName=outputFile.csv&fileExist=append&appendChars=\\n")
//
//                // toD meand dynamic URI
//                .toD("jpa:" + NameAddress.class.getName() + "?nativeQuery=DELETE FROM NAME_ADDRESS WHERE ID = ${header.consumedId}&useExecuteUpdate=true")
//                .end();
//
//    }
//}
