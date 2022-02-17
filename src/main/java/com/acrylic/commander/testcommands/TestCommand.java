package com.acrylic.commander.testcommands;

import com.acrylic.commander.annotations.Command;
import com.acrylic.commander.annotations.CommandAction;
import com.acrylic.commander.annotations.CommandPermission;

@Command(command = "test",
         aliases = {"test-command"})
@CommandPermission(permissions = {"test.admin", "test.mod"})
public class TestCommand {



    @CommandAction(id = "")
    public void onCommandFail() {

    }

}
