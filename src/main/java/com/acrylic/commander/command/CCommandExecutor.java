package com.acrylic.commander.command;

import org.bukkit.command.CommandExecutor;
import org.jetbrains.annotations.NotNull;

import java.util.Locale;

public interface CCommandExecutor extends CommandExecutor {

    @NotNull
    String getLabel();

    @NotNull
    String[] getAliases();

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
