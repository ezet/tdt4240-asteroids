package no.ntnu.tdt4240.asteroids.controller;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;

import no.ntnu.tdt4240.asteroids.Asteroids;
import no.ntnu.tdt4240.asteroids.service.ServiceLocator;
import no.ntnu.tdt4240.asteroids.view.IView;
import no.ntnu.tdt4240.asteroids.view.MultiplayerView;

public class MultiplayerController extends ScreenAdapter implements IMultiplayerController {

    @SuppressWarnings("unused")
    private static final String TAG = MultiplayerController.class.getSimpleName();
    private final Asteroids game;
    private final MultiplayerView view;


    public MultiplayerController(final Asteroids game) {
        this.game = game;
        view = new MultiplayerView(game.getBatch(), this);
    }

    @Override
    public void show() {
        super.show();
        Gdx.input.setInputProcessor(view.getInputProcessor());
        Gdx.app.debug(TAG, "Show");


    }

    @Override
    public void hide() {
        super.hide();
        Gdx.input.setInputProcessor(null);
        Gdx.app.debug(TAG, "HIDE");


    }

    @Override
    public void render(float delta) {
        update(delta);
        draw();
    }

    @Override
    public void resize(int width, int height) {
        super.resize(width, height);
        view.resize(width, height);
    }

    private void update(float delta) {
        view.update(delta);
        // TODO: handle input and process events
    }

    private void draw() {
        view.draw();
    }

    @Override
    public void onQuickgame() {

    }

    @Override
    public void onHostGame() {

    }

    @Override
    public void onBack() {
        game.setScreen(new MainController(game));
    }

    @Override
    public void onInvitePlayers() {
        ServiceLocator.getAppComponent().getNetworkService().viewSelectOpponents();
    }


    public interface IMainView extends IView {
        void resize(int width, int height);
    }
}
