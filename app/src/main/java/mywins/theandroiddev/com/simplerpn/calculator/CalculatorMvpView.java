package mywins.theandroiddev.com.simplerpn.calculator;

/**
 * Created by jakub on 15.12.17.
 */

public interface CalculatorMvpView {

    void displayExpressionEmptyMessage();

    void displayRpnResult(String expression);

    void displayEqualsResult(String expression);

    void displayInputExpression(String expression);

    void displayUnsupportedMessage();

    void clearInput();

    void displayDeleteButton();

    void displayClearButton();
}
