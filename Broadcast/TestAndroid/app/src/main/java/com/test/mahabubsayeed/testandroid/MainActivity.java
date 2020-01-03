package com.test.mahabubsayeed.testandroid;

import android.content.Context;
import android.media.MediaPlayer;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.ConnectivityManager;
import android.net.wifi.WifiManager;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextClock;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;
import android.widget.ToggleButton;

import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends AppCompatActivity {
    ToggleButton setWifi;
    TextClock clock;
    TimePicker timePicker;
    MediaPlayer mediaPlayer;
    Button stop;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_main );
        setWifi= findViewById(R.id.toggole);
        clock= findViewById(R.id.textClock);
        timePicker= findViewById(R.id.timePicker);
        mediaPlayer= MediaPlayer.create(this, R.raw.alarmton);
        stop= findViewById(R.id.stop);

        final WifiManager wifiManager= (WifiManager) getApplicationContext().getSystemService(Context.WIFI_SERVICE);
        setWifi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (setWifi.isChecked()){
                    wifiManager.setWifiEnabled(true);
                    Toast.makeText(MainActivity.this, "Enable wifi", Toast.LENGTH_SHORT).show();
                }else {
                    wifiManager.setWifiEnabled(false);
                    Toast.makeText(MainActivity.this, "Disable wifi", Toast.LENGTH_SHORT).show();
                }
            }
        });
        ConnectivityManager connectivityManager= (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        /*if (connectivityManager.getActiveNetworkInfo()== null ){
            setWifi.setChecked(false);
        } else {
            setWifi.setChecked(true);
        }*/
        final Ringtone ringtone= RingtoneManager.getRingtone(getApplicationContext(),RingtoneManager.getDefaultUri(RingtoneManager.TYPE_RINGTONE));
        final Timer timer= new Timer();
        timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                if (clock.getText().toString().equals(AlarmTime())){
                    mediaPlayer.start();
                    ringtone.play();
                } else {
                    mediaPlayer.stop();
                    ringtone.stop();
                }
            }
        },0,1000);
        stop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ringtone.stop();
                /*
                * just stop ringtone service.... linke ringtone.stop();
                * */
                clock.setTimeZone(timer.toString());
            }
        });
    }

    private String AlarmTime() {
        Integer hour= timePicker.getCurrentHour();
        Integer menite= timePicker.getCurrentMinute();
        String alarmTime;
        String alarmMenite;
        if (menite<10){
            alarmMenite="0";
            alarmMenite= alarmMenite.concat(menite.toString());
        } else {
            alarmMenite= menite.toString();
        }
        if (hour>12){
            hour= hour-12;
            alarmTime= hour.toString().concat(":").concat(alarmMenite).concat(" PM");
        } else {
            alarmTime= hour.toString().concat(":").concat(alarmMenite).concat(" AM");
        }

        return alarmTime;
    }
}
