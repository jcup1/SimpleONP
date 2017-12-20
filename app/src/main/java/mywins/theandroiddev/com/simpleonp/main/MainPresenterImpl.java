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
            view.displayEqualsResult(converter.evaluate(insertedExpression));
        } else {
            view.displayExpressionEmptyMessage();
        }


    }

    @Override
    public void onClick(int id, Character c, String insertedExpression) {

        if (Character.isDigit(c) || converter.operator(c)) {
            view.appendInput(String.valueOf(c));
            convertToONP(insertedExpression);
        }

        switch (id) {

            case R.id.btn_dot:
                view.displayUnsupportedMessage();
                break;
            case R.id.btn_equals:
                calculate(insertedExpression);
                break;
            case R.id.btn_del:
                view.displayInput(removeLastChar(insertedExpression));
                convertToONP(insertedExpression);
                break;

            default:
                break;
        }
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
