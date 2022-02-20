package com.acrylic.commander.arguments;

import com.acrylic.commander.functional.ObjectToObject;
import org.jetbrains.annotations.NotNull;

public final class CommandParameterImpl<T>
        implements CommandParameter<T> {

    public static <T> CommandParameterImpl<T> create(Class<T> typeClass, ObjectToObject<String, ArgumentParserResult<T>> parser) {
        return new CommandParameterImpl<>(typeClass, parser);
    }

    private final Class<T> typeClass;
    private final ObjectToObject<String, ArgumentParserResult<T>> parser;

    CommandParameterImpl(Class<T> typeClass, ObjectToObject<String, ArgumentParserResult<T>> parser) {
        this.typeClass = typeClass;
        this.parser = parser;
    }

    @Override
    public @NotNull Class<T> getType() {
        return typeClass;
    }

    @Override
    public @NotNull ArgumentParserResult<T> parseArgument(String argument) {
        return parser.from(argument);
    }
}
