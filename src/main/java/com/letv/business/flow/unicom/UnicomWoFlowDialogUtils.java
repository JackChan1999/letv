package com.letv.business.flow.unicom;

import android.app.Activity;
import android.app.AlertDialog.Builder;
import android.app.Dialog;
import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.widget.CheckBox;
import android.widget.TextView;
import com.letv.android.client.business.R;
import com.letv.android.wo.ex.WoInterface.LetvWoFlowListener;
import com.letv.core.BaseApplication;
import com.letv.core.bean.AlbumInfo.Channel;
import com.letv.core.db.PreferencesManager;
import com.letv.core.utils.LogInfo;
import com.letv.core.utils.NetworkUtils;
import com.letv.core.utils.StatisticsUtils;
import com.letv.core.utils.UIsUtils;
import com.letv.datastatistics.constant.PageIdConstant;
import com.letv.plugin.pluginconfig.commom.JarConstant;
import com.letv.plugin.pluginloader.loader.JarResOverrideInterface;

public class UnicomWoFlowDialogUtils {
    private static final int CREATE_DIALOG = 1;
    private static final int UNICOM_TOAST_DELAY_TIME = 6000;
    private static boolean isShowDialog = false;
    private Handler mHandler = new Handler() {
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            int i = msg.what;
        }
    };

    public interface UnicomDialogClickListener {
        void onCancel();

        void onConfirm();

        void onResponse(boolean z);
    }

    public static void showWoFreeActivatedToast(Context context) {
        if (context != null) {
            try {
                final Dialog dialog = new Builder(context).create();
                View view = LayoutInflater.from(context).inflate(R.layout.wo_usable, null);
                view.setOnTouchListener(new OnTouchListener() {
                    public boolean onTouch(View v, MotionEvent event) {
                        dialog.dismiss();
                        return true;
                    }
                });
                dialog.show();
                dialog.getWindow().setContentView(view);
                dialog.setCanceledOnTouchOutside(false);
                new Handler(context.getMainLooper()).postDelayed(new Runnable() {
                    public void run() {
                        try {
                            dialog.dismiss();
                        } catch (Exception e) {
                        }
                    }
                }, 6000);
            } catch (Exception e) {
            }
        }
    }

    public static void showDialog(Context context, final UnicomDialogClickListener listener) {
        try {
            final Dialog dialog = new Builder(context).create();
            View view = LayoutInflater.from(context).inflate(R.layout.wo_play_end_dialog, null);
            view.setOnTouchListener(new OnTouchListener() {
                public boolean onTouch(View v, MotionEvent event) {
                    return true;
                }
            });
            ((TextView) view.findViewById(R.id.second_record)).setOnClickListener(new OnClickListener() {
                public void onClick(View view) {
                    dialog.dismiss();
                    if (listener != null) {
                        listener.onConfirm();
                    }
                }
            });
            dialog.show();
            dialog.setCanceledOnTouchOutside(false);
            dialog.getWindow().setContentView(view);
        } catch (Exception e) {
        }
    }

    public void showOrderConfirmEnquireDialog(Context context, final UnicomDialogClickListener listener) {
        if (context == null) {
            try {
                if (isShowDialog) {
                    LogInfo.log("unicom", "直接返回");
                    return;
                }
            } catch (Exception e) {
                listener.onResponse(isShowDialog);
                return;
            }
        }
        View view = LayoutInflater.from(context).inflate(R.layout.wo_play_wo_inik_sdk_fail_dialog, null);
        Builder builder = new Builder(context);
        builder.setView(view);
        final Dialog dialog = builder.create();
        if (!(context instanceof Activity)) {
            dialog.getWindow().setType(Channel.TYPE_WEBVIEW);
        }
        dialog.show();
        view.setOnTouchListener(new OnTouchListener() {
            public boolean onTouch(View v, MotionEvent event) {
                return true;
            }
        });
        TextView unOrder = (TextView) view.findViewById(R.id.wo_init_sdk_fail_two);
        ((TextView) view.findViewById(R.id.wo_init_sdk_fail_three)).setOnClickListener(new OnClickListener() {
            public void onClick(View view) {
                UnicomWoFlowDialogUtils.isShowDialog = false;
                dialog.dismiss();
                if (listener != null) {
                    UnicomWoFlowDialogUtils.this.mHandler.post(new Runnable() {
                        public void run() {
                            listener.onConfirm();
                        }
                    });
                }
            }
        });
        unOrder.setOnClickListener(new OnClickListener() {
            public void onClick(View view) {
                UnicomWoFlowDialogUtils.isShowDialog = false;
                dialog.dismiss();
                if (listener != null) {
                    UnicomWoFlowDialogUtils.this.mHandler.post(new Runnable() {
                        public void run() {
                            listener.onCancel();
                        }
                    });
                }
            }
        });
        isShowDialog = true;
        dialog.setCanceledOnTouchOutside(false);
    }

    public static void woWebViewNotPlayDialog(Context context, final UnicomDialogClickListener listener) {
        if (context == null) {
            try {
                if (isShowDialog) {
                    return;
                }
            } catch (Exception e) {
                return;
            }
        }
        if (context instanceof JarResOverrideInterface) {
            ((JarResOverrideInterface) context).setResourcePath(false, null, null);
        }
        final Dialog dialog = new Builder(context).create();
        View view = LayoutInflater.from(context).inflate(R.layout.wo_play_wo_webview_not_play_dialog, null);
        view.setOnTouchListener(new OnTouchListener() {
            public boolean onTouch(View v, MotionEvent event) {
                return true;
            }
        });
        TextView btn_return = (TextView) view.findViewById(R.id.wo_flow_flow_wo_webview_return);
        ((TextView) view.findViewById(R.id.wo_flow_flow_wo_webview_continue_play)).setOnClickListener(new OnClickListener() {
            public void onClick(View view) {
                UnicomWoFlowDialogUtils.isShowDialog = false;
                dialog.dismiss();
                if (listener != null) {
                    listener.onConfirm();
                }
            }
        });
        btn_return.setOnClickListener(new OnClickListener() {
            public void onClick(View view) {
                UnicomWoFlowDialogUtils.isShowDialog = false;
                dialog.dismiss();
                if (listener != null) {
                    listener.onCancel();
                }
            }
        });
        dialog.show();
        isShowDialog = true;
        dialog.setCanceledOnTouchOutside(false);
        dialog.getWindow().setContentView(view);
        if (context instanceof JarResOverrideInterface) {
            ((JarResOverrideInterface) context).setResourcePath(true, "LetvLeso.apk", JarConstant.LETV_LESO_PACKAGENAME);
        }
    }

    public void showWoMainDialog(final Context context, final UnicomDialogClickListener listener, final String str) {
        if (context != null) {
            try {
                if (context instanceof Activity) {
                    UnicomWoFlowManager.getInstance().checkUnicomWoFreeFlow(context, new LetvWoFlowListener() {
                        public void onResponseOrderInfo(boolean isSupportProvince, boolean isOrder, boolean isUnOrderSure, String freeUrl, boolean isSmsSuccess) {
                            if (context != null) {
                                UnicomWoFlowDialogUtils.this.mHandler.post(new Runnable() {
                                    public void run() {
                                        try {
                                            UnicomWoFlowDialogUtils.this.showUnicomTrafficPackagesDialog(context, listener, str);
                                        } catch (Exception e) {
                                            LogInfo.log("UnicomUtils", "woMainDialog e=" + e.getMessage());
                                        }
                                    }
                                });
                            }
                        }
                    });
                }
            } catch (Exception e) {
                if (listener != null) {
                    listener.onResponse(false);
                }
            }
        }
    }

    public void showUnicomTrafficPackagesDialog(Context context, final UnicomDialogClickListener listener, String str) {
        if (context != null && listener != null) {
            try {
                final Dialog dialog = new Builder(context).create();
                View view = LayoutInflater.from(context).inflate(R.layout.wo_flow_main, null);
                view.setOnTouchListener(new OnTouchListener() {
                    public boolean onTouch(View v, MotionEvent event) {
                        return true;
                    }
                });
                TextView woBuy = (TextView) view.findViewById(R.id.wo_flow_main_dialog_buy);
                final CheckBox woCheckBox = (CheckBox) view.findViewById(R.id.wo_flow_main_dialog_checkbox);
                final UnicomDialogClickListener unicomDialogClickListener = listener;
                final Context context2 = context;
                final String str2 = str;
                ((TextView) view.findViewById(R.id.wo_flow_main_dialog_know)).setOnClickListener(new OnClickListener() {
                    public void onClick(View view) {
                        dialog.dismiss();
                        if (unicomDialogClickListener != null) {
                            unicomDialogClickListener.onCancel();
                        }
                        StatisticsUtils.staticticsInfoPost(context2, "19", "h23", null, 1, null, UnicomWoFlowDialogUtils.getPageId(str2), null, null, null, null, null);
                    }
                });
                woBuy.setOnClickListener(new OnClickListener() {
                    public void onClick(View view) {
                        if (NetworkUtils.getNetworkType() == 0) {
                            UIsUtils.showToast(R.string.wo_flow_flow_no_net_toast);
                            return;
                        }
                        dialog.dismiss();
                        if (listener != null) {
                            listener.onConfirm();
                        }
                    }
                });
                woCheckBox.setOnClickListener(new OnClickListener() {
                    public void onClick(View view) {
                        PreferencesManager.getInstance().setWoFlowAlert(woCheckBox.isChecked());
                    }
                });
                dialog.show();
                dialog.getWindow().setContentView(view);
                dialog.getWindow().getAttributes().width = UIsUtils.dipToPx(270.0f);
                dialog.setCanceledOnTouchOutside(false);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public static String getPageId(String str) {
        String pageid = com.letv.pp.utils.NetworkUtils.DELIMITER_LINE;
        if ("LetvChannelActivity".equals(str)) {
            return PageIdConstant.categoryPage;
        }
        if ("LetvHomeActivity".equals(str)) {
            return PageIdConstant.index;
        }
        if ("LetvHotActivity".equals(str)) {
            return PageIdConstant.hotIndexCategoryPage;
        }
        if ("LetvLiveActivity".equals(str)) {
            return PageIdConstant.onLiveIndexCtegoryPage;
        }
        if ("LetvTopicProxyActivity".equals(str)) {
            return PageIdConstant.topicListCategoryPage;
        }
        if ("TopMyActivity".equals(str)) {
            return PageIdConstant.myHomePage;
        }
        if (!"LetvBaseActivity".equals(str)) {
            return pageid;
        }
        if (UIsUtils.isLandscape(BaseApplication.getInstance())) {
            return PageIdConstant.fullPlayPage;
        }
        return PageIdConstant.halpPlayPage;
    }

    public static void woVipCancelDialog(Context context) {
        if (context != null) {
            try {
                final Dialog dialog = new Builder(context).create();
                View view = LayoutInflater.from(context).inflate(R.layout.wo_flow_dialog_other, null);
                view.setOnTouchListener(new OnTouchListener() {
                    public boolean onTouch(View v, MotionEvent event) {
                        return true;
                    }
                });
                ((TextView) view.findViewById(R.id.wo_flow_main_dialog_know)).setOnClickListener(new OnClickListener() {
                    public void onClick(View view) {
                        dialog.dismiss();
                    }
                });
                dialog.show();
                dialog.getWindow().setContentView(view);
                dialog.setCanceledOnTouchOutside(false);
            } catch (Exception e) {
            }
        }
    }

    public static void woUnorderSureDialog(Context context, final UnicomDialogClickListener listener) {
        try {
            final Dialog dialog = new Builder(context).create();
            View view = LayoutInflater.from(context).inflate(R.layout.wo_flow_dialog_other_unorder, null);
            view.setOnTouchListener(new OnTouchListener() {
                public boolean onTouch(View v, MotionEvent event) {
                    return true;
                }
            });
            ((TextView) view.findViewById(R.id.wo_flow_main_dialog_sure)).setOnClickListener(new OnClickListener() {
                public void onClick(View view) {
                    dialog.dismiss();
                    if (listener != null) {
                        listener.onConfirm();
                    }
                }
            });
            dialog.show();
            dialog.getWindow().setContentView(view);
            dialog.setCanceledOnTouchOutside(false);
        } catch (Exception e) {
        }
        if (context != null) {
        }
    }
}
