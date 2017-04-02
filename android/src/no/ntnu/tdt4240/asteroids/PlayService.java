package no.ntnu.tdt4240.asteroids;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;

import com.badlogic.gdx.Gdx;
import com.google.android.gms.games.Games;
import com.google.android.gms.games.multiplayer.Invitation;
import com.google.android.gms.games.multiplayer.OnInvitationReceivedListener;
import com.google.android.gms.games.multiplayer.realtime.RealTimeMessage;
import com.google.android.gms.games.multiplayer.realtime.RealTimeMessageReceivedListener;
import com.google.android.gms.games.multiplayer.realtime.Room;
import com.google.android.gms.games.multiplayer.realtime.RoomConfig;
import com.google.android.gms.games.multiplayer.realtime.RoomStatusUpdateListener;
import com.google.android.gms.games.multiplayer.realtime.RoomUpdateListener;
import com.google.example.games.basegameutils.GameHelper;

import java.util.List;

import no.ntnu.tdt4240.asteroids.service.network.INetworkService;

import static no.ntnu.tdt4240.asteroids.AndroidLauncher.RC_WAITING_ROOM;


public class PlayService implements INetworkService, RoomUpdateListener, RealTimeMessageReceivedListener, RoomStatusUpdateListener, OnInvitationReceivedListener {

    private static final String TAG = "PlayService";


    private final AndroidLauncher activity;
    private final GameHelper gameHelper;
    private String incomingInvitationId;

    public PlayService(AndroidLauncher activity, GameHelper.GameHelperListener listener) {
        this.activity = activity;
        gameHelper = new GameHelper(activity, GameHelper.CLIENT_GAMES);
        gameHelper.enableDebugLog(true);
        gameHelper.setShowErrorDialogs(true);
        gameHelper.setup(listener);
    }

    public GameHelper getGameHelper() {
        return gameHelper;
    }

