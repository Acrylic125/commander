package com.acrylic.commander.executed;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

public final class ExecutedCommandRecord<T extends CommandSender> {

    private final T sender;
    private final Command command;
    private final String label;
    private final String[] args;

    public ExecutedCommandRecord(T sender, Command command, String label, String[] args) {
        this.sender = sender;
        this.command = command;
        this.label = label;
        this.args = args;
    }

    public T getSender() {
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

    public String getArg(int index) {
        return this.args[Math.min(Math.max(index, 0), this.args.length)];
    }

}
