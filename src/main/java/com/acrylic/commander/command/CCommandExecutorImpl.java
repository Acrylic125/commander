package com.acrylic.commander.command;

import com.acrylic.commander.functional.ExecutedCommandConsumer;
import com.acrylic.commander.functional.ObjectToObject;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.NotNull;

import java.util.*;

public final class CCommandExecutorImpl<S extends CommandSender>
        implements CCommandExecutor {

    private final ObjectToObject<CommandSender, Optional<S>> senderExtractor;
    private Collection<CCommandExecutor> arguments = new Stack<>();
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

    private boolean tryArgument(ExecutedCommand<CommandSender> executedCommand, String argument) {
        String comparableArgument = CCommandExecutor.toComparableCommand(argument);
        for (CCommandExecutor argumentExecutor : this.arguments) {
            if (comparableArgument.equals(CCommandExecutor.toComparableCommand(argumentExecutor.getLabel()))) {
                argumentExecutor.run(executedCommand);
                return true;
            }
        }
        return false;
    }

    @Override
    public void run(ExecutedCommand<CommandSender> executedCommand) {
        Optional<S> extractedSenderOptional = senderExtractor.from(executedCommand.getSender());
        if (!extractedSenderOptional.isPresent())
            return;
        S extractedSender = extractedSenderOptional.get();

        ExecutedCommand<S> wrappedExecutedCommand = new ExecutedCommand<>(extractedSender, executedCommand.getCommand(), label, executedCommand.getArgs());
        if (!this.testPredicates(wrappedExecutedCommand))
            return;

        if (this.tryArgument(executedCommand))
            return;

        if (this.handler != null)
            this.handler.accept(wrappedExecutedCommand);
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {

    }

    public void setHandler(ExecutedCommandConsumer<S> handler) {
        this.handler = handler;
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

    public @NotNull Collection<CCommandPredicate<S>> getPredicates() {
        return this.predicates;
    }

    public void setPredicates(Collection<CCommandPredicate<S>> predicates) {
        this.predicates = predicates;
    }

    public Collection<CCommandExecutor> getArguments() {
        return arguments;
    }

    public void setArguments(Collection<CCommandExecutor> arguments) {
        this.arguments = arguments;
    }
}
