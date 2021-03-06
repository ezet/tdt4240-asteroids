package no.ntnu.tdt4240.asteroids.view.widget;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.scenes.scene2d.ui.Widget;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;

class GamepadButton extends Widget {

    private final Drawable touchButton;
    private final ShapeRenderer shapeRenderer;

    GamepadButton(Drawable touchButton) {
        this.touchButton = touchButton;
        setTouchable(Touchable.enabled);
        shapeRenderer = new ShapeRenderer();
        shapeRenderer.setColor(1, 0, 0, 0.3f);
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        super.draw(batch, parentAlpha);
        Color c = getColor();
        batch.setColor(c.r, c.g, c.b, c.a * parentAlpha);
        touchButton.draw(batch, getX(), getY(), getWidth(), getHeight());
//        shapeRenderer.setProjectionMatrix(batch.getProjectionMatrix());
//        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
//        float radius = Math.min(getWidth(), getHeight()) / 2;
//        shapeRenderer.circle(getX() + radius, getY() + radius, radius);
//        shapeRenderer.end();
    }


}
