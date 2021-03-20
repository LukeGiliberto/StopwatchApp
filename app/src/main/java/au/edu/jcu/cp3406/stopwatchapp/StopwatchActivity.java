package au.edu.jcu.cp3406.stopwatchapp;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class StopwatchActivity extends AppCompatActivity {

    private Stopwatch stopwatch = new Stopwatch();
    private Handler handler;
    private boolean isRunning;
    private TextView display;
    private Button toggle;
    private int speed;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        display = findViewById(R.id.display);
        toggle = findViewById(R.id.toggle);

        speed = 1000;
        isRunning = false;
        if (savedInstanceState == null){
            stopwatch = new Stopwatch();
        }
        else {
            stopwatch = new Stopwatch(savedInstanceState.getString("value"));
            display.setText(stopwatch.toString());
            boolean running = savedInstanceState.getBoolean("running");
            if (running){
                enableStopwatch();
            }
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState){
        super.onSaveInstanceState(outState);
        outState.putString("value", stopwatch.toString());
        outState.putBoolean("running", isRunning);
    }

    @SuppressLint("SetTextI18n")
    private void enableStopwatch(){
        isRunning = true;
        toggle.setText("stop");
        handler = new Handler();
        handler.post(new Runnable() {
            @Override
            public void run() {
                if (isRunning){
                    stopwatch.tick();
                    display.setText(stopwatch.toString());
                    handler.postDelayed(this,speed);
                }
            }
        });
    }

    private void disableStopwatch(){
        toggle = (Button) findViewById(R.id.toggle);
        toggle.setText("start");
        isRunning = false;
    }

    public void buttonClicked(View view){
        if (isRunning) {
            disableStopwatch();
        }
        else {
            enableStopwatch();
        }
    }

    @SuppressLint("SetTextI18n")
    public void resetClicked(View view){
        stopwatch = new Stopwatch();
        display.setText("00:00:00");
    }

    public void settingsClicked(View view){
        Intent intent = new Intent(this, SettingsActivity.class);
        startActivityForResult(intent, SettingsActivity.SETTINGS_REQUEST);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data){
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == SettingsActivity.SETTINGS_REQUEST){
            if (resultCode == RESULT_OK){
                if (data != null){
                    speed = data.getIntExtra("speed", 1000);
                }
            }
        }
    }


}
