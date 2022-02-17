package com.acrylic.commander.command;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

public class ExecutedCommand<S extends CommandSender> {

    private final S sender;
    private final Command command;
    private final String label;
    private final String[] args;

    public ExecutedCommand(S sender, Command command, String label, String[] args) {
        this.sender = sender;
        this.command = command;
        this.label = label;
        this.args = args;
    }

    public S getSender() {
        return sender;
    }

    public Command getCommand() {
        return command;
    }

    public String getLabel() {
        return label;
    }

    public String[] getArgs() {
        return args;
    }
}
