package com.example.lampara;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.Button;
import android.widget.NumberPicker;
import android.widget.TextView;
import androidx.appcompat.widget.Toolbar;
import androidx.cardview.widget.CardView;

public class temporizador extends Toolbarclass {
    private NumberPicker numberPickerHours, numberPickerMinutes, numberPickerSeconds;
    private TextView selectedTimeTextView;
    private Button startButton, stopButton;
    private CardView notificationCardView;
    private CountDownTimer countDownTimer;
    private long timeLeftInMillis;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_temporizador);

        Toolbar myToolbar = findViewById(R.id.toolbar);
        setupToolbar(myToolbar);

        numberPickerHours = findViewById(R.id.numberPickerHours);
        numberPickerMinutes = findViewById(R.id.numberPickerMinutes);
        numberPickerSeconds = findViewById(R.id.numberPickerSeconds);
        selectedTimeTextView = findViewById(R.id.selectedTimeTextView);
        startButton = findViewById(R.id.startButton);
        stopButton = findViewById(R.id.stopButton);
        notificationCardView = findViewById(R.id.notificationCardView);

        numberPickerHours.setMinValue(0);
        numberPickerHours.setMaxValue(23);
        numberPickerMinutes.setMinValue(0);
        numberPickerMinutes.setMaxValue(59);
        numberPickerSeconds.setMinValue(0);
        numberPickerSeconds.setMaxValue(59);

        numberPickerHours.setOnValueChangedListener((picker, oldVal, newVal) -> updateSelectedTime());
        numberPickerMinutes.setOnValueChangedListener((picker, oldVal, newVal) -> updateSelectedTime());
        numberPickerSeconds.setOnValueChangedListener((picker, oldVal, newVal) -> updateSelectedTime());

        startButton.setOnClickListener(v -> startTimer());
        stopButton.setOnClickListener(v -> stopTimer());
    }

    private void updateSelectedTime() {
        String time = String.format("%02d:%02d:%02d", numberPickerHours.getValue(), numberPickerMinutes.getValue(), numberPickerSeconds.getValue());
        selectedTimeTextView.setText(time);
    }

    private void startTimer() {
        int hours = numberPickerHours.getValue();
        int minutes = numberPickerMinutes.getValue();
        int seconds = numberPickerSeconds.getValue();

        timeLeftInMillis = (hours * 3600 + minutes * 60 + seconds) * 1000;

        countDownTimer = new CountDownTimer(timeLeftInMillis, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                timeLeftInMillis = millisUntilFinished;
                updateCountDownText();
            }

            @Override
            public void onFinish() {
                showNotification();
                sendTimerFinishedBroadcast();
            }
        }.start();
    }

    private void updateCountDownText() {
        int hours = (int) (timeLeftInMillis / 1000) / 3600;
        int minutes = (int) ((timeLeftInMillis / 1000) % 3600) / 60;
        int seconds = (int) (timeLeftInMillis / 1000) % 60;

        String timeLeftFormatted = String.format("%02d:%02d:%02d", hours, minutes, seconds);
        selectedTimeTextView.setText(timeLeftFormatted);
    }

    private void stopTimer() {
        if (countDownTimer != null) {
            countDownTimer.cancel();
            countDownTimer = null;
        }
        notificationCardView.setVisibility(View.GONE); // Hide notification if timer is stopped
    }

    private void showNotification() {
        notificationCardView.setVisibility(View.VISIBLE);
    }

    private void sendTimerFinishedBroadcast() {
        Intent intent = new Intent("com.example.lampara.TIMER_FINISHED");
        sendBroadcast(intent);
    }
}
