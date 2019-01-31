package com.wong.ali.qr;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.wong.ali.BaseActivity;
import com.wong.ali.R;
import com.wong.ali.annotation.MyButterKnife;
import com.wong.ali.annotation.OnMyBindView;
import com.wong.ali.utils.WifiAdmin;

public class WIFIActivity extends BaseActivity {


    private static final String PARAM = "param";

    public static final void startActivity(Context context, String param) {
        Intent intent = new Intent();
        Bundle bundle = new Bundle();
        bundle.putString(PARAM, param);
        intent.putExtras(bundle);
        intent.setClass(context, WIFIActivity.class);
        context.startActivity(intent);
    }


    @OnMyBindView(R.id.tv_wifi_name)
    TextView mTvWifiName;

    @OnMyBindView(R.id.btn_wifi_connect)
    Button mBtnWifi;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wifi);
        MyButterKnife.init(this);

        connectWifi();

    }

    private WifiAdmin wifiAdmin;

    private void connectWifi() {


        wifiAdmin = new WifiAdmin(WIFIActivity.this);
        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            String strResult = bundle.getString(PARAM);

//            WIFI:T:WPA;P:"12345678";S:john_wifi;
            if (strResult != null && strResult.contains("P:") && strResult.contains("T:")) {// 自动连接wifi
                Log.e("扫描返回的结果----->", strResult);// 还是要判断
                String passwordTemp = strResult.substring(strResult
                        .indexOf("P:"));
                final String password = passwordTemp.substring(2,
                        passwordTemp.indexOf(";"));
                String netWorkTypeTemp = strResult.substring(strResult
                        .indexOf("T:"));
                final String netWorkType = netWorkTypeTemp.substring(2,
                        netWorkTypeTemp.indexOf(";"));
                String netWorkNameTemp = strResult.substring(strResult
                        .indexOf("S:"));
                final String netWorkName = netWorkNameTemp.substring(2,
                        netWorkNameTemp.indexOf(";"));

                mTvWifiName.setText(netWorkName);

                if (!wifiAdmin.mWifiManager.isWifiEnabled()) {
                    Toast.makeText(this, "开启wifi设置", Toast.LENGTH_LONG)
                            .show();
                    wifiAdmin.openWifi();
                }


                mBtnWifi.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        openConnectWifiDialog(netWorkType, netWorkName, password);
                    }
                });


            }

        } else {
            finish();
        }
    }

    private void openConnectWifiDialog(final String netWorkType, final String netWorkName, final String password) {
        Dialog alertDialog = new AlertDialog.Builder(this)
                .setTitle("扫描到可用wifi")
                .setIcon(R.drawable.ic_launcher)
                .setMessage("wifi名：" + netWorkName)
                .setNegativeButton("取消",
                        new DialogInterface.OnClickListener() {

                            @Override
                            public void onClick(
                                    DialogInterface dialog,
                                    int which) {
                                // TODO Auto-generated method stub
                            }
                        })
                .setPositiveButton("加入此wifi ",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(
                                    DialogInterface dialog,
                                    int which) {
                                int net_type = 0x13;
                                if (netWorkType
                                        .compareToIgnoreCase("wpa") == 0) {
                                    net_type = WifiAdmin.TYPE_WPA;// wpa
                                } else if (netWorkType
                                        .compareToIgnoreCase("wep") == 0) {
                                    net_type = WifiAdmin.TYPE_WEP;// wep
                                } else {
                                    net_type = WifiAdmin.TYPE_NO_PASSWD;// 无加密
                                }
                                wifiAdmin.addNetwork(netWorkName,
                                        password,
                                        net_type);

                                finish();

                            }
                        }).create();
        alertDialog.show();
    }
}
