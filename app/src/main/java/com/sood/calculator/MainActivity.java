package com.sood.calculator;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    // IDs of all the numeric buttons
    private int[] numericButtons = {R.id.btnZero, R.id.btnOne, R.id.btnTwo, R.id.btnThree, R.id.btnFour, R.id.btnFive, R.id.btnSix, R.id.btnSeven, R.id.btnEight, R.id.btnNine};
    // IDs of all the operator buttons
    private int[] operatorButtons = {R.id.btnAdd, R.id.btnSubtract, R.id.btnMultiply, R.id.btnDivide,R.id.btnModulus,R.id.btnSquare};
    // TextView used to display the output
    private TextView txtScreen;
    // Represent whether the lastly pressed key is numeric or not
    private boolean lastNumeric;
    // If true, do not allow to add another DOT
    private boolean lastDot;
    // If result, remove the text
    private boolean isResult;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        // Find the TextView
        this.txtScreen = (TextView) findViewById(R.id.txtScreen);
        // Find and set OnClickListener to numeric buttons
        setNumericOnClickListener();
        // Find and set OnClickListener to operator buttons, equal button and decimal point button
        setOperatorOnClickListener();
    }

    /**
     * Find and set OnClickListener to numeric buttons.
     */
    private void setNumericOnClickListener() {
        // Create a common OnClickListener
        View.OnClickListener listener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Just append/set the text of clicked button
                Button button = (Button) v;
                if (txtScreen.getText().toString().equals("0") || isResult || txtScreen.getText().toString().toLowerCase().equals("infinity")){
                    txtScreen.setText(button.getText());
                    isResult = false;
                }else {
                    txtScreen.append(button.getText());
                }
                // Set the flag
                lastNumeric = true;
            }
        };
        // Assign the listener to all the numeric buttons
        for (int id : numericButtons) {
            findViewById(id).setOnClickListener(listener);
        }
    }

    /**
     * Find and set OnClickListener to operator buttons, equal button and decimal point button.
     */
    private void setOperatorOnClickListener() {
        // Create a common OnClickListener for operators
        View.OnClickListener listener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // If the current state is Error do not append the operator
                // If the last input is number only, append the operator
                if (lastNumeric) {
                    Button button = (Button) v;
                    txtScreen.append(button.getText());
                    isResult = false;
                    lastNumeric = false;
                    lastDot = false;    // Reset the DOT flag
                }
            }
        };
        // Assign the listener to all the operator buttons
        for (int id : operatorButtons) {
            findViewById(id).setOnClickListener(listener);
        }
        // Decimal point
        findViewById(R.id.btnDot).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (lastNumeric && !lastDot && !isResult) {
                    txtScreen.append(".");
                    lastNumeric = false;
                    lastDot = true;
                    isResult=false;
                }
            }
        });
        // Clear button
        findViewById(R.id.btnClear).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                txtScreen.setText("0");  // Clear the screen
                // Reset all the states and flags
                lastNumeric = false;
                lastDot = false;
            }
        });
        // Last pop
        findViewById(R.id.btnLastClear).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String txt = txtScreen.getText().toString();
                if (txt.length()>0 && !txt.equals("0") && !isResult) {
                    txtScreen.setText(txt.substring(0, txt.length() - 1));  // Clear the screen
                    // Reset all the states and flags
                    if (lastNumeric)
                        lastNumeric = false;
                    else
                        lastNumeric = true;
                    if (lastDot)
                        lastDot = false;
                    else
                        lastDot = true;
                }else {
                    txtScreen.setText("0");
                }
            }
        });
        // Equal button
        findViewById(R.id.btnEqual).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onEqual();
            }
        });
    }

    /**
     * Logic to calculate the solution.
     */
    private void onEqual() {
        // If the current state is error, nothing to do.
        // If the last input is a number only, solution can be found.
        if (lastNumeric) {
            // Read the expression
            String txt = txtScreen.getText().toString();

            CalculatorMain advanceCalculator = new CalculatorMain();
            String postfixExpression = advanceCalculator.createStringPostFix(txt);

            float result = advanceCalculator.evaluateResult(postfixExpression);
            Log.d(result+" ","result");
            txtScreen.setText(result+"");

            isResult =  true;
        }
    }
}