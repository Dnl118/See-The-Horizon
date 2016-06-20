package com.seethehorizon.game.model;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;

/**
 * Created by Danilo on 18/06/2016.
 */
public class End extends AbstractGameObject {

    public End(){
        init();
    }

    private void init(){
        dimension.set(1,1);
        bounds.set(0, 0, dimension.x, dimension.y);
    }

    @Override
    public void render(SpriteBatch batch) {

    }
}
