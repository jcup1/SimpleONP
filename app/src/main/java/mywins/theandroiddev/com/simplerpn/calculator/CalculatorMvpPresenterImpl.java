package mywins.theandroiddev.com.simplerpn.calculator;

import android.text.TextUtils;

import mywins.theandroiddev.com.simplerpn.Converter;
import mywins.theandroiddev.com.simplerpn.Evaluator;
import mywins.theandroiddev.com.simplerpn.R;

/**
 * Created by jakub on 15.12.17.
 */

public class CalculatorMvpPresenterImpl implements CalculatorMvpPresenter {

    private CalculatorMvpView view;
    private Converter converter;
    private Evaluator evaluator;
    private boolean resultShown;

    CalculatorMvpPresenterImpl() {
        converter = new Converter();
        evaluator = new Evaluator();
        setResultShown(false);
    }

    @Override
    public void onAttachView(CalculatorMvpView view) {
        this.view = view;
    }

    @Override
    public void onDetachView() {
        view = null;
    }

    @Override
    public void convertToRpn(String infixExpression) {
        if (isExpressionEmpty(infixExpression)) {
            view.displayExpressionEmptyMessage();

        } else {
            view.displayRpnResult(converter.toRpn(infixExpression));
        }
    }

    @Override
    public void calculate(String insertedExpression) {
        double result;

        if (isLastCharacterNotOperator(insertedExpression)) {
            setResultShown(true);
            result = evaluator.evaluate(insertedExpression);
            view.displayEqualsResult(String.valueOf((Math.round(result))));
            view.displayClearButton();
        }

    }

    private boolean isLastCharacterNotOperator(String insertedExpression) {
        return !converter.isOperator(getLastCharacter(insertedExpression));
    }

    @Override
    public void onClick(int id, Character insertedCharacter, String insertedExpression) {

        if (resultShown) {
            onResultShown(insertedCharacter, insertedExpression, id);
        } else if (isExpressionEmpty(insertedExpression)) {
            onExpressionEmpty(insertedCharacter);
        } else {
            onExpressionNotEmpty(insertedCharacter, insertedExpression, id);
        }

    }

    private void onExpressionEmpty(Character character) {
        if (isAllowedAsFirstCharacter(character)) {
            writeExpression("", character);
        }
    }

    private void onExpressionNotEmpty(Character character, String expression, int id) {

        if (isNotDividingByZero(character, expression) && Character.isDigit(character)) {
            writeExpression(expression, character);
        } else if (converter.isOperator(character) && isNotDoublingOperator(character, expression)) {
            writeExpression(expression, character);
        } else if (converter.isOperator(character)) {
            writeExpression(removeLastChar(expression), character);
        } else {
            switchCharacterId(id, expression);
        }

    }

    private void writeExpression(String expression, Character character) {
        view.displayInputExpression(expression + character);
        convertToRpn(expression + character);
    }

    private void switchCharacterId(int id, String insertedExpression) {

        switch (id) {

            case R.id.equals_button:
                calculate(insertedExpression);

                break;
            case R.id.delete_button:
                delete(insertedExpression);

                break;
            case R.id.dot_button:

                unsupported();
                break;
            default:
                break;
        }
    }

    private boolean isNotDividingByZero(Character character, String insertedExpression) {
        return !(character.equals('0') && getLastCharacter(insertedExpression) == '/');

    }

    private boolean isAllowedAsFirstCharacter(Character character) {

        return (Character.isDigit(character) || character.equals('-')) && !character.equals('0');

    }

    private void onResultShown(Character character, String insertedExpression, int id) {
        if (resultShown) {
            if (Character.isDigit(character) && isAllowedAsFirstCharacter(character)) {
                view.clearInput();
                view.displayDeleteButton();
                setResultShown(false);
                writeExpression("", character);

            } else if (converter.isOperator(character)) {
                view.displayDeleteButton();
                writeExpression(insertedExpression, character);
                setResultShown(false);

            } else {
                switchCharacterId(id, insertedExpression);
            }

        }
    }

    private void unsupported() {
        view.displayUnsupportedMessage();
    }

    private void delete(String insertedExpression) {
        if (resultShown) {
            view.clearInput();
            view.displayDeleteButton();
            setResultShown(false);

        } else {
            view.displayInputExpression(removeLastChar(insertedExpression));
            convertToRpn(insertedExpression);
        }
    }

    @Override
    public boolean isResultShown() {
        return resultShown;
    }

    @Override
    public void setResultShown(boolean resultShown) {
        this.resultShown = resultShown;
    }

    private boolean isExpressionEmpty(String expression) {
        return TextUtils.isEmpty(expression);
    }

    private String removeLastChar(String expression) {
        if (expression == null || expression.length() == 0) return null;
        else return expression.substring(0, expression.length() - 1);
    }

    private char getLastCharacter(String insertedExpression) {
        return insertedExpression.charAt(insertedExpression.length() - 1);
    }

    //is user trying to write 2 operators in a row?
    private boolean isNotDoublingOperator(Character character, String insertedExpression) {
        return !(converter.isOperator(character) && converter.isOperator(getLastCharacter(insertedExpression)));
    }
}
