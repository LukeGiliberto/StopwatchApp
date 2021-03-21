package au.edu.jcu.cp3406.stopwatchapp;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.SeekBar;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class SettingsActivity extends AppCompatActivity {

    public static int SETTINGS_REQUEST = 1234;
    //private EditText editText;
    private SeekBar seekBar;
    private TextView speedGauge;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        //int oldSpeed = savedInstanceState.getInt("speed", 500);
        setContentView(R.layout.activity_settings);
        seekBar = findViewById(R.id.speed);
        speedGauge = findViewById(R.id.speedGauge);
        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {

            int progressChangedValue;
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                progressChangedValue = progress;
            }
            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
            }
            @SuppressLint("SetTextI18n")
            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                int speedValue = progressChangedValue + 500;
                speedGauge.setText("Speed: "+speedValue+"ms/tick");
            }
        });

    }

    public void doneClicked(View view){
        int speed = (seekBar.getProgress() + 500); //subtract value to make ticks faster

        Intent data = new Intent();
        data.putExtra("speed", speed);
        setResult(RESULT_OK, data);
        finish();
    }

}
