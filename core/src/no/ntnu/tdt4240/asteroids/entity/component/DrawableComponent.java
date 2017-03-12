package no.ntnu.tdt4240.asteroids.entity.component;

import com.badlogic.ashley.core.Component;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class DrawableComponent implements Component {

    public final TextureRegion region;

    public DrawableComponent(TextureRegion region) {
        this.region = region;
    }

}
