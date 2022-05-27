package net.minecraft.settings.user;

import org.jetbrains.annotations.NotNull;

import java.util.Random;

public class SessionID {

    @NotNull
    public static String sessionUpdate(){
        Random r = new Random();
        int x = r.nextInt(1000000000);
        return String.valueOf(x);
    }
}
