package com.seethehorizon.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.seethehorizon.game.screens.GameScreen;

/**
 * Created by Danilo on 20/02/2016.
 */
public class SeeTheHorizon extends Game {

	@Override
	public void create() {
		setScreen(new GameScreen());
	}
}
