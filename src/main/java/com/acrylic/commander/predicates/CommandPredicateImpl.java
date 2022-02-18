package com.acrylic.commander.predicates;

import com.acrylic.commander.executed.ExecutedCommand;
import com.acrylic.commander.functional.ExecutedCommandConsumer;
import com.acrylic.commander.functional.ExecutedCommandPredicate;
import org.bukkit.command.CommandSender;

public final class CommandPredicateImpl<T extends CommandSender>
        implements CommandPredicate<T> {

    public static <T extends CommandSender> CommandPredicateImpl<T> create(
            ExecutedCommandPredicate<T> predicate, ExecutedCommandConsumer<T> onFail) {
        return new CommandPredicateImpl<>(predicate, onFail);
    }

    private final ExecutedCommandPredicate<T> predicate;
    private final ExecutedCommandConsumer<T> onFail;

    CommandPredicateImpl(ExecutedCommandPredicate<T> predicate, ExecutedCommandConsumer<T> onFail) {
        this.predicate = predicate;
        this.onFail = onFail;
    }

    @Override
    public boolean test(ExecutedCommand<T> executedCommand) {
        return this.predicate.test(executedCommand);
    }

    @Override
    public void onFail(ExecutedCommand<T> executedCommand) {
        this.onFail.accept(executedCommand);
    }
}
