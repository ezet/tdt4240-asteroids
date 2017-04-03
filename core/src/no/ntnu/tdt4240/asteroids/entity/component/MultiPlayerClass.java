package no.ntnu.tdt4240.asteroids.entity.component;

import com.badlogic.ashley.core.Component;

public class MultiPlayerClass implements Component {

    public final String id;

    public MultiPlayerClass(String id) {
        this.id = id;
    }
}
