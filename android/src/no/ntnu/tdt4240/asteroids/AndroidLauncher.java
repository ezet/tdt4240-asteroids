package no.ntnu.tdt4240.asteroids;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.WindowManager;

import com.badlogic.gdx.backends.android.AndroidApplication;
import com.badlogic.gdx.backends.android.AndroidApplicationConfiguration;
import com.google.android.gms.games.Games;
import com.google.android.gms.games.multiplayer.Multiplayer;
import com.google.android.gms.games.multiplayer.realtime.RoomConfig;
import com.google.example.games.basegameutils.GameHelper;

import java.util.ArrayList;

public class AndroidLauncher extends AndroidApplication implements GameHelper.GameHelperListener {


    public static final int RC_ACHIEVEMENTS = 1;
    public static final int RC_SELECT_PLAYERS = 10000;
    public static final int RC_LEADERBOARD = 2;
    public static final int RC_INVITATION_INBOX = 10001;
    public final static int RC_WAITING_ROOM = 10002;
    private static final String TAG = AndroidLauncher.class.getSimpleName();
    private static AndroidLauncher instance;
    private PlayService playService;

    public AndroidLauncher() {
        instance = this;
    }

    public static AndroidLauncher getInstance() {
        return instance;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        AndroidApplicationConfiguration config = new AndroidApplicationConfiguration();
        config.useCompass = false;
        config.useAccelerometer = false;
        config.useImmersiveMode = true;
        config.useWakelock = true;
        playService = new PlayService(this, this);
        initialize(new Asteroids(playService), config);
    }

    @Override
    protected void onStart() {
        super.onStart();
        playService.getGameHelper().onStart(this);
    }

    @Override
    protected void onStop() {
        super.onStop();
        playService.getGameHelper().onStop();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case RC_ACHIEVEMENTS:
                Log.d(TAG, "onActivityResult: RC_ACHIEVEMENTS");
                break;
            case RC_LEADERBOARD:
                Log.d(TAG, "onActivityResult: RC_LEADERBOARD");
                break;
            case RC_SELECT_PLAYERS:
                Log.d(TAG, "onActivityResult: RC_SELECT_PLAYERS");
                handleSelectPlayersResult(resultCode, data);
                break;
            case RC_INVITATION_INBOX:
                handleInvitationInboxResult(resultCode, data);
                break;
            case RC_WAITING_ROOM:
                handleWaitingRoomResult(resultCode, data);
                break;
            default:
                playService.getGameHelper().onActivityResult(requestCode, resultCode, data);
        }
    }

    private void handleWaitingRoomResult(int resultCode, Intent data) {
        Log.d(TAG, "handleWaitingRoomResult: ");

    }

    private void handleInvitationInboxResult(int resultCode, Intent data) {
        Log.d(TAG, "handleInvitationInboxResult: ");

    }

    private void handleSelectPlayersResult(int response, Intent data) {
        Log.d(TAG, "handleSelectPlayersResult: ");
        if (response != Activity.RESULT_OK) {
            Log.w(TAG, "*** select players UI cancelled, " + response);
//            switchToMainScreen();
            return;
        }

        Log.d(TAG, "Select players UI succeeded.");

        // get the invitee list
        final ArrayList<String> invitees = data.getStringArrayListExtra(Games.EXTRA_PLAYER_IDS);
        Log.d(TAG, "Invitee count: " + invitees.size());

        // get the automatch criteria
        Bundle autoMatchCriteria = null;
        int minAutoMatchPlayers = data.getIntExtra(Multiplayer.EXTRA_MIN_AUTOMATCH_PLAYERS, 0);
        int maxAutoMatchPlayers = data.getIntExtra(Multiplayer.EXTRA_MAX_AUTOMATCH_PLAYERS, 0);
        if (minAutoMatchPlayers > 0 || maxAutoMatchPlayers > 0) {
            autoMatchCriteria = RoomConfig.createAutoMatchCriteria(
                    minAutoMatchPlayers, maxAutoMatchPlayers, 0);
            Log.d(TAG, "Automatch criteria: " + autoMatchCriteria);
        }

        // create the room
        Log.d(TAG, "Creating room...");
        RoomConfig.Builder rtmConfigBuilder = RoomConfig.builder(playService);
        rtmConfigBuilder.addPlayersToInvite(invitees);
        rtmConfigBuilder.setMessageReceivedListener(playService);
        rtmConfigBuilder.setRoomStatusUpdateListener(playService);
        if (autoMatchCriteria != null) {
            rtmConfigBuilder.setAutoMatchCriteria(autoMatchCriteria);
        }
//        switchToScreen(R.id.screen_wait);
        keepScreenOn();
//        resetGameVars();
        Games.RealTimeMultiplayer.create(playService.getGameHelper().getApiClient(), rtmConfigBuilder.build());
        Log.d(TAG, "Room created, waiting for it to be ready...");
    }


    @Override
    public void onSignInFailed() {
        Log.d(TAG, "onSignInFailed: ");
//        playService.getGameHelper().beginUserInitiatedSignIn();
    }

    @Override
    public void onSignInSucceeded() {
        Log.d(TAG, "onSignInSucceeded: ");
    }

    // Sets the flag to keep this screen on. It's recommended to do that during
    // the
    // handshake when setting up a game, because if the screen turns off, the
    // game will be
    // cancelled.
    void keepScreenOn() {
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
    }

    // Clears the flag that keeps the screen on.
    void stopKeepingScreenOn() {
        getWindow().clearFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
    }

}
