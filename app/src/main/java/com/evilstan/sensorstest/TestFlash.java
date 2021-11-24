package com.evilstan.sensorstest;

import android.annotation.SuppressLint;
import android.content.Context;
import android.hardware.camera2.CameraAccessException;
import android.hardware.camera2.CameraManager;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton;
import com.google.android.material.slider.Slider;
import com.google.android.material.slider.Slider.OnSliderTouchListener;
import com.google.android.material.switchmaterial.SwitchMaterial;
import com.google.android.material.textfield.TextInputEditText;
import java.util.Timer;
import java.util.TimerTask;

public class TestFlash extends AppCompatActivity implements
    OnClickListener,
    OnSliderTouchListener {

    private CameraManager cameraManager;
    private String getCameraID;
    private boolean isFlashOn;

    ExtendedFloatingActionButton sosButton;
    ExtendedFloatingActionButton strobeTextButton;
    SwitchMaterial flashSwitch;
    SwitchMaterial strobeSwitch;

    Slider slider;
    Timer timer;
    TextInputEditText flashTextInput;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_flash);

        initComponents();
        initFlash();
    }


    @SuppressLint("ClickableViewAccessibility")
    private void initComponents() {
        sosButton = findViewById(R.id.sos_flash_button);
        strobeTextButton = findViewById(R.id.strobe_text_button);
        strobeSwitch = findViewById(R.id.strobe_switch);
        flashSwitch = findViewById(R.id.flash_switch);
        slider = findViewById(R.id.strobing_interval_slider);

        flashTextInput = findViewById((R.id.flash_text_input));

        slider.addOnSliderTouchListener(this);

        strobeSwitch.setOnClickListener(this);
        flashSwitch.setOnClickListener(this);

        sosButton.setOnClickListener(this);
        strobeTextButton.setOnClickListener(this);
    }


    private void initFlash() {
        cameraManager = (CameraManager) getSystemService(Context.CAMERA_SERVICE);
        isFlashOn = false;
        try {
            // O back camera,
            // 1 front camera
            getCameraID = cameraManager.getCameraIdList()[0];
        } catch (CameraAccessException e) {
            e.printStackTrace();
        }
    }


    @Override
    public void onStartTrackingTouch(@NonNull Slider slider) {

    }

    @Override
    public void onStopTrackingTouch(@NonNull Slider slider) {
        if (strobeSwitch.isChecked()) {
            stroboscope(false, (long) slider.getValue());
            stroboscope(true, (long) slider.getValue());
        }
    }

    @Override
    public void onClick(View view) {
        String id = getResources().getResourceEntryName(view.getId());

        switch (id) {
            case "sos_flash_button":
                turnOffEverything();
                break;
            case "strobe_text_button":
                turnOffEverything();
                break;
            case "strobe_switch":
                flashSwitch.setChecked(false);
                toggleFlash(false);
                stroboscope(strobeSwitch.isChecked(), (long) slider.getValue());
                break;
            case "flash_switch":
                strobeSwitch.setChecked(false);
                strobeOff();
                toggleFlash(flashSwitch.isChecked());
                break;
        }
    }

    private void strobeMorse(String text){
        String[] morseText = MorseConvertor.convertTextTomMorse(text);

    }

    private void turnOffEverything(){
        flashSwitch.setChecked(false);
        strobeSwitch.setChecked(false);
        toggleFlash(false);
        strobeOff();
    }

    private void strobeOff(){
        stroboscope(false, 100);
    }

    boolean timerOn = false;

    private void stroboscope(boolean start, long interval) {
        if (!start) {
            timerOn = true;
            toggleFlash(false);
        }

        if (timerOn) {
            try {
                timer.cancel();
            } catch (Exception ignored) {}
            timerOn = false;
        } else {
            TimerTask timerTask = new TimerTask() {
                @Override
                public void run() {
                    strobe();
                }
            };
            timer = new Timer();
            timer.schedule(timerTask, 1, interval);
            timerOn = true;
        }
    }

    private void strobe() {
        toggleFlash(!isFlashOn);
    }

    private void toggleFlash(boolean turnedOn) {
        try {
            cameraManager.setTorchMode(getCameraID, turnedOn);
            isFlashOn = turnedOn;
        } catch (CameraAccessException e) {
            e.printStackTrace();
        }
    }
}