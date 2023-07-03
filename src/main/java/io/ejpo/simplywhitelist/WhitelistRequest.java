package io.ejpo.simplywhitelist;

/**
 * A WhitelistRequest object represents a code that is tied to a particular login event and UUID
 * The WhitelistRequest objects are kept in memory for some lifetime and are cached to a file
 * every n requests or after a time period (TBD) so will also have to be able to dump itself as JSON
 * as well as be constructed from JSON
 */
public class WhitelistRequest {
}
