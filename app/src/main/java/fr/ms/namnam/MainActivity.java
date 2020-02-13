package fr.ms.namnam;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity {

    private static final String CHANNEL_ID = "0";
    private int notificationId;
    private EditText titre;
    private EditText message;
    private Button creer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        notificationId = 0;

        titre = findViewById(R.id.titre);
        message = findViewById(R.id.message);
        creer = findViewById(R.id.creer);

        creer.setOnClickListener(onClickCreer());

        createNotificationChannel();
    }

    private View.OnClickListener onClickCreer(){
        return new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String sTitre = titre.getText().toString();
                String sMessage = message.getText().toString();

                if(sTitre != null && sMessage != null && sTitre.length() > 0 && sMessage.length() > 0){
                    creerNotification(sTitre, sMessage);
                    titre.setText("");
                    message.setText("");
                }
            }
        };
    }

    private void creerNotification(String titre, String message){
        NotificationCompat.Builder builder = new NotificationCompat.Builder(this, CHANNEL_ID)
                .setSmallIcon(R.mipmap.round_ic_launcher)
                .setContentTitle(titre)
                .setContentText(message)
                .setPriority(NotificationCompat.PRIORITY_DEFAULT);

        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(this);
        notificationManager.notify(notificationId++, builder.build());
        Log.d("namnam", "Notification id : " + (notificationId - 1) + " créée avec succès");
    }

    private void createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            CharSequence name = getString(R.string.channel_name);
            String description = getString(R.string.channel_description);
            int importance = NotificationManager.IMPORTANCE_DEFAULT;
            NotificationChannel channel = new NotificationChannel(CHANNEL_ID, name, importance);
            channel.setDescription(description);

            NotificationManager notificationManager = getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }
    }
}