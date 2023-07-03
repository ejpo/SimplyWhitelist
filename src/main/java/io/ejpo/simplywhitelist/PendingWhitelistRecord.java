package io.ejpo.simplywhitelist;

import java.util.UUID;

public record PendingWhitelistRecord(UUID playerUUID, String playerName, String whitelistCode) {

}
