package fr.enissay.utils.commands;

import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.MessageChannel;
import net.dv8tion.jda.api.entities.User;

public interface BotCommand {

    void onCommand(final String[] args, final User user, final MessageChannel channel, final Message userMessage, final Guild guild);

}
