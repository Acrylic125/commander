package com.acrylic.commander.handler;

import com.acrylic.commander.executed.ExecutedCommand;
import com.acrylic.commander.functional.ExecutedCommandConsumer;
import com.acrylic.commander.predicates.CommandPredicate;
import org.bukkit.command.CommandSender;

import java.util.Collection;
import java.util.Optional;
import java.util.Stack;

public final class CommandHandlerImpl<T extends CommandSender>
        implements CommandHandler<T> {

    private Collection<CommandPredicate<T>> predicates = new Stack<>();
    private ExecutedCommandConsumer<T> handler;

    public Collection<CommandPredicate<T>> getPredicates() {
        return predicates;
    }

    public void setPredicates(Collection<CommandPredicate<T>> predicates) {
        this.predicates = predicates;
    }

    public ExecutedCommandConsumer<T> getHandler() {
        return handler;
    }

    public void setHandler(ExecutedCommandConsumer<T> handler) {
        this.handler = handler;
    }

    @Override
    public void handle(ExecutedCommand<T> executedCommand) {
        Optional<CommandPredicate<T>> optionalPredicate = CommandPredicate.findPredicates(this.predicates, executedCommand);
        if (optionalPredicate.isPresent()) {
            optionalPredicate.get().onFail(executedCommand);
            return;
        }
        if (this.handler != null)
            this.handler.accept(executedCommand);
    }
}
