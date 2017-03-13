package no.ntnu.tdt4240.asteroids.entity;

import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.Touchpad;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;

import no.ntnu.tdt4240.asteroids.entity.component.VelocityComponent;

import static no.ntnu.tdt4240.asteroids.entity.util.ComponentMappers.velocityMapper;

public class InputHandler extends ChangeListener {

    private static final String TAG = InputHandler.class.getSimpleName();
    private final Entity player;

    public InputHandler(Entity player) {
        this.player = player;
    }

    @Override
    public void changed(ChangeEvent event, Actor actor) {
        VelocityComponent velocityComponent = velocityMapper.get(player);

        Touchpad touchpad = (Touchpad) actor;
        float knobPercentX = touchpad.getKnobPercentX();
        float knobPercentY = touchpad.getKnobPercentY();
        velocityComponent.setX(knobPercentX * 300);
        velocityComponent.setY(knobPercentY * 300);
    }
}
