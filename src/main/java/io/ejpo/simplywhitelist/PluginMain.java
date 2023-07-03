package io.ejpo.simplywhitelist;

import io.ejpo.simplywhitelist.discord.BotMainTask;
import io.ejpo.simplywhitelist.discord.DiscordContext;
import net.kyori.adventure.text.Component;
import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.plugin.java.JavaPlugin;

public class PluginMain extends JavaPlugin implements Listener {
    @Override
    public void onEnable() {
        this.saveDefaultConfig();

        this.getLogger().info("Registering Events");
        Bukkit.getPluginManager().registerEvents(this, this);

        this.getLogger().info("Creating Discord Context");
        var discordContext = new DiscordContext(this.getConfig().getConfigurationSection("discord-settings").getString("discord-token"));
        var bot = new BotMainTask(this.getLogger(), discordContext);

        this.getLogger().info("Starting Bot Thread");
        bot.runTask(this);
    }

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        event.getPlayer().sendMessage(Component.text("Hello, " + event.getPlayer().getName() + "!"));
    }
}
