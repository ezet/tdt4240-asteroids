package no.ntnu.tdt4240.asteroids.game.entity.component;

import com.badlogic.ashley.core.Component;
import com.badlogic.gdx.utils.Pool;

public class PlayerClass implements Component, Pool.Poolable {

    public String displayName;

    public boolean isSelf;

    public PlayerClass(String displayName) {
        this.displayName = displayName;
    }

    @SuppressWarnings("unused")
    public PlayerClass() {
        reset();
    }

    @Override
    public void reset() {
        displayName = "";
    }

}
