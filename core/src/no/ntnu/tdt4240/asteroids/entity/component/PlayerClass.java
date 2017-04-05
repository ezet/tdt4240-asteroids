package no.ntnu.tdt4240.asteroids.entity.component;

import com.badlogic.ashley.core.Component;
import com.badlogic.gdx.utils.Pool;

public class PlayerClass implements Component, Pool.Poolable {

    public String id;
    private int score = 0;

    public PlayerClass(String id) {
        this.id = id;
    }

    public PlayerClass() {
        this.id = "noID";
    }

    public void incrementScore(){
        score++;
    }

    public int getScore(){
        return score;
    }

    @Override
    public void reset() {
        id = "";
    }

}
