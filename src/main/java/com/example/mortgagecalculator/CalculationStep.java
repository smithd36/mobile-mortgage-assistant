package com.example.mortgagecalculator;

/**
 * Represents a single step in the mortgage calculation process.
 * Each step includes a prompt resource ID that corresponds to
 * the user input required at that step.
 *
 * @author Drey Smith
 * @since 09.30.2023
 */
public class CalculationStep {
    private int promptResourceId;

    /**
     * Constructs a CalculationStep with the specified prompt resource ID.
     *
     * @param promptResourceId The resource ID of the prompt associated with this step.
     */
    public CalculationStep(int promptResourceId) {
        this.promptResourceId = promptResourceId;
    }

    /**
     * Gets the resource ID of the prompt associated with this step.
     *
     * @return The resource ID of the prompt.
     */
    public int getPromptResourceId() {
        return promptResourceId;
    }
}