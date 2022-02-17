package com.acrylic.commander.functional;

import com.acrylic.commander.command.ExecutedCommand;
import org.bukkit.command.CommandSender;

@FunctionalInterface
public interface ExecutedCommandConsumer<S extends CommandSender> {

    void apply(ExecutedCommand<S> executedCommand);

}
