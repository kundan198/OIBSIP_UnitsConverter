package com.example.unitsconverter;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    private Spinner conversionTypeSpinner, sourceUnitSpinner, targetUnitSpinner;
    private EditText valueInput;
    private Button convertButton;
    private TextView valueLabel, sourceLabel, targetLabel, resultLabel, resultTextView;

    private final String[] conversionTypes = {"Length", "Weight", "Temperature"};
    private final String[][] units = {
            {"Centimeters", "Meters", "Inches"},
            {"Grams", "Kilograms"},
            {"Celsius", "Fahrenheit"}
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        conversionTypeSpinner = findViewById(R.id.conversionTypeSpinner);
        sourceUnitSpinner = findViewById(R.id.sourceUnitSpinner);
        targetUnitSpinner = findViewById(R.id.targetUnitSpinner);
        valueInput = findViewById(R.id.valueInput);
        convertButton = findViewById(R.id.convertButton);
        valueLabel = findViewById(R.id.valueLabel);
        sourceLabel = findViewById(R.id.sourceLabel);
        targetLabel = findViewById(R.id.targetLabel);
        resultLabel = findViewById(R.id.resultLabel);
        resultTextView = findViewById(R.id.resultTextView);

        ArrayAdapter<String> conversionTypeAdapter = new ArrayAdapter<>(this,
                android.R.layout.simple_spinner_item, conversionTypes);
        conversionTypeAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        conversionTypeSpinner.setAdapter(conversionTypeAdapter);

        conversionTypeSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                setUnitSpinnerAdapter(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        convertButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                convertUnits();
            }
        });
    }

    private void setUnitSpinnerAdapter(int categoryIndex) {
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this,
                android.R.layout.simple_spinner_item, units[categoryIndex]);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        sourceUnitSpinner.setAdapter(adapter);
        targetUnitSpinner.setAdapter(adapter);

        valueLabel.setVisibility(View.VISIBLE);
        sourceLabel.setVisibility(View.VISIBLE);
        targetLabel.setVisibility(View.VISIBLE);
        resultLabel.setVisibility(View.VISIBLE);
        valueInput.setVisibility(View.VISIBLE);
        sourceUnitSpinner.setVisibility(View.VISIBLE);
        targetUnitSpinner.setVisibility(View.VISIBLE);
        convertButton.setVisibility(View.VISIBLE);
        resultTextView.setVisibility(View.VISIBLE);
    }

    private void convertUnits() {
        try {
            double inputValue = Double.parseDouble(valueInput.getText().toString());
            String sourceUnit = sourceUnitSpinner.getSelectedItem().toString();
            String targetUnit = targetUnitSpinner.getSelectedItem().toString();

            // Conversion logic
            double result;
            switch (sourceUnit) {
                case "Centimeters":
                    if (targetUnit.equals("Meters")) {
                        result = inputValue / 100.0;
                    } else if (targetUnit.equals("Inches")) {
                        result = inputValue / 2.54;
                    } else {
                        result = inputValue;
                    }
                    break;
                case "Meters":
                    if (targetUnit.equals("Centimeters")) {
                        result = inputValue * 100.0;
                    } else if (targetUnit.equals("Inches")) {
                        result = inputValue * 39.37;
                    } else {
                        result = inputValue;
                    }
                    break;
                case "Inches":
                    if (targetUnit.equals("Centimeters")) {
                        result = inputValue * 2.54;
                    } else if (targetUnit.equals("Meters")) {
                        result = inputValue / 39.37;
                    } else {
                        result = inputValue;
                    }
                    break;
                case "Grams":
                    if (targetUnit.equals("Kilograms")) {
                        result = inputValue / 1000.0;
                    } else {
                        result = inputValue;
                    }
                    break;
                case "Kilograms":
                    if (targetUnit.equals("Grams")) {
                        result = inputValue * 1000.0;
                    } else {
                        result = inputValue;
                    }
                    break;
                case "Celsius":
                    if (targetUnit.equals("Fahrenheit")) {
                        result = (inputValue * 9 / 5) + 32;
                    } else {
                        result = inputValue;
                    }
                    break;
                case "Fahrenheit":
                    if (targetUnit.equals("Celsius")) {
                        result = (inputValue - 32) * 5 / 9;
                    } else {
                        result = inputValue;
                    }
                    break;
                default:
                    result = inputValue;
                    break;
            }


            // Display result
            resultTextView.setText(String.format("%.2f %s = %.2f %s", inputValue, sourceUnit, result, targetUnit));
        } catch (NumberFormatException e) {
            resultTextView.setText("Invalid input");
        }
    }
}
