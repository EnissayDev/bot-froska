package fr.enissay.utils.embed;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.MessageEmbed;

import java.awt.*;

public class EmbedUtils {

    public static EmbedBuilder getEmbed(final String title, final String url, final Color color, final String description){
        final EmbedBuilder eb = new EmbedBuilder();

        if (title != null) eb.setTitle(title, url);
        if (color != null) eb.setColor(color);
        if (description != null) eb.setDescription(description);

        return eb;
    }

}
