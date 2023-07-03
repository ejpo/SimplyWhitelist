package io.ejpo.simplywhitelist;

import java.util.UUID;

// TODO: Convert this into a whitelist request, the whitelist request manages the generation of the code
// on construction, requiring the UUID and the playerName
// The specifications for the code, such as the length and complexity should come from the configuration file
public record PendingWhitelistRecord(UUID playerUUID, String playerName, String whitelistCode) {

}
