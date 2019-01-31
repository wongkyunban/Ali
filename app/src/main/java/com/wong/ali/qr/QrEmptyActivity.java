package com.wong.ali.qr;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import com.wong.ali.BaseActivity;
import com.wong.ali.R;
import com.wong.ali.annotation.MyButterKnife;
import com.wong.ali.annotation.OnMyBindView;

import java.net.MalformedURLException;
import java.net.URL;

public class QrEmptyActivity extends BaseActivity {

    private static final String PARAM  = "param";

    public static final void startActivity(Context context,String param){
        Intent intent = new Intent();
        Bundle bundle = new Bundle();
        bundle.putString(PARAM,param);
        intent.putExtras(bundle);
        intent.setClass(context,QrEmptyActivity.class);
        context.startActivity(intent);
    }

    @OnMyBindView(R.id.tv_qr_content)
    TextView mTvQrContent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_qr_empty);
        MyButterKnife.init(this);

        initDatas();

        setTitle("扫一扫");
    }

    private void initDatas(){
        Intent intent = getIntent();

        if (intent == null || intent.getExtras() == null) {
            finish();
        }else {
            Bundle bundle = intent.getExtras();
            mTvQrContent.setText(bundle.getString(PARAM));
        }
    }
}
