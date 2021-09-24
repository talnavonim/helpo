package huji.postpc.y2021.talme.myapplication;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.os.Build;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.core.app.NotificationManagerCompat;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

public class PushNotificationService extends FirebaseMessagingService {
    private final String CHAT_CHANNEL = "CHAT_CHANNEL";
    private final String OFFERS_CHANNEL = "OFFERS_CHANNEL";
    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public void onMessageReceived(@NonNull RemoteMessage remoteMessage) {
        super.onMessageReceived(remoteMessage);
        String title = remoteMessage.getNotification().getTitle();
        String body = remoteMessage.getNotification().getBody();
        int notificationCounter = 1;

        // todo - check if offers or chat

        NotificationChannel channel = new NotificationChannel(OFFERS_CHANNEL,"newOffers", NotificationManager.IMPORTANCE_HIGH);
        getSystemService(NotificationManager.class).createNotificationChannel(channel);
        Notification.Builder notification = new Notification.Builder(this,OFFERS_CHANNEL)
                .setContentTitle("New offer for your request!")
                .setContentText("Check HelpoApp for more info")
                .setSmallIcon(R.drawable.googleg_disabled_color_18)
                .setAutoCancel(true);
        NotificationManagerCompat.from(this).notify(notificationCounter,notification.build());
    }
}
