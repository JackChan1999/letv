package com.letv.android.client.dlna.controller;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnDismissListener;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import com.letv.android.client.commonlib.adapter.LetvBaseArrayAdapter;
import com.letv.android.client.commonlib.adapter.ViewHolder;
import com.letv.android.client.dlna.R;
import com.letv.core.utils.BaseTypeUtils;
import com.letv.core.utils.UIsUtils;
import com.tencent.open.yyb.TitleBar;
import java.util.List;
import org.cybergarage.upnp.Device;

public class DLNADevicesDialogHelper {
    private DevicesListAdapter mAdapter;
    private Context mContext;
    private DLNAController mController;
    private Dialog mDevicesDialog;

    private class DevicesListAdapter extends LetvBaseArrayAdapter<Device> {
        public DevicesListAdapter(Context context) {
            super(context);
        }

        public void setList(List<Device> list) {
            clear();
            super.setList(list);
        }

        public View getView(int position, View convertView, ViewGroup parent) {
            final Device deviceInfo = (Device) getItem(position);
            ViewHolder holder = ViewHolder.get(this.mContext, convertView, R.layout.devices_list_item);
            TextView devName = (TextView) holder.getView(R.id.device_title);
            int padding = UIsUtils.dipToPx(TitleBar.SHAREBTN_RIGHT_MARGIN);
            devName.setPadding(0, padding, 0, padding);
            devName.setText(BaseTypeUtils.ensureStringValidate(deviceInfo.getFriendlyName()));
            ImageView iv = (ImageView) holder.getView(R.id.device_already_playing);
            iv.setVisibility(4);
            Device playingDevice = DLNADevicesDialogHelper.this.mController.mPlayingDevice;
            if (playingDevice != null && playingDevice.getFriendlyName().equals(deviceInfo.getFriendlyName())) {
                iv.setVisibility(0);
            }
            holder.getConvertView().setOnClickListener(new OnClickListener() {
                public void onClick(View v) {
                    DLNADevicesDialogHelper.this.mController.play(deviceInfo);
                    DLNADevicesDialogHelper.this.dismissDialog();
                }
            });
            return holder.getConvertView();
        }
    }

    public DLNADevicesDialogHelper(Context context, DLNAController controller) {
        this.mContext = context;
        this.mController = controller;
    }

    public void refreshAdapter(List<Device> list) {
        if (this.mAdapter == null || this.mDevicesDialog == null) {
            showDialog(list);
        } else {
            this.mAdapter.setList(list);
        }
    }

    private void showDialog(List<Device> list) {
        if (this.mDevicesDialog != null) {
            this.mDevicesDialog.dismiss();
            this.mDevicesDialog = null;
        }
        this.mDevicesDialog = new Dialog(this.mContext, R.style.dlna_push_style);
        this.mDevicesDialog.getWindow().setContentView(R.layout.layout_devices_list);
        ((TextView) this.mDevicesDialog.findViewById(R.id.ts_title)).setText(R.string.dlna_device_list);
        ListView devicesList = (ListView) this.mDevicesDialog.getWindow().findViewById(R.id.devices_list);
        this.mDevicesDialog.findViewById(R.id.cancel).setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                DLNADevicesDialogHelper.this.dismissDialog();
            }
        });
        this.mAdapter = new DevicesListAdapter(this.mContext);
        devicesList.setAdapter(this.mAdapter);
        this.mAdapter.setList(list);
        devicesList.setOnItemClickListener(new OnItemClickListener() {
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                DLNADevicesDialogHelper.this.dismissDialog();
            }
        });
        this.mDevicesDialog.setCanceledOnTouchOutside(false);
        this.mDevicesDialog.getWindow().setLayout(UIsUtils.dipToPx(310.0f), UIsUtils.dipToPx(44.0f) * 5);
        this.mDevicesDialog.setOnDismissListener(new OnDismissListener() {
            public void onDismiss(DialogInterface dialog) {
                DLNADevicesDialogHelper.this.mAdapter = null;
            }
        });
        try {
            this.mDevicesDialog.show();
        } catch (Exception e) {
        }
    }

    private void dismissDialog() {
        if (this.mDevicesDialog != null && this.mContext != null) {
            try {
                this.mDevicesDialog.dismiss();
                this.mDevicesDialog = null;
            } catch (Exception e) {
            }
            this.mAdapter = null;
        }
    }
}
