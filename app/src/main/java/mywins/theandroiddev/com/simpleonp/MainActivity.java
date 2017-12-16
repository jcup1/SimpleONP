package mywins.theandroiddev.com.simpleonp;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements MainView, View.OnClickListener {

    MainPresenter mainPresenter;

    TextView resultIsTv, resultTv;
    EditText insertExpressionEt;
    Button toInfixButton, toONPButton;

    @Override
    protected void onStart() {
        super.onStart();

        mainPresenter.attachView(this);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        resultIsTv = findViewById(R.id.resultIsTv);
        resultTv = findViewById(R.id.resultTv);
        insertExpressionEt = findViewById(R.id.insertExpressionEt);
        toInfixButton = findViewById(R.id.toInfixButton);
        toONPButton = findViewById(R.id.toONPButton);

        setResultInvisible();

        toInfixButton.setOnClickListener(this);
        toONPButton.setOnClickListener(this);



        mainPresenter = new MainPresenterImpl();
    }

    @Override
    protected void onStop() {
        super.onStop();
        mainPresenter.detachView();
    }

    @Override
    public void displayExpressionNotProper() {
        setResultInvisible();
        clearResult();
        showMessage("Expression not proper");
    }

    @Override
    public void displayResult(String s) {
        setResultVisible();
        resultTv.setText(s);
    }

    public void setResultVisible() {
        resultTv.setVisibility(View.VISIBLE);
        resultIsTv.setVisibility(View.VISIBLE);
    }

    public void setResultInvisible() {
        resultTv.setVisibility(View.INVISIBLE);
        resultIsTv.setVisibility(View.INVISIBLE);
    }

    public void clearResult() {
        resultTv.setText("");
    }

    private void showMessage(String s) {
        Toast.makeText(this, s, Toast.LENGTH_SHORT).show();
    }

    public String getExpression() {
        return insertExpressionEt.getText().toString();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {

            case R.id.toONPButton:
                mainPresenter.toONP(getExpression());
                break;
            case R.id.toInfixButton:
                mainPresenter.toInfix(getExpression());
                break;
            default:
                break;
        }
    }
}
