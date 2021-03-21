package au.edu.jcu.cp3406.stopwatchapp;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import android.annotation.SuppressLint;
import android.app.AlertDialog;
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
        isRunning = false;
        if (savedInstanceState == null){
            stopwatch = new Stopwatch();
            speed = 1000;
        }
        else {
            stopwatch = new Stopwatch(savedInstanceState.getString("value"));
            display.setText(stopwatch.toString());
            speed = savedInstanceState.getInt("speed");
            boolean running = savedInstanceState.getBoolean("running");
            if (running){
                enableStopwatch();
            }
        }
    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState){
        super.onSaveInstanceState(outState);
        outState.putString("value", stopwatch.toString());
        outState.putBoolean("running", isRunning);
        outState.putInt("speed", speed);
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

    @SuppressLint("SetTextI18n")
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
        intent.putExtra("speed", speed);
        startActivityForResult(intent, SettingsActivity.SETTINGS_REQUEST);
    }

    /**
     * help from: https://stackoverflow.com/questions/6413700/android-proper-way-to-use-onbackpressed-with-toast
     */
    public void onBackPressed (){
        new AlertDialog.Builder(this)
                .setTitle("Really Exit?")
                .setMessage("Are you sure you want to exit?")
                .setNegativeButton("no", null)
                //.setNeutralButton("maybe",null)
                .setPositiveButton("yes",
                        (arg0, arg1) -> StopwatchActivity.super.onBackPressed()).create().show();
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
