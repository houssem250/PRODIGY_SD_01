package com.example.temperaturesconversion;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {

    private EditText editCelsius, editFahrenheit, editKelvin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        // Set padding to handle system insets
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // Initialize EditText fields
        editCelsius = findViewById(R.id.editCelsius);
        editFahrenheit = findViewById(R.id.editFahrenheit);
        editKelvin = findViewById(R.id.editKelvin);

        // Add listeners for each EditText field to trigger conversion when text changes
        editCelsius.addTextChangedListener(new SimpleTextWatcher() {
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (!editCelsius.hasFocus()) return;
                convertFromCelsius();
            }
        });

        editFahrenheit.addTextChangedListener(new SimpleTextWatcher() {
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (!editFahrenheit.hasFocus()) return;
                convertFromFahrenheit();
            }
        });

        editKelvin.addTextChangedListener(new SimpleTextWatcher() {
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (!editKelvin.hasFocus()) return;
                convertFromKelvin();
            }
        });

        // Set initial value and trigger conversion
        editCelsius.setText("0");
        convertFromCelsius();
    }

    private void convertFromCelsius() {
        try {
            double celsius = Double.parseDouble(editCelsius.getText().toString());
            double[] results = TemperatureConverter.celsiusToFahrenheitAndKelvin(celsius);
            editFahrenheit.setText(String.valueOf(results[0]));
            editKelvin.setText(String.valueOf(results[1]));
        } catch (NumberFormatException e) {
            // Handle empty or invalid input
            editFahrenheit.setText("");
            editKelvin.setText("");
        }
    }

    private void convertFromFahrenheit() {
        try {
            double fahrenheit = Double.parseDouble(editFahrenheit.getText().toString());
            double[] results = TemperatureConverter.fahrenheitToCelsiusAndKelvin(fahrenheit);
            editCelsius.setText(String.valueOf(results[0]));
            editKelvin.setText(String.valueOf(results[1]));
        } catch (NumberFormatException e) {
            // Handle empty or invalid input
            editCelsius.setText("");
            editKelvin.setText("");
        }
    }

    private void convertFromKelvin() {
        try {
            double kelvin = Double.parseDouble(editKelvin.getText().toString());
            double[] results = TemperatureConverter.kelvinToCelsiusAndFahrenheit(kelvin);
            editCelsius.setText(String.valueOf(results[0]));
            editFahrenheit.setText(String.valueOf(results[1]));
        } catch (NumberFormatException e) {
            // Handle empty or invalid input
            editCelsius.setText("");
            editFahrenheit.setText("");
        }
    }

    private abstract static class SimpleTextWatcher implements TextWatcher {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) { }
        @Override
        public void afterTextChanged(Editable s) { }
    }

    protected static class TemperatureConverter {

        public static double[] celsiusToFahrenheitAndKelvin(double celsius) {
            double fahrenheit = (celsius * 9/5) + 32;
            double kelvin = celsius + 273.15;
            return new double[]{fahrenheit, kelvin};
        }

        public static double[] fahrenheitToCelsiusAndKelvin(double fahrenheit) {
            double celsius = (fahrenheit - 32) * 5/9;
            double kelvin = (fahrenheit - 32) * 5/9 + 273.15;
            return new double[]{celsius, kelvin};
        }

        public static double[] kelvinToCelsiusAndFahrenheit(double kelvin) {
            double celsius = kelvin - 273.15;
            double fahrenheit = (kelvin - 273.15) * 9/5 + 32;
            return new double[]{celsius, fahrenheit};
        }
    }
}
