package fr.enissay.listeners;

import fr.enissay.Bot;
import fr.enissay.utils.embed.EmbedUtils;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.TextChannel;
import net.dv8tion.jda.api.events.guild.member.GuildMemberJoinEvent;
import net.dv8tion.jda.api.events.guild.member.GuildMemberLeaveEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

import java.awt.*;
import java.util.List;
import java.util.Random;

public class WelcomeListener extends ListenerAdapter {
    @Override
    public void onGuildMemberJoin(GuildMemberJoinEvent event) {
        String[] messages = {
                "[member] just joined the server.",
                "wow [member] joined us.",
                "yup, [member] joined."
        };
        Random rand = new Random();
        int number = rand.nextInt(messages.length);

        final EmbedBuilder join = EmbedUtils.getEmbed("Join", event.getMember().getUser().getAvatarUrl(), Color.GREEN, messages[number].replace("[member]", event.getMember().getAsMention()));
        //join.setImage(event.getMember().getUser().getAvatarUrl());
        join.setImage(event.getMember().getUser().getAvatarUrl());

        List<TextChannel> channels = Bot.getJDA().getTextChannelsByName("welcome", true);
        for (TextChannel ch : channels)
            ch.sendMessage(join.build()).queue();
    }
    @Override
    public void onGuildMemberLeave(GuildMemberLeaveEvent event) {
        String[] messages = {
                "[member] just left the server.",
                "wow [member] left us.",
                "yup, [member] left."
        };
        Random rand = new Random();
        int number = rand.nextInt(messages.length);

        final EmbedBuilder join = EmbedUtils.getEmbed("Leave", event.getMember().getUser().getAvatarUrl(), Color.RED, messages[number].replace("[member]", event.getMember().getAsMention()));
        //join.setImage(event.getMember().getUser().getAvatarUrl());
        join.setImage(event.getMember().getUser().getAvatarUrl());

        List<TextChannel> channels = Bot.getJDA().getTextChannelsByName("welcome", true);
        for (TextChannel ch : channels)
            ch.sendMessage(join.build()).queue();
    }
}
