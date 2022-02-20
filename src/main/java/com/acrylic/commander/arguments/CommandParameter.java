package com.acrylic.commander.arguments;

import org.jetbrains.annotations.NotNull;

public interface CommandParameter<T> {

    @NotNull
    Class<T> getType();

    @NotNull
    ArgumentParserResult<T> parseArgument(String argument);

}
