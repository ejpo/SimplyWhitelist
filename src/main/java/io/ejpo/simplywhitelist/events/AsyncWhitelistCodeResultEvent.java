package io.ejpo.simplywhitelist.events;

import io.ejpo.simplywhitelist.WhitelistResult;
import net.dv8tion.jda.api.interactions.commands.OptionType;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

public class AsyncWhitelistCodeResultEvent extends Event {
    //TODO: Flesh this event out a bit, add a result field and also add a way to callback to the user said result
    private static final HandlerList handlers = new HandlerList();

    /**
     * The result returned by the whitelist code
     */
    private final WhitelistResult result;
    /**
     * The Entity (User or Channel) that will recieve
     * feedback based on the result
     */
    private final String callbackID;
    /**
     * The option type of the callback entity so that we can
     * choose the correct action for the callback
     */
    private final OptionType callbackEntityType;

    public AsyncWhitelistCodeResultEvent(WhitelistResult result,
                                         String callbackID,
                                         OptionType callbackEntityType) {
        super(true);
        this.result = result;
        this.callbackID = callbackID;
        this.callbackEntityType = callbackEntityType;
    }

    public WhitelistResult getResult() {
        return result;
    }

    public String getCallbackID() {
        return callbackID;
    }

    public OptionType getCallbackEntityType() {
        return callbackEntityType;
    }

    public HandlerList getHandlers() {
        return handlers;
    }

    public static HandlerList getHandlerList() {
        return handlers;
    }
}
