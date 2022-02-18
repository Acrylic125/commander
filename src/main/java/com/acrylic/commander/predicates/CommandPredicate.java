package com.acrylic.commander.predicates;

import com.acrylic.commander.executed.ExecutedCommand;
import org.bukkit.command.CommandSender;

import java.util.Collection;

public interface CommandPredicate<T extends CommandSender> {

    boolean test(ExecutedCommand<T> executedCommand);

    void onFail(ExecutedCommand<T> executedCommand);

    static <T extends CommandSender> boolean runPredicates(Collection<CommandPredicate<T>> predicates, ExecutedCommand<T> executedCommand) {
        for (CommandPredicate<T> predicate : predicates) {
            if (predicate.test(executedCommand)) {
                predicate.onFail(executedCommand);
                return false;
            }
        }
        return true;
    }

}
