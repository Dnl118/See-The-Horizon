package com.seethehorizon.game.model;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.seethehorizon.game.assets.Assets;

/**
 * Created by Danilo on 23/04/2016.
 */
public class Live extends AbstractGameObject {

    private TextureRegion regLive;
    public boolean collected;

    public Live(){
        init();
    }

    private void init(){
        dimension.set(0.5f, 0.5f);
        regLive = Assets.instance.will.willAsset;
        //seta bounding box para detectar colisao
        bounds.set(0, 0, dimension.x, dimension.y);
        collected = false;
    }

    @Override
    public void render(SpriteBatch batch) {
        if(collected){
            return;
        }
        TextureRegion reg = null;
        reg = regLive;
        batch.draw(reg.getTexture(), position.x, position.y,
                origin.x, origin.y, dimension.x, dimension.y, scale.x, scale.y,
                rotation, reg.getRegionX(), reg.getRegionY(),
                reg.getRegionWidth(), reg.getRegionHeight(), false, false);
    }

    public int getScore(){
        //retorna pontos
        return 50;
    }



}
