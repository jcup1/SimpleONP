package mywins.theandroiddev.com.simpleonp.main;

/**
 * Created by jakub on 15.12.17.
 */

public interface MainView {

    void displayExpressionEmptyMessage();

    void displayONPResult(String s);

    void displayEqualsResult(String s);

    void appendInput(String s);

    void displayMessage(String s);

    void displayInput(String s);

    void displayUnsupportedMessage();

    void clearInput();

    void displayButtonDEL();

    void displayButtonCLR();
}
