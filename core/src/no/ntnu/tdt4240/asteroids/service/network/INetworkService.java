package no.ntnu.tdt4240.asteroids.service.network;

import java.util.ArrayList;

public interface INetworkService {
    void signIn();

    void signOut();

    void rateGame();

    void unlockAchievement();

    void submitScore(int highScore);

    void showAchievement();

    void showScore();

    void sendUnreliableMessageToOthers(byte[] messageData);

    void setMessageReceivedListener(IGameListener listener);

    boolean isSignedIn();

    void startQuickGame();

    void startSelectOpponents();

    void setGameListener(IGameListener gameListener);

    void setNetworkListener(INetworkListener networkListener);

    interface IGameListener {

        void onGameStarting();
    }

    interface INetworkListener {

        void onReliableMessageReceived(String senderParticipantId, int describeContents, byte[] messageData);

        void onUnreliableMessageReceived(String senderParticipantId, int describeContents, byte[] messageData);

        void onRoomReady(ArrayList<String> participantIds);
    }
}