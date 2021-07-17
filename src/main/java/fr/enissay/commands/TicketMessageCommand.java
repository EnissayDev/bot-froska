package fr.enissay.commands;

import fr.enissay.utils.commands.BotCommand;
import fr.enissay.utils.commands.BotCommandInfo;
import fr.enissay.utils.embed.EmbedUtils;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.MessageChannel;
import net.dv8tion.jda.api.entities.User;

import java.awt.*;

@BotCommandInfo(getModuleName = "sendticketmsg", ID = 4, getPermission = Permission.ADMINISTRATOR)
public class TicketMessageCommand implements BotCommand {

    @Override
    public void onCommand(String[] args, User user, MessageChannel channel, Message userMessage, Guild guild) {
        channel.sendMessage(EmbedUtils.getEmbed("Tickets!", null, Color.ORANGE, "React to \uD83C\uDF9F if you want to create a ticket.").build()).queue(rest -> {
            rest.addReaction("\uD83C\uDF9F").queue();
        });
    }
}
