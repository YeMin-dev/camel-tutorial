package com.apache.camel_basic.processor;

import com.apache.camel_basic.beans.NameAddress;
import com.apache.camel_basic.beans.OutboundNameAddress;
import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class InboundMessageProcessor implements Processor {

    Logger logger = LoggerFactory.getLogger(InboundMessageProcessor.class);

    @Override
    public void process(Exchange exchange) throws Exception {

        // Getting data line by line in string format
        // String fileData = exchange.getIn().getBody(String.class);

        // Getting data line by line in POJO format
        NameAddress nameAddress = exchange.getIn().getBody(NameAddress.class);
        exchange.getIn().setBody(new OutboundNameAddress(nameAddress.getName(), returnOutboundAddress(nameAddress)));
        exchange.getIn().setHeader("consumedId", nameAddress.getId());

        // exchange.getIn().setBody(fileDataPojo.toString());
    }

    private String returnOutboundAddress(NameAddress address) {

        StringBuilder concatenatedAddress = new StringBuilder();
        concatenatedAddress.append(address.getHouseNumber());
        concatenatedAddress.append(" " + address.getCity());
        concatenatedAddress.append(" " + address.getProvince());
        concatenatedAddress.append(" " + address.getPostalCode());

        return concatenatedAddress.toString();
    }
}
