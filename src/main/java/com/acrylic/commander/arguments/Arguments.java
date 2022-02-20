package com.acrylic.commander.arguments;

import java.util.Optional;

public final class Arguments {

    public static Arguments create(CommandParameter<?>[] parameters, String[] rawArgs) {
        if (parameters.length != rawArgs.length) {
            return
        }
    }

    private final CommandParameter<?>[] parameters;
    private final String[] rawArgs;

    Arguments(CommandParameter<?>[] parameters, String[] rawArgs) {
        this.parameters = parameters;
        this.rawArgs = rawArgs;
    }

    public Optional<Object> getArg(int index) {
        if (index < 0 || index >= parameters.length)
            return Optional.empty();
        return Optional.of(parameters[index].parseArgument(rawArgs[index]));
    }

}
