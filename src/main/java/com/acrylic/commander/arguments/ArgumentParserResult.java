package com.acrylic.commander.arguments;

public final class ArgumentParserResult<T> {

    public static <T> ArgumentParserResult<T> create(State state, T result) {
        return new ArgumentParserResult<>(state, result);
    }

    public enum State {
        FAILED,
        SUCCESSFUL
    }

    private final State state;
    private final T result;

    ArgumentParserResult(State state, T result) {
        this.state = state;
        this.result = result;
    }

    public State getState() {
        return state;
    }

    public T getResult() {
        return result;
    }
}
