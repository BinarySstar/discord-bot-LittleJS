package org.commands.music;

import net.dv8tion.jda.api.entities.GuildVoiceState;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.interactions.commands.build.OptionData;
import org.commands.CommandInterface;
import org.musicplayer.GuildMusicManager;
import org.musicplayer.MusicListener;
import org.musicplayer.TrackScheduler;

import java.util.List;

public class Skip implements CommandInterface {
    @Override
    public String getCommandName() {
        return "건너뛰기";
    }

    @Override
    public String getCommandDescription() {
        return "현재 재생 중인 노래를 건너뜁니다.";
    }

    @Override
    public List<OptionData> getCommandOptions() {
        return null;
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

        GuildMusicManager musicManager = MusicListener.getInstance().getGuildMusicManager(event.getGuild());
        TrackScheduler scheduler = musicManager.getScheduler();
        if(scheduler.isRepeat()){
            //반복을 비활성화시켜 다음 곡으로 건너뛰기
            scheduler.setRepeat(false);
        }
        scheduler.stopTrack();
        event.reply("건너뛰기가 완료되었습니다").queue();
    }
}
