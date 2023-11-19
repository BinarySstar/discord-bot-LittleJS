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
import java.util.Calendar;
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
        embedBuilder.setAuthor("현재 재생목록", null, event.getGuild().getIconUrl());
        if(queue.isEmpty()) {
            embedBuilder.setDescription("목록이 비어있습니다");
        }
        for(int i = 0; i < queue.size(); i++) {
            AudioTrackInfo info2 = queue.get(i).getInfo();
            embedBuilder.addField("목록:", (i + 1) + ". " +
                    info2.title +
                    "[" + formattedTrackLength + "] " +
                    info2.author , false);
        }
        embedBuilder.setColor(Color.YELLOW)
                    .setFooter(sb);
        event.replyEmbeds(embedBuilder.build()).queue();
    }
}
