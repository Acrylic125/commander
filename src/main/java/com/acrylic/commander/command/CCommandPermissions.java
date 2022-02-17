package com.acrylic.commander.command;

import com.acrylic.commander.functional.ExecutedCommandConsumer;
import com.acrylic.commander.functional.ExecutedCommandPredicate;
import org.bukkit.command.CommandSender;

public abstract class CCommandPermissions<S extends CommandSender>
        implements CCommandPredicate<S> {

    public static <S extends CommandSender> CCommandPermissions<S> create(String permission, String message) {
        return create(permission, (executedCommand -> executedCommand.getSender().sendMessage(message)));
    }

    public static <S extends CommandSender> CCommandPermissions<S> create(String[] permission, String message) {
        return create(permission, (executedCommand -> executedCommand.getSender().sendMessage(message)));
    }

    public static <S extends CommandSender> CCommandPermissions<S> create(String permission, ExecutedCommandConsumer<S> onFail) {
        return create(new String[] {permission}, onFail);
    }

    public static <S extends CommandSender> CCommandPermissions<S> create(String[] permissions, ExecutedCommandConsumer<S> onFail) {
        return new CCommandPermissions<S>(permissions) {
            @Override
            public void onFail(ExecutedCommand<S> executedCommand) {
                onFail.apply(executedCommand);
            }
        };
    }

    private final String[] permissions;
    private final ExecutedCommandPredicate<S> predicate;

    public CCommandPermissions(String[] permissions) {
        this.permissions = permissions;
        this.predicate = (executedCommand) -> {
            final CommandSender sender = executedCommand.getSender();

            for (String permission : this.permissions) {
                if (sender.hasPermission(permission))
                    return true;
            }
            return false;
        };
    }

    @Override
    public final ExecutedCommandPredicate<S> getPredicate() {
        return this.predicate;
    }

}
