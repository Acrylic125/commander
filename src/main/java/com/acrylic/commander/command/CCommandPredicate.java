package com.acrylic.commander.command;

import com.acrylic.commander.functional.ExecutedCommandPredicate;
import org.bukkit.command.CommandSender;

public interface CCommandPredicate<S extends CommandSender> {

    ExecutedCommandPredicate<S> getPredicate();

    void onFail(ExecutedCommand<S> executedCommand);

}
