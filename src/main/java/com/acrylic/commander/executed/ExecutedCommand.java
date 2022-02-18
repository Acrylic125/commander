package com.acrylic.commander.executed;

import org.bukkit.command.CommandSender;

public final class ExecutedCommand<T extends CommandSender> {

    private final ExecutedCommandRecord<T> record;
    private final int offsetArgument;

    public ExecutedCommand(ExecutedCommandRecord<T> record) {
        this(record, 0);
    }

    public ExecutedCommand(ExecutedCommandRecord<T> record, int offsetArgument) {
        this.record = record;
        this.offsetArgument = offsetArgument;
    }

    public ExecutedCommandRecord<T> getRecord() {
        return this.record;
    }

    public T getSender() {
        return this.record.getSender();
    }

    public String getArg(int index) {
        return this.record.getArg(this.offsetArgument + index);
    }

}
