package org.commands.music;

import com.sedmelluq.discord.lavaplayer.track.AudioTrack;
import com.sedmelluq.discord.lavaplayer.track.AudioTrackInfo;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.GuildVoiceState;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.interactions.commands.OptionType;
import net.dv8tion.jda.api.interactions.commands.build.OptionData;
import org.commands.CommandInterface;
import org.musicplayer.GuildMusicManager;
import org.musicplayer.MusicListener;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class Remove implements CommandInterface {
    @Override
    public String getCommandName() {
        return "제거";
    }

    @Override
    public String getCommandDescription() {
        return "목록에 있는 노래를 제거합니다.";
    }

    @Override
    public List<OptionData> getCommandOptions() {
        List<OptionData> options = new ArrayList<>();
        options.add(new OptionData(OptionType.INTEGER, "번호", "제거하고자 하는 노래의 번호를 입력해주세요.", true));
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

        GuildMusicManager guildMusicManager = MusicListener.getInstance().getGuildMusicManager(event.getGuild());
        List<AudioTrack> queue = new LinkedList<>(guildMusicManager.getScheduler().getQueue());
        EmbedBuilder embedBuilder = new EmbedBuilder();
        if(queue.isEmpty()) {
            embedBuilder.setDescription("목록이 비어있습니다");
        }
        int index = event.getOption("번호").getAsInt();
        AudioTrackInfo info = queue.get(index - 1).getInfo();
        guildMusicManager.getScheduler().getQueue().remove(index - 1);
        embedBuilder.setDescription(info.title + "가 제거되었습니다");

        event.replyEmbeds(embedBuilder.build()).queue();
    }
}
