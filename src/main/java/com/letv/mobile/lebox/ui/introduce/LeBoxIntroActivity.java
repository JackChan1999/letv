package com.letv.mobile.lebox.ui.introduce;

import android.app.Activity;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import com.letv.mobile.lebox.R;
import com.letv.mobile.lebox.jump.PageJumpUtil;

public class LeBoxIntroActivity extends Activity implements OnClickListener {
    private static final String TAG = "LeBoxIntroActivity";
    private Button mLeboxCommonFaq;
    private Button mLeboxIntroBuy;
    private Button mLeboxIntroConn;
    private TextView mLeboxIntroHowContent;
    private ImageView mTitleLeft;
    private TextView mTitleTextView;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lebox_introduce);
        initView();
    }

    private void initView() {
        this.mTitleLeft = (ImageView) findViewById(R.id.common_nav_left);
        this.mTitleTextView = (TextView) findViewById(R.id.common_nav_title);
        this.mTitleTextView.setText(R.string.btn_text_le_box_introduce);
        this.mLeboxIntroBuy = (Button) findViewById(R.id.btn_lebox_main_intro_buy);
        this.mLeboxIntroConn = (Button) findViewById(R.id.btn_lebox_main_intro_conn);
        this.mLeboxIntroHowContent = (TextView) findViewById(R.id.id_lebox_main_intro_how_content);
        this.mLeboxCommonFaq = (Button) findViewById(R.id.btn_lebox_main_intro_faq);
        this.mLeboxIntroHowContent.setText(Html.fromHtml(getString(R.string.lebox_main_intro_how_content)));
        this.mTitleLeft.setOnClickListener(this);
        this.mLeboxIntroBuy.setOnClickListener(this);
        this.mLeboxIntroConn.setOnClickListener(this);
        this.mLeboxCommonFaq.setOnClickListener(this);
    }

    protected void onResume() {
        super.onResume();
    }

    protected void onPause() {
        super.onPause();
    }

    protected void onStop() {
        super.onStop();
    }

    protected void onDestroy() {
        super.onDestroy();
    }

    public void onClick(View v) {
        int id = v.getId();
        if (R.id.common_nav_left == id) {
            finish();
        } else if (R.id.btn_lebox_main_intro_buy == id) {
            PageJumpUtil.jumpBuyPage(this);
            finish();
        } else if (R.id.btn_lebox_main_intro_conn == id) {
            PageJumpUtil.jumpQrCodeScan(this);
            finish();
        } else if (R.id.btn_lebox_main_intro_faq == id) {
            PageJumpUtil.jumpToBrowser(this, "http://bbs.le.com/forum-1468-1.html");
        }
    }
}
