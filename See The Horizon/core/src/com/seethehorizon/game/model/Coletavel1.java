package com.seethehorizon.game.model;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.seethehorizon.game.assets.Assets;

/**
 * Created by Francisco on 16/04/2016.
 * Coeltavel que dara pontos ao Will
 */
public class Coletavel1 extends AbstractGameObject {

    private TextureRegion regColetavel1;
    public boolean collected;

    public Coletavel1(){
        init();
    }

    private void init() {
        dimension.set(0.5f, 0.5f);
        regColetavel1 = Assets.instance.coletavel1.coletavel1;
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
        reg = regColetavel1;
        batch.draw(reg.getTexture(), position.x, position.y,
                origin.x, origin.y, dimension.x, dimension.y, scale.x, scale.y,
                rotation, reg.getRegionX(), reg.getRegionY(),
                reg.getRegionWidth(), reg.getRegionHeight(), false, false);
    }

    public int getScore() {
        //retorna pontos que o coletavel da
        return 1;
    }
}
