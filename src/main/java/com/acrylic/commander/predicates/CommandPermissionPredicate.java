package com.acrylic.commander.predicates;

import com.acrylic.commander.command.ExecutedCommand;
import com.acrylic.commander.functional.ExecutedCommandConsumer;
import com.acrylic.commander.functional.ExecutedCommandPredicate;
import org.bukkit.command.CommandSender;

public abstract class CommandPermissionPredicate<S extends CommandSender>
        implements CommandPredicate<S> {

    public static <S extends CommandSender> CommandPermissionPredicate<S> create(String permission, String message) {
        return create(permission, (executedCommand -> executedCommand.getSender().sendMessage(message)));
    }

    public static <S extends CommandSender> CommandPermissionPredicate<S> create(String[] permission, String message) {
        return create(permission, (executedCommand -> executedCommand.getSender().sendMessage(message)));
    }

    public static <S extends CommandSender> CommandPermissionPredicate<S> create(String permission, ExecutedCommandConsumer<S> onFail) {
        return create(new String[] {permission}, onFail);
    }

    public static <S extends CommandSender> CommandPermissionPredicate<S> create(String[] permissions, ExecutedCommandConsumer<S> onFail) {
        return new CommandPermissionPredicate<S>(permissions) {
            @Override
            public void onFail(ExecutedCommand<S> executedCommand) {
                onFail.accept(executedCommand);
            }
        };
    }

    private final String[] permissions;
    private final ExecutedCommandPredicate<S> predicate;

    public CommandPermissionPredicate(String[] permissions) {
        this.permissions = permissions;
        this.predicate = (executedCommand) -> {
            final S sender = executedCommand.getSender();

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
