package demoapp.itsteps.com.demoapp.ui;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import demoapp.itsteps.com.demoapp.R;

/**
 * Created by dhashimov on 3/18/17.
 */

public class MainActivity extends Activity {

    private TextView txtHellow;
    private Button btnClick;
    private Button btnDecrease;
    private Button btnNext;
    private int counter = 0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        txtHellow = (TextView) findViewById(R.id.txt_hello);
        btnClick = (Button) findViewById(R.id.btn_click_me);
        btnClick.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                txtHellow.setText(""+counter);
                counter++;
            }
        });
        btnDecrease = (Button) findViewById(R.id.btn_decrease);
        btnDecrease.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                txtHellow.setText(""+counter);
                counter--;
            }
        });
        btnNext = (Button) findViewById(R.id.btn_next);
        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent =  new Intent(MainActivity.this , SecondActivity.class);
                startActivity(intent);
            }
        });

    }


    //Used when activity is started for result
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
    }
}
