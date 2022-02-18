package com.acrylic.commander.handler;

import com.acrylic.commander.executed.ExecutedCommand;
import com.acrylic.commander.handler.CommandHandler;
import org.bukkit.command.BlockCommandSender;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.Nullable;

public final class MultiSenderCommandHandler
        implements CommandHandler<CommandSender> {

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {
        private CommandHandler<Player> playerCommandHandler;
        private CommandHandler<ConsoleCommandSender> consoleSenderCommandHandler;
        private CommandHandler<BlockCommandSender> blockSenderCommandHandler;
        private CommandHandler<CommandSender> defaultHandler;

        Builder() {}

        public Builder handlePlayer(CommandHandler<Player> handler) {
            this.playerCommandHandler = handler;
            return this;
        }

        public Builder handleConsoleSender(CommandHandler<ConsoleCommandSender> handler) {
            this.consoleSenderCommandHandler = handler;
            return this;
        }

        public Builder handleBlockSender(CommandHandler<BlockCommandSender> handler) {
            this.blockSenderCommandHandler = handler;
            return this;
        }

        public Builder handleDefault(CommandHandler<CommandSender> handler) {
            this.defaultHandler = handler;
            return this;
        }

        public MultiSenderCommandHandler build() {
            final MultiSenderCommandHandler handler = new MultiSenderCommandHandler();
            handler.setPlayerCommandHandler(this.playerCommandHandler);
            handler.setConsoleSenderCommandHandler(this.consoleSenderCommandHandler);
            handler.setBlockSenderCommandHandler(this.blockSenderCommandHandler);
            handler.setDefaultHandler(this.defaultHandler);
            return handler;
        }
    }

    private CommandHandler<Player> playerCommandHandler;
    private CommandHandler<ConsoleCommandSender> consoleSenderCommandHandler;
    private CommandHandler<BlockCommandSender> blockSenderCommandHandler;
    private CommandHandler<CommandSender> defaultHandler;

    @Nullable
    public CommandHandler<Player> getPlayerCommandHandler() {
        return playerCommandHandler;
    }

    public void setPlayerCommandHandler(@Nullable CommandHandler<Player> playerCommandHandler) {
        this.playerCommandHandler = playerCommandHandler;
    }

    @Nullable
    public CommandHandler<ConsoleCommandSender> getConsoleSenderCommandHandler() {
        return consoleSenderCommandHandler;
    }

    public void setConsoleSenderCommandHandler(@Nullable CommandHandler<ConsoleCommandSender> consoleSenderCommandHandler) {
        this.consoleSenderCommandHandler = consoleSenderCommandHandler;
    }

    @Nullable
    public CommandHandler<BlockCommandSender> getBlockSenderCommandHandler() {
        return blockSenderCommandHandler;
    }

    public void setBlockSenderCommandHandler(@Nullable CommandHandler<BlockCommandSender> blockSenderCommandHandler) {
        this.blockSenderCommandHandler = blockSenderCommandHandler;
    }

    @Nullable
    public CommandHandler<CommandSender> getDefaultHandler() {
        return defaultHandler;
    }

    public void setDefaultHandler(@Nullable CommandHandler<CommandSender> defaultHandler) {
        this.defaultHandler = defaultHandler;
    }

    @Override
    public void handle(ExecutedCommand<CommandSender> executedCommand) {
        CommandSender sender = executedCommand.getSender();
        if (this.playerCommandHandler != null && sender instanceof Player) {
            this.playerCommandHandler.handle(ExecutedCommand.create(executedCommand, (Player) sender));
            return;
        }
        if (this.consoleSenderCommandHandler != null && sender instanceof ConsoleCommandSender) {
            this.consoleSenderCommandHandler.handle(ExecutedCommand.create(executedCommand, (ConsoleCommandSender) sender));
            return;
        }
        if (this.blockSenderCommandHandler != null && sender instanceof BlockCommandSender) {
            this.blockSenderCommandHandler.handle(ExecutedCommand.create(executedCommand, (BlockCommandSender) sender));
            return;
        }
        if (this.defaultHandler != null)
            this.defaultHandler.handle(ExecutedCommand.create(executedCommand));
    }
}
