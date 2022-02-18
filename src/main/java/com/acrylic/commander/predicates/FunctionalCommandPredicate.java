package com.acrylic.commander.predicates;

import com.acrylic.commander.command.ExecutedCommand;
import com.acrylic.commander.functional.ExecutedCommandConsumer;
import com.acrylic.commander.functional.ExecutedCommandPredicate;
import org.bukkit.command.CommandSender;

public final class FunctionalCommandPredicate<S extends CommandSender> implements CommandPredicate<S> {

    public static <S extends CommandSender> FunctionalCommandPredicate<S> create(
            ExecutedCommandPredicate<S> predicate, ExecutedCommandConsumer<S> onFail) {
        return new FunctionalCommandPredicate<>(predicate, onFail);
    }

    private final ExecutedCommandPredicate<S> predicate;
    private final ExecutedCommandConsumer<S> onFail;

    FunctionalCommandPredicate(ExecutedCommandPredicate<S> predicate, ExecutedCommandConsumer<S> onFail) {
        this.predicate = predicate;
        this.onFail = onFail;
    }

    @Override
    public ExecutedCommandPredicate<S> getPredicate() {
        return this.predicate;
    }

    @Override
    public void onFail(ExecutedCommand<S> executedCommand) {
        this.onFail.accept(executedCommand);
    }
}
