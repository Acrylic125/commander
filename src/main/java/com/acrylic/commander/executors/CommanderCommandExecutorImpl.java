package com.acrylic.commander.executors;

import com.acrylic.commander.executed.ExecutedCommand;
import com.acrylic.commander.predicates.CommandPredicate;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.NotNull;

import java.util.Collection;
import java.util.Stack;

public class CommanderCommandExecutorImpl implements CommanderCommandExecutor {

    private Collection<CommanderCommandExecutor> arguments = new Stack<>();
    private Collection<CommandPredicate<CommandSender>> predicates = new Stack<>();
    private String[] aliases;
    private String label;

    public CommanderCommandExecutorImpl(String label) {
        this.label = label;
    }

    public Collection<CommanderCommandExecutor> getArguments() {
        return arguments;
    }

    public void setArguments(Collection<CommanderCommandExecutor> arguments) {
        this.arguments = arguments;
    }

    public Collection<CommandPredicate<CommandSender>> getPredicates() {
        return predicates;
    }

    public void setPredicates(Collection<CommandPredicate<CommandSender>> predicates) {
        this.predicates = predicates;
    }

    @Override
    public @NotNull String getLabel() {
        return this.label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    @Override
    public @NotNull String[] getAliases() {
        return this.aliases;
    }

    public void setAliases(String[] aliases) {
        this.aliases = aliases;
    }

    @Override
    public void run(ExecutedCommand<CommandSender> executedCommand) {

    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        this.run(ExecutedCommand.create(sender, command, label, args));
        return false;
    }
}
