package fr.enissay;

import fr.enissay.commands.EnableMessagesCommand;
import fr.enissay.commands.SendMsgCommand;
import fr.enissay.commands.TestCommand;
import fr.enissay.commands.TicketMessageCommand;
import fr.enissay.listeners.WelcomeListener;
import fr.enissay.ticket.TicketListener;
import fr.enissay.utils.commands.BotCommandListener;
import fr.enissay.utils.commands.BotCommandManager;
import fr.enissay.utils.embed.EmbedUtils;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.entities.Activity;
import net.dv8tion.jda.api.entities.MessageChannel;
import net.dv8tion.jda.api.entities.TextChannel;
import net.dv8tion.jda.api.requests.GatewayIntent;
import net.dv8tion.jda.api.utils.Compression;
import net.dv8tion.jda.api.utils.MemberCachePolicy;
import net.dv8tion.jda.api.utils.cache.CacheFlag;

import javax.security.auth.login.LoginException;
import java.awt.*;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;

public class Bot {

    private static LinkedList<EmbedBuilder> AUTO_MESSAGES = new LinkedList<>();
    public static boolean shouldSendMessages = true;
    private static JDA jda = null;

    public static void main(String[] args) {
        //Part of the bot
        //final JDABuilder builder = JDABuilder.createLight("ODY0MjgwOTU2OTA2NTA0MjQy.YOzKXQ.esgCaImvCl0dPlQKvsrTH7Uyu5Q", GatewayIntent.GUILD_MESSAGES, GatewayIntent.GUILD_MEMBERS);
        final JDABuilder builder = JDABuilder.createDefault("ODY0MjgwOTU2OTA2NTA0MjQy.YOzKXQ.esgCaImvCl0dPlQKvsrTH7Uyu5Q");

        builder.enableIntents(GatewayIntent.GUILD_MEMBERS);
        //builder.disableCache(CacheFlag.MEMBER_OVERRIDES, CacheFlag.VOICE_STATE);
        builder.setBulkDeleteSplittingEnabled(false);
        builder.setCompression(Compression.NONE);
        builder.setMemberCachePolicy(MemberCachePolicy.ALL);

        //Commands registration
        System.out.println("Checking commands...");

        new BotCommandManager().addCommands(new TestCommand()/*, new EnableMessagesCommand()*/, new SendMsgCommand(), new TicketMessageCommand());
        new BotCommandManager().getCommands().forEach(bot -> System.out.println("MODULE: " + new BotCommandManager().getCommandAnnotationInfo(bot).getModuleName()));

        //Listeners
        builder.addEventListeners(new BotCommandListener(), new WelcomeListener(), new TicketListener());

        //Auto messages
        AUTO_MESSAGES.addAll(Arrays.asList(EmbedUtils.getEmbed("This is a message", null, Color.ORANGE, "this is a cool description"),
                EmbedUtils.getEmbed("yep another message it's infinite", null, Color.GREEN, "this is ANOTHER cool description")));

        final Random random = new Random();

        try {
            jda = builder.build();
            jda.awaitReady();
        } catch (LoginException | InterruptedException e) {
            e.printStackTrace();
        }

        while (true){
            try {
                if (shouldSendMessages) {
                    final int delay = 1000 * 60 * 60;
                    final int id = random.nextInt(AUTO_MESSAGES.size());
                    final EmbedBuilder chosenMessage = AUTO_MESSAGES.get(id);

                    List<TextChannel> channels = jda.getTextChannelsByName("general", true);
                    for (TextChannel ch : channels) {
                        ch.sendMessage(chosenMessage.build()).queue();
                        ch.sendMessage("current delay : " + (delay / 1000) + "s");
                    }
                    Thread.sleep(delay);
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public static JDA getJDA(){
        return jda;
    }
}
