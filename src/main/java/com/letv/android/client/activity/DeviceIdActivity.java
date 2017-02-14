package com.letv.android.client.activity;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import com.letv.android.client.R;
import com.letv.android.client.commonlib.activity.WrapActivity;
import com.letv.core.config.LetvConfig;
import com.letv.core.constant.LetvConstant.Global;
import com.letv.core.db.PreferencesManager;
import com.letv.core.utils.BaseTypeUtils;
import com.letv.core.utils.LetvUtils;
import com.letv.hackdex.VerifyLoad;
import com.letv.hotfixlib.HotFix;
import io.fabric.sdk.android.services.events.EventsFilesManager;

public class DeviceIdActivity extends WrapActivity {
    private RadioButton mButton_normal;
    private RadioButton mButton_server;
    private RadioButton mButton_test;
    private RadioGroup mRadioGroup;
    private LinearLayout mRadioLayout;
    private Spinner mSpinner;
    private CheckBox mUseDPCheckBox;
    private CheckBox mUseEndlessRecyclerCBox;

    public DeviceIdActivity() {
        if (HotFix.PREVENT_VERIFY) {
            System.out.println(VerifyLoad.class);
        }
    }

    protected void onCreate(Bundle arg0) {
        int i;
        int i2 = 0;
        super.onCreate(arg0);
        setContentView(R.layout.device_id);
        ((TextView) findViewById(R.id.device_id)).setText(Global.DEVICEID);
        findViewById(R.id.device_back_btn).setOnClickListener(new OnClickListener(this) {
            final /* synthetic */ DeviceIdActivity this$0;

            {
                if (HotFix.PREVENT_VERIFY) {
                    System.out.println(VerifyLoad.class);
                }
                this.this$0 = this$0;
            }

            public void onClick(View v) {
                this.this$0.finish();
            }
        });
        ((TextView) findViewById(R.id.pcode)).setText(Global.PCODE);
        ((TextView) findViewById(R.id.appkey)).setText(LetvConfig.getAppKey());
        this.mSpinner = (Spinner) findViewById(R.id.spinner);
        this.mSpinner.setVisibility(LetvConfig.isDebug() ? 0 : 8);
        this.mUseDPCheckBox = (CheckBox) findViewById(R.id.use_double_player);
        CheckBox checkBox = this.mUseDPCheckBox;
        if (LetvConfig.isDebug()) {
            i = 0;
        } else {
            i = 8;
        }
        checkBox.setVisibility(i);
        this.mUseEndlessRecyclerCBox = (CheckBox) findViewById(R.id.use_endless_recyclerview);
        CheckBox checkBox2 = this.mUseEndlessRecyclerCBox;
        if (!LetvConfig.isDebug()) {
            i2 = 8;
        }
        checkBox2.setVisibility(i2);
        initTestButton();
        refreshRadioState();
        setSpinner();
        initDP();
    }

    private void initDP() {
        boolean z = true;
        this.mUseDPCheckBox.setChecked(PreferencesManager.getInstance().getSwitchStreamEnable() == 1);
        this.mUseDPCheckBox.setOnCheckedChangeListener(new OnCheckedChangeListener(this) {
            final /* synthetic */ DeviceIdActivity this$0;

            {
                if (HotFix.PREVENT_VERIFY) {
                    System.out.println(VerifyLoad.class);
                }
                this.this$0 = this$0;
            }

            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                PreferencesManager.getInstance().setSwitchStreamEnable(isChecked ? 1 : 0);
            }
        });
        CheckBox checkBox = this.mUseEndlessRecyclerCBox;
        if (PreferencesManager.getInstance().getSwitchEndlessRecyclerEnable() != 1) {
            z = false;
        }
        checkBox.setChecked(z);
        this.mUseEndlessRecyclerCBox.setOnCheckedChangeListener(new OnCheckedChangeListener(this) {
            final /* synthetic */ DeviceIdActivity this$0;

            {
                if (HotFix.PREVENT_VERIFY) {
                    System.out.println(VerifyLoad.class);
                }
                this.this$0 = this$0;
            }

            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                PreferencesManager.getInstance().setSwitchEndlessRecyclerEnable(isChecked ? 1 : 0);
            }
        });
    }

    private void setSpinner() {
        final String[] cityArray = getResources().getStringArray(R.array.city_location);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.city_location, 17367048);
        adapter.setDropDownViewResource(17367049);
        this.mSpinner.setAdapter(adapter);
        this.mSpinner.setOnItemSelectedListener(new OnItemSelectedListener(this) {
            final /* synthetic */ DeviceIdActivity this$0;

            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long l) {
                PreferencesManager.getInstance().setCitySelect(position);
                PreferencesManager.getInstance().setLocationCode(cityArray[position]);
                if (position != 0) {
                    PreferencesManager.getInstance().setGeoCode(cityArray[position].substring(0, cityArray[position].lastIndexOf(EventsFilesManager.ROLL_OVER_FILE_NAME_SEPARATOR)));
                }
                LetvUtils.resetLoacationMessage();
            }

            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });
        if (BaseTypeUtils.getElementFromArray(cityArray, PreferencesManager.getInstance().getCitySelect()) != null) {
            this.mSpinner.setSelection(PreferencesManager.getInstance().getCitySelect());
        }
    }

    public String getActivityName() {
        return DeviceIdActivity.class.getName();
    }

    public Activity getActivity() {
        return this;
    }

    private void initTestButton() {
        this.mRadioLayout = (LinearLayout) findViewById(R.id.choose_layout);
        this.mRadioLayout.setVisibility(LetvConfig.isDebug() ? 0 : 8);
        this.mRadioGroup = (RadioGroup) findViewById(R.id.radioButton);
        this.mButton_test = (RadioButton) findViewById(R.id.test);
        this.mButton_normal = (RadioButton) findViewById(2131361814);
        this.mButton_server = (RadioButton) findViewById(R.id.server);
        this.mRadioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener(this) {
            final /* synthetic */ DeviceIdActivity this$0;

            {
                if (HotFix.PREVENT_VERIFY) {
                    System.out.println(VerifyLoad.class);
                }
                this.this$0 = this$0;
            }

            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId) {
                    case 2131361814:
                        PreferencesManager.getInstance().setApiLevel(1);
                        break;
                    case R.id.test /*2131362460*/:
                        PreferencesManager.getInstance().setApiLevel(0);
                        break;
                    case R.id.server /*2131362461*/:
                        PreferencesManager.getInstance().setApiLevel(2);
                        break;
                }
                this.this$0.refreshRadioState();
            }
        });
    }

    private void refreshRadioState() {
        switch (PreferencesManager.getInstance().getApiLevel().intValue()) {
            case 0:
                this.mButton_test.setChecked(true);
                return;
            case 1:
                this.mButton_normal.setChecked(true);
                return;
            case 2:
                this.mButton_server.setChecked(true);
                return;
            default:
                return;
        }
    }
}
