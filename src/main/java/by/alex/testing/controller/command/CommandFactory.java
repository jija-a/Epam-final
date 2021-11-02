package by.alex.testing.controller.command;

import by.alex.testing.controller.command.impl.ToHomePageCommand;

import java.util.Map;

public class CommandFactory {

    private static final Map<String, Command> commandMap = initCommandMap();

    private CommandFactory(){
    }

    private static Map<String, Command> initCommandMap() {
        return Map.ofEntries(
                Map.entry("to_home_page", new ToHomePageCommand())
        );
    }

    public static Command resolveCommand(String commandName) {

        return commandMap.getOrDefault(commandName, new ToHomePageCommand());
    }
}
