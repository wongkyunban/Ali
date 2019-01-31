package com.wong.ali;

import android.content.Intent;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;

import com.wong.ali.constant.Constant;

public class CaptureActivity extends AppCompatActivity {

    public static final int REQUEST_CODE_SCAN = 1;  //qr request code

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_capture);
        startQR();
    }
    private void startQR(){
        Intent intent = new Intent(CaptureActivity.this, CaptureActivity.class);
        /*ZxingConfig是配置类  可以设置是否显示底部布局，闪光灯，相册，是否播放提示音  震动等动能
         * 也可以不传这个参数
         * 不传的话  默认都为默认不震动  其他都为true
         * */

        //ZxingConfig config = new ZxingConfig();
        //config.setShowbottomLayout(true);//底部布局（包括闪光灯和相册）
        //config.setPlayBeep(true);//是否播放提示音
        //config.setShake(true);//是否震动
        //config.setShowAlbum(true);//是否显示相册
        //config.setShowFlashLight(true);//是否显示闪光灯
        //intent.putExtra(Constant.INTENT_ZXING_CONFIG, config);
        startActivityForResult(intent, REQUEST_CODE_SCAN);
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // 扫描二维码/条码回传
        if (requestCode == REQUEST_CODE_SCAN && resultCode == RESULT_OK) {
            if (data != null) {

                String content = data.getStringExtra(Constant.CODED_CONTENT);
//                result.setText("扫描结果为：" + content);
            }
        }
    }
}
