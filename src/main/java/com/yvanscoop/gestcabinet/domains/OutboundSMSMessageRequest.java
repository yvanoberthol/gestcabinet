package com.yvanscoop.gestcabinet.domains;

import java.io.Serializable;

public class OutboundSMSMessageRequest implements Serializable {
    private String address;
    private String senderAddress;
    private String outboundSMSTextMessage;

    public OutboundSMSMessageRequest() {
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getSenderAddress() {
        return senderAddress;
    }

    public void setSenderAddress(String senderAddress) {
        this.senderAddress = senderAddress;
    }

    public String getOutboundSMSTextMessage() {
        return outboundSMSTextMessage;
    }

    public void setOutboundSMSTextMessage(String outboundSMSTextMessage) {
        this.outboundSMSTextMessage = outboundSMSTextMessage;
    }
}
