package mywins.theandroiddev.com.simpleonp.main;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import mywins.theandroiddev.com.simpleonp.R;

import static android.view.View.INVISIBLE;
import static android.view.View.VISIBLE;
import static mywins.theandroiddev.com.simpleonp.R.string.expression_empty;

public class MainActivity extends AppCompatActivity implements MainView, View.OnClickListener {

    public static final String RESULT_SHOWN_KEY = "RESULT_SHOWN";
    public static final String RESULT_KEY = "RESULT";
    public static final String RESULT_EXPRESSION_LABEL = "RESULT_EXPRESSION";

    boolean resultShown = false;

    MainPresenter mainPresenter;

    TextView inputTv, resultTv;

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

//        if (savedInstanceState != null) {
//            resultShown = savedInstanceState.getBoolean(RESULT_SHOWN_KEY);
//            if (resultShown) {
//                setResultVisible();
//                resultTv.setText(savedInstanceState.getString(RESULT_KEY));
//            } else setResultInvisible();
//        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
//        outState.putBoolean(RESULT_SHOWN_KEY, resultShown);
//        outState.putString(RESULT_KEY, getResult());
    }

    @Override
    protected void onStop() {
        super.onStop();
        mainPresenter.onDetachView();
    }

    public void setResultVisible() {
        resultTv.setVisibility(VISIBLE);
        resultShown = true;
    }

    public void setResultInvisible() {
        resultTv.setVisibility(INVISIBLE);
        resultShown = false;
    }


//    @Override
//    public void onClick(View v) {
//        switch (v.getId()) {
//
//            case R.id.toONPButton:
//                mainPresenter.convertToONP(getInsertedExpression());
//                break;
//            case R.id.toInfixButton:
//                mainPresenter.convertToInfix(getInsertedExpression());
//                break;
//            case R.id.copyResultButton:
//                copyResultToClipboard(getResult());
//                showMessage(getString(result_copied));
//                break;
//            default:
//                break;
//        }
//    }

    public void clearResult() {
        resultTv.setText("");
    }

    private void showMessage(String s) {
        Toast.makeText(this, s, Toast.LENGTH_SHORT).show();
    }

    public String getInsertedExpression() {
        return inputTv.getText().toString();
    }

    public String getResult() {
        return resultTv.getText().toString();
    }

    private void copyResultToClipboard(String expression) {
        ClipboardManager clipboard = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
        ClipData clip = ClipData.newPlainText(RESULT_EXPRESSION_LABEL, expression);
        if (clipboard != null) {
            clipboard.setPrimaryClip(clip);
        }
    }

    @Override
    public void displayExpressionEmptyMessage() {
        setResultInvisible();
        clearResult();
        showMessage(getString(expression_empty));
    }

    @Override
    public void displayResult(String s) {
        setResultVisible();
        resultTv.setText(s);
    }

    @Override
    public void onClick(View view) {
        Button b = (Button) view;
        switch (view.getId()) {
            default:
                inputTv.append(b.getText() + " ");
        }
    }
}
