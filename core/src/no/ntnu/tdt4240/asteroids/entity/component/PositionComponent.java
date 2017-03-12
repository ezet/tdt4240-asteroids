package no.ntnu.tdt4240.asteroids.entity.component;

import com.badlogic.ashley.core.Component;

public class PositionComponent implements Component {

    public float x;

    public float y;

    public float getX() {
        return x;
    }

    public void setX(float x) {
        this.x = x;
    }

    public float getY() {
        return y;
    }

    public void setY(float y) {
        this.y = y;
    }
}
