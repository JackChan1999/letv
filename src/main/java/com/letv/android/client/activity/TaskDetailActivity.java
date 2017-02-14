package com.letv.android.client.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;
import com.letv.android.client.R;
import com.letv.hackdex.VerifyLoad;
import com.letv.hotfixlib.HotFix;

public class TaskDetailActivity extends PimBaseActivity {
    private TextView _task_summary;
    private Button backHome;
    private int flag;
    private int points;
    private TextView task_detail;
    private TextView task_summary;
    private TextView task_title;

    public TaskDetailActivity() {
        if (HotFix.PREVENT_VERIFY) {
            System.out.println(VerifyLoad.class);
        }
        this.flag = 0;
        this.points = 0;
    }

    public int getContentView() {
        return R.layout.task_detail;
    }

    protected void onCreate(Bundle arg0) {
        super.onCreate(arg0);
        initUI();
        initView();
        initListener();
        setTitle(2131100925);
    }

    public void initUI() {
        super.initUI();
        this.task_detail = (TextView) findViewById(R.id.task_detail);
        this.task_title = (TextView) findViewById(R.id.task_title);
        this.task_summary = (TextView) findViewById(R.id.task_summary);
        this._task_summary = (TextView) findViewById(R.id._task_summary);
        this.backHome = (Button) findViewById(R.id.backHome);
    }

    private void initListener() {
        this.backHome.setOnClickListener(new OnClickListener(this) {
            final /* synthetic */ TaskDetailActivity this$0;

            {
                if (HotFix.PREVENT_VERIFY) {
                    System.out.println(VerifyLoad.class);
                }
                this.this$0 = this$0;
            }

            public void onClick(View arg0) {
                Intent intent = new Intent(this.this$0, MainActivity.class);
                intent.setFlags(536870912);
                this.this$0.startActivity(intent);
            }
        });
    }

    private void initView() {
        Intent intent = getIntent();
        this.points = intent.getIntExtra("my_points", 0);
        String summary = "";
        if (this.points == 0) {
            summary = getString(2131100904);
        } else {
            summary = String.format(getString(2131100903), new Object[]{Integer.valueOf(this.points)});
        }
        this.flag = intent.getIntExtra("flag", R.id.leVideo);
        switch (this.flag) {
            case 0:
                this.task_detail.setText(2131100926);
                this.task_summary.setText(summary);
                this.task_title.setText(getString(2131100536));
                this._task_summary.setText("+5");
                return;
            case 1:
                this.task_detail.setText(2131100927);
                this.task_summary.setText(summary);
                this.task_title.setText(getString(2131101145));
                this._task_summary.setText("+1");
                return;
            case 2:
                this.task_detail.setText(2131100928);
                this.task_summary.setText(summary);
                this.task_title.setText(getString(2131100848));
                this._task_summary.setText("+5");
                return;
            default:
                return;
        }
    }

    public String getActivityName() {
        return TaskDetailActivity.class.getName();
    }

    public Activity getActivity() {
        return this;
    }
}
