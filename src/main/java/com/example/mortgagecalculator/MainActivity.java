/**
 * The Main Activity of the Mortgage Calculator App.
 * -
 * This activity is responsible for guiding the user through each step of
 * the mortgage calculation process, presenting a series of prompts one by one.
 * Once all input steps are completed, the "Calculate My Mortgage" button becomes
 * available. Upon clicking this button, a pop-up window (Alert) displays a summary
 * of the user's inputs and the calculated monthly mortgage payment.
 * -
 * The user interface elements and their roles:
 * - Prompt TextView: Displays a prompt for user input.
 * - Input EditText: Allows the user to enter numerical input.
 * - Next Button: Advances to the next step and handles input validation.
 * - Recalculate Button: Allows the user to start a new calculation.
 * - Results TextView: Displays the calculated monthly mortgage payment.
 * -
 * The calculation steps:
 * 1. Enter Principal Amount ($)
 * 2. Enter Interest Rate (%)
 * 3. Enter Mortgage Period (Years)
 * -
 * When all steps are completed, the monthly mortgage payment is calculated
 * and displayed in the Results TextView. The Recalculate Button becomes visible
 * to start a new calculation.
 * -
 * @author Drey Smith
 * @since 09/30/2023
 */
package com.example.mortgagecalculator;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import java.util.Locale;

/**
 * The main activity class of the Mortgage Calculator App.
 * -
 * This class extends AppCompatActivity and serves as the entry point for the application.
 * It manages the user interface and the mortgage calculation process.
 * -
 * @author Drey Smith
 * @since 09/30/2023
 * @see AppCompatActivity
 */
public class MainActivity extends AppCompatActivity {

    // declare UI variables
    private EditText inputEditText;
    private TextView resultsTextView;
    private Button nextButton;
    private Button recalculateButton;
    // declare index for each step of the calculation
    private int currentStep;
    // declare calculation object
    private Calculation calculation;
    // array of text prompts for each step of calculation
    final CalculationStep[] steps = {
            new CalculationStep(R.string.prompt_principle),
            new CalculationStep(R.string.prompt_interest_rate),
            new CalculationStep(R.string.prompt_mortgage_period)
    };

    /**
     * Called when the activity is first created.
     * Initializes the user interface, sets up the initial state, and prepares
     * the application for user interaction.
     * -
     * @param savedInstanceState A Bundle containing the activity's previously saved state,
     *                           if applicable.
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Set the content view to the main layout
        setContentView(R.layout.activity_main);

        // Initialize the Calculation object for mortgage calculations
        calculation = new Calculation();

        // Initialize UI elements and set initial visibility
        resultsTextView = findViewById(R.id.txtResults);
        nextButton = findViewById(R.id.btnNext);
        inputEditText = findViewById(R.id.editTxtInput);

        recalculateButton = findViewById(R.id.btnRecalculate);
        recalculateButton.setVisibility(View.GONE);

        // Set the current step to the beginning of the mortgage calculation process
        currentStep = 0;

        // Guide the user to the first input step
        guideUserToNextStep();

        // Set up event listeners for UI elements
        setupListeners();
    }

    /**
     * Set up event listeners for user interface buttons.
     * Associates click actions with their co-related methods.
     */
    private void setupListeners() {
        // Set a click listener for the "Next" button
        nextButton.setOnClickListener(view -> handleNextButtonClick());

        // Set a click listener for the "Recalculate" button
        recalculateButton.setOnClickListener(view -> handleRecalculateButtonClick());
    }

    /**
     * Guides the user to the next input step in the mortgage calculation process.
     * Updates the user interface elements to display the appropriate prompt for the
     * current step and clears the input field for the user's input.
     * If all steps are completed, the "Next" button is hidden.
     */
    private void guideUserToNextStep() {

        // Check if there are more steps to display
        if (currentStep < steps.length) {

            // Get the CalculationStep object for the current step
            CalculationStep step = steps[currentStep];

            // Get the resource ID of the prompt for the current step
            int promptResourceId = step.getPromptResourceId();
            String prompt = getString(promptResourceId);

            // reset hints and text on UI
            resultsTextView.setText(prompt);
            inputEditText.setHint(prompt);
            inputEditText.getText().clear();
            currentStep++; // increment step counter
        } else {
            // if all steps complete, hide next button
            nextButton.setVisibility(View.GONE);
        }
    }

