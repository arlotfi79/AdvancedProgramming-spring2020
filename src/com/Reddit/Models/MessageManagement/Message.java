package com.Reddit.Models.MessageManagement;

import com.Reddit.Models.UserManagement.Direct;
import com.Reddit.Models.UserManagement.Group;
import com.Reddit.Models.UserManagement.Room;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Message {

    private static List<Message> messageDataBase = new ArrayList<>();

    private MessageData messageData;
    private boolean isSeen;
    private Date sendTime;

    public Message(MessageData messageData) {
        this.messageData = messageData;
        this.isSeen = false;
        this.sendTime = new Date();
        this.anythingToMessageRels = new ArrayList<>();
        this.messageToMessageRels = new ArrayList<>();
    }

    // ******************************************* Connections *******************************************************//

    private List<AnythingToMessageRel> anythingToMessageRels;
    private List<MessageToMessageRel> messageToMessageRels;

    // ******************************************* Getter & Setters ***************************************************//

    public static List<Message> getMessageDataBase() {
        return messageDataBase;
    }

    public static void setMessageDataBase(List<Message> messageDataBase) {
        Message.messageDataBase = messageDataBase;
    }

    public MessageData getMessageData() {
        return messageData;
    }

    public void setMessageData(MessageData messageData) {
        this.messageData = messageData;
    }

    public boolean isSeen() {
        return isSeen;
    }

    public void setSeen(boolean seen) {
        isSeen = seen;
    }

    public List<AnythingToMessageRel> getAnythingToMessageRels() {
        return anythingToMessageRels;
    }

    public void setAnythingToMessageRels(List<AnythingToMessageRel> anythingToMessageRels) {
        this.anythingToMessageRels = anythingToMessageRels;
    }

    public List<MessageToMessageRel> getMessageToMessageRels() {
        return messageToMessageRels;
    }

    public void setMessageToMessageRels(List<MessageToMessageRel> messageToMessageRels) {
        this.messageToMessageRels = messageToMessageRels;
    }

    public Date getSendTime() {
        return sendTime;
    }

    public void setSendTime(Date sendTime) {
        this.sendTime = sendTime;
    }

    //************************************************* Methods *****************************************************//

    // 1. Add Message To DataBase
    public void sendMessage() {
        messageDataBase.add(this);
    }

    // 2. Get Senders
    public List<Messagable> getSender() {
        List<Messagable> senders = new ArrayList<>();

        for (AnythingToMessageRel anythingToMessageRel : anythingToMessageRels) {
            if (anythingToMessageRel.getAnyThingToMessageRelType() == AnyThingToMessageRelType.SENDER) {
                senders.add(anythingToMessageRel.getMessagable());
            }
        }
        return senders;
    }

    // 2.1 Get Sender
    public Room getMessageSender(){
        for (AnythingToMessageRel anythingToMessageRel : anythingToMessageRels) {
            if (anythingToMessageRel.getAnyThingToMessageRelType() == AnyThingToMessageRelType.SENDER) {
                if (anythingToMessageRel.getMessagable() instanceof Direct){
                    return ((Direct) anythingToMessageRel.getMessagable());
                }
                else if (anythingToMessageRel.getMessagable() instanceof Group){
                    return ((Group) anythingToMessageRel.getMessagable());
                }
            }
        }
        return null;
    }

    // 3. Get Receivers
    public List<Messagable> getReceiver() {
        List<Messagable> receivers = new ArrayList<>();
        for (AnythingToMessageRel anythingToMessageRel : anythingToMessageRels) {
            if (anythingToMessageRel.getAnyThingToMessageRelType() == AnyThingToMessageRelType.RECEIVER) {
                receivers.add(anythingToMessageRel.getMessagable());
            }
        }
        return receivers;
    }

    // 4. Show Messages
    public static List<Message> getMessagesFromMessagable(Messagable messagable){
        List<Message> myMessages = new ArrayList<>();
        for (Message message : messageDataBase){
            for (Messagable messagable1 : message.getReceiver()){
                if (messagable1 == messagable){
                    myMessages.add(message);
                    break;
                }
            }
        }
        return myMessages;
    }

    // 5. get AnythingToMessageRelType
    public AnyThingToMessageRelType getAnythingToMessageRelType(Messagable messagable) {
        for (AnythingToMessageRel anythingToMessageRel : anythingToMessageRels) {
            if (anythingToMessageRel.getMessagable() == messagable) {
                return anythingToMessageRel.getAnyThingToMessageRelType();
            }
        }
        return null;
    }


}
