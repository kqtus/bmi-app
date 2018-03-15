package com.example.marek.bmi;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import java.util.Locale;

public class ResultActivity extends AppCompatActivity {
    private double result;
    public final static double UPPER_OK_RANGE = 25.0f;
    public final static double LOWER_OK_RANGE = 18.0f;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);

        deserializeReceivedData();
        setupResult();
        updateBackgroundColor();
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    protected void deserializeReceivedData() {
        Bundle extras = getIntent().getExtras();

        if (extras != null) {
            result = extras.getDouble("result");
        }
    }

    protected void updateBackgroundColor() {
        int bkColour;
        if (result <= UPPER_OK_RANGE && result >= LOWER_OK_RANGE) {
            bkColour = getResources().getColor(R.color.colorGoodBmi);
        }
        else {
            bkColour = getResources().getColor(R.color.colorBadBmi);
        }

        ((TextView)findViewById(R.id.bmiResult)).setTextColor(bkColour);
    }

    protected void setupResult() {
        TextView bmiTextView = findViewById(R.id.bmiResult);
        bmiTextView.setText(String.format(Locale.ENGLISH, "%.2f", result));
    }
}
