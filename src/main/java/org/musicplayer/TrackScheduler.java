package org.musicplayer;

import com.sedmelluq.discord.lavaplayer.player.AudioPlayer;
import com.sedmelluq.discord.lavaplayer.player.event.AudioEventAdapter;
import com.sedmelluq.discord.lavaplayer.track.AudioTrack;
import com.sedmelluq.discord.lavaplayer.track.AudioTrackEndReason;
import com.sedmelluq.discord.lavaplayer.track.AudioTrackInfo;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import org.jetbrains.annotations.NotNull;

import java.awt.*;
import java.util.Calendar;
import java.util.LinkedList;

public class TrackScheduler extends AudioEventAdapter {
    private final AudioPlayer player;
    private final LinkedList<AudioTrack> queue;
    private boolean isRepeat = false;

    public TrackScheduler(AudioPlayer player) {
        this.player = player;
        this.queue = new LinkedList<>();
    }

    @Override
    public void onTrackEnd(AudioPlayer player, AudioTrack track, AudioTrackEndReason endReason) {
        if(isRepeat){
            AudioTrack repeatedTrack = track.makeClone();
            player.startTrack(repeatedTrack, false);
        }
        else{
            player.startTrack(queue.poll(), false);
        }
    }

    public void queue(AudioTrack track) {
        if (!player.startTrack(track, true)) {
            queue.offer(track);
        }
    }

    public AudioPlayer getPlayer(){
        return player;
    }

    public LinkedList<AudioTrack> getQueue(){
        return queue;
    }

    public boolean isRepeat(){
        return isRepeat;
    }

    public void setRepeat(boolean isRepeat){
        this.isRepeat = isRepeat;
    }

    public void stopTrack(){
        player.stopTrack();
    }

    public void showTrack(@NotNull SlashCommandInteractionEvent event){
        GuildMusicManager guildMusicManager = MusicListener.getInstance().getGuildMusicManager(event.getGuild());
        if(guildMusicManager.getScheduler().getPlayer().getPlayingTrack() == null) {
            event.reply("현재 재생되고 있는 노래가 없습니다!").queue();
            return;
        }
        AudioTrackInfo info = guildMusicManager.getScheduler().getPlayer().getPlayingTrack().getInfo();

        Calendar now = Calendar.getInstance();
        int amPm = now.get(Calendar.AM_PM);
        String strAmPm = (amPm == Calendar.AM) ? "오전" : "오후";
        int hour = now.get(Calendar.HOUR);
        int minute = now.get(Calendar.MINUTE);
        String formattedHour = String.format("%02d", hour);
        String formattedMinute = String.format("%02d", minute);

        long trackLength = info.length;
        long trackHours = (trackLength / 1000) / 3600;
        long trackMinutes = ((trackLength / 1000) % 3600) / 60;
        long trackSeconds = (trackLength / 1000) % 60;
        String formattedTrackLength = null;
        if(trackHours < 1 ){
            formattedTrackLength = String.format("%02d", trackMinutes)
                    + ":" + String.format("%02d", trackSeconds);
        }
        else {
            formattedTrackLength = String.format("%02d", trackHours)
                    + ":" + String.format("%02d", trackMinutes)
                    + ":"  + String.format("%02d", trackSeconds);
        }



        String sb = new StringBuilder()
                .append(strAmPm)
                .append(" ")
                .append(formattedHour)
                .append(":")
                .append(formattedMinute)
                .append(" / ")
                .append(event.getMember().getEffectiveName())
                .toString();
        EmbedBuilder embedBuilder = new EmbedBuilder();
        embedBuilder.setAuthor(info.author, null, "https://cdn1.iconfinder.com/data/icons/music-instrument-vol-2/512/play_start_music_sound-512.png")
                .setTitle(info.title)
                .setThumbnail(info.artworkUrl)
                .setColor(Color.YELLOW)
                .addField("길이", formattedTrackLength, true)
                .addField("링크", info.uri, true)
                .setFooter(sb);

        event.replyEmbeds(embedBuilder.build()).queue();
    }
}