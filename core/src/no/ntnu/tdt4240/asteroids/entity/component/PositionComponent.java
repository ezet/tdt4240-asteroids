package no.ntnu.tdt4240.asteroids.entity.component;

import com.badlogic.ashley.core.Component;

public class PositionComponent implements Component {

    public float x;

    public float y;

    public PositionComponent(int x, int y) {
        this.x = x;
        this.y = y;
    }
}
