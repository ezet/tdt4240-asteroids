package no.ntnu.tdt4240.asteroids.input;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Touchable;

public class TouchpadButton extends Actor {

    private TextureRegion buttonRegion;

    public TouchpadButton() {
        Texture texture = new Texture("data/touchKnob.png");
        buttonRegion = new TextureRegion(texture);
        setTouchable(Touchable.enabled);

    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        batch.draw(buttonRegion, getX(), getY(), getWidth(), getHeight());
        super.draw(batch, parentAlpha);
    }
}
