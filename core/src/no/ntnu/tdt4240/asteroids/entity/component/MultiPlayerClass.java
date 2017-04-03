package no.ntnu.tdt4240.asteroids.entity.component;

import com.badlogic.ashley.core.Component;
import com.badlogic.gdx.utils.Pool;

public class MultiPlayerClass implements Component, Pool.Poolable {

    public String id;

    public MultiPlayerClass() {
        id = null;
    }

    public MultiPlayerClass(String id) {
        this.id = id;
    }

    @Override
    public void reset() {
        id = null;
    }
}
