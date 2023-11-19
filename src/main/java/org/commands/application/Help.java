package org.commands.application;

import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.interactions.commands.build.OptionData;
import org.commands.CommandInterface;

import java.util.List;

public class Help implements CommandInterface {
    @Override
    public String getCommandName() {
        return "도움말";
    }

    @Override
    public String getCommandDescription() {
        return null;
    }

    @Override
    public List<OptionData> getCommandOptions() {
        return null;
    }

    @Override
    public void execute(SlashCommandInteractionEvent event) {

    }
}
