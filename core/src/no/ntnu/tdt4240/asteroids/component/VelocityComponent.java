package no.ntnu.tdt4240.asteroids.component;

import com.badlogic.ashley.core.Component;

public class VelocityComponent implements Component {

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
