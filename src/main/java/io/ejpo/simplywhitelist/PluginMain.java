package io.ejpo.simplywhitelist;

import io.ejpo.simplywhitelist.discord.BotMainTask;
import io.ejpo.simplywhitelist.discord.DiscordContext;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.LinearComponents;
import net.kyori.adventure.text.TextComponent;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerLoginEvent;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.Optional;
import java.util.UUID;

public class PluginMain extends JavaPlugin implements Listener {
    @Override
    public void onEnable() {
        this.saveDefaultConfig();

        this.getLogger().info("Registering Bukkit Events");
        Bukkit.getPluginManager().registerEvents(this, this);
        Bukkit.getPluginManager().registerEvents(new WhitelistService(this.getLogger()), this);

        this.getLogger().info("Creating Discord Context");
        var discordContext = new DiscordContext(this.getConfig().getConfigurationSection("discord-settings").getString("discord-token"));
        var bot = new BotMainTask(this.getLogger(), discordContext);

        this.getLogger().info("Starting Bot Thread");
        bot.runTask(this);
    }

    /* TODO:
     We want to intercept the LoginEvent and Disallow the logins of anyone who is not on the whitelist
    https://hub.spigotmc.org/javadocs/spigot/org/bukkit/event/player/PlayerLoginEvent.html
    We also want to make sure that we use the servers whitelist.json as the source of truth for who is and isn't
    whitelisted so that there is backwards compatability if the plugin is removed/disabled/broken
    */
    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        event.getPlayer().sendMessage(Component.text("Hello, " + event.getPlayer().getName() + "!"));
    }
}
