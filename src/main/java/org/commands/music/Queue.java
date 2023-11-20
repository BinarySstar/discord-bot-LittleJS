package org.commands.music;

import com.sedmelluq.discord.lavaplayer.track.AudioTrack;
import com.sedmelluq.discord.lavaplayer.track.AudioTrackInfo;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.GuildVoiceState;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.interactions.commands.build.OptionData;
import org.commands.CommandInterface;
import org.musicplayer.GuildMusicManager;
import org.musicplayer.MusicListener;

import java.awt.*;
import java.time.Instant;
import java.util.LinkedList;
import java.util.List;

public class Queue implements CommandInterface {
    @Override
    public String getCommandName() {
        return "목록";
    }

    @Override
    public String getCommandDescription() {
        return "현재 재생목록에 저장되어 있는 곡들을 보여줍니다";
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
        List<AudioTrack> queue = new LinkedList<>(guildMusicManager.getScheduler().getQueue());

        EmbedBuilder embedBuilder = new EmbedBuilder();
        embedBuilder.setAuthor("현재 재생목록", null, event.getGuild().getIconUrl())
                    .setColor(Color.YELLOW);
        StringBuilder sb2 = new StringBuilder();
        if(queue.isEmpty()) {
            embedBuilder.setDescription("목록이 비어있습니다");
        }
        else {
            for(int i = 0; i < queue.size(); i++) {
                AudioTrackInfo info = queue.get(i).getInfo();
                sb2.append(i + 1)
                        .append(". " + info.title)
                        .append(" [" + formatTrackLength(info.length) + "] ")
                        .append("\n");
            }
            embedBuilder.addField("목록", String.valueOf(sb2), false)
                    .setFooter(event.getMember().getEffectiveName())
                    .setTimestamp(Instant.now());
        }
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
