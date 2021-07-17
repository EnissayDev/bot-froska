package fr.enissay.ticket;

import net.dv8tion.jda.api.entities.User;

import javax.annotation.Nonnull;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.stream.Collectors;

public class TicketManager {

    private static LinkedList<Ticket> tickets = new LinkedList<>();

    public void addTicket(final @Nonnull Ticket ticket){
        if (!tickets.contains(ticket)) tickets.add(ticket);
    }

    public void removeTicket(final @Nonnull Ticket ticket){
        tickets.removeIf(theThicket -> theThicket == ticket && (theThicket != null && ticket != null));
    }

    public boolean hasATicket(final @Nonnull User user){
        return getTickets().stream()
                .filter(ticket -> ticket.getCreator() == user)
                .collect(Collectors.toList()).size() + 1 > 1;
    }

    public List<Ticket> getTickets(final @Nonnull User user){
        return getTickets().stream()
                .filter(ticket -> ticket.getCreator() == user)
                .collect(Collectors.toList());
    }

    public Ticket getTicket(final @Nonnull User user){
        return getTickets().stream()
                .filter(ticket -> ticket.getCreator() == user)
                .findAny().orElse(null);
    }

    public User getCreatorByTicketId(final @Nonnull int id){
        return getTickets().stream()
                .filter(ticket -> ticket.getId() == id)
                .findAny()
                .orElse(null).getCreator();
    }

    public LinkedList<Ticket> getTickets() {
        return tickets;
    }
}
