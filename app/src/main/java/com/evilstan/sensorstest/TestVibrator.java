package com.evilstan.sensorstest;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Build.VERSION;
import android.os.Build.VERSION_CODES;
import android.os.Bundle;
import android.os.VibrationEffect;
import android.os.Vibrator;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import androidx.appcompat.app.AppCompatActivity;
import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton;
import com.google.android.material.textfield.TextInputEditText;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class TestVibrator extends AppCompatActivity implements
    OnTouchListener,
    OnClickListener {

    Vibrator vibrator;

    ExtendedFloatingActionButton vibrateButton;
    ExtendedFloatingActionButton clickButton;
    ExtendedFloatingActionButton doubleClickButton;
    ExtendedFloatingActionButton heavyClickButton;
    ExtendedFloatingActionButton tickButton;
    ExtendedFloatingActionButton sosButton;
    ExtendedFloatingActionButton vibrateTextButton;

    TextInputEditText vibrateTextInput;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vibrator_test);

        setTitle(R.string.check_vibrator);
        initComponents();
    }

    @SuppressLint("ClickableViewAccessibility")
    private void initComponents() {
        vibrator = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);

        vibrateButton = findViewById(R.id.vibrate_button);
        clickButton = findViewById(R.id.flash_button);
        doubleClickButton = findViewById(R.id.strobe_button);
        heavyClickButton = findViewById(R.id.heavy_click_button);
        tickButton = findViewById(R.id.tick_button);
        sosButton = findViewById(R.id.sos_button);
        vibrateTextButton = findViewById(R.id.vibrate_text_button);

        vibrateTextInput = findViewById(R.id.vibrate_text_input);

        vibrateButton.setOnTouchListener(this);
        clickButton.setOnClickListener(this);
        doubleClickButton.setOnClickListener(this);
        heavyClickButton.setOnClickListener(this);
        tickButton.setOnClickListener(this);
        sosButton.setOnClickListener(this);
        vibrateTextButton.setOnClickListener(this);
    }

    @Override
    public boolean onTouch(View view, MotionEvent motionEvent) {
        int action = motionEvent.getActionMasked();
        if (action == MotionEvent.ACTION_DOWN) {
            long[] pattern = {0, 200, 0};
            vibrator.vibrate(pattern, 0); // 0 to repeat endlessly.
        } else if (action == MotionEvent.ACTION_UP || action == MotionEvent.ACTION_CANCEL) {
            vibrator.cancel();
        }
        return false;
    }


    @Override
    public void onClick(View view) {
        String id = getResources().getResourceEntryName(view.getId());

        vibrator.cancel();

        if (VERSION.SDK_INT >= VERSION_CODES.Q) {
            switch (id) {
                case "vibrateButton":
                    vibrateWithEffect(VibrationEffect.DEFAULT_AMPLITUDE);
                    break;
                case "click_button":
                    vibrateWithEffect(VibrationEffect.EFFECT_CLICK);
                    break;
                case "double_click_button":
                    vibrateWithEffect(VibrationEffect.EFFECT_DOUBLE_CLICK);
                    break;
                case "heavy_click_button":
                    vibrateWithEffect(VibrationEffect.EFFECT_HEAVY_CLICK);
                    break;
                case "tick_button":
                    vibrateWithEffect(VibrationEffect.EFFECT_TICK);
                    break;
                case "sos_button":
                    vibratePattern("SOS");
                    break;
                case "vibrate_text_button":
                    vibrateText();
                    break;
            }
        } else {
            vibrator.vibrate(500);
        }
    }

    @SuppressLint("NewApi")
    private void vibrateWithEffect(int effect) {
        if (effect == VibrationEffect.DEFAULT_AMPLITUDE) {
            vibrator.vibrate(VibrationEffect.createOneShot(200, VibrationEffect.DEFAULT_AMPLITUDE));
        } else {
            vibrator.vibrate(VibrationEffect.createPredefined(effect));
        }
    }

    private void vibrateText() {
        String s = vibrateTextInput.getText().toString();
        vibratePattern(s);
    }

    private void vibratePattern(String string) {
        String[] text = textToMorse(string);
        List<Long> patternList = new ArrayList<>();

        long dot = 100;
        long dash = 3 * dot;
        long pause = dot;
        long pauseSplitLetters = 3*dot;
        long pauseSplitWords = 7*dot;

        //iterate letters in text
        for (String letter : text) {
            boolean firstSymbol = true;

            //in case of space add longest pause, empty vibrating time and break iteration in loop
            if  (letter.equals(" ")){
                patternList.add(pauseSplitWords);
                patternList.add((long)0);
                continue;
            }

            //iterate symbols in letter
            String[] symbols = letter.split("");
            for (String symbol : symbols) {

                //if first symbol in letter, add long pause before, After, add dor or dash
                if (firstSymbol) {
                    patternList.add(pauseSplitLetters);
                    firstSymbol = false;
                } else {
                    patternList.add(pause);
                }

                switch (symbol) {
                    case "-":
                        patternList.add(dash);
                        break;
                    case ".":
                        patternList.add(dot);
                        break;
                }
            }
        }

        //set first pause to zero
        //patternList.set(0, (long) 0);

        long[] pattern = new long[patternList.size()];
        for (int i = 0; i < patternList.size(); i++) {
            pattern[i] = patternList.get(i);
        }

        vibrator.vibrate(pattern, -1);
    }

    private String[] textToMorse(String text) {
        String[] textArray = text.split("");
        String[] result = new String[textArray.length];

        for (int i = 0; i < textArray.length; i++) {
            String letter = textArray[i];
            result[i] = getMorseCodeOfLetter(letter);
        }

        return result;
    }

    private String getMorseCodeOfLetter(String s) {
        s = s.toLowerCase(Locale.ROOT);
        switch (s) {
            case "1":
                return ".----";
            case "2":
                return "..---";
            case "3":
                return "...--";
            case "4":
                return "....-";
            case "5":
                return ".....";
            case "6":
                return "-....";
            case "7":
                return "--...";
            case "8":
                return "---..";
            case "9":
                return "----.";
            case "0":
                return "-----";
            case "a":
                return ".-";
            case "b":
                return "-...";
            case "c":
                return "-.-.";
            case "d":
                return "-..";
            case "e":
                return ".";
            case "f":
                return "..-.";
            case "g":
                return "--.";
            case "h":
                return "....";
            case "i":
                return "..";
            case "k":
                return ".---";
            case "l":
                return ".-..";
            case "m":
                return "--";
            case "n":
                return "-.";
            case "o":
                return "---";
            case "p":
                return ".--.";
            case "q":
                return "--.-";
            case "r":
                return ".-.";
            case "s":
                return "...";
            case "t":
                return "-";
            case "u":
                return "..-";
            case "v":
                return "...-";
            case "w":
                return ".--";
            case "x":
                return "-..-";
            case "y":
                return "-.--";
            case "z":
                return "--..";
            default:
                return " ";
        }
    }
}