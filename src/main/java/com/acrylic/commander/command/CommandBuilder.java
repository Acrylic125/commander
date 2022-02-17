package com.acrylic.commander.command;

import com.acrylic.commander.functional.ExecutedCommandConsumer;
import com.acrylic.commander.functional.ObjectToObject;
import org.bukkit.command.BlockCommandSender;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.*;

public final class CommandBuilder<S extends CommandSender> {

    private static final ObjectToObject<CommandSender, Optional<CommandSender>> COMMAND_SENDER =
            Optional::of;
    private static final ObjectToObject<CommandSender, Optional<BlockCommandSender>> BLOCK_SENDER =
            (commandSender) -> (commandSender instanceof BlockCommandSender) ? Optional.of((BlockCommandSender) commandSender) : Optional.empty();
    private static final ObjectToObject<CommandSender, Optional<ConsoleCommandSender>> CONSOLE_SENDER =
            (commandSender) -> (commandSender instanceof ConsoleCommandSender) ? Optional.of((ConsoleCommandSender) commandSender) : Optional.empty();
    private static final ObjectToObject<CommandSender, Optional<Player>> PLAYER_SENDER =
            (commandSender) -> (commandSender instanceof Player) ? Optional.of((Player) commandSender) : Optional.empty();

    public static CommandBuilder<CommandSender> builder(@NotNull String label) {
        return new CommandBuilder<>(COMMAND_SENDER, label);
    }

    public static CommandBuilder<BlockCommandSender> blockSenderBuilder(@NotNull String label) {
        return new CommandBuilder<>(BLOCK_SENDER, label);
    }

    public static CommandBuilder<ConsoleCommandSender> consoleSenderBuilder(@NotNull String label) {
        return new CommandBuilder<>(CONSOLE_SENDER, label);
    }

    public static CommandBuilder<Player> playerSenderBuilder(@NotNull String label) {
        return new CommandBuilder<>(PLAYER_SENDER, label);
    }

    private final Map<String, CCommandExecutor> arguments = new HashMap<>();
    private final ObjectToObject<CommandSender, Optional<S>> senderExtractor;
    private final Collection<String> aliases = new Stack<>();
    private final String label;
    private final Collection<CCommandPredicate<S>> predicates = new Stack<>();
    private ExecutedCommandConsumer<S> handler;

    CommandBuilder(ObjectToObject<CommandSender, Optional<S>> senderExtractor, String label) {
        this.senderExtractor = senderExtractor;
        this.label = label;
    }

    public CommandBuilder<S> alias(String alias) {
        this.aliases.add(alias);
        return this;
    }

    public CommandBuilder<S> aliases(String... aliases) {
        this.aliases.addAll(Arrays.asList(aliases));
        return this;
    }

    public CommandBuilder<S> filter(CCommandPredicate<S> filter) {
        this.predicates.add(filter);
        return this;
    }

    public CommandBuilder<S> permission(CCommandPermissions<S> permissions) {
        this.predicates.add(permissions);
        return this;
    }

    public CommandBuilder<S> handle(ExecutedCommandConsumer<S> handler) {
        this.handler = handler;
        return this;
    }

    public CCommandExecutorImpl<S> build() {
        final CCommandExecutorImpl<S> commandExecutor = new CCommandExecutorImpl<>(this.senderExtractor, this.label, this.aliases.toArray(new String[0]));
        commandExecutor.setPredicates(this.predicates);
        commandExecutor.setHandler(this.handler);
        return commandExecutor;
    }

}
