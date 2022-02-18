package com.acrylic.commander.predicates;

import com.acrylic.commander.executed.ExecutedCommand;
import org.bukkit.command.CommandSender;

import java.util.Collection;
import java.util.Optional;

public interface CommandPredicate<T extends CommandSender> {

    boolean test(ExecutedCommand<T> executedCommand);

    void onFail(ExecutedCommand<T> executedCommand);

    static <T extends CommandSender> Optional<CommandPredicate<T>> findPredicates(Collection<CommandPredicate<T>> predicates, ExecutedCommand<T> executedCommand) {
        for (CommandPredicate<T> predicate : predicates) {
            if (predicate.test(executedCommand)) {
                return Optional.of(predicate);
            }
        }
        return Optional.empty();
    }

}
