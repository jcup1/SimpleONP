package mywins.theandroiddev.com.simplerpn.calculator;

/**
 * Created by jakub on 15.12.17.
 */

public interface CalculatorMvpView {

    void displayExpressionEmptyMessage();

    void displayRpnResult(String s);

    void displayEqualsResult(String s);

    void displayInputExpression(String s);

    void displayUnsupportedMessage();

    void clearInput();

    void displayDeleteButton();

    void displayClearButton();
}
