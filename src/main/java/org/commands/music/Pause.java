package org.commands.music;

import net.dv8tion.jda.api.entities.GuildVoiceState;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.interactions.commands.build.OptionData;
import org.commands.CommandInterface;
import org.musicplayer.GuildMusicManager;
import org.musicplayer.MusicListener;

import java.util.List;

public class Pause implements CommandInterface {
    @Override
    public String getCommandName() {
        return "일시정지";
    }

    @Override
    public String getCommandDescription() {
        return "현재 재생 중인 음악을 일시정지합니다.";
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

        GuildMusicManager guildMusicManager = MusicListener.getInstance().getGuildMusicManager(event.getGuild());
        if(!guildMusicManager.getScheduler().getPlayer().isPaused()){
            guildMusicManager.getScheduler().getPlayer().setPaused(true);
        }
        else{
            event.reply("이미 일시정지 되어있습니다").queue();
            return;
        }
        event.reply("현재 곡을 일시정지합니다").queue();
    }
}
