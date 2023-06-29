package io.ejpo.simplywhitelist.discord;

public class DiscordContext {
    private String discordToken;

    public DiscordContext (String token) {
        discordToken = token;
    }

    public String getToken() {
        return discordToken;
    }
}
