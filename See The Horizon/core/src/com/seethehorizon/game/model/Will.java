package com.seethehorizon.game.model;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.seethehorizon.game.assets.Assets;
import com.seethehorizon.game.util.Constants;

/**
 * Created by Francisco on 16/04/2016.
 */
public class Will extends AbstractGameObject {

    public static final String TAG = Will.class.getName();

    private final float JUMP_TIME_MAX = 0.3f;
    private final float JUMP_TIME_MIN = 0.1f;
    private final float JUMP_TIME_OFFSET_FLYING =
            JUMP_TIME_MAX - 0.018f;

    public enum VIEW_DIRECTION {
        LEFT,
        RIGHT
    }

    public enum JUMP_STATE {
        GROUNDED,
        FALLING,
        JUMP_RISING,
        JUMP_FALLING
    }

    private TextureRegion regWill;

    public VIEW_DIRECTION viewDirection;
    public float timeJumping;
    public JUMP_STATE jumpState;
    public boolean hasSpecialPower;
    public float timeLeftSpecialPower;

    public Will() {
        init();
    }

    public void init() {
        dimension.set(1, 1);
        regWill = Assets.instance.will.willAsset;
        //centraliza a imagem no objeto
        origin.set(dimension.x / 2, dimension.y / 2);
        //cria bounding box para detectar colisao
        bounds.set(0, 0, dimension.x, dimension.y);
        //seta valores utilizados nos calculos da fisica do jogo
        maxVelocity.set(3.0f, 4.0f);
        friction.set(12.0f, 0.0f);
        acceleration.set(0.0f, -25.0f);
        //direcao para onde esta olhando
        viewDirection = VIEW_DIRECTION.RIGHT;
        //estado em que se encontra o pulo do personagem
        jumpState = JUMP_STATE.FALLING;
        timeJumping = 0;
        hasSpecialPower = false;
        timeLeftSpecialPower = 0;
    }

    public void setJumping(boolean jumpKeyPressed) {
        switch (jumpState) {
            case GROUNDED:
                //Gdx.app.log(TAG, "GROUNDED");
                //personagem esta em cima de uma plataforma
                if (jumpKeyPressed) {
                    //zera tempo de pulo
                    //seta seu estado como groudned
                    timeJumping = 0;
                    jumpState = JUMP_STATE.JUMP_RISING;
                }
                break;
            case JUMP_RISING:
                //Gdx.app.log(TAG, "JUMP_RISING");
                //pulando e subindo
                if (!jumpKeyPressed) {
                    //se esta pulando e subindo mas botao que faz o
                    //personagem pular nao esta mais pressionado,
                    //entao ele esta caindo
                    jumpState = JUMP_STATE.JUMP_FALLING;
                    break;
                }
            case FALLING:
                //Gdx.app.log(TAG, "FALLING");
                //esta caindo
            case JUMP_FALLING:
                //Gdx.app.log(TAG, "JUMP_FALLING");
                //esta caindo apos realizar um salto
                if (jumpKeyPressed && hasSpecialPower) {
                    timeJumping = JUMP_TIME_OFFSET_FLYING;
                    jumpState = JUMP_STATE.JUMP_RISING;
                }
                break;
        }
    }

    public void setSpecialPowerActive(boolean powerPicked) {
        hasSpecialPower = powerPicked;
        if (powerPicked) {
            timeLeftSpecialPower = Constants.POWER_DURATION;
        }
    }

    public boolean isSpecialPowerActive() {
        //retorna poder ativo apenas se poder estiver ativo e
        //tempo para o poder acabar ainda nao estiver zerado
        return hasSpecialPower && timeLeftSpecialPower > 0;
    }

    @Override
    public void update(float deltaTime) {
        super.update(deltaTime);
        //if para teste, retirar depois
        if (velocity.x != 0) {
            viewDirection = velocity.x < 0 ? VIEW_DIRECTION.LEFT : VIEW_DIRECTION.RIGHT;
        }
        if (timeLeftSpecialPower > 0) {
            timeLeftSpecialPower -= deltaTime;
            if (timeLeftSpecialPower < 0) {
                //desativa poder
                timeLeftSpecialPower = 0;
                setSpecialPowerActive(false);
            }
        }
    }

    @Override
    protected void updateMotionY(float deltaTime) {
        switch (jumpState) {
            case GROUNDED:
                jumpState = JUMP_STATE.FALLING;
                break;
            case JUMP_RISING:
                //adiciona deltaTime ao tempo de pulo
                timeJumping += deltaTime;
                //verifica tempo maximo de pulo
                if (timeJumping <= JUMP_TIME_MAX) {
                    // se for menor ou igual ao tempo maximo, pode continuar no movimento
                    velocity.y = maxVelocity.y;
                }
                break;
            case FALLING:
                break;
            case JUMP_FALLING:
                //adiciona deltaTime ao tempo de pulo
                timeJumping += deltaTime;
                //caso botao de pulo tenha sido precionado por um periodo muito curto
                //faz com que o personagem pule a altura minima
                if (timeJumping > 0 && timeJumping <= JUMP_TIME_MIN) {
                    velocity.y = maxVelocity.y;
                }
        }
        if (jumpState != JUMP_STATE.GROUNDED) {
            super.updateMotionY(deltaTime);
        }
    }

    @Override
    public void render(SpriteBatch batch) {
        TextureRegion reg = null;
        //coloca cor diferenciada quando pega objeto que da poder
        if (hasSpecialPower) {
            batch.setColor(1.0f, 0.8f, 0.0f, 1.0f);
        }
        //desenha imagem
        reg = regWill;
        batch.draw(reg.getTexture(), position.x, position.y, origin.x,
                origin.y, dimension.x, dimension.y, scale.x, scale.y, rotation,
                reg.getRegionX(), reg.getRegionY(), reg.getRegionWidth(),
                reg.getRegionHeight(), viewDirection == VIEW_DIRECTION.LEFT,
                false);
        //volta para cor branca
        batch.setColor(1, 1, 1, 1);
    }
}
