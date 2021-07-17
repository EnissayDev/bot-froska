package fr.enissay.commands;

import fr.enissay.Bot;
import fr.enissay.utils.commands.BotCommand;
import fr.enissay.utils.commands.BotCommandInfo;
import fr.enissay.utils.embed.EmbedUtils;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.MessageChannel;
import net.dv8tion.jda.api.entities.User;

import java.awt.*;
import java.util.concurrent.TimeUnit;

@BotCommandInfo(getModuleName = "managemsgs", ID = 2, getPermission = Permission.ADMINISTRATOR)
public class EnableMessagesCommand implements BotCommand {

    @Override
    public void onCommand(String[] args, User user, MessageChannel channel, Message userMessage, Guild guild) {
        if (Bot.shouldSendMessages) {
            userMessage.delete();
            channel.sendMessage(EmbedUtils.getEmbed("Manage Auto Messages", null, Color.RED, "Auto messages are now disabled.").build())
                    .delay(5, TimeUnit.SECONDS) // delete 1 minute later
                    .flatMap(Message::delete).queue();
            Bot.shouldSendMessages = false;
        } else {
            userMessage.delete();
            channel.sendMessage(EmbedUtils.getEmbed("Manage Auto Messages", null, Color.GREEN, "Auto messages are now enabled.").build())
                    .delay(5, TimeUnit.SECONDS) // delete 1 minute later
                    .flatMap(Message::delete).queue();
            Bot.shouldSendMessages = true;

        }
    }
}