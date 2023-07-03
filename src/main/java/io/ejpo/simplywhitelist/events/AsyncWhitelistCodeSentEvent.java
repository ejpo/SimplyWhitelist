package io.ejpo.simplywhitelist.events;

import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

public class AsyncWhitelistCodeSentEvent extends Event {
    //TODO: Add fields to allow for callback to user/channel of succesful/unseccseful whitelisting
    private static final HandlerList handlers = new HandlerList();
    private String message;

    public AsyncWhitelistCodeSentEvent(String whitelistCodeRecieved) {
        super(true);
        message = whitelistCodeRecieved;
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
