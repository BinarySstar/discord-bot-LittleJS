package org.commands;

import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.interactions.commands.SlashCommandInteraction;
import net.dv8tion.jda.api.interactions.commands.build.OptionData;

import java.util.List;

public interface CommandInterface {
    String getCommandName();

    String getCommandDescription();

    List<OptionData> getCommandOptions();

    void execute(SlashCommandInteractionEvent event);

}
