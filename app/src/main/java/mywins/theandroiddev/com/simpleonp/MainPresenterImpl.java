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
            view.displayResult(converter.toONP(infixExpression));
        } else {
            view.displayExpressionEmptyMessage();
        }
    }

    @Override
    public void convertToInfix(String ONPExpression) {
        if (!isExpressionEmpty(ONPExpression)) {
            view.displayResult(converter.toInfix(ONPExpression));
        } else {
            view.displayExpressionEmptyMessage();
        }
    }

    private boolean isExpressionEmpty(String s) {
        return TextUtils.isEmpty(s);
    }

}
