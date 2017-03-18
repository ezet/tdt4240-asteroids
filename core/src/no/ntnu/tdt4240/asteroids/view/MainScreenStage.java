package no.ntnu.tdt4240.asteroids.view;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Scaling;
import com.badlogic.gdx.utils.viewport.ScalingViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

import no.ntnu.tdt4240.asteroids.controller.MainScreen;


public class MainScreenStage extends Stage implements IMainScreenView {

    private static final String TAG = MainScreenStage.class.getSimpleName();
    private static Viewport viewport = new ScalingViewport(Scaling.stretch, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
    public final Skin buttonSkin = new Skin(Gdx.files.internal("data/uiskin.json"));
    public final TextButton play = new TextButton("PLAY", buttonSkin);
    public final TextButton exit = new TextButton("EXIT", buttonSkin);
    private final Table table = new Table();
    private final BitmapFont defaultFont = new BitmapFont();
    private final Label.LabelStyle defaultLabelStyle = new Label.LabelStyle(defaultFont, Color.WHITE);
    private final TextButton.TextButtonStyle buttonStyle = new TextButton.TextButtonStyle();
    private final MainScreen.InputHandler inputHandler;
    // TODO: implement main screen gui


    public MainScreenStage(MainScreen.InputHandler inputHandler, Batch batch) {
        super(viewport, batch);
        this.inputHandler = inputHandler;
//        setDebugAll(true);
        init();
        addActor(table);
    }

    private void init() {
        play.getLabel().setFontScale(3);
        exit.getLabel().setFontScale(3);
        table.setFillParent(true);
        table.add(play).pad(30);
        table.row();
        table.add(exit).pad(30);
        table.row();
        play.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                inputHandler.onPlay();
            }
        });
        exit.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                inputHandler.onExit();
            }
        });
    }

    @Override
    public void update(float delta) {
        act(delta);
    }

    @Override
    public void draw() {
//        Batch batch = getBatch();
//        batch.disableBlending();
//        batch.begin();
//         TODO: draw background
//        batch.end();
//        batch.enableBlending();
        super.draw();
    }

    @Override
    public InputProcessor getInputProcessor() {
        return this;
    }
}