    /**
     * Handles the click event of the "Next" button.
     * -
     * This method is called when the user clicks the "Next" button to advance to the next
     * step of the mortgage calculation process. It validates the user's input, sets the
     * corresponding calculation parameter based on the current step, and updates the user
     * interface accordingly.
     * -
     * @throws NumberFormatException If the user's input cannot be parsed as a valid number.
     * @see #guideUserToNextStep()
     * @see #showReviewAlert()
     */
    private void handleNextButtonClick() {
        // Get the user's input from the inputEditText field
        String inputStr = inputEditText.getText().toString();

        // Check if the input is empty and show an error toast if so
        if (inputStr.isEmpty()) {
            Toast.makeText(this, R.string.empty_input_error, Toast.LENGTH_SHORT).show();
            return;
        }

        // Parse the input as a double value
        double inputValue = Double.parseDouble(inputStr);

        // Determine the current step and set the corresponding calculation parameter
        switch (currentStep - 1) {
            case 0:
                calculation.setPrinciple(inputValue);
                break;
            case 1:
                calculation.setInterestRate(inputValue);
                // Change the text of the "Next" button to "Calculate" for the last step
                nextButton.setText(R.string.calculate); // set text for last step
                break;
            case 2:
                calculation.setMortgagePeriod(inputValue);
                // Proceed to display the review alert
                showReviewAlert();
                return;
        }

        // Move to the next step in the user guidance
        guideUserToNextStep();
    }

    /**
     * Displays an alert dialog summarizing the mortgage calculation results and user inputs.
     * -
     * This method retrieves the principle amount, interest rate, and mortgage period from
     * the Calculation object and calculates the monthly mortgage payment. If the calculation
     * is successful (i.e., the monthly payment is finite), it formats the results and displays
     * them in an alert dialog. Additionally, it manages the visibility of user interface elements
     * to provide a clear view of the results and allows for recalculation.
     * -
     * @see Calculation
     */
    private void showReviewAlert() {
        // Use Calculation object to get values to display on Alert
        double principle = calculation.getPrinciple();
        double interestRate = calculation.getInterestRate();
        double mortgagePeriod = calculation.getMortgagePeriod();

        // Calculate the monthly payment and store in monthlyPayment
        double monthlyPayment = calculation.calculateMonthlyPayment(principle, interestRate, mortgagePeriod);

        if (Double.isFinite(monthlyPayment)) {
            // Format monthly payment into 2 decimal places and Locale date (warning fix)
            String formattedMonthlyPayment = String.format(Locale.US, "%.2f", monthlyPayment);

            // Build an overview message to display all values and result to user
            StringBuilder message = new StringBuilder();
            message.append("\n").append(getString(R.string.message_principle)).append(principle).append("\n\n");
            message.append(getString(R.string.message_interest)).append(" ").append(interestRate).append(getString(R.string.percent)).append("\n\n");
            message.append(getString(R.string.message_mortgage)).append(" ").append(mortgagePeriod).append(" ").append(getString(R.string.years)).append("\n\n");
            message.append("\n\n" + getString(R.string.message_result)).append(formattedMonthlyPayment);

            // set result text view
            resultsTextView.setText(getString(R.string.monthly_payment) + formattedMonthlyPayment);

            // hide input bar and next button they are irrelevant at this stage
            inputEditText.setVisibility(View.GONE);
            nextButton.setVisibility(View.GONE);

            // show recalculate button to start a new calculation
            recalculateButton.setVisibility(View.VISIBLE);

            // Create and display an alert dialog with the mortgage overview
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle(R.string.mortgage_overview);
            builder.setMessage(message.toString());
            // set to null so that no function is called onClick
            builder.setPositiveButton(R.string.looks_good, null);
            builder.create().show();
        } else {
            // Display an error message if the calculation is invalid
            resultsTextView.setText(R.string.invalid_input_error);
        }
    }

    /**
     * Handles the button click event for the "Recalculate" button.
     * -
     * This method clears the input field, resets the current step to the first step,
     * changes the text of the "Next" button back to "Next," and restores the visibility
     * of the input field, prompt, and "Next" button while hiding the "Recalculate" button.
     * This allows the user to start a new mortgage calculation from the beginning.
     */
    private void handleRecalculateButtonClick() {
        // clear the input field
        inputEditText.getText().clear();

        // Reset to first step
        currentStep = 1;

        // Reset text to 'Next'
        nextButton.setText(R.string.next);

        // Restore visibility of the input field, prompt and next button
        nextButton.setVisibility(View.VISIBLE);
        inputEditText.setVisibility(View.VISIBLE);
        resultsTextView.setVisibility(View.VISIBLE);

        // rest prompt and hint to the first step
        resultsTextView.setText(getString(steps[0].getPromptResourceId()));
        inputEditText.setHint(getString(steps[0].getPromptResourceId()));

        // Hide the Recalculate button
        recalculateButton.setVisibility(View.GONE);
    }
}