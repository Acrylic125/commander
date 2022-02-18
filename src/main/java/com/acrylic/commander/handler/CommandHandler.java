package com.acrylic.commander.handler;

import com.acrylic.commander.command.ExecutedCommand;
import com.acrylic.commander.functional.ExecutedCommandConsumer;
import com.acrylic.commander.predicates.CommandPredicate;
import org.bukkit.command.CommandSender;

public interface CommandHandler<T extends CommandSender> {

    void handle(ExecutedCommand<T> executedCommand);

}
