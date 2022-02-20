package com.acrylic.commander.executors;

import com.acrylic.commander.executed.ExecutedCommand;
import com.acrylic.commander.handler.CommandHandler;
import com.acrylic.commander.predicates.CommandPermissionsPredicate;
import com.acrylic.commander.predicates.CommandPredicate;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.*;

public final class CommanderCommandExecutor
        implements CommandExecutor {

    public static Builder builder(@NotNull String label) {
        return new Builder(label);
    }

    public static class Builder {
        private final Collection<CommanderCommandExecutor> arguments = new Stack<>();
        private final Collection<CommandPredicate<CommandSender>> prePredicates = new Stack<>();
        private final Collection<String> aliases = new Stack<>();
        private final String label;
        private CommandHandler<CommandSender> handler;

        Builder(String label) {
            this.label = label;
        }

        public Builder arg(@NotNull CommanderCommandExecutor arg) {
            this.arguments.add(arg);
            return this;
        }

        public Builder alias(@NotNull String alias) {
            this.aliases.add(alias);
            return this;
        }

        public Builder aliases(@NotNull String... aliases) {
            Collections.addAll(this.aliases, aliases);
            return this;
        }

        public Builder permission(CommandPermissionsPredicate<CommandSender> permissionsPredicate) {
            return this.prefilter(permissionsPredicate);
        }

        public Builder prefilter(@NotNull CommandPredicate<CommandSender> filter) {
            this.prePredicates.add(filter);
            return this;
        }

        public Builder handle(@Nullable CommandHandler<CommandSender> handler) {
            this.handler = handler;
            return this;
        }

        public CommanderCommandExecutor build() {
            final CommanderCommandExecutor executor = new CommanderCommandExecutor(this.label);
            executor.setAliases(this.aliases.toArray(new String[0]));
            executor.setArguments(this.arguments);
            executor.setPrePredicates(this.prePredicates);
            executor.setHandler(this.handler);
            return executor;
        }
    }

    // The command arguments.
    private Collection<CommanderCommandExecutor> arguments = null;
    // Pre predicates are filters that check BEFORE anything. This includes argument checks.
    private Collection<CommandPredicate<CommandSender>> prePredicates = null;
    // Aliases are alternative names used for this command.
    private String[] aliases = new String[0];
    // The main identifier for this command.
    private String label;
    // What should be executed when this command successfully executes.
    private CommandHandler<CommandSender> handler;

    CommanderCommandExecutor(String label) {
        this.label = label;
    }

    @Nullable
    public Collection<CommanderCommandExecutor> getArguments() {
        return arguments;
    }

    public void setArguments(@Nullable Collection<CommanderCommandExecutor> arguments) {
        this.arguments = arguments;
    }

    @Nullable
    public Collection<CommandPredicate<CommandSender>> getPrePredicates() {
        return prePredicates;
    }

    public void setPrePredicates(@Nullable Collection<CommandPredicate<CommandSender>> prePredicates) {
        this.prePredicates = prePredicates;
    }

    public @NotNull String getLabel() {
        return this.label;
    }

    public void setLabel(@NotNull String label) {
        this.label = label;
    }

    public @NotNull String[] getAliases() {
        return this.aliases;
    }

    public void setAliases(@NotNull String[] aliases) {
        this.aliases = aliases;
    }

    @Nullable
    public CommandHandler<CommandSender> getHandler() {
        return handler;
    }

    public void setHandler(@Nullable CommandHandler<CommandSender> handler) {
        this.handler = handler;
    }

    private Optional<CommanderCommandExecutor> findArgument(String argument) {
        if (this.arguments != null) {
            for (CommanderCommandExecutor commanderCommandExecutor : this.arguments) {
                if (commanderCommandExecutor.isStringThisCommand(argument))
                    return Optional.of(commanderCommandExecutor);
            }
        }
        return Optional.empty();
    }

    public void runCommand(ExecutedCommand<CommandSender> executedCommand) {
        // Pre checks.
        if (this.prePredicates != null) {
            Optional<CommandPredicate<CommandSender>> predicateOptional = CommandPredicate.findPredicates(this.prePredicates, executedCommand);
            if (predicateOptional.isPresent()) {
                predicateOptional.get().onFail(executedCommand);
                return;
            }
        }

        // Argument check.
        final String argument = executedCommand.getArg(0);
        if (argument != null) {
            final Optional<CommanderCommandExecutor> argumentExecutorOptional = this.findArgument(argument);
            if (argumentExecutorOptional.isPresent()) {
                // Recreate a new ExecutedCommand object. Do not reuse.
                argumentExecutorOptional.get()
                        .runCommand(ExecutedCommand.create(executedCommand, executedCommand.getDepth() + 1));
                return;
            }
        }

        // Run handlers
        if (this.handler != null)
            this.handler.handle(executedCommand);
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        this.runCommand(ExecutedCommand.create(sender, command, label, args));
        return false;
    }

    private boolean isStringThisCommand(@NotNull String command) {
        final String loweredCommand = toComparableCommand(command);
        if (loweredCommand.equals(toComparableCommand(this.getLabel())))
            return true;
        final String[] aliases = this.getAliases();
        for (String alias : aliases) {
            if (loweredCommand.equals(toComparableCommand(alias)))
                return true;
        }
        return false;
    }

    private static String toComparableCommand(String command) {
        return command.toLowerCase(Locale.ROOT);
    }
}
