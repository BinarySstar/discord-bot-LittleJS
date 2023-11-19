package org.example;

import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.channel.concrete.VoiceChannel;
import net.dv8tion.jda.api.entities.channel.middleman.MessageChannel;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.events.session.ReadyEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.dv8tion.jda.api.interactions.commands.build.Commands;
import net.dv8tion.jda.api.managers.AudioManager;
import org.jetbrains.annotations.NotNull;

public class MyListener extends ListenerAdapter
{
    @Override
    public void onMessageReceived(@NotNull MessageReceivedEvent event)
    {
        if (event.getAuthor().isBot()) return;
        // We don't want to respond to other bot accounts, including ourself
        Message message = event.getMessage();
        String content = message.getContentRaw();
        // getContentRaw() is an atomic getter
        // getContentDisplay() is a lazy getter which modifies the content for e.g. console view (strip discord formatting)
        if (content.equals("!ping"))
        {
            MessageChannel channel = event.getChannel();
            channel.sendMessage("Pong!").queue(); // Important to call .queue() on the RestAction returned by sendMessage(...)
        }
        if (content.equals("이진성"))
        {
            MessageChannel channel = event.getChannel();
            channel.sendMessage("164.9!").queue(); // Important to call .queue() on the RestAction returned by sendMessage(...)
        }
        if (content.equals("손종현"))
        {
            MessageChannel channel = event.getChannel();
            channel.sendMessage("머리 탈탈 탈모!").queue(); // Important to call .queue() on the RestAction returned by sendMessage(...)
        }
        if (content.equals("신동혁"))
        {
            MessageChannel channel = event.getChannel();
            channel.sendMessage("어린노무자식!").queue(); // Important to call .queue() on the RestAction returned by sendMessage(...)
        }
        if (content.equals("박주영"))
        {
            MessageChannel channel = event.getChannel();
            channel.sendMessage("말!").queue(); // Important to call .queue() on the RestAction returned by sendMessage(...)
        }
        if (content.equals("김태현"))
        {
            MessageChannel channel = event.getChannel();
            channel.sendMessage("원사까지 갑니다").queue(); // Important to call .queue() on the RestAction returned by sendMessage(...)
        }
        if (content.equals("박지원"))
        {
            MessageChannel channel = event.getChannel();
            channel.sendMessage("ddp!").queue(); // Important to call .queue() on the RestAction returned by sendMessage(...)
        }
        if (content.equals("대머리") || content.equals("빡빡이") || content.equals("탈모") || content.equals("머머리")
            || content.equals("bald") || content.equals("라이즈") || content.equals("파이크") || content.equals("홍석천")
            || content.equals("리신") || content.equals("신지드") || content.equals("매끈매끈") || content.equals("반짝반짝")
            || content.equals("맨들맨들")) {
            MessageChannel channel = event.getChannel();
            channel.sendMessage("손종현").queue(); // Important to call .queue() on the RestAction returned by sendMessage(...)
        }
        if (content.equals("응애"))
        {
            MessageChannel channel = event.getChannel();
            channel.sendMessage("나 아기진성").queue();
        }
    }
}
