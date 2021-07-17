package fr.enissay.ticket;

import net.dv8tion.jda.api.entities.User;

import java.util.Date;

public class Ticket {

    private User creator;
    private Date date;
    private long channelId;
    private int id;

    public Ticket(User creator, Date date, long channelId, int id) {
        this.creator = creator;
        this.date = date;
        this.channelId = channelId;
        this.id = id;
    }

    public User getCreator() {
        return creator;
    }

    public void setCreator(User creator) {
        this.creator = creator;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public long getChannelId() {
        return channelId;
    }

    public void setChannelId(int channelId) {
        this.channelId = channelId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
