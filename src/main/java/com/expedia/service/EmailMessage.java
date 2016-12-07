package com.expedia.service;

import com.fasterxml.jackson.annotation.JsonProperty;


public class EmailMessage {

    @JsonProperty( "received_date" )
    private String receivedDate;

    @JsonProperty( "subject" )
    private String subject;

    @JsonProperty( "content" )
    private String content;

    @JsonProperty( "from_email" )
    private String fromEmail;

    @JsonProperty( "message_id" )
    private String messageId;

    public String getReceivedDate() {
        return receivedDate;
    }

    public void setReceivedDate(String receivedDate) {
        this.receivedDate = receivedDate;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getMessageId() {
        return messageId;
    }

    public void setMessageId(String messageId) {
        this.messageId = messageId;
    }

    public String getFromEmail() {
        return fromEmail;
    }

    public void setFromEmail(String fromEmail) {
        this.fromEmail = fromEmail;
    }

    @Override
    public String toString() {
        return "EmailMessage{" +
                "receivedDate='" + receivedDate + '\'' +
                ", subject='" + subject + '\'' +
                ", content='" + content + '\'' +
                ", fromEmail='" + fromEmail + '\'' +
                ", messageId='" + messageId + '\'' +
                '}';
    }
}
