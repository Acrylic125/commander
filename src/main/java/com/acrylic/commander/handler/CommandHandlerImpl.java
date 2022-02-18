package com.acrylic.commander.handler;

import com.acrylic.commander.executed.ExecutedCommand;
import com.acrylic.commander.predicates.CommandPredicate;
import org.bukkit.command.CommandSender;

import java.util.Collection;
import java.util.Stack;

public class CommandHandlerImpl<T extends CommandSender>
        implements CommandHandler<T> {

    private final Collection<CommandPredicate<T>> filters;

    public CommandHandlerImpl() {
        this(new Stack<>());
    }

    public CommandHandlerImpl(Collection<CommandPredicate<T>> filters) {
        this.filters = filters;
    }

    public Collection<CommandPredicate<T>> getFilters() {
        return this.filters;
    }

    @Override
    public void handle(ExecutedCommand<T> executedCommand) {

    }
}
