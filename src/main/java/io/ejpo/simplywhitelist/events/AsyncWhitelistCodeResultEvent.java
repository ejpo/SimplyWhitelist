package io.ejpo.simplywhitelist.events;

import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

public class AsyncWhitelistCodeResultEvent extends Event {
    //TODO: Flesh this event out a bit, add a result field and also add a way to callback to the user said result
    private static final HandlerList handlers = new HandlerList();
    private String message;

    public AsyncWhitelistCodeResultEvent(String result) {
        super(true);
        message = result;
    }

    public String getMessage() {
        return message;
    }

    public HandlerList getHandlers() {
        return handlers;
    }

    public static HandlerList getHandlerList() {
        return handlers;
    }
}
