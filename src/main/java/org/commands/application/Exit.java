package org.commands.application;

import net.dv8tion.jda.api.entities.GuildVoiceState;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.interactions.commands.build.OptionData;
import org.commands.CommandInterface;

import java.util.List;

public class Exit implements CommandInterface {
    @Override
    public String getCommandName() {
        return "종료";
    }

    @Override
    public String getCommandDescription() {
        return "봇을 종료합니다";
    }

    @Override
    public List<OptionData> getCommandOptions() {
        return null;
    }

    @Override
    public void execute(SlashCommandInteractionEvent event) {
        Member self = event.getGuild().getSelfMember();
        GuildVoiceState selfVoiceState = self.getVoiceState();
        if(selfVoiceState.inAudioChannel()){
            selfVoiceState.getGuild().leave();
        }
        return;
    }
}
