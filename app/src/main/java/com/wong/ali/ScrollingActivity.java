package com.wong.ali;

import android.content.Intent;
import android.os.Bundle;
import com.google.android.material.appbar.AppBarLayout;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import androidx.appcompat.widget.Toolbar;

import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.wong.ali.amap.location.CheckPermissionsActivity;
import com.wong.ali.annotation.MyButterKnife;
import com.wong.ali.annotation.OnMyBindView;
import com.wong.ali.constant.Constant;
import com.wong.ali.qr.MainActivity;
import com.wong.ali.qr.ScanQrActivity;
import com.wong.ali.utils.SharedPreferencesHelper;
import com.wong.ali.web.BrowserActivity;

public class ScrollingActivity extends CheckPermissionsActivity implements AppBarLayout.OnOffsetChangedListener, View.OnClickListener {


    private AppBarLayout appBar;
    /**
     * 大布局背景，遮罩层
     */
    private View bgContent;
    /**
     * 展开状态下toolbar显示的内容
     */
    private View toolbarOpen;
    /**
     * 展开状态下toolbar的遮罩层
     */
    private View bgToolbarOpen;
    /**
     * 收缩状态下toolbar显示的内容
     */
    private View toolbarClose;
    /**
     * 收缩状态下toolbar的遮罩层
     */
    private View bgToolbarClose;

    private SharedPreferencesHelper helper;


    @OnMyBindView(R.id.ll_scan)
    LinearLayout mLlScan;
    @OnMyBindView(R.id.ll_run)
    LinearLayout mLlRun;
    @OnMyBindView(R.id.ll_map)
    LinearLayout mLlMap;

    @OnMyBindView(R.id.iv_scan)
    ImageView mIvScan;
    @OnMyBindView(R.id.iv_run)
    ImageView mIvRun;
    @OnMyBindView(R.id.iv_map)
    ImageView mIvMap;

    @OnMyBindView(R.id.et_search_product)
    EditText mEtSearch;

    @OnMyBindView(R.id.iv_search)
    ImageView mIvSearch;

    @OnMyBindView(R.id.iv_setting)
    ImageView mIvSetting;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scrolling);
        MyButterKnife.init(this);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        helper = new SharedPreferencesHelper(
                getApplicationContext(), Constant.SHARED_DB);
        initViews();


    }


    private void initViews() {
        appBar = findViewById(R.id.app_bar);
        bgContent = findViewById(R.id.bg_content);
        toolbarOpen = findViewById(R.id.include_toolbar_open);
        bgToolbarOpen = findViewById(R.id.bg_toolbar_open);
        toolbarClose = findViewById(R.id.include_toolbar_close);
        bgToolbarClose = findViewById(R.id.bg_toolbar_close);

        appBar.addOnOffsetChangedListener(this);

        mLlScan.setOnClickListener(this);
        mLlRun.setOnClickListener(this);
        mLlMap.setOnClickListener(this);

        mIvScan.setOnClickListener(this);
        mIvRun.setOnClickListener(this);
        mIvMap.setOnClickListener(this);
        mIvSearch.setOnClickListener(this);
        mIvSetting.setOnClickListener(this);

        mEtSearch.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                    searchByEngine();
                    return true;
                }
                return false;
            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_scrolling, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
        //垂直方向偏移量
        int offset = Math.abs(verticalOffset);
        //最大偏移距离
        int scrollRange = appBarLayout.getTotalScrollRange();
        if (offset <= scrollRange / 2) {//当滑动没超过一半，展开状态下toolbar显示内容，根据收缩位置，改变透明值
            toolbarOpen.setVisibility(View.VISIBLE);
            toolbarClose.setVisibility(View.GONE);
            //根据偏移百分比 计算透明值
            float scale2 = (float) offset / (scrollRange / 2);
            int alpha2 = (int) (255 * scale2);
//            bgToolbarOpen.setBackgroundColor(Color.argb(alpha2, 25, 209, 131));

        } else {//当滑动超过一半，收缩状态下toolbar显示内容，根据收缩位置，改变透明值
            toolbarClose.setVisibility(View.VISIBLE);
            toolbarOpen.setVisibility(View.GONE);
            float scale3 = (float) (scrollRange - offset) / (scrollRange / 2);
            int alpha3 = (int) (255 * scale3);
//            bgToolbarClose.setBackgroundColor(Color.argb(alpha3, 25, 209, 131));
        }
        //根据偏移百分比计算扫一扫布局的透明度值
        float scale = (float) offset / scrollRange;
        int alpha = (int) (255 * scale);
//        bgContent.setBackgroundColor(Color.argb(alpha, 25, 209, 131));
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {


            //scan
            case R.id.ll_scan:
            case R.id.iv_scan:

                ScrollingActivity.this.startActivity(new Intent(ScrollingActivity.this, ScanQrActivity.class));
                break;
            //run
            case R.id.ll_run:
            case R.id.iv_run:
                ScrollingActivity.this.startActivity(new Intent(ScrollingActivity.this, MainActivity.class));

                break;
            //map
            case R.id.ll_map:
            case R.id.iv_map:

                break;

                //search
            case R.id.iv_search:

              searchByEngine();
                break;

            case R.id.iv_setting:
            SettingActivity.startActivity(ScrollingActivity.this);
                break;


        }

    }

    private void searchByEngine(){
        String param = mEtSearch.getText().toString();
        if(!TextUtils.isEmpty(param)){
            String url = helper.getValue(Constant.SEARCH_ENGINE,Constant.BAI_DU) + param;
            BrowserActivity.startActivity(ScrollingActivity.this,url);
        }

    }
}
