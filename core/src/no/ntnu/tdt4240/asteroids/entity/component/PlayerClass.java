package no.ntnu.tdt4240.asteroids.entity.component;

import com.badlogic.ashley.core.Component;
import com.badlogic.gdx.utils.Pool;

public class PlayerClass implements Component, Pool.Poolable {

    public String id = "";

    public PlayerClass(String id) {
        this.id = id;
    }

    public PlayerClass() {
    }

    @Override
    public void reset() {
        id = "";
    }

}
