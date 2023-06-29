package io.ejpo.simplywhitelist.discord;

import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import org.bukkit.scheduler.BukkitRunnable;

public class BotMainTask extends BukkitRunnable {

    private DiscordContext _currentContext;
    public BotMainTask (DiscordContext context) {
        _currentContext = context;
    }
    @Override
    public void run() {
        JDA jda = JDABuilder.createDefault(_currentContext.getToken()).build();
    }
}
