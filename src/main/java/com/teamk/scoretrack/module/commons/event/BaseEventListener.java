package com.teamk.scoretrack.module.commons.event;

public abstract class BaseEventListener<EVENT> {
    public abstract void handleEvent(EVENT event);
}
