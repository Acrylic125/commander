package com.acrylic.commander.predicates;

import com.acrylic.commander.command.ExecutedCommand;
import com.acrylic.commander.functional.ExecutedCommandPredicate;
import org.bukkit.command.CommandSender;

public interface CommandPredicate<T extends CommandSender> {

    boolean test(ExecutedCommand<T> executedCommand);

    void onFail(ExecutedCommand<T> executedCommand);

}
