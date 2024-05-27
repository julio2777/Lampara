package com.example.lampara;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.ToggleButton;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

public class MainActivity extends Toolbarclass {

    private ToggleButton toggleButton;
    private ImageView imgfoco;
    private TextView textView;
    private BroadcastReceiver timerReceiver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar myToolbar = findViewById(R.id.toolbar);
        setupToolbar(myToolbar);

        toggleButton = findViewById(R.id.togglebutton);
        imgfoco = findViewById(R.id.imgfoco);
        textView = findViewById(R.id.textView3);

        toggleButton.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked) {
                imgfoco.setImageResource(R.drawable.messi_on);
                textView.setText("Encendido");
            } else {
                imgfoco.setImageResource(R.drawable.messi);
                textView.setText("Apagado");
            }
        });

        registerTimerReceiver();
    }

    @Override
    protected void onResume() {
        super.onResume();
        // Actualizar UI según sea necesario
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(timerReceiver);
    }

    private void registerTimerReceiver() {
        IntentFilter filter = new IntentFilter("com.example.lampara.TIMER_FINISHED");
        timerReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                boolean isLampOn = toggleButton.isChecked();
                toggleButton.setChecked(!isLampOn);
            }
        };
        registerReceiver(timerReceiver, filter);
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        setIntent(intent); // update the intent
    }
}
