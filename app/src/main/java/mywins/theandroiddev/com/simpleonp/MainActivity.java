package mywins.theandroiddev.com.simpleonp;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import static android.view.View.GONE;
import static android.view.View.INVISIBLE;
import static android.view.View.OnClickListener;
import static android.view.View.VISIBLE;
import static mywins.theandroiddev.com.simpleonp.R.string.expression_empty;
import static mywins.theandroiddev.com.simpleonp.R.string.result_copied;

public class MainActivity extends AppCompatActivity implements MainView, OnClickListener {

    MainPresenter mainPresenter;

    TextView resultIsTv, resultTv;
    EditText insertExpressionEt;
    Button toInfixButton, toONPButton, copyResultButton;
    boolean resultShown = false;

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
        copyResultButton = findViewById(R.id.copyResultButton);


        toInfixButton.setOnClickListener(this);
        toONPButton.setOnClickListener(this);
        copyResultButton.setOnClickListener(this);

        mainPresenter = new MainPresenterImpl();

        if (savedInstanceState != null) {
            resultShown = savedInstanceState.getBoolean("RESULT_SHOWN");
            if (resultShown) {
                setResultVisible();
                resultTv.setText(savedInstanceState.getString("RESULT"));
            } else setResultInvisible();
        }

    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putBoolean("RESULT_SHOWN", resultShown);
        outState.putString("RESULT", getResult());
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
        showMessage(getString(expression_empty));
    }

    @Override
    public void displayResult(String s) {
        setResultVisible();
        resultTv.setText(s);
    }

    public void setResultVisible() {
        resultTv.setVisibility(VISIBLE);
        resultIsTv.setVisibility(VISIBLE);
        copyResultButton.setVisibility(VISIBLE);
        resultShown = true;
    }

    public void setResultInvisible() {
        resultTv.setVisibility(INVISIBLE);
        resultIsTv.setVisibility(INVISIBLE);
        copyResultButton.setVisibility(GONE);
        resultShown = false;
    }

    public void clearResult() {
        resultIsTv.setText("");
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
            case R.id.copyResultButton:
                copyResult(getResult());
                showMessage(getString(result_copied));
                break;
            default:
                break;
        }
    }

    private void copyResult(String expression) {
        ClipboardManager clipboard = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
        ClipData clip = ClipData.newPlainText("result expression", expression);
        if (clipboard != null) {
            clipboard.setPrimaryClip(clip);
        }
    }

    public String getResult() {
        return resultTv.getText().toString();
    }
}
