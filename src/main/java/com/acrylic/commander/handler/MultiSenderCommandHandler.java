package com.acrylic.commander.handler;

import com.acrylic.commander.executed.ExecutedCommand;
import com.acrylic.commander.handler.CommandHandler;
import org.bukkit.command.BlockCommandSender;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;

public final class MultiSenderCommandHandler
        implements CommandHandler<CommandSender> {

    private CommandHandler<Player> playerCommandHandler;
    private CommandHandler<ConsoleCommandSender> consoleSenderCommandHandler;
    private CommandHandler<BlockCommandSender> blockSenderCommandHandler;
    private CommandHandler<CommandSender> defaultHandler;

    public CommandHandler<Player> getPlayerCommandHandler() {
        return playerCommandHandler;
    }

    public void setPlayerCommandHandler(CommandHandler<Player> playerCommandHandler) {
        this.playerCommandHandler = playerCommandHandler;
    }

    public CommandHandler<ConsoleCommandSender> getConsoleSenderCommandHandler() {
        return consoleSenderCommandHandler;
    }

    public void setConsoleSenderCommandHandler(CommandHandler<ConsoleCommandSender> consoleSenderCommandHandler) {
        this.consoleSenderCommandHandler = consoleSenderCommandHandler;
    }

    public CommandHandler<BlockCommandSender> getBlockSenderCommandHandler() {
        return blockSenderCommandHandler;
    }

    public void setBlockSenderCommandHandler(CommandHandler<BlockCommandSender> blockSenderCommandHandler) {
        this.blockSenderCommandHandler = blockSenderCommandHandler;
    }

    public CommandHandler<CommandSender> getDefaultHandler() {
        return defaultHandler;
    }

    public void setDefaultHandler(CommandHandler<CommandSender> defaultHandler) {
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

        this.defaultHandler.handle(ExecutedCommand.create(executedCommand));
    }
}
