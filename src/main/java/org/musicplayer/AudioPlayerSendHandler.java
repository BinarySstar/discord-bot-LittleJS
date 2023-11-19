package org.musicplayer;

import com.sedmelluq.discord.lavaplayer.player.AudioPlayer;
import com.sedmelluq.discord.lavaplayer.track.playback.MutableAudioFrame;

import net.dv8tion.jda.api.audio.AudioSendHandler;
import net.dv8tion.jda.api.entities.Guild;
import org.jetbrains.annotations.Nullable;

import java.nio.ByteBuffer;

/**
 * This is a wrapper around AudioPlayer which makes it behave as an AudioSendHandler for JDA. As JDA calls canProvide
 * before every call to provide20MsAudio(), we pull the frame in canProvide() and use the frame we already pulled in
 * provide20MsAudio().
 */
public class AudioPlayerSendHandler implements AudioSendHandler {
    private final AudioPlayer player;
    private final ByteBuffer buffer;
    private final Guild guild;
    private final MutableAudioFrame frame;


    public AudioPlayerSendHandler(AudioPlayer player, Guild guild) {
        this.player = player;
        this.buffer = ByteBuffer.allocate(1024);
        this.guild = guild;
        this.frame = new MutableAudioFrame();
        this.frame.setBuffer(buffer);
    }

    @Override
    public boolean canProvide() {
        // returns true if audio was provided
        return player.provide(frame);
    }

    @Override
    @Nullable
    public ByteBuffer provide20MsAudio() {
        // flip to make it a read buffer
        return buffer.flip();
    }

    @Override
    public boolean isOpus() {
        return true;
    }
}