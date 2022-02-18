package com.acrylic.commander.executors;

import com.acrylic.commander.executed.ExecutedCommand;
import com.acrylic.commander.predicates.CommandPredicate;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Collection;
import java.util.Optional;
import java.util.Stack;

public final class CommanderCommandExecutorImpl implements CommanderCommandExecutor {

    // The command arguments.
    private Collection<CommanderCommandExecutor> arguments = new Stack<>();
    // Pre predicates are filters that check BEFORE anything. This includes argument checks.
    private Collection<CommandPredicate<CommandSender>> prePredicates = new Stack<>();
    // Aliases are alternative names used for this command.
    private String[] aliases;
    // The main identifier for this command.
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

    public Collection<CommandPredicate<CommandSender>> getPrePredicates() {
        return prePredicates;
    }

    public void setPrePredicates(Collection<CommandPredicate<CommandSender>> prePredicates) {
        this.prePredicates = prePredicates;
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

    @Nullable
    private CommanderCommandExecutor findArgument(String argument) {
        for (CommanderCommandExecutor commanderCommandExecutor : this.arguments) {
            if (commanderCommandExecutor.isStringThisCommand(argument))
                return commanderCommandExecutor;
        }
        return null;
    }

    @Override
    public void run(ExecutedCommand<CommandSender> executedCommand) {
        // Pre checks.
        Optional<CommandPredicate<CommandSender>> predicateOptional = CommandPredicate.findPredicates(this.prePredicates, executedCommand);
        if (predicateOptional.isPresent()) {
            predicateOptional.get().onFail(executedCommand);
            return;
        }

        // Argument check.
        final String argument = executedCommand.getArg(0);
        if (argument != null) {
            final CommanderCommandExecutor argumentExecutor = this.findArgument(argument);
            if (argumentExecutor != null) {
                // Recreate a new ExecutedCommand object. Do not reuse.
                argumentExecutor.run(ExecutedCommand.create(executedCommand, executedCommand.getOffsetArgument() + 1));
                return;
            }
        }

        // Run handlers

    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        this.run(ExecutedCommand.create(sender, command, label, args));
        return false;
    }
}
