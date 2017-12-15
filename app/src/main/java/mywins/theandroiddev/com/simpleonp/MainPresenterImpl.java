package mywins.theandroiddev.com.simpleonp;

import java.util.List;

/**
 * Created by jakub on 15.12.17.
 */

public class MainPresenterImpl implements MainPresenter {

    MainView view;
    List<String> numbers;

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
}
