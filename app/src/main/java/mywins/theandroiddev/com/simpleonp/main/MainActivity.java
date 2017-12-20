package mywins.theandroiddev.com.simpleonp.main;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import mywins.theandroiddev.com.simpleonp.R;

public class MainActivity extends AppCompatActivity implements MainView, View.OnClickListener {


    public static final String INPUT_KEY = "INPUT_STATE";
    public static final String RESULT_KEY = "RESULT_STATE";
    public static final String RESULT_SHOWN = "RESULT_SHOWN_STATE";

    MainPresenter mainPresenter;

    TextView inputTv, resultTv;

    //Switches CLR and DEL

    Button btn7, btn8, btn9, btn4, btn5, btn6, btn1, btn2, btn3, btnDot, btn0, btnEquals;
    Button btnDel, btnDivide, btnMultiply, btnMinus, btnPlus;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        inputTv = findViewById(R.id.inputTv);
        resultTv = findViewById(R.id.resultTv);

        btn7 = findViewById(R.id.btn_7);
        btn8 = findViewById(R.id.btn_8);
        btn9 = findViewById(R.id.btn_9);
        btn4 = findViewById(R.id.btn_4);
        btn5 = findViewById(R.id.btn_5);
        btn6 = findViewById(R.id.btn_6);
        btn1 = findViewById(R.id.btn_1);
        btn2 = findViewById(R.id.btn_2);
        btn3 = findViewById(R.id.btn_3);
        btnDot = findViewById(R.id.btn_dot);
        btn0 = findViewById(R.id.btn_0);
        btnEquals = findViewById(R.id.btn_equals);

        btnDel = findViewById(R.id.btn_del);
        btnDivide = findViewById(R.id.btn_divide);
        btnMultiply = findViewById(R.id.btn_multiply);
        btnMinus = findViewById(R.id.btn_minus);
        btnPlus = findViewById(R.id.btn_plus);

        btn7.setOnClickListener(this);
        btn8.setOnClickListener(this);
        btn9.setOnClickListener(this);
        btn4.setOnClickListener(this);
        btn5.setOnClickListener(this);
        btn6.setOnClickListener(this);
        btn1.setOnClickListener(this);
        btn2.setOnClickListener(this);
        btn3.setOnClickListener(this);
        btnDot.setOnClickListener(this);
        btn0.setOnClickListener(this);
        btnEquals.setOnClickListener(this);

        btnDel.setOnClickListener(this);
        btnDivide.setOnClickListener(this);
        btnMultiply.setOnClickListener(this);
        btnMinus.setOnClickListener(this);
        btnPlus.setOnClickListener(this);

        mainPresenter = new MainPresenterImpl();

    }

    @Override
    protected void onStart() {
        super.onStart();

        mainPresenter.onAttachView(this);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);

        if (savedInstanceState != null) {
            inputTv.setText(savedInstanceState.getString(INPUT_KEY));
            resultTv.setText(savedInstanceState.getString(RESULT_KEY));
            mainPresenter.setResultShown(savedInstanceState.getBoolean(RESULT_SHOWN));

        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString(INPUT_KEY, getInsertedExpression());
        outState.putString(RESULT_KEY, getResultONPExpression());
        outState.putBoolean(RESULT_SHOWN, getResultShown());
    }

    @Override
    protected void onStop() {
        super.onStop();
        mainPresenter.onDetachView();
    }

    public void clearResult() {
        resultTv.setText("");
    }

    public String getInsertedExpression() {
        return inputTv.getText().toString();
    }

    public String getResultONPExpression() {
        return resultTv.getText().toString();
    }

    @Override
    public void displayExpressionEmptyMessage() {
        clearResult();
    }

    @Override
    public void displayONPResult(String s) {
        resultTv.setText(s);
    }

    @Override
    public void displayEqualsResult(String s) {
        clearResult();
        inputTv.setText(s);

    }

    @Override
    public void appendInput(String s) {
        inputTv.append(s);
    }

    @Override
    public void displayInput(String s) {
        inputTv.setText(s);
    }

    @Override
    public void displayUnsupportedMessage() {
        Toast.makeText(this, getString(R.string.unsupported_message), Toast.LENGTH_SHORT).show();
    }

    @Override
    public void clearInput() {
        inputTv.setText("");
    }

    @Override
    public void displayButtonDEL() {
        btnDel.setText(getString(R.string.btn_del_text));

    }

    @Override
    public void displayButtonCLR() {
        btnDel.setText(R.string.btn_clr_text);

    }

    @Override
    public void displayMessage(String s) {
        Toast.makeText(this, s, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onClick(View view) {

        Button b = (Button) view;
        mainPresenter.onClick(view.getId(), getButtonChar(b), getInsertedExpression());

    }

    private Character getButtonChar(Button b) {
        return b.getText().toString().charAt(0);
    }

    public boolean getResultShown() {
        return mainPresenter.getResultShown();
    }
}
