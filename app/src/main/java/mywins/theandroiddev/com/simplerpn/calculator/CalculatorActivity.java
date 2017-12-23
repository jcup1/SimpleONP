package mywins.theandroiddev.com.simplerpn.calculator;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import mywins.theandroiddev.com.simplerpn.R;

public class CalculatorActivity extends AppCompatActivity implements CalculatorMvpView, View.OnClickListener {


    public static final String INPUT_INSTANCE_STATE_KEY = "INPUT_INSTANCE_STATE";
    public static final String RESULT_INSTANCE_STATE_KEY = "RESULT_INSTANCE_STATE";
    public static final String RESULT_SHOWN_INSTANCE_STATE_KEY = "RESULT_SHOWN_INSTANCE_STATE";

    CalculatorMvpPresenter presenter;

    TextView inputTextView, resultTextView;
    Button num0Button, num1Button, num2Button, num3Button, num4Button, num5Button, num6Button, num7Button, num8Button, num9Button, dotButton, equalsButton;
    Button deleteButton, divideButton, multiplyButton, minusButton, plusButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        inputTextView = findViewById(R.id.input_text_view);
        resultTextView = findViewById(R.id.result_text_view);

        num0Button = findViewById(R.id.num_0_button);
        num1Button = findViewById(R.id.num_1_button);
        num2Button = findViewById(R.id.num_2_button);
        num3Button = findViewById(R.id.num_3_button);
        num4Button = findViewById(R.id.num_4_button);
        num5Button = findViewById(R.id.num_5_button);
        num6Button = findViewById(R.id.num_6_button);
        num7Button = findViewById(R.id.num_7_button);
        num8Button = findViewById(R.id.num_8_button);
        num9Button = findViewById(R.id.num_9_button);
        dotButton = findViewById(R.id.dot_button);
        equalsButton = findViewById(R.id.equals_button);

        deleteButton = findViewById(R.id.delete_button);
        divideButton = findViewById(R.id.divide_button);
        multiplyButton = findViewById(R.id.multiply_button);
        minusButton = findViewById(R.id.minus_button);
        plusButton = findViewById(R.id.plus_button);

        num0Button.setOnClickListener(this);
        num1Button.setOnClickListener(this);
        num2Button.setOnClickListener(this);
        num3Button.setOnClickListener(this);
        num4Button.setOnClickListener(this);
        num5Button.setOnClickListener(this);
        num6Button.setOnClickListener(this);
        num7Button.setOnClickListener(this);
        num8Button.setOnClickListener(this);
        num9Button.setOnClickListener(this);
        dotButton.setOnClickListener(this);
        equalsButton.setOnClickListener(this);

        deleteButton.setOnClickListener(this);
        divideButton.setOnClickListener(this);
        multiplyButton.setOnClickListener(this);
        minusButton.setOnClickListener(this);
        plusButton.setOnClickListener(this);

        presenter = new CalculatorMvpPresenterImpl();

    }

    @Override
    protected void onStart() {
        super.onStart();

        presenter.onAttachView(this);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);

        if (savedInstanceState != null) {
            inputTextView.setText(savedInstanceState.getString(INPUT_INSTANCE_STATE_KEY));
            resultTextView.setText(savedInstanceState.getString(RESULT_INSTANCE_STATE_KEY));
            presenter.setResultShown(savedInstanceState.getBoolean(RESULT_SHOWN_INSTANCE_STATE_KEY));

        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString(INPUT_INSTANCE_STATE_KEY, getInsertedExpression());
        outState.putString(RESULT_INSTANCE_STATE_KEY, getResultExpression());
        outState.putBoolean(RESULT_SHOWN_INSTANCE_STATE_KEY, isResultShown());
    }

    @Override
    protected void onStop() {
        super.onStop();
        presenter.onDetachView();
    }

    public void clearResult() {
        resultTextView.setText("");
    }

    public String getInsertedExpression() {
        return inputTextView.getText().toString();
    }

    public String getResultExpression() {
        return resultTextView.getText().toString();
    }

    @Override
    public void displayRpnResult(String result) {
        resultTextView.setText(result);
    }

    @Override
    public void displayEqualsResult(String result) {
        clearResult();
        inputTextView.setText(result);

    }

    @Override
    public void displayInputExpression(String expression) {
        inputTextView.setText(expression);
    }

    @Override
    public void displayExpressionEmptyMessage() {
        clearResult();
    }

    @Override
    public void displayUnsupportedMessage() {
        Toast.makeText(this, getString(R.string.unsupported), Toast.LENGTH_SHORT).show();
    }

    @Override
    public void clearInput() {
        inputTextView.setText("");
    }

    @Override
    public void displayDeleteButton() {
        deleteButton.setText(getString(R.string.delete_button));

    }

    @Override
    public void displayClearButton() {
        deleteButton.setText(R.string.clear_button);

    }

    @Override
    public void onClick(View view) {
        Button button;

        button = (Button) view;
        presenter.onClick(view.getId(), getButtonCharacter(button), getInsertedExpression());

    }

    private Character getButtonCharacter(Button button) {
        return button.getText().toString().charAt(0);
    }

    public boolean isResultShown() {
        return presenter.isResultShown();
    }
}
