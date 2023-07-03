package io.ejpo.simplywhitelist.discord;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.channel.ChannelType;
import net.dv8tion.jda.api.entities.channel.concrete.TextChannel;
import net.dv8tion.jda.api.events.interaction.ModalInteractionEvent;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.events.interaction.component.ButtonInteractionEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.dv8tion.jda.api.interactions.components.ActionRow;
import net.dv8tion.jda.api.interactions.components.buttons.Button;
import net.dv8tion.jda.api.interactions.components.text.TextInput;
import net.dv8tion.jda.api.interactions.components.text.TextInputStyle;
import net.dv8tion.jda.api.interactions.modals.Modal;
import net.dv8tion.jda.api.utils.messages.MessageCreateData;

import javax.annotation.Nonnull;
import java.util.logging.Logger;

public class WhitelistDiscordService extends ListenerAdapter {

    private final Logger _logger;

    public WhitelistDiscordService(Logger logger) {
        this._logger = logger;
    }
    @Override
    public void onButtonInteraction(@Nonnull ButtonInteractionEvent event) {
        if(event.getButton().getId().equals("get-whitelisted")){
            var whitelistStringTextbox = TextInput.create("whitelist-string","Whitelist Code", TextInputStyle.SHORT)
                    .setRequired(true)
                    .setPlaceholder("Example: 6GhsY")
                    .setRequiredRange(5,5)
                    .build();

            var mb = Modal.create("whitelist-string-modal","Enter your whitelist code");
            mb.addComponents(
                    ActionRow.of(whitelistStringTextbox)
            );
            var modal = mb.build();
            event.replyModal(modal).queue();
        }
    }

    @Override
    public void onModalInteraction (@Nonnull ModalInteractionEvent event) {
        if (event.getModalId().equals("whitelist-string-modal")){
            _logger.info("Modal Submitted");
            event.reply("Code recognised, your account {ACCOUNT HERE} has been whitelisted")
                    .setEphemeral(true)
                    .queue();
        }
    }

    @Override
    public void onSlashCommandInteraction(@Nonnull SlashCommandInteractionEvent event) {
        if(event.getName().equals("swcreatebutton")) {
            _logger.info("Received request to create button");

            if(!(event.getOption("channel").getAsChannel().getType().equals(ChannelType.TEXT))) {
                event.reply("Uh-Oh, you need to specify a text-channel!").queue();
                return;
            }

            TextChannel channel = event.getOption("channel").getAsChannel().asTextChannel();
            var eb = new EmbedBuilder();
            var embed = eb.setTitle("Get Whitelisted")
                    .setAuthor("Whitelisting Service")
                    .setDescription("Enter your whitelist code here and get whitelisted")
                    .build();
            var msg = MessageCreateData.fromEmbeds(embed);

            event.reply("Creating the whitelist embed now").setEphemeral(true).queue();
            channel.sendMessage(msg).addActionRow(Button.primary("get-whitelisted", "Whitelist me!")).queue();
        }
    }
}
