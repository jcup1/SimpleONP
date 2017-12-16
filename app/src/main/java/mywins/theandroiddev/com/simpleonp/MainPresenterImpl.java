package mywins.theandroiddev.com.simpleonp;

import android.text.TextUtils;

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
    public void attachView(MainView view) {
        this.view = view;
    }

    @Override
    public void detachView() {
        view = null;
    }

    @Override
    public void toONP(String infixExpression) {
        if (!isExpressionEmpty(infixExpression)) {
            view.displayResult(converter.toONP(infixExpression));
        } else {
            view.displayExpressionNotProper();
        }
    }

    @Override
    public void toInfix(String ONPExpression) {
        if (!isExpressionEmpty(ONPExpression)) {
            view.displayResult(converter.toInfix(ONPExpression));
        } else {
            view.displayExpressionNotProper();
        }
    }


    private boolean isExpressionEmpty(String s) {
        return TextUtils.isEmpty(s);
    }

}
