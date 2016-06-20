package com.seethehorizon.game.model;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.seethehorizon.game.assets.Assets;

/**
 * Created by Danilo on 24/04/2016.
 */
public class Cristal2 extends AbstractGameObject{

    private TextureRegion regCristal2;
    public boolean collected;

    public Cristal2(){
        init();
    }

    private void init() {
        dimension.set(0.5f, 0.5f);
        regCristal2 = Assets.instance.cristal2.cristal2;
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
        reg = regCristal2;
        batch.draw(reg.getTexture(), position.x, position.y,
                origin.x, origin.y, dimension.x, dimension.y, scale.x, scale.y,
                rotation, reg.getRegionX(), reg.getRegionY(),
                reg.getRegionWidth(), reg.getRegionHeight(), false, false);
    }

    public int getScore() {
        //retorna pontos que o coletavel da
        return 30;
    }


}
