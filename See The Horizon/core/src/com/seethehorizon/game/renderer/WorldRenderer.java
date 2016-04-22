package com.seethehorizon.game.renderer;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.Disposable;
import com.seethehorizon.game.assets.Assets;
import com.seethehorizon.game.controller.WorldController;
import com.seethehorizon.game.util.Constants;

/**
 * Created by Francisco on 03/04/2016.
 */
public class WorldRenderer implements Disposable {

    private OrthographicCamera camera;
    private OrthographicCamera cameraGUI;
    private SpriteBatch spriteBatch;
    private WorldController worldController;

    public WorldRenderer(WorldController worldController) {
        this.worldController = worldController;
        init();
    }

    private void init() {
        spriteBatch = new SpriteBatch();
        camera = new OrthographicCamera(Constants.VIEWPORT_WIDTH, Constants.VIEWPORT_HEIGHT);
        camera.position.set(0, 0, 0);
        camera.update();
        cameraGUI = new OrthographicCamera(Constants.VIEWPORT_GUI_WIDTH, Constants.VIEWPORT_GUI_HEIGHT);
        cameraGUI.position.set(0, 0, 0);
        cameraGUI.setToOrtho(true); //flipa no eixo y
        cameraGUI.update();
    }

    public void render() {
        renderWorld(spriteBatch);
        renderGui(spriteBatch);
    }

    public void resize(int width, int height) {
        camera.viewportWidth = (Constants.VIEWPORT_HEIGHT / height) * width;
        camera.update();
        cameraGUI.viewportHeight = Constants.VIEWPORT_GUI_HEIGHT;
        cameraGUI.viewportWidth = Constants.VIEWPORT_GUI_HEIGHT / height * width;
        cameraGUI.position.set(cameraGUI.viewportWidth / 2, cameraGUI.viewportHeight / 2, 0);
        cameraGUI.update();
    }

    @Override
    public void dispose() {
        spriteBatch.dispose();
    }

    private void renderWorld(SpriteBatch batch) {
        worldController.cameraHelper.applyTo(camera);
        batch.setProjectionMatrix(camera.combined);
        batch.begin();
        worldController.level.render(batch);
        batch.end();
    }

    private void renderGuiScore(SpriteBatch batch) {
        float x = -15;
        float y = -15;
        batch.draw(Assets.instance.coletavel1.coletavel1, x, y, 50, 50, 100, 100, 0.35f, -0.35f, 0);
        Assets.instance.fonts.defaultBig.draw(batch, "" + worldController.score, x + 75, y + 37);
    }

    private void renderGuiExtraLive(SpriteBatch batch) {
        float x = cameraGUI.viewportWidth - 50 -
                Constants.LIVES_START * 50;
        float y = -15;
        for (int i = 0; i < Constants.LIVES_START; i++) {
            if (worldController.lives <= i) {
                batch.setColor(0.5f, 0.5f, 0.5f, 0.5f);
            }
            batch.draw(Assets.instance.will.willAsset, x + i * 50, y, 50, 50, 120, 100, 0.35f, -0.35f, 0);
            batch.setColor(1, 1, 1, 1);
        }
    }

    private void renderGuiFpsCounter(SpriteBatch batch) {
        float x = cameraGUI.viewportWidth - 55;
        float y = cameraGUI.viewportHeight - 15;
        int fps = Gdx.graphics.getFramesPerSecond();
        BitmapFont fpsFont = Assets.instance.fonts.defaultNormal;
        if (fps >= 45) {
            // 45 ou mais fps mostrado em azul
            fpsFont.setColor(0, 1, 0, 1);
        } else if (fps >= 30) {
            // 30 ou mais fps mostrado em amarelo
            fpsFont.setColor(1, 1, 0, 1);
        } else {
            // menos que 30 fps mostrado em vermelho
            fpsFont.setColor(1, 0, 0, 1);
        }
        fpsFont.draw(batch, "FPS: " + fps, x, y);
        fpsFont.setColor(1, 1, 1, 1); // white
    }

    private void renderGui(SpriteBatch batch) {
        batch.setProjectionMatrix(cameraGUI.combined);
        batch.begin();
        // desenha coletael1 coletados + texto no lado de cima esquerdo
        renderGuiScore(batch);
        // desenha vidas
        renderGuiExtraLive(batch);
        // desenha o fps
        renderGuiFpsCounter(batch);
        batch.end();
    }

    private void renderGuiGameOverMessage (SpriteBatch batch) {
        //centralizando mensagem
        float x = cameraGUI.viewportWidth / 2;
        float y = cameraGUI.viewportHeight / 2;
        if (worldController.isGameOver()) {
            BitmapFont fontGameOver = Assets.instance.fonts.defaultBig;
            fontGameOver.setColor(1, 0.75f, 0.25f, 1);
            //draw game over
            fontGameOver.draw(batch, "GAME OVER", x, y, 0, 0, 0, 0, true);
            //drawMultiLine(batch, "GAME OVER", x, y, 0,
                   // Align.center);
            fontGameOver.setColor(1, 1, 1, 1);
        }
    }

}
