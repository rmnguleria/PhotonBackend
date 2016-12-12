package com.expedia.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Inject;

public class MailReceiver {

    private static final Logger logger = LoggerFactory.getLogger(MailReceiver.class);

    @Inject
    private UserService userService;

    /*@RabbitListener(queues = Constants.QUEUE_NAME)
    public void receive(Message message) {

        ObjectMapper mapper = new ObjectMapper();

        EmailMessage bo;
        try {
            bo = mapper.readValue(message.getBody(), EmailMessage.class);

            String[] tokens = bo.getSubject().split(":");
            String email = bo.getFromEmail();
            email = email.substring(email.indexOf('<') + 1, email.indexOf('>'));
            Assert.hasText("RPT", tokens[0]);
            Assert.isTrue(tokens.length == 4);
            ApprovalBO approvalBO = new ApprovalBO(
                    tokens[2],
                    new String(Base64.decodeBase64(tokens[3].getBytes())),
                    email,
                    Integer.parseInt(tokens[1]));
            userService.takeActionOnRequest(approvalBO);
        }catch (Exception e){
            logger.error("Error while processing message", e.getMessage(),e);
        }
    }*/
}
