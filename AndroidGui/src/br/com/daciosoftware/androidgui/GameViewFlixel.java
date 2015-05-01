package br.com.daciosoftware.androidgui;

import org.flixel.FlxGame;
import org.flixel.FlxGameView;

import android.content.Context;

public class GameViewFlixel extends FlxGameView{
    public GameView(Context context, Class<? extends Object> resource){
	super(new FlxGame(400,240,simpleJumper.class,context,resource),context);
    }

}
