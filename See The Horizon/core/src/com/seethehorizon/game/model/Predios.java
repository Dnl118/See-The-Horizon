package com.seethehorizon.game.model;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.MathUtils;
import com.seethehorizon.game.assets.Assets;

/**
 * Created by Danilo on 10/04/2016.
 */
public class Predios extends AbstractGameObject {

    private TextureRegion predios;

    private int length;

    public Predios(int length) {
        this.length = length;
        init();
    }

    private void init() {
        dimension.set(10, 4);
        predios = Assets.instance.levelDecoration.predios;
        origin.x = -dimension.x * 2;
        length += dimension.x * 2;
    }

    private void drawPredios(SpriteBatch batch, float offsetX, float offsetY, float tintColor) {
        TextureRegion region = null;
        batch.setColor(tintColor, tintColor, tintColor, 1);
        float xRel = dimension.x * offsetX;
        float yRel = dimension.y * offsetY;

        int prediosLength = 0;
        prediosLength += MathUtils.ceil(length / 2 * dimension.x);
        prediosLength += MathUtils.ceil(0.5f * offsetX);
        for (int i = 0; i < prediosLength; i++) {
            region = predios;
            if(region != null) {
                batch.draw(region.getTexture(), origin.x + xRel, position.y + origin.y + yRel,
                        origin.x, origin.y, dimension.x, dimension.y, scale.x, scale.y, rotation,
                        region.getRegionX(), region.getRegionY(), region.getRegionWidth(),
                        region.getRegionHeight(), false, false);
                xRel += dimension.x;
                batch.draw(region.getTexture(), origin.x + xRel, position.y + origin.y + yRel,
                        origin.x, origin.y, dimension.x, dimension.y, scale.x, scale.y, rotation,
                        region.getRegionX(), region.getRegionY(), region.getRegionWidth(),
                        region.getRegionHeight(), true, false);
            } else {
                Gdx.app.debug("Predios", "region veio nula");
            }
        }
        batch.setColor(1, 1, 1, 1);
    }

    @Override
    public void render(SpriteBatch batch) {
        drawPredios(batch, 0.5f, 0.5f, 0.5f);
        drawPredios(batch, 0.25f, 0.25f, 0.7f);
        drawPredios(batch, 0.0f, 0.0f, 0.9f);
    }

}
