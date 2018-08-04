package com.shadowmc.plugin.customevents;

import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

public class TagsUpdateEvent extends Event {

    private static HandlerList handlerList = new HandlerList();

    public static HandlerList getHandlerList() {
        return handlerList;
    }

    private final Type type;
    private final String tag;

    public TagsUpdateEvent(Type type, String tag) {
        super();
        this.type = type;
        this.tag = tag;
    }

    public Type getType() {
        return type;
    }

    public String getTag() {
        return tag;
    }

    public enum Type{
        CREATE, REMOVE, EDITED
    }


    public HandlerList getHandlers() {
        return handlerList;
    }
}
