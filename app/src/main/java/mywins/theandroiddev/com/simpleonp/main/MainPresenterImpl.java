package mywins.theandroiddev.com.simpleonp.main;

import android.text.TextUtils;

import mywins.theandroiddev.com.simpleonp.Converter;
import mywins.theandroiddev.com.simpleonp.R;

/**
 * Created by jakub on 15.12.17.
 */

public class MainPresenterImpl implements MainPresenter {

    private MainView view;
    private Converter converter;
    private boolean resultShown = false;

    MainPresenterImpl() {
        converter = new Converter();
    }

    @Override
    public void onAttachView(MainView view) {
        this.view = view;
    }

    @Override
    public void onDetachView() {
        view = null;
    }

    @Override
    public void convertToONP(String infixExpression) {
        if (!isExpressionEmpty(infixExpression)) {
            view.displayONPResult(converter.toONP(infixExpression));
        } else {
            view.displayExpressionEmptyMessage();
        }
    }

    //Not used
    @Override
    public void convertToInfix(String ONPExpression) {
        if (!isExpressionEmpty(ONPExpression)) {
            view.displayONPResult(converter.toInfix(ONPExpression));
        } else {
            view.displayExpressionEmptyMessage();
        }
    }

    @Override
    public void calculate(String insertedExpression) {
        if (!isExpressionEmpty(insertedExpression)) {
            if (!converter.operator(insertedExpression.charAt(insertedExpression.length() - 1))) {
                resultShown = true;
                double result = converter.evaluate(insertedExpression);
                view.displayEqualsResult(String.valueOf(((long) result)));
                view.displayButtonCLR();
            }
        } else {
            view.displayExpressionEmptyMessage();
        }


    }

    @Override
    public void onClick(int id, Character c, String insertedExpression) {

        if (resultShown && Character.isDigit(c)) {
            view.clearInput();
            view.displayButtonDEL();
            setResultShown(false);
        }

        if (resultShown && converter.operator(c)) {
            view.displayButtonDEL();
            setResultShown(false);
        }

        if (Character.isDigit(c) || converter.operator(c)) {

            if (insertedExpression.length() > 0) {
                char lastChar = insertedExpression.charAt(insertedExpression.length() - 1);

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

            view.displayInput(insertedExpression + c);
            convertToONP(insertedExpression + c);
            return;
        }

        switch (id) {

            case R.id.btn_equals:
                calculate(insertedExpression);
                break;
            case R.id.btn_del:
                delete(insertedExpression);
                break;
            case R.id.btn_dot:
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
            setResultShown(false);
            view.displayButtonDEL();
        } else {
            view.displayInput(removeLastChar(insertedExpression));
            convertToONP(insertedExpression);
        }
    }

    @Override
    public boolean getResultShown() {
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
