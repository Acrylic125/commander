package com.acrylic.commander.executors;

import com.acrylic.commander.command.ExecutedCommand;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.NotNull;

public class CommanderCommandExecutorImpl implements CommanderCommandExecutor {


    @Override
    public @NotNull String getLabel() {
        return null;
    }

    @Override
    public @NotNull String[] getAliases() {
        return new String[0];
    }

    @Override
    public void run(ExecutedCommand<CommandSender> executedCommand) {

    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        return false;
    }
}
