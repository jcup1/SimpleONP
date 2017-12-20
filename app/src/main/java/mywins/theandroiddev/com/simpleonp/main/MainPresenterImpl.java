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
            view.displayEqualsResult(converter.evaluate(insertedExpression));
        } else {
            view.displayExpressionEmptyMessage();
        }


    }

    @Override
    public void onClick(int id, Character c, String s, String insertedExpression) {

        if (Character.isDigit(c) || c.equals('+') || c.equals('-')) {
            view.appendInput(s);
            convertToONP(insertedExpression);
        }

        switch (id) {

            case R.id.btn_dot:
                view.displayMessage("Don't support float");
                break;
            case R.id.btn_equals:
                calculate(insertedExpression);
                break;
            case R.id.btn_del:
                view.displayInput(removeLastChar(insertedExpression));
                convertToONP(insertedExpression);
                break;
            case R.id.btn_divide:
                view.appendInput("/");
                break;
            case R.id.btn_multiply:
                view.appendInput("*");
                break;

            default:
                break;
        }
    }

    private boolean isExpressionEmpty(String s) {
        return TextUtils.isEmpty(s);
    }

    public String removeLastChar(String s) {
        return (s == null || s.length() == 0)
                ? null
                : (s.substring(0, s.length() - 1));
    }

}
