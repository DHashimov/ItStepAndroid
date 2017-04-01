package demoapp.itsteps.com.demoapp.ui;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import demoapp.itsteps.com.demoapp.R;
import demoapp.itsteps.com.singletons.PicassoInstance;

public class SecondActivity extends AppCompatActivity {


    @Bind(R.id.img_cover)
    ImageView imgCover;
    @Bind(R.id.progress)
    ProgressBar progressBar;
    private String url = "http://s.hswstatic.com/gif/mount-vesuvius-118385602.jpg";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);
        ButterKnife.bind(this);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                PicassoInstance.get(SecondActivity.this).with(SecondActivity.this)
                        .load(url)
                        .into(imgCover, new com.squareup.picasso.Callback() {

                            @Override
                            public void onSuccess() {
                                progressBar.setVisibility(View.GONE);
                            }

                            @Override
                            public void onError() {
                                progressBar.setVisibility(View.GONE);
                                Toast.makeText(SecondActivity.this, "There was an issue loading the image", Toast.LENGTH_SHORT);
                            }
                        });
            }
        }, 3000);
    }


    @OnClick(R.id.btn_next)
    public void onNextClicked() {
        Intent intent = new Intent(SecondActivity.this, ThirdActivity.class);
        startActivity(intent);
    }


}
