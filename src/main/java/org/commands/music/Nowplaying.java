package org.commands.music;

import com.sedmelluq.discord.lavaplayer.track.AudioTrack;
import com.sedmelluq.discord.lavaplayer.track.AudioTrackInfo;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.GuildVoiceState;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.interactions.commands.build.OptionData;
import org.commands.CommandInterface;
import org.jetbrains.annotations.NotNull;
import org.musicplayer.GuildMusicManager;
import org.musicplayer.MusicListener;

import java.awt.*;
import java.time.Instant;
import java.util.List;

public class Nowplaying implements CommandInterface {
    @Override
    public String getCommandName() {
        return "재생정보";
    }

    @Override
    public String getCommandDescription() {
        return "현재 재생되고 있는 노래의 정보를 표시합니다";
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

        MusicListener musicListener = MusicListener.getInstance();
        showTrack(event);
    }
    public void showTrack(@NotNull SlashCommandInteractionEvent event){
        GuildMusicManager guildMusicManager = MusicListener.getInstance().getGuildMusicManager(event.getGuild());
        AudioTrack audioTrack = guildMusicManager.getScheduler().getPlayer().getPlayingTrack();
        if(audioTrack == null) {
            event.reply("현재 재생되고 있는 노래가 없습니다!").queue();
            return;
        }
        AudioTrackInfo info = audioTrack.getInfo();

        EmbedBuilder embedBuilder = new EmbedBuilder();
        embedBuilder.setAuthor(info.author, null, "https://cdn1.iconfinder.com/data/icons/music-instrument-vol-2/512/play_start_music_sound-512.png")
                .setTitle(info.title)
                .setThumbnail(info.artworkUrl)
                .setColor(Color.YELLOW)
                .addField("길이", formatTrackLength(info.length), true)
                .addField("링크", info.uri, true)
                .setFooter(event.getMember().getEffectiveName())
                .setTimestamp(Instant.now());

        event.replyEmbeds(embedBuilder.build()).queue();
    }

    private String formatTrackLength(long trackLength) {
        long trackHours = (trackLength / 1000) / 3600;
        long trackMinutes = ((trackLength / 1000) % 3600) / 60;
        long trackSeconds = (trackLength / 1000) % 60;

        if (trackHours < 1) {
            return String.format("%02d:%02d", trackMinutes, trackSeconds);
        } else {
            return String.format("%02d:%02d:%02d", trackHours, trackMinutes, trackSeconds);
        }
    }
}
