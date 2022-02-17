package com.acrylic.commander.command;

import com.acrylic.commander.functional.ExecutedCommandConsumer;
import com.acrylic.commander.functional.ObjectToObject;
import org.bukkit.command.BlockCommandSender;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.*;

public final class CCommandExecutorImpl<S extends CommandSender> implements CCommandExecutor {

    private final Map<String, CCommandExecutor> arguments = new HashMap<>();
    private final ObjectToObject<CommandSender, Optional<S>> senderExtractor;
    private String label;
    private Collection<CCommandPredicate<S>> predicates = new Stack<>();
    private ExecutedCommandConsumer<S> handler;
    private String[] aliases;

    CCommandExecutorImpl(ObjectToObject<CommandSender, Optional<S>> senderExtractor, String label, String[] aliases) {
        this.senderExtractor = senderExtractor;
        this.label = label;
        this.aliases = aliases;
    }

    private boolean testPredicates(ExecutedCommand<S> executedCommand) {
        for (CCommandPredicate<S> predicate : predicates) {
            if (predicate.getPredicate().test(executedCommand)) {
                predicate.onFail(executedCommand);
                return false;
            }
        }
        return true;
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        Optional<S> extractedSenderOptional = senderExtractor.from(sender);
        if (!extractedSenderOptional.isPresent())
            return false;
        S extractedSender = extractedSenderOptional.get();

        ExecutedCommand<S> executedCommand = new ExecutedCommand<>(extractedSender, command, label, args);
        if (this.testPredicates(executedCommand))
            return false;

        this.handler.apply(executedCommand);
        return false;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public void setPredicates(Collection<CCommandPredicate<S>> predicates) {
        this.predicates = predicates;
    }

    public void setHandler(ExecutedCommandConsumer<S> handler) {
        this.handler = handler;
    }

    public void setAliases(String[] aliases) {
        this.aliases = aliases;
    }

    @Override
    public @NotNull String getLabel() {
        return this.label;
    }

    @Override
    public @NotNull String[] getAliases() {
        return this.aliases;
    }

    public void addPermissions(@NotNull CCommandPermissions<S> permissions) {
        this.addPredicate(permissions);
    }

    public void addPredicate(@NotNull CCommandPredicate<S> permissions) {
        this.predicates.add(permissions);
    }

    public @NotNull Collection<CCommandPredicate<S>> getPredicates() {
        return this.predicates;
    }

    public @NotNull Map<String, CCommandExecutor> getArguments() {
        return this.arguments;
    }
}
