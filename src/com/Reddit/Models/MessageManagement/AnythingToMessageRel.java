package com.Reddit.Models.MessageManagement;

public class AnythingToMessageRel {

    public AnythingToMessageRel(Messagable messagable, AnyThingToMessageRelType anyThingToMessageRelType) {
        this.messagable = messagable;
        this.anyThingToMessageRelType = anyThingToMessageRelType;
    }

    // ******************************************* Connections *******************************************************//

    private Messagable messagable; // Every Messagable Has (n) AnythingToMessageRel
    private AnyThingToMessageRelType anyThingToMessageRelType;

    // ******************************************* Getter & Setters ***************************************************//

    public Messagable getMessagable() {
        return messagable;
    }

    public void setMessagable(Messagable messagable) {
        this.messagable = messagable;
    }

    public AnyThingToMessageRelType getAnyThingToMessageRelType() {
        return anyThingToMessageRelType;
    }

    public void setAnyThingToMessageRelType(AnyThingToMessageRelType anyThingToMessageRelType) {
        this.anyThingToMessageRelType = anyThingToMessageRelType;
    }

}