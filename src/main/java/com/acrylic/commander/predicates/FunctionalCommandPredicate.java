package com.acrylic.commander.predicates;

import com.acrylic.commander.command.ExecutedCommand;
import com.acrylic.commander.functional.ExecutedCommandConsumer;
import com.acrylic.commander.functional.ExecutedCommandPredicate;
import org.bukkit.command.CommandSender;

public final class FunctionalCommandPredicate<T extends CommandSender>
        implements CommandPredicate<T> {

    public static <T extends CommandSender> FunctionalCommandPredicate<T> create(
            ExecutedCommandPredicate<T> predicate, ExecutedCommandConsumer<T> onFail) {
        return new FunctionalCommandPredicate<>(predicate, onFail);
    }

    private final ExecutedCommandPredicate<T> predicate;
    private final ExecutedCommandConsumer<T> onFail;

    FunctionalCommandPredicate(ExecutedCommandPredicate<T> predicate, ExecutedCommandConsumer<T> onFail) {
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
