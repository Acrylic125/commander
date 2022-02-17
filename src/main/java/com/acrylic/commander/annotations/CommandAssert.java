package com.acrylic.commander.annotations;

import com.acrylic.commander.enumerable.CommandAssertion;

public @interface CommandAssert {

    CommandAssertion[] assertions();

    String failActionId() default "";

    String failedMessage() default "&cYou do not have permission to use this command.";

}
