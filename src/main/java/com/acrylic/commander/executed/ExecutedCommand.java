package com.acrylic.commander.executed;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

import java.util.Arrays;

public final class ExecutedCommand<T extends CommandSender> {

    @SuppressWarnings("all")
    public static <T extends CommandSender> ExecutedCommand<T> create(ExecutedCommand<?> executedCommand) {
        return create(executedCommand, 0);
    }

    @SuppressWarnings("all")
    public static <T extends CommandSender> ExecutedCommand<T> create(ExecutedCommand<?> executedCommand, int offsetArgument) {
        return create((T) executedCommand.sender, executedCommand.command, executedCommand.label, executedCommand.args, offsetArgument);
    }

    public static <T extends CommandSender> ExecutedCommand<T> create(T sender, Command command, String label, String[] args) {
        return create(sender, command, label, args, 0);
    }

    public static <T extends CommandSender> ExecutedCommand<T> create(T sender, Command command, String label, String[] args, int offsetArgument) {
        return new ExecutedCommand<>(sender, command, label, args, offsetArgument);
    }

    private final T sender;
    private final Command command;
    private final String label;
    private final String[] args;
    private final int offsetArgument;

    ExecutedCommand(T sender, Command command, String label, String[] args, int offsetArgument) {
        this.sender = sender;
        this.command = command;
        this.label = label;
        this.args = args;
        this.offsetArgument = offsetArgument;
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
        return Arrays.copyOfRange(this.args, this.offsetArgument, this.args.length - 1);
    }

    public String getArg(int index) {
        return this.getOriginalArg(index + offsetArgument);
    }

    public String[] getOriginalArgs() {
        return Arrays.copyOf(this.args, this.args.length);
    }

    public String getOriginalArg(int index) {
        return (index < 0 || index >= this.args.length) ? null : this.args[index];
    }
}
