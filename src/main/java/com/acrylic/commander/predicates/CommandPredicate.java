package com.acrylic.commander.predicates;

import com.acrylic.commander.command.ExecutedCommand;
import com.acrylic.commander.functional.ExecutedCommandPredicate;
import org.bukkit.command.CommandSender;

public interface CommandPredicate<S extends CommandSender> {

    ExecutedCommandPredicate<S> getPredicate();

    void onFail(ExecutedCommand<S> executedCommand);

}
