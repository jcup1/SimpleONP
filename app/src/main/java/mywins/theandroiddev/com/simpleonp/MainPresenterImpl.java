package mywins.theandroiddev.com.simpleonp;

import android.text.TextUtils;

/**
 * Created by jakub on 15.12.17.
 */

public class MainPresenterImpl implements MainPresenter {

    MainView view;
    Converter converter;

    public MainPresenterImpl() {
        converter = new Converter();
    }

    @Override
    public void attachView(MainView view) {
        this.view = view;
    }



    @Override
    public void detachView() {
        view = null;
    }

    @Override
    public void toONP(String infixExpression) {
        if (isInfixExpressionProper(infixExpression)) {
            view.displayResult(converter.toONP(infixExpression));
        } else {
            view.displayExpressionNotProper();
        }
    }

    @Override
    public void toInfix(String ONPExpression) {
        if (isONPExpressionProper(ONPExpression)) {
            view.displayResult(convertONPToInfix(ONPExpression));
        } else {
            view.displayExpressionNotProper();
        }
    }

    public boolean isInfixExpressionProper(String expression) {

        return !TextUtils.isEmpty(expression);
        //TODO implement more cases or merge ONP and Infix methods
    }

    public boolean isONPExpressionProper(String expression) {

        return !TextUtils.isEmpty(expression);
    }

    private String convertONPToInfix(String ONPExpression) {
        return "Infix";
    }

}
