package com.Reddit.Models.MessageManagement;

public class MessageToMessageRel {

    public MessageToMessageRel(Message message, MessageToMessageRelType messageToMessageRelType) {
        this.messageToMessageRelType = messageToMessageRelType;
        this.message = message;
    }

    // ******************************************* Connections *******************************************************//

    private MessageToMessageRelType messageToMessageRelType;
    private Message message;

    // ******************************************* Properties *******************************************************//

    public MessageToMessageRelType getMessageToMessageRelType() {
        return messageToMessageRelType;
    }

    public void setMessageToMessageRelType(MessageToMessageRelType messageToMessageRelType) {
        this.messageToMessageRelType = messageToMessageRelType;
    }

    public Message getMessage() {
        return message;
    }

    public void setMessage(Message message) {
        this.message = message;
    }
}
