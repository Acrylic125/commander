package com.acrylic.commander.functional;

import com.acrylic.commander.executed.ExecutedCommand;
import org.bukkit.command.CommandSender;

@FunctionalInterface
public interface ExecutedCommandConsumer<S extends CommandSender> {

    void accept(ExecutedCommand<S> executedCommand);

}
