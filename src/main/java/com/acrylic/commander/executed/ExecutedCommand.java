package com.acrylic.commander.executed;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.Nullable;

import java.util.Arrays;

public final class ExecutedCommand<T extends CommandSender> {

    public static <T extends CommandSender, S extends CommandSender> ExecutedCommand<S> create(ExecutedCommand<T> executedCommand, S commandSender) {
        return create(executedCommand, commandSender, 0);
    }

    public static <T extends CommandSender, S extends CommandSender> ExecutedCommand<S> create(ExecutedCommand<T> executedCommand, S commandSender, int offsetArgument) {
        return create(commandSender, executedCommand.command, executedCommand.label, executedCommand.args, offsetArgument);
    }

    public static <T extends CommandSender> ExecutedCommand<T> create(ExecutedCommand<T> executedCommand) {
        return create(executedCommand, 0);
    }

    public static <T extends CommandSender> ExecutedCommand<T> create(ExecutedCommand<T> executedCommand, int offsetArgument) {
        return create(executedCommand.sender, executedCommand.command, executedCommand.label, executedCommand.args, offsetArgument);
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
    private final int depth;

    ExecutedCommand(T sender, Command command, String label, String[] args, int depth) {
        this.sender = sender;
        this.command = command;
        this.label = label;
        this.args = args;
        this.depth = depth;
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
        return Arrays.copyOfRange(this.args, this.depth, this.args.length - 1);
    }

    @Nullable
    public String getArg(int index) {
        return this.getOriginalArg(index + depth);
    }

    public int sizeOfArgs() {
        return this.args.length - this.depth;
    }

    public String[] getOriginalArgs() {
        return Arrays.copyOf(this.args, this.args.length);
    }

    @Nullable
    public String getOriginalArg(int index) {
        return (index < 0 || index >= this.args.length) ? null : this.args[index];
    }

    public int sizeOfOriginalArgs() {
        return this.args.length;
    }

    public int getDepth() {
        return depth;
    }
}
