package com.example.floridaclubmanager;

import android.content.Context;
import android.media.MediaPlayer;
import android.media.SoundPool;

public class SoundPlayer {
    public static MediaPlayer player;
    public static SoundPool soundPool;
    public static void playSong(Context context, int song, int position){
        player = MediaPlayer.create(context, song);
        player.seekTo(position);
        player.setLooping(true);
        player.start();
    }
}
