package com.acrylic.commander.predicates;

import com.acrylic.commander.command.ExecutedCommand;
import com.acrylic.commander.functional.ExecutedCommandConsumer;
import org.bukkit.command.CommandSender;

public abstract class CommandPermissionPredicate<T extends CommandSender>
        implements CommandPredicate<T> {

    public static <T extends CommandSender> CommandPermissionPredicate<T> create(String permission, String message) {
        return create(permission, (executedCommand -> executedCommand.getSender().sendMessage(message)));
    }

    public static <T extends CommandSender> CommandPermissionPredicate<T> create(String[] permission, String message) {
        return create(permission, (executedCommand -> executedCommand.getSender().sendMessage(message)));
    }

    public static <T extends CommandSender> CommandPermissionPredicate<T> create(String permission, ExecutedCommandConsumer<T> onFail) {
        return create(new String[] {permission}, onFail);
    }

    public static <T extends CommandSender> CommandPermissionPredicate<T> create(String[] permissions, ExecutedCommandConsumer<T> onFail) {
        return new CommandPermissionPredicate<T>(permissions) {
            @Override
            public void onFail(ExecutedCommand<T> executedCommand) {
                onFail.accept(executedCommand);
            }
        };
    }

    private final String[] permissions;

    public CommandPermissionPredicate(String[] permissions) {
        this.permissions = permissions;
    }

    @Override
    public boolean test(ExecutedCommand<T> executedCommand) {
        final T sender = executedCommand.getSender();

        for (String permission : this.permissions) {
            if (sender.hasPermission(permission))
                return true;
        }
        return false;
    }
}
