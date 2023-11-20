package org.commands;

import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.events.session.ReadyEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.dv8tion.jda.api.interactions.commands.build.Commands;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class CommandManager extends ListenerAdapter {

    private List<CommandInterface> commands;

    public CommandManager() {
        commands = new ArrayList<>();
    }

    @Override
    public void onReady(@NotNull ReadyEvent event){
        for(Guild guild : event.getJDA().getGuilds()) {
            for(CommandInterface command : commands) {
                if(command.getCommandOptions() == null) {
                    guild.upsertCommand(command.getCommandName(), command.getCommandDescription()).queue();
                }
                else {
                    guild.upsertCommand(command.getCommandName(), command.getCommandDescription()).addOptions(command.getCommandOptions()).queue();
                }
            }
        }
        return;
    }

    @Override
    public void onSlashCommandInteraction(@NotNull SlashCommandInteractionEvent event){
        for(CommandInterface command : commands){
            if(command.getCommandName().equals(event.getName())){
                command.execute(event);
                return;
            }
        }
    }

    public void add(CommandInterface command){
        commands.add(command);
    }
}
