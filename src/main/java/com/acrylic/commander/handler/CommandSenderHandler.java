package com.acrylic.commander.handler;

import com.acrylic.commander.functional.ExecutedCommandConsumer;
import com.acrylic.commander.predicates.CommandPredicate;
import org.bukkit.command.CommandSender;

public interface CommandSenderHandler<S extends CommandSender> {

    ExecutedCommandConsumer<S> getHandler();

    CommandPredicate<S> getPredicates();

}
