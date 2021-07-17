package fr.enissay.utils.commands;

import fr.enissay.utils.embed.EmbedUtils;
import net.dv8tion.jda.api.entities.ChannelType;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

import java.awt.*;
import java.util.concurrent.TimeUnit;

public class BotCommandListener extends ListenerAdapter {

    @Override
    public void onMessageReceived(MessageReceivedEvent event)
    {
        final BotCommandManager manager = new BotCommandManager();

        manager.getCommands().stream()
                .filter(bot -> event.getMessage().getContentRaw().equalsIgnoreCase(manager.getCommandAnnotationInfo(bot).getPrefix() + manager.getCommandAnnotationInfo(bot).getModuleName()) || event.getMessage().getContentRaw().split(" ")[0].equalsIgnoreCase(manager.getCommandAnnotationInfo(bot).getPrefix() + manager.getCommandAnnotationInfo(bot).getModuleName()))
                .forEach(bot -> {
                    if (event.getMember().getPermissions().contains(manager.getCommandAnnotationInfo(bot).getPermission()))
                        bot.onCommand(event.getMessage().getContentRaw().split(" "), event.getAuthor(), event.getChannel(), event.getMessage(), event.getGuild());
                    else {
                        event.getMessage().delete();
                        event.getChannel().sendMessage(EmbedUtils.getEmbed("Permission denied.", null, Color.RED, "You do not have the permissions to use this command.").build())
                                .delay(5, TimeUnit.SECONDS) // delete 1 minute later
                                .flatMap(Message::delete).queue();
                    }
                });
    }
}
