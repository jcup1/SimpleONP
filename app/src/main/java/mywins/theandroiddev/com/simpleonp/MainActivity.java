package mywins.theandroiddev.com.simpleonp;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity implements MainView {

    MainPresenter mainPresenter;

    TextView resultIsTv, resultTv;
    EditText insertExpression;
    Button calculate;

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
        insertExpression = findViewById(R.id.insertExpressionEt);
        calculate = findViewById(R.id.calculate);


        mainPresenter = new MainPresenterImpl();
    }

    @Override
    protected void onStop() {
        super.onStop();
        mainPresenter.detachView();
    }

    @Override
    public void displayNumbers() {

    }
}
