package org.application;

import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.entities.Activity;
import net.dv8tion.jda.api.requests.GatewayIntent;
import org.commands.CommandManager;
import org.commands.music.*;

public class Main {
    public static void main(String[] args) {
        if(args.length < 1){
            System.out.println("명령 인수에 token 값이 있어야 합니다!");
            return;
        }
        //args[0] : token(보안상 코드에 직접 노출시키는 것보다 환경변수를 통해 토큰을 넣어준다.
        JDA jda = JDABuilder.createDefault(args[0])
                .setActivity(Activity.playing("개발"))
                .enableIntents(GatewayIntent.MESSAGE_CONTENT)
                .build();

        CommandManager manager = new CommandManager();
        manager.add(new Play());
        manager.add(new Skip());
        manager.add(new Nowplaying());
        manager.add(new Queue());
        manager.add(new Repeat());
        manager.add(new Pause());
        manager.add(new Resume());
        manager.add(new Remove());
        jda.addEventListener(manager);
    }
}