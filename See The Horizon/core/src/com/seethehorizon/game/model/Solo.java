package com.seethehorizon.game.model;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.seethehorizon.game.assets.Assets;

/**
 * Created by Francisco on 10/04/2016.
 */
public class Solo extends AbstractGameObject {

    private TextureRegion soloEdg;
    private TextureRegion soloMiddle;

    private int length;

    public Solo() {
        init();
    }

    private void init() {
        dimension.set(1, 1.5f);
        //carregadno assets
        soloEdg = Assets.instance.solo.edge;
        soloMiddle = Assets.instance.solo.middle;
        //definindo comprimento inicial
        length = 1;
    }

    public void setLength(int lenght) {
        this.length = lenght;
        //tambem seta bounding box para deteccao de colisao
        bounds.set(0, 0, dimension.x * length, dimension.y);
    }

    public void increaseLenght(int qtd) {
        length += qtd;
    }

    @Override
    public void render(SpriteBatch batch) {
        TextureRegion region = null;
        float relX = 0;
        float relY = 0;

        //desenhando lateral esquerda
        region = soloEdg;
        relX -= dimension.x / 4;
        batch.draw(region.getTexture(), position.x + relX, position.y + relY,
                origin.x, origin.y, dimension.x / 4, dimension.y, scale.x, scale.y,
                rotation, region.getRegionX(), region.getRegionY(), region.getRegionWidth(),
                region.getRegionHeight(), false, false);

        //preenchendo o meio
        relX = 0;
        region = soloMiddle;
        for (int i = 0; i < length; i++) {
            batch.draw(region.getTexture(), position.x + relX, position.y + relY,
                    origin.x, origin.y, dimension.x, dimension.y, scale.x, scale.y,
                    rotation, region.getRegionX(), region.getRegionY(), region.getRegionWidth(),
                    region.getRegionHeight(), false, false);
            relX += dimension.x;
        }

        //desenhando lateral direita
        region = soloEdg;
        batch.draw(region.getTexture(), position.x + relX, position.y + relY,
                origin.x, origin.y, dimension.x / 4, dimension.y, scale.x, scale.y,
                rotation, region.getRegionX(), region.getRegionY(), region.getRegionWidth(),
                region.getRegionHeight(), true, false);
    }
}
