package fr.enissay.commands;

import fr.enissay.utils.commands.BotCommand;
import fr.enissay.utils.commands.BotCommandInfo;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.MessageChannel;
import net.dv8tion.jda.api.entities.User;

@BotCommandInfo(getModuleName = "test", ID = 1, getPermission = Permission.ADMINISTRATOR)
public class TestCommand implements BotCommand {

    @Override
    public void onCommand(String[] args, User user, MessageChannel channel, Message userMessage, Guild guild) {
        channel.sendMessage("DEBUG: " + user.getAsTag() + " - " + user.getName() + " - " + user.getId()).queue();
    }
}
