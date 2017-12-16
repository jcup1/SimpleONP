package mywins.theandroiddev.com.simpleonp;

/**
 * Created by jakub on 15.12.17.
 */

public interface MainPresenter {

    void onAttachView(MainView view);

    void onDetachView();

    void convertToONP(String expression);

    void convertToInfix(String expression);

}
