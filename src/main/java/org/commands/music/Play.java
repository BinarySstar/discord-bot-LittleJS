package org.commands.music;

import net.dv8tion.jda.api.entities.GuildVoiceState;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.interactions.commands.OptionType;
import net.dv8tion.jda.api.interactions.commands.build.OptionData;
import org.commands.CommandInterface;
import org.musicplayer.MusicListener;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

public class Play implements CommandInterface {
    @Override
    public String getCommandName() {
        return "재생";
    }

    @Override
    public String getCommandDescription() {
        return "노래를 재생합니다.";
    }

    @Override
    public List<OptionData> getCommandOptions() {
        List<OptionData> options = new ArrayList<>();
        options.add(new OptionData(OptionType.STRING, "name", "재생하고자 하는 노래 제목을 입력해주세요.", true));
        return options;
    }

    @Override
    public void execute(SlashCommandInteractionEvent event) {
        // 클라이언트 member
        Member member = event.getMember();
        GuildVoiceState memberVoiceState = member.getVoiceState();

        if(!memberVoiceState.inAudioChannel()){
            event.reply("보이스 채널에 참여하셔야 합니다!").queue();
            return;
        }

        // 봇 self
        Member self = event.getGuild().getSelfMember();
        GuildVoiceState selfVoiceState = self.getVoiceState();

        if(!selfVoiceState.inAudioChannel()){
            event.getGuild().getAudioManager().openAudioConnection(memberVoiceState.getChannel());
        }
        else {
            if(selfVoiceState.getChannel() != memberVoiceState.getChannel()){
                event.reply("저랑 같은 채널에 있어야 합니다!").queue();
                return;
            }
        }

        String name = event.getOption("name").getAsString();
        try {
            new URI(name);
        }
        catch (URISyntaxException e){
            name = "ytsearch:" + name;
        }

        MusicListener musicListener = MusicListener.getInstance();
        musicListener.loadAndPlay(event.getGuild(), name, () ->  musicListener.getGuildMusicManager(event.getGuild()).getScheduler().showTrack(event));
    }

}
