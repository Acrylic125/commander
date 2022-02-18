package com.acrylic.commander;

import com.acrylic.commander.executors.CommanderCommandExecutor;
import com.acrylic.commander.handler.MultiSenderCommandHandler;
import org.bukkit.plugin.java.JavaPlugin;

public final class Commander extends JavaPlugin {

    @Override
    public void onEnable() {
        // Plugin startup logic
        CommanderCommandExecutor.builder("hello")
                .aliases("")
                .handle(MultiSenderCommandHandler.builder()
                        .handlePlayer((player) -> {

                        })
                        .build());
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }
}
