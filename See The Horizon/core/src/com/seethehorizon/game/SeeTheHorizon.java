package com.seethehorizon.game;

import com.badlogic.gdx.Application;
import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.GL20;
import com.seethehorizon.game.assets.Assets;
import com.seethehorizon.game.controller.WorldController;
import com.seethehorizon.game.renderer.WorldRenderer;

public class SeeTheHorizon implements ApplicationListener {

    private static final String TAG = SeeTheHorizon.class.getName();

    private WorldController worldController;
    private WorldRenderer   worldRenderer;

    //define se o jogo esta pausado
    private boolean paused;

    @Override
    public void create() {
        //setando log para debug
        Gdx.app.setLogLevel(Application.LOG_DEBUG);
        //carregadno assets
        Assets.instance.init(new AssetManager());
        //instanciando controller e renderer
        worldController = new WorldController();
        worldRenderer = new WorldRenderer(worldController);
        //o jogo nao esta pausado no comeco
        paused = false;
    }

    @Override
    public void resize(int width, int height) {
        worldRenderer.resize(width, height);
    }

    @Override
    public void render() {
        //se o jogo esta pausado nao atualiza o mundo do jogo
        if(!paused) {
            //atualiza o mundo do jogo pelo tempo
            worldController.update(Gdx.graphics.getDeltaTime());
        }
        //seta o a cor de fundo para azul (gera cor pelo RGBA)
        //passando valor por exadecimal
        Gdx.gl.glClearColor(0x64/255.0f, 0x95/255.0f, 0xed/255.0f, 0xff/255.0f);
        //limpa a tela
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        //renderiza o mundo do jogo na tela
        worldRenderer.render();
    }

    @Override
    public void pause() {
        paused = true;
    }

    @Override
    public void resume() {
        paused = false;
    }

    @Override
    public void dispose() {
        worldRenderer.dispose();
        Assets.instance.dispose();
    }
}
