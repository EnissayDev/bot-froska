package fr.enissay.utils.commands;

import fr.enissay.Bot;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

public class BotCommandManager {

    /**
     * List of all the Commands
     */
    public static List<BotCommand> commands = new ArrayList<>();

    /**
     *
     * Add animals and check if the command
     * is null or not
     *
     * @param command
     * @return this
     */
    public BotCommandManager addCommand(final BotCommand command) {
        if (command != null
                && !(commands.contains(command))) {

            commands.add(command);
            return this;
        }
        return this;
    }

    /**
     *
     * Add multiple animals in only
     * one function
     *
     * @param commands
     * @return this
     */
    public BotCommandManager addCommands(final BotCommand... commands) {
        for (BotCommand command : commands)
            addCommand(command);
        return this;
    }

    /**
     *
     * Get the Command informations with index of a List
     *
     * @param index
     * @return Animal
     */
    public BotCommand getCommandInformation(final int index) {
        return getCommands().get(index);
    }

    /**
     *
     * To get the annotation {@link BotCommandInfo} of an
     * impletemented class with its informations by an Index of a List
     *
     * @param index
     * @return info
     */
    public BotCommandInfo getCommandAnnotationInfo(final int index) {

        BotCommandInfo info = null;
        Class<? extends BotCommand> classe = getCommands().get(index).getClass();
        info = (BotCommandInfo) classe.getAnnotation(BotCommandInfo.class);

        if (info != null)
            return info;
        else return null;
    }

    /**
     *
     * To get the annotation {@link BotCommandInfo} of an
     * impletemented class with its informations by an Animal
     *
     * @param command
     * @return info
     */
    public BotCommandInfo getCommandAnnotationInfo(final BotCommand command) {

        BotCommandInfo info = null;
        Class<? extends BotCommand> classe = command.getClass();
        info = (BotCommandInfo) classe.getAnnotation(BotCommandInfo.class);

        if (info != null)
            return info;
        else return null;
    }

    /**
     *
     * @return list of Commands
     */
    public List<BotCommand> getCommands(){
        return commands;
    }
}
