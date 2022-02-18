package com.acrylic.commander.functional;

import com.acrylic.commander.executed.ExecutedCommand;
import org.bukkit.command.CommandSender;

@FunctionalInterface
public interface ExecutedCommandPredicate<S extends CommandSender> {

    boolean test(ExecutedCommand<S> executedCommand);

}
