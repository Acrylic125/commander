package com.acrylic.commander.handler;

import com.acrylic.commander.executed.ExecutedCommand;
import org.bukkit.command.CommandSender;

public interface CommandHandler<T extends CommandSender> {

    void handle(ExecutedCommand<T> executedCommand);

}
