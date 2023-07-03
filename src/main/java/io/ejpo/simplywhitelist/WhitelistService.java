package io.ejpo.simplywhitelist;

import io.ejpo.simplywhitelist.events.AsyncWhitelistCodeResultEvent;
import io.ejpo.simplywhitelist.events.AsyncWhitelistCodeSentEvent;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.TextComponent;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerLoginEvent;

import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Optional;
import java.util.UUID;
import java.util.logging.Logger;

public class WhitelistService implements Listener {
    private final int WHITELIST_CODE_SIZE = 5; //TODO: Move this to configuration

    private HashMap<UUID,PendingWhitelistRecord> UUIDToCodeMap;
    private ArrayList<UUID> acceptedUUIDs;

    private final Logger _logger;
    private final SecureRandom secureRandom;


    public WhitelistService(Logger logger) {
        this._logger = logger;
        this.UUIDToCodeMap = new HashMap<>();
        this.acceptedUUIDs = new ArrayList<>(); //TODO: cache the acceptedUUIDs to a file so that its preserved between restarts
        this.secureRandom = new SecureRandom();
    }

    private String generateWhitelistCode() {
        final String upper = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
        final String lower = upper.toLowerCase();
        final String digits = "1234567890";
        final String alphaNumericAlphabet = upper + lower + digits;

        StringBuilder sb = new StringBuilder(WHITELIST_CODE_SIZE);
        for(int i = 0; i < WHITELIST_CODE_SIZE; i++){
            sb.append(alphaNumericAlphabet.charAt(secureRandom.nextInt(alphaNumericAlphabet.length())));
        }

        return sb.toString();
    }

    @EventHandler
    public void onPlayerLoginEvent(PlayerLoginEvent event) {
        UUID connectingUUID = event.getPlayer().getUniqueId();

        if(acceptedUUIDs.contains(connectingUUID)) {
            event.getPlayer().setWhitelisted(true);
            acceptedUUIDs.remove(connectingUUID);
        }

        Optional<OfflinePlayer> whitelistedPlayer = Bukkit.getServer().getWhitelistedPlayers().stream().
                filter(op -> op.getUniqueId().equals(connectingUUID)).
                findFirst();

        String whitelistCode;

        if(whitelistedPlayer.isEmpty()){
            if(!UUIDToCodeMap.containsKey(connectingUUID)){
                whitelistCode = generateWhitelistCode();
                UUIDToCodeMap.put(connectingUUID,new PendingWhitelistRecord(connectingUUID,
                        event.getPlayer().getName(), whitelistCode));
            } else {
                whitelistCode = UUIDToCodeMap.get(connectingUUID).whitelistCode();
            }
            TextComponent whitelistKickTC = Component.text("You need to register on discord with this code: " + whitelistCode);
            event.disallow(PlayerLoginEvent.Result.KICK_WHITELIST,
                    whitelistKickTC);
        }
    }

    @EventHandler
    public void onWhitelistCodeSentEvent(AsyncWhitelistCodeSentEvent event) {
        var playerToWhitelist = UUIDToCodeMap.values().stream().filter(r -> r.whitelistCode().equals(event.getMessage())).findFirst();
        if(playerToWhitelist.isEmpty()){
            _logger.warning("Invalid whitelist code received: " + event.getMessage());
            var invalidResultEvent = new AsyncWhitelistCodeResultEvent("Invalid Code");
            Bukkit.getServer().getPluginManager().callEvent(invalidResultEvent);

            return;
        }

        acceptedUUIDs.add(playerToWhitelist.get().playerUUID());

        _logger.info("Plugin received code: " + event.getMessage());
    }
}
