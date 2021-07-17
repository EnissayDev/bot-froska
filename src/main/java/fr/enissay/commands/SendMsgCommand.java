package fr.enissay.commands;

import fr.enissay.utils.commands.BotCommand;
import fr.enissay.utils.commands.BotCommandInfo;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.entities.*;

@BotCommandInfo(getModuleName = "sendmsg", ID = 3, getPermission = Permission.ADMINISTRATOR)
public class SendMsgCommand implements BotCommand {

    static void sendMessage(User user, String content) {
        user.openPrivateChannel().queue(channel -> { // this is a lambda expression
            // the channel is the successful response
            channel.sendMessage(content.replace("%mem", user.getAsMention())).queue();
        });
    }

    @Override
    public void onCommand(String[] args, User user, MessageChannel channel, Message userMessage, Guild guild) {
        String[] cp = new String[args.length - 1];
        System.arraycopy(args, 1, cp, 0, cp.length);
        String run = String.join(" ", cp);
        String finalRun = run;
        guild.getMemberCache().stream()
                .filter(theUser -> !theUser.getUser().isBot())
                .map(Member::getUser)
                .forEach(users -> sendMessage(users, finalRun));
        System.out.println("done output: " + run);

        /*guild.getMemberCache().stream().filter(theUser -> !theUser.getUser().getName().equalsIgnoreCase("Froska BOT")).map(Member::getUser).forEach(users -> {
            System.out.println("found user : " + users.getName());
            users.openPrivateChannel().queue(channel1 -> {
                String run = "";
                for (int i = 1; i < args.length; i++){
                    run += args[i];
                }
                channel1.sendMessage(run).queue();
                System.out.println("done output: " + run);
            });
        });*/
    }
}