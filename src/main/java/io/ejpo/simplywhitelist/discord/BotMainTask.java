package io.ejpo.simplywhitelist.discord;

import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.ISnowflake;
import net.dv8tion.jda.api.entities.channel.Channel;
import net.dv8tion.jda.api.interactions.commands.OptionType;
import net.dv8tion.jda.api.interactions.commands.build.Commands;
import net.dv8tion.jda.api.interactions.components.ActionRow;
import net.dv8tion.jda.api.interactions.components.text.TextInput;
import net.dv8tion.jda.api.interactions.components.text.TextInputStyle;
import net.dv8tion.jda.api.interactions.modals.Modal;
import org.bukkit.Bukkit;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.Arrays;
import java.util.EventListener;
import java.util.List;
import java.util.logging.Logger;

public class BotMainTask extends BukkitRunnable implements EventListener {

    private final DiscordContext currentContext;
    private final Logger _logger;
    public BotMainTask (Logger logger, DiscordContext context) {
        // Replace this with proper dependency injection later ()I.E make a logging service
        // that depends on the plugin class instead
        this._logger = logger;
        this.currentContext = context;
    }
    @Override
    public void run() {
        _logger.info("Discord Token: " + currentContext.getToken());
        JDA jda = JDABuilder.createDefault(currentContext.getToken()).build();

        try {
            jda.awaitReady();
        }
        catch (IllegalStateException e) {
            //TODO: Kill the plugin cleanly
            _logger.severe("JDA was shutdown before it was fully initialised");
            _logger.severe(Arrays.toString(e.getStackTrace()));
        }
        catch (InterruptedException e) {
            //TODO: Kill the plugin cleanly
            _logger.severe("JDA thread was interrupted during initialisation");
            _logger.severe(Arrays.toString(e.getStackTrace()));
        }

        _logger.info("Bot Started :)");

        registerListeners(jda);

        List<Long> guildIDs = jda.getGuilds().stream().map(ISnowflake::getIdLong).toList();

        jda.updateCommands().addCommands(
            Commands.slash("swcreatebutton",
                "Creates the button to generate whitelist codes in this channel")
                    .addOption(OptionType.CHANNEL,"channel",
                            "Specify the channel to post the whitelisting message",
                            true)
        ).queue();

        _logger.info("Connected Guilds: " + guildIDs);
    }

    private void registerListeners(JDA api) {
        api.addEventListener(new WhitelistDiscordService(_logger));
    }
}
