package fr.enissay.ticket;

import fr.enissay.Bot;
import fr.enissay.utils.embed.EmbedUtils;
import fr.enissay.utils.messages.ReactionHandler;
import fr.enissay.utils.messages.ReactionListener;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.entities.MessageChannel;
import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.events.message.react.MessageReactionAddEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.dv8tion.jda.api.requests.restaction.MessageAction;
import net.dv8tion.jda.internal.handle.MessageReactionHandler;
import net.dv8tion.jda.internal.requests.Route;

import javax.annotation.Nonnull;
import java.awt.*;
import java.sql.Date;
import java.time.Instant;
import java.util.EnumSet;

public class TicketListener extends ListenerAdapter {

    private void sendMessage(User user, String content) {
        user.openPrivateChannel().queue(channel -> { // this is a lambda expression
            // the channel is the successful response
            channel.sendMessage(content.replace("%mem", user.getAsMention())).queue();
        });
    }

    @Override
    public void onMessageReactionAdd(@Nonnull MessageReactionAddEvent event) {
        final MessageChannel channel = event.getReaction().getChannel();
        final User user = event.getUser();
        final TicketManager ticketManager = new TicketManager();

        if (channel.getName().equalsIgnoreCase("ticket") && !user.isBot() && event.getReaction().getReactionEmote().getName().equalsIgnoreCase("\uD83C\uDF9F")) {
            if ((ticketManager.getTickets(user).size() + 1) > 1) {//added + 1  because of order (shows 0 when it's actually 1)
                sendMessage(user, "Sorry %mem but it seems that you already have a ticket which is `" + "ticket" + "-" + user.getName() + "#" + ticketManager.getTicket(user).getId() + "`");
                event.getReaction().removeReaction(user).queue();
            } else {
                Bot.getJDA().getCategoriesByName("TICKETS", true).forEach(category -> {
                    category.getGuild().createTextChannel("ticket-" + user.getName() + "-" + (ticketManager.getTickets().size() + 1))
                            .setParent(category)
                            .syncPermissionOverrides()
                            .addPermissionOverride(event.getMember(), EnumSet.of(Permission.VIEW_CHANNEL), null)
                            .queue(response -> {
                                response.sendMessage(user.getAsMention()).queue();
                                response.sendMessage(EmbedUtils.getEmbed("Ticket of: " + user.getName(), null, Color.GREEN, "Your ticket has been created.\nTo close it react on \uD83D\uDD12").setThumbnail(user.getAvatarUrl()).build()).queue(message -> {
                                    ReactionListener<String> handler = new ReactionListener<>(user.getIdLong(), message.getId());
                                    //handler.setExpiresIn(TimeUnit.MINUTES, 1);
                                    handler.registerReaction("\uD83D\uDD12",  (ret) -> {
                                        ticketManager.removeTicket(ticketManager.getTicket(user));
                                        event.getReaction().removeReaction(user).queue();
                                        ret.getTextChannel().delete().queue();
                                        /*event.getGuild().getTextChannelsByName("ticket-" + user.getName(), true).forEach(textChannel -> {
                                            textChannel.delete().queue();
                                        });*/
                                    });
                                    new ReactionHandler().addReactionListener(message.getGuild().getIdLong(), message, handler);
                                    //message.addReaction("\uD83D\uDD12").queue();
                                    ticketManager.addTicket(new Ticket(user, Date.from(Instant.now()), message.getIdLong(), ticketManager.getTickets().size() + 1));
                                });
                            });
                });
                event.getReaction().removeReaction(user).queue();
            }
        }else if (!user.isBot() && channel.getName().split("-")[0].equalsIgnoreCase("ticket")){
            if (event.getReaction().getReactionEmote().getName().equalsIgnoreCase("\uD83D\uDD12")){
                final User ticketUser = ticketManager.getCreatorByTicketId(Integer.valueOf(channel.getName().split("-")[2]));
                final Ticket ticket = ticketManager.getTicket(ticketUser);

                event.getReaction().removeReaction(user).queue();
                event.getGuild().getTextChannelsByName("ticket-" +  ticketUser.getName() + "-" + ticket.getId(), true).forEach(textChannel -> {
                    textChannel.sendMessage(EmbedUtils.getEmbed("Closed.", null, Color.YELLOW, "Ticket closed by " + user.getAsMention()).build()).queue();
                    textChannel.putPermissionOverride(event.getMember())
                            .setAllow(Permission.EMPTY_PERMISSIONS)
                            .queue();
                    textChannel.getMemberPermissionOverrides().forEach(perm -> {
                        if (perm.getAllowed().contains(Permission.VIEW_CHANNEL))
                            perm.delete();
                    });
                    //textChannel.getManager().setName(textChannel.getName() + "-closed");

                    sendMessage(ticketUser, "You ticket has been closed by `" + user.getName() + "`");
                    textChannel.sendMessage(EmbedUtils.getEmbed(null, null, Color.RED, "❌ Delete the ticket").build()).queue(response -> {
                        response.addReaction("❌").queue();
                    });
                    //textChannel.delete().queue();
                });

            }else if (event.getReaction().getReactionEmote().getName().equalsIgnoreCase("❌")){
                final User ticketUser = ticketManager.getCreatorByTicketId(Integer.valueOf(channel.getName().split("-")[2]));
                final Ticket ticket = ticketManager.getTicket(ticketUser);

                event.getReaction().removeReaction(user).queue();
                event.getGuild().getTextChannelsByName("ticket-" +  ticketUser.getName() + "-" + ticket.getId(), true).forEach(textChannel -> {
                    textChannel.delete().queue();
                    ticketManager.removeTicket(ticketManager.getTicket(user));
                });
            }
        }
    }
}
