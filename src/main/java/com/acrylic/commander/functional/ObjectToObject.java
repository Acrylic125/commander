package com.acrylic.commander.functional;

@FunctionalInterface
public interface ObjectToObject<F, T> {

    T from(F from);

}
