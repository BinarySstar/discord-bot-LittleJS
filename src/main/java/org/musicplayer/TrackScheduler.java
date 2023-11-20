package org.musicplayer;

import com.sedmelluq.discord.lavaplayer.player.AudioPlayer;
import com.sedmelluq.discord.lavaplayer.player.event.AudioEventAdapter;
import com.sedmelluq.discord.lavaplayer.track.AudioTrack;
import com.sedmelluq.discord.lavaplayer.track.AudioTrackEndReason;
import com.sedmelluq.discord.lavaplayer.track.AudioTrackInfo;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.channel.concrete.TextChannel;


import java.awt.*;
import java.time.Instant;
import java.util.LinkedList;

public class TrackScheduler extends AudioEventAdapter {
    private final AudioPlayer player;
    private final LinkedList<AudioTrack> queue;
    private boolean isRepeat = false;

    private final Guild guild;

    public TrackScheduler(AudioPlayer player, Guild guild) {
        this.player = player;
        this.queue = new LinkedList<>();
        this.guild = guild;
    }

    @Override
    public void onTrackStart(AudioPlayer player, AudioTrack track) {
        AudioTrackInfo trackInfo = track.getInfo();
        TextChannel channel = (TextChannel) guild.getDefaultChannel();
        EmbedBuilder embedBuilder = new EmbedBuilder()
                .setTitle(trackInfo.title)
                .setAuthor(trackInfo.author, null, "https://cdn1.iconfinder.com/data/icons/music-instrument-vol-2/512/play_start_music_sound-512.png")
                .setThumbnail(trackInfo.artworkUrl)
                .setColor(Color.YELLOW)
                .addField("길이", formatTrackLength(trackInfo.length), true)
                .addField("링크", trackInfo.uri, true)
                .setTimestamp(Instant.now());
        channel.sendMessageEmbeds(embedBuilder.build()).queue();
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