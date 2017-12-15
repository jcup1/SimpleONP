package mywins.theandroiddev.com.simpleonp;

import android.text.TextUtils;

import java.util.List;

/**
 * Created by jakub on 15.12.17.
 */

public class MainPresenterImpl implements MainPresenter {

    MainView view;
    List<String> numbers;
    private boolean expressionProper;

    public MainPresenterImpl() {

    }

    @Override
    public void attachView(MainView view) {
        this.view = view;
    }

    @Override
    public void addNumbers() {
        view.displayNumbers();
    }

    @Override
    public void detachView() {
        view = null;
    }

    @Override
    public void calculate(String expression) {
        if (isExpressionProper(expression)) {
            view.displayResult(convertExpression(expression));

        } else {
            view.displayExpressionNotProper();
        }
    }

    private String convertExpression(String expression) {
        return "TODO";
    }

    public boolean isExpressionProper(String expression) {

        return !TextUtils.isEmpty(expression);
        //TODO else if
    }
}
