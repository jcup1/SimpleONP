package mywins.theandroiddev.com.simplerpn.calculator;

import android.text.TextUtils;

import mywins.theandroiddev.com.simplerpn.Converter;
import mywins.theandroiddev.com.simplerpn.R;

/**
 * Created by jakub on 15.12.17.
 */

public class CalculatorMvpPresenterImpl implements CalculatorMvpPresenter {

    private CalculatorMvpView view;
    private Converter converter;
    private boolean resultShown = false;


    CalculatorMvpPresenterImpl() {
        converter = new Converter();
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
        if (!isExpressionEmpty(infixExpression)) {
            view.displayRpnResult(converter.toRpn(infixExpression));
        } else {
            view.displayExpressionEmptyMessage();
        }
    }

    @Override
    public void calculate(String insertedExpression) {
        double result;

        if (!isExpressionEmpty(insertedExpression)) {
            if (!converter.operator(insertedExpression.charAt(insertedExpression.length() - 1))) {
                resultShown = true;
                result = converter.evaluate(insertedExpression);
                view.displayEqualsResult(String.valueOf(((long) result)));
                view.displayClearButton();
            }
        } else {
            view.displayExpressionEmptyMessage();
        }


    }

    @Override
    public void onClick(int id, Character c, String insertedExpression) {

        if (resultShown) {

            if (Character.isDigit(c)) {
                view.clearInput();
                view.displayDeleteButton();
                setResultShown(false);
            }
            if (converter.operator(c)) {
                view.displayDeleteButton();
                setResultShown(false);
            }
        }

        if (Character.isDigit(c) || converter.operator(c)) {
            char lastChar;
            if (insertedExpression.length() > 0) {
                lastChar = insertedExpression.charAt(insertedExpression.length() - 1);

                //don't divide by 0
                if (c.equals('0') && lastChar == '/') {
                    return;
                }

                if (converter.operator(c) && converter.operator(lastChar)) {
                    insertedExpression = removeLastChar(insertedExpression);
                }
            }

            if (insertedExpression.length() == 0) {
                if (c.equals('/') || c.equals('*') || c.equals('+')) return;
            }

            view.displayInputExpression(insertedExpression + c);
            convertToRpn(insertedExpression + c);
            return;
        }

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
    public void setResultShown(boolean b) {
        resultShown = b;
    }

    private boolean isExpressionEmpty(String s) {
        return TextUtils.isEmpty(s);
    }

    private String removeLastChar(String s) {
        return (s == null || s.length() == 0)
                ? null
                : (s.substring(0, s.length() - 1));
    }

}
