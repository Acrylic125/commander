package com.acrylic.commander.predicates;

import com.acrylic.commander.executed.ExecutedCommand;
import com.acrylic.commander.functional.ExecutedCommandConsumer;
import org.bukkit.command.CommandSender;

public abstract class CommandPermissionsPredicate<T extends CommandSender>
        implements CommandPredicate<T> {

    public static <T extends CommandSender> CommandPermissionsPredicate<T> create(String permission, String message) {
        return create(permission, (executedCommand -> executedCommand.getSender().sendMessage(message)));
    }

    public static <T extends CommandSender> CommandPermissionsPredicate<T> create(String[] permission, String message) {
        return create(permission, (executedCommand -> executedCommand.getSender().sendMessage(message)));
    }

    public static <T extends CommandSender> CommandPermissionsPredicate<T> create(String permission, ExecutedCommandConsumer<T> onFail) {
        return create(new String[] {permission}, onFail);
    }

    public static <T extends CommandSender> CommandPermissionsPredicate<T> create(String[] permissions, ExecutedCommandConsumer<T> onFail) {
        return new CommandPermissionsPredicate<T>(permissions) {
            @Override
            public void onFail(ExecutedCommand<T> executedCommand) {
                onFail.accept(executedCommand);
            }
        };
    }

    private final String[] permissions;

    public CommandPermissionsPredicate(String[] permissions) {
        this.permissions = permissions;
    }

    @Override
    public final boolean test(ExecutedCommand<T> executedCommand) {
        final T sender = executedCommand.getSender();

        for (String permission : this.permissions) {
            if (sender.hasPermission(permission))
                return true;
        }
        return false;
    }
}
