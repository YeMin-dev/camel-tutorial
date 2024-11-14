package com.apache.camel_basic.beans;

import org.apache.camel.Exchange;

public class InboundRestProcessingBean {

    public void validate(Exchange exchange) {

        NameAddress nameAddress = exchange.getIn().getBody(NameAddress.class);
        exchange.getIn().setHeader("userCity", nameAddress.getCity());

    }

    public void validate2(Exchange exchange) {

        NameAddress nameAddress = exchange.getIn().getBody(NameAddress.class);
        exchange.getIn().setHeader("userCity", nameAddress.getCity());

    }
}
