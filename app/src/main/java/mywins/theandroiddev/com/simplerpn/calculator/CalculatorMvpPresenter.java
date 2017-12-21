package mywins.theandroiddev.com.simplerpn.calculator;

/**
 * Created by jakub on 15.12.17.
 */

public interface CalculatorMvpPresenter {

    void onAttachView(CalculatorMvpView view);

    void onDetachView();

    void convertToRpn(String expression);

    void calculate(String insertedExpression);

    void onClick(int id, Character c, String insertedExpression);

    boolean isResultShown();

    void setResultShown(boolean aBoolean);
}