    @Override
    public void signIn() {
        try {
            activity.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    gameHelper.beginUserInitiatedSignIn();
                }
            });
        } catch (Exception e) {
            Gdx.app.log("MainActivity", "Log in failed: " + e.getMessage() + ".");
        }
    }

    @Override
    public void signOut() {
        try {
            activity.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    gameHelper.signOut();
                }
            });
        } catch (Exception e) {
            Gdx.app.log("MainActivity", "Log out failed: " + e.getMessage() + ".");
        }
    }

    @Override
    public void rateGame() {
        String str = "Your PlayStore Link";
        activity.startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(str)));
    }

    @Override
    public void unlockAchievement() {
        Games.Achievements.unlock(gameHelper.getApiClient(),
                activity.getString(R.string.achievement_dum_dum));
    }

    @Override
    public void submitScore(int highScore) {
        if (isSignedIn()) {
            Games.Leaderboards.submitScore(gameHelper.getApiClient(),
                    activity.getString(R.string.leaderboard_highest), highScore);
        }
    }

    @Override
    public void showAchievement() {
        if (isSignedIn()) {
            activity.startActivityForResult(Games.Achievements.getAchievementsIntent(gameHelper.getApiClient()), AndroidLauncher.RC_ACHIEVEMENTS);
        } else {
            signIn();
        }
    }

    @Override
    public void showScore() {
        if (isSignedIn()) {
            activity.startActivityForResult(Games.Leaderboards.getLeaderboardIntent(gameHelper.getApiClient(),
                    activity.getString(R.string.leaderboard_highest)), AndroidLauncher.RC_LEADERBOARD);
        } else {
            signIn();
        }
    }

    @Override
    public void sendUnreliableMessageToOthers(byte[] messageData) {
        Games.RealTimeMultiplayer.sendUnreliableMessageToOthers(gameHelper.getApiClient(), messageData, "roomId");
    }

    @Override
    public void setMessageReceivedListener(NetworkMessageReceivedListener listener) {
        // TODO: create adapter between NetworkMessageReceivedListener and RealTimeMessageReceivedListener
    }

    @Override
    public boolean isSignedIn() {
        return gameHelper.isSignedIn();
    }


    @Override
    public void startQuickGame() {
        // quick-start a game with 1 randomly selected opponent
        final int MIN_OPPONENTS = 1, MAX_OPPONENTS = 1;
        Bundle autoMatchCriteria = RoomConfig.createAutoMatchCriteria(MIN_OPPONENTS,
                MAX_OPPONENTS, 0);
        RoomConfig.Builder rtmConfigBuilder = RoomConfig.builder(this);
        rtmConfigBuilder.setMessageReceivedListener(this);
        rtmConfigBuilder.setRoomStatusUpdateListener(this);
        rtmConfigBuilder.setAutoMatchCriteria(autoMatchCriteria);
//        switchToScreen(R.id.screen_wait);
//        keepScreenOn();
//        resetGameVars();
        Games.RealTimeMultiplayer.create(gameHelper.getApiClient(), rtmConfigBuilder.build());
    }

    @Override
    public void viewSelectOpponents() {
        Intent intent = Games.RealTimeMultiplayer.getSelectOpponentsIntent(gameHelper.getApiClient(), 1, 3);
//        switchToScreen(R.id.screen_wait);
        activity.startActivityForResult(intent, AndroidLauncher.RC_SELECT_PLAYERS);
    }

    @Override
    public void onRoomCreated(int i, Room room) {
        Log.d(TAG, "onRoomCreated: ");
    }

    @Override
    public void onJoinedRoom(int i, Room room) {
        Log.d(TAG, "onJoinedRoom: ");
    }

    @Override
    public void onLeftRoom(int i, String s) {
        Log.d(TAG, "onLeftRoom: ");
    }

    @Override
    public void onRoomConnected(int i, Room room) {
        Log.d(TAG, "onRoomConnected: ");
    }

    @Override
    public void onRealTimeMessageReceived(RealTimeMessage realTimeMessage) {
        Log.d(TAG, "onRealTimeMessageReceived: ");
    }

    @Override
    public void onRoomConnecting(Room room) {
        Log.d(TAG, "onRoomConnecting: ");
    }

    @Override
    public void onRoomAutoMatching(Room room) {
        Log.d(TAG, "onRoomAutoMatching: ");
    }

    @Override
    public void onPeerInvitedToRoom(Room room, List<String> list) {
        Log.d(TAG, "onPeerInvitedToRoom: ");
    }

    @Override
    public void onPeerDeclined(Room room, List<String> list) {
        Log.d(TAG, "onPeerDeclined: ");
    }

    @Override
    public void onPeerJoined(Room room, List<String> list) {
        Log.d(TAG, "onPeerJoined: ");

    }

    @Override
    public void onPeerLeft(Room room, List<String> list) {
        Log.d(TAG, "onPeerLeft: ");
    }

    @Override
    public void onConnectedToRoom(Room room) {
        Log.d(TAG, "onConnectedToRoom: ");
    }

    @Override
    public void onDisconnectedFromRoom(Room room) {
        Log.d(TAG, "onDisconnectedFromRoom: ");
    }

    @Override
    public void onPeersConnected(Room room, List<String> list) {
        Log.d(TAG, "onPeersConnected: ");
    }

    @Override
    public void onPeersDisconnected(Room room, List<String> list) {
        Log.d(TAG, "onPeersDisconnected: ");
    }

    @Override
    public void onP2PConnected(String s) {
        Log.d(TAG, "onP2PConnected: ");
    }

    @Override
    public void onP2PDisconnected(String s) {
        Log.d(TAG, "onP2PDisconnected: ");
    }

    @Override
    public void onInvitationReceived(Invitation invitation) {
        // We got an invitation to play a game! So, store it in
        // incomingInvitationId
        // and show the popup on the screen.
        Log.d(TAG, "onInvitationReceived: ");
        incomingInvitationId = invitation.getInvitationId();
//        ((TextView) findViewById(R.id.incoming_invitation_text)).setText(
//                invitation.getInviter().getDisplayName() + " " +
//                        getString(R.string.is_inviting_you));
//        switchToScreen(mCurScreen); // This will show the invitation popup
    }

    @Override
    public void onInvitationRemoved(String s) {
        Log.d(TAG, "onInvitationRemoved: ");

    }

    // Leave the room.
    void leaveRoom() {
        Log.d(TAG, "Leaving room.");
//        mSecondsLeft = 0;
//        stopKeepingScreenOn();
//        if (mRoomId != null) {
//            Games.RealTimeMultiplayer.leave(mGoogleApiClient, this, mRoomId);
//            mRoomId = null;
//            switchToScreen(R.id.screen_wait);
//        } else {
//            switchToMainScreen();
//        }
    }

    // Show the waiting room UI to track the progress of other players as they enter the
    // room and get connected.
    void showWaitingRoom(Room room) {
        // minimum number of players required for our game
        // For simplicity, we require everyone to join the game before we start it
        // (this is signaled by Integer.MAX_VALUE).
        final int MIN_PLAYERS = Integer.MAX_VALUE;
        Intent i = Games.RealTimeMultiplayer.getWaitingRoomIntent(gameHelper.getApiClient(), room, MIN_PLAYERS);

        // show waiting room UI
        activity.startActivityForResult(i, RC_WAITING_ROOM);
    }
}
