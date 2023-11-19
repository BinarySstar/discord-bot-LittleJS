package org.musicplayer;

import com.sedmelluq.discord.lavaplayer.player.AudioPlayer;
import com.sedmelluq.discord.lavaplayer.player.AudioPlayerManager;
import net.dv8tion.jda.api.entities.Guild;
public class GuildMusicManager {

    private final TrackScheduler scheduler;
    private final AudioPlayerSendHandler handler;

    public GuildMusicManager(AudioPlayerManager manager, Guild guild) {
        AudioPlayer player = manager.createPlayer();
        scheduler = new TrackScheduler(player);
        player.addListener(scheduler);
        handler = new AudioPlayerSendHandler(player, guild);
    }

    public TrackScheduler getScheduler() { return scheduler;}
    public AudioPlayerSendHandler getSendHandler() {
        return handler;
    }

}