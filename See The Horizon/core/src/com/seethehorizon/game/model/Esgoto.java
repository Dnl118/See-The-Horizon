package com.seethehorizon.game.model;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.seethehorizon.game.assets.Assets;

/**
 * Created by Danilo on 10/04/2016.
 */
public class Esgoto extends AbstractGameObject {

    private TextureRegion esgoto;
    private float length;

    public Esgoto(float length){
        this.length = length;
        init();
    }

    private void init(){
        dimension.set(length * 10, 3);

        esgoto = Assets.instance.levelDecoration.esgoto;
        origin.x = -dimension.x / 2;
    }

    @Override
    public void render(SpriteBatch batch) {
        TextureRegion region = null;
        region = esgoto;
        batch.draw(region.getTexture(), position.x + origin.x, position.y + origin.y,
                origin.x, origin.y, dimension.x, dimension.y, scale.x, scale.y, rotation,
                region.getRegionX(), region.getRegionY(), region.getRegionWidth(),
                region.getRegionHeight(), false, false);
    }
}
