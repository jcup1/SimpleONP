package mywins.theandroiddev.com.simpleonp;

/**
 * Created by jakub on 15.12.17.
 */

public interface MainPresenter {

    void attachView(MainView view);

    void detachView();

    void toONP(String expression);

    void toInfix(String expression);
}
