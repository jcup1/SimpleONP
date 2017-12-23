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
    private Character lastCharacter;

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
    public void calculate(String infixExpression) {
        double result;

        if (isLastCharacterNotOperator(infixExpression)) {
            setResultShown(true);
            result = evaluator.evaluate(infixExpression);
            view.displayEqualsResult(String.valueOf((Math.round(result))));
            view.displayClearButton();
        }

    }

    private boolean isLastCharacterNotOperator(String expression) {
        return !converter.isOperator(getLastCharacter(expression));
    }

    @Override
    public void onClick(int id, Character insertedCharacter, String inputExpression) {

        if (resultShown) {
            onResultShown(id, inputExpression, insertedCharacter);
        } else if (isExpressionEmpty(inputExpression)) {
            onExpressionEmpty(insertedCharacter);
        } else {
            onExpressionNotEmpty(id, inputExpression, insertedCharacter);
        }

    }

    private void onExpressionEmpty(Character character) {
        if (isAllowedAsFirstCharacter(character)) {
            writeExpression("", character);
        }
    }

    private void onExpressionNotEmpty(int id, String expression, Character character) {

        if (isNotDividingByZero(expression, character) && Character.isDigit(character)
                && isNotMinusZero(expression, character)) {
            writeExpression(expression, character);
        } else if (converter.isOperator(character) && isNotDoublingOperator(expression, character)) {
            writeExpression(expression, character);
        } else if (converter.isOperator(character) && isAllowedInDoubling(expression, character)) {
            writeExpression(expression, character);
        } else {
            switchCharacterId(id, expression);
        }

    }

    private void writeExpression(String expression, Character character) {
        view.displayInputExpression(expression + character);
        convertToRpn(expression + character);
    }

    private void switchCharacterId(int id, String expression) {

        switch (id) {

            case R.id.equals_button:
                calculate(expression);

                break;
            case R.id.delete_button:
                delete(expression);

                break;
            case R.id.dot_button:

                unsupported();
                break;
            default:
                break;
        }
    }

    private boolean isNotDividingByZero(String expression, Character character) {
        return !(character.equals('0') && getLastCharacter(expression) == '/');

    }

    private boolean isAllowedAsFirstCharacter(Character character) {

        return (Character.isDigit(character) || character.equals('-')) && !character.equals('0');

    }

    private void onResultShown(int id, String expression, Character character) {
        if (resultShown) {
            if (Character.isDigit(character) && isAllowedAsFirstCharacter(character)) {
                view.clearInput();
                view.displayDeleteButton();
                setResultShown(false);
                writeExpression("", character);

            } else if (converter.isOperator(character)) {
                view.displayDeleteButton();
                writeExpression(expression, character);
                setResultShown(false);

            } else {
                switchCharacterId(id, expression);
            }

        }
    }

    private void unsupported() {
        view.displayUnsupportedMessage();
    }

    private void delete(String expression) {
        if (resultShown) {
            view.clearInput();
            view.displayDeleteButton();
            setResultShown(false);

        } else {
            view.displayInputExpression(removeLastChar(expression));
            convertToRpn(expression);
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

    private char getLastCharacter(String expression) {
        return expression.charAt(expression.length() - 1);
    }

    //is user trying to write 2 operators in a row?
    private boolean isNotDoublingOperator(String expression, Character character) {
        return !(converter.isOperator(character) && converter.isOperator(getLastCharacter(expression)));
    }


    private boolean isAllowedInDoubling(String expression, Character character) {

        lastCharacter = getLastCharacter(expression);
        //I can handle it here calling a method onDoubleMinus
        return isDoubleMinus(lastCharacter, character) || isMinusAfterOperator(lastCharacter, character);
    }


    private boolean areCharactersEqual(Character lastAcceptedCharacter, Character inputCharacter) {
        return lastAcceptedCharacter.equals(inputCharacter);
    }

    private boolean isNotMinusZero(String expression, Character character) {
        return !(character.equals('0') && expression.length() == 1 && expression.charAt(0) == '-');
    }

    private boolean isDoubleMinus(Character lastAcceptedCharacter, Character inputCharacter) {
        return lastAcceptedCharacter.equals('-') && inputCharacter.equals('-');
    }

    private boolean isMinusAfterOperator(Character lastAcceptedCharacter, Character inputCharacter) {
        return converter.isOperator(lastAcceptedCharacter) && inputCharacter.equals('-');
    }
}
