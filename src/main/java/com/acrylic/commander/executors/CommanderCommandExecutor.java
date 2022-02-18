package com.acrylic.commander.executors;

import com.acrylic.commander.executed.ExecutedCommand;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.NotNull;

import java.util.Collections;
import java.util.Locale;

public interface CommanderCommandExecutor
        extends CommandExecutor {

    @NotNull
    String getLabel();

    @NotNull
    String[] getAliases();

    void run(ExecutedCommand<CommandSender> executedCommand);

    default boolean isStringThisCommand(@NotNull String command) {
        final String loweredCommand = toComparableCommand(command);
        if (loweredCommand.equals(toComparableCommand(this.getLabel())))
            return true;
        final String[] aliases = this.getAliases();
        for (String alias : aliases) {
            if (loweredCommand.equals(toComparableCommand(alias)))
                return true;
        }
        return false;
    }

    static String toComparableCommand(String command) {
        return command.toLowerCase(Locale.ROOT);
    }


}
