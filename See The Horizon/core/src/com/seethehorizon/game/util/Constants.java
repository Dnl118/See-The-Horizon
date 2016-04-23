package com.seethehorizon.game.util;

/**
 * Created by Danilo on 03/04/2016.
 */
public class Constants{

    public static final float VIEWPORT_WIDTH = 5.0f;
    public static float VIEWPORT_HEIGHT = 5.0f;

    public static final float VIEWPORT_GUI_WIDTH = 800.0f;
    public static final float VIEWPORT_GUI_HEIGHT = 480.0f;

    public static final String TEXTURE_ATLAS_OBJECTS = "images/see_the_horizon.pack";

    public static final int LIVES_START = 3;
    public static final int MAX_LIVES = 3;

    public static final float POWER_DURATION  = 5.0f;

    //tempo de espera para respawn do will
    public static final float TIME_TO_RESPAWN = 3.0f;

    //minimo de rotacao do acelerometro para iniciar movimento
    public static final float MIN_ANGLE_FOR_ACCELERATION = 5.0f;
    //maimo de rotacao do acelerometro para atingir velocidade maxima
    public static final float MAX_ANGLE_FOR_ACCELERATION = 20.0f;

    public static final String LEVEL_01 = "levels/level-01.png";

}
