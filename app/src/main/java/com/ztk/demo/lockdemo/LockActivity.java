package com.ztk.demo.lockdemo;

import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.TextView;

import com.ztk.demo.lockdemo.view.SlidingFinishLayout;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import androidx.appcompat.app.AppCompatActivity;


/**
 * @author ztk
 */
public class LockActivity extends AppCompatActivity implements SlidingFinishLayout.OnSlidingFinishListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_DISMISS_KEYGUARD | WindowManager.LayoutParams.FLAG_SHOW_WHEN_LOCKED);
        getWindow().getDecorView().setSystemUiVisibility(
                View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                        | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                        | View.SYSTEM_UI_FLAG_FULLSCREEN
                        | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_IMMERSIVE// hide status bar
        );

        setContentView(R.layout.activity_lock);
        initView();
    }

    private void initView() {
        TextView tvLockTime = findViewById(R.id.lock_time);
        TextView tvLockDate = findViewById(R.id.lock_date);
        SlidingFinishLayout vLockRoot = findViewById(R.id.lock_root);
        vLockRoot.setOnSlidingFinishListener(this);

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("HH:mm-M月dd日 E", Locale.CHINESE);
        String[] date = simpleDateFormat.format(new Date()).split("-");
        tvLockTime.setText(date[0]);
        tvLockDate.setText(date[1]);
    }

    /**
     * 重写物理返回键，使不能回退
     */
    @Override
    public void onBackPressed() {
    }

    /**
     * 滑动销毁锁屏页面
     */
    @Override
    public void onSlidingFinish() {
        finish();
    }
}
