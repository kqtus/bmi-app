package com.example.marek.bmi;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.NumberPicker;
import android.widget.Switch;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    private final static int MIN_HEIGHT = 50;
    private final static int MAX_HEIGHT = 280;
    private final static int MIN_WEIGHT = 10;
    private final static int MAX_WEIGHT = 250;

    private BmiService bmiService;

    NumberPicker massPicker;
    NumberPicker heightPicker;
    Switch localeSwitch;
    TextView weightUnitTextView;
    TextView heightUnitTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bmi);

        initService();
        initControls();
        toggleLocale(null);
    }

    protected void initControls() {
        massPicker = findViewById(R.id.massPicker);
        heightPicker = findViewById(R.id.heightPicker);
        localeSwitch = findViewById(R.id.localeSwitch);
        weightUnitTextView = findViewById(R.id.weightUnitTextView);
        heightUnitTextView = findViewById(R.id.heightUnitTextView);

        localeSwitch.setChecked(bmiService.getUnitStandard() == BmiService.UnitStandard.ENGLISH);

        setupPickers();

        massPicker.setValue((int)(bmiService.getWeight()));
        heightPicker.setValue((int)(bmiService.getHeight()));
    }

    protected void setupPickers() {
        double wght_multiplier = 1;
        double hght_multiplier = 1;

        if (localeSwitch.isChecked())
        {
            wght_multiplier = BmiService.EU_TO_UK_WEIGHT_MULTIPLIER;
            hght_multiplier = BmiService.EU_TO_UK_HEIGHT_MULTIPLIER;
        }

        massPicker.setMinValue((int)(MIN_WEIGHT * wght_multiplier));
        massPicker.setMaxValue((int)(MAX_WEIGHT * wght_multiplier));

        heightPicker.setMinValue((int)(MIN_HEIGHT * hght_multiplier));
        heightPicker.setMaxValue((int)(MAX_HEIGHT * hght_multiplier));
    }

    protected void initService() {
        bmiService = new BmiService();

        SharedPreferences sharedPreferences = getPreferences(Context.MODE_PRIVATE);

        float notSavedIndicator = -1;
        float savedWeight = sharedPreferences.getFloat(getString(R.string.weight_preference_key), notSavedIndicator);
        float savedHeight = sharedPreferences.getFloat(getString(R.string.height_preference_key), notSavedIndicator);
        boolean savedLocaleIsUk = sharedPreferences.getBoolean(getString(R.string.locale_preference_key), false);

        if (savedWeight != notSavedIndicator) {
            bmiService.setHeight(savedHeight);
            bmiService.setWeight(savedWeight);
            bmiService.setUnitStandard(savedLocaleIsUk
                    ? BmiService.UnitStandard.ENGLISH
                    : BmiService.UnitStandard.EUROPEAN
            );
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.about_me:
                showAboutMe();
            case R.id.save_bmi:
                saveBmi();
                return true;
            default:
                return false;
        }
    }

    public void showResult(View view) {
        bmiService.setWeight(massPicker.getValue());
        bmiService.setHeight(heightPicker.getValue());
        bmiService.setUnitStandard(localeSwitch.isChecked()
                ? BmiService.UnitStandard.ENGLISH
                : BmiService.UnitStandard.EUROPEAN);

        Intent showResultIntent = new Intent(view.getContext(), ResultActivity.class);
        showResultIntent.putExtra("result", bmiService.getBmi());
        startActivity(showResultIntent);
    }

    public void showAboutMe() {
        Intent showAboutMeIntent = new Intent(getBaseContext(), AboutActivity.class);
        startActivity(showAboutMeIntent);
    }

    public void toggleLocale(View view) {
        if (localeSwitch.isChecked()) {
            toggleEnLocale();
        }
        else {
            toggleEuLocale();
        }

        setupPickers();
    }

    private void toggleEnLocale() {
        heightUnitTextView.setText(R.string.uk_height_unit);
        weightUnitTextView.setText(R.string.uk_weight_unit);
    }

    private void toggleEuLocale() {
        heightUnitTextView.setText(R.string.eu_height_unit);
        weightUnitTextView.setText(R.string.eu_weight_unit);
    }

    public void saveBmi() {
        SharedPreferences preferences = getPreferences(Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();

        editor.putFloat(
                getString(R.string.weight_preference_key),
                massPicker.getValue()
        );

        editor.putFloat(
                getString(R.string.height_preference_key),
                heightPicker.getValue()
        );

        editor.putBoolean(
                getString(R.string.locale_preference_key),
                localeSwitch.isChecked()
        );

        editor.apply();
    }
}
