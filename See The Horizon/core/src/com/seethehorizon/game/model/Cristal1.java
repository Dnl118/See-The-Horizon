package com.seethehorizon.game.model;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.seethehorizon.game.assets.Assets;

/**
 * Created by Danilo on 16/04/2016.
 * Item coletavel que ira dar poder ao Will
 */
public class Cristal1 extends AbstractGameObject {

    private TextureRegion regCristal1;
    public boolean collected;

    public Cristal1(){
        init();
    }

    private void init() {
        dimension.set(0.5f, 0.5f);
        regCristal1 = Assets.instance.cristal1.cristal1;
        //seta bounding box para detectar colisao
        bounds.set(0, 0, dimension.x, dimension.y);
        collected = false;
    }

    @Override
    public void render(SpriteBatch batch) {
        //se moeda foi coletada sai do metiodo
        if (collected) {
            return;
        }
        TextureRegion reg = null;
        reg = regCristal1;
        batch.draw(reg.getTexture(), position.x, position.y,
                origin.x, origin.y, dimension.x, dimension.y, scale.x, scale.y,
                rotation, reg.getRegionX(), reg.getRegionY(),
                reg.getRegionWidth(), reg.getRegionHeight(), false, false);
    }

    public int getScore() {
        //retorna pontos que o coletavel da
        return 15;
    }
}
