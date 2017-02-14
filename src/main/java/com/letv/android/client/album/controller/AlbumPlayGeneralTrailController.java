package com.letv.android.client.album.controller;

import android.view.View;
import android.widget.TextView;
import com.letv.android.client.album.AlbumPlayActivity;
import com.letv.android.client.album.R;
import com.letv.android.client.album.controller.AlbumPlayVipTrailController.ShowState;
import com.letv.core.bean.AlbumPayInfoBean;

public class AlbumPlayGeneralTrailController extends AlbumPlayVipTrailController {
    private View mStartOpenVip = this.mActivity.findViewById(R.id.vip_trail_start_use_general_vip_btn);
    private View mStartOpenVipRight = this.mActivity.findViewById(R.id.vip_trail_start_open_vip_btns_btn);
    private TextView mStartTip1 = ((TextView) this.mActivity.findViewById(R.id.vip_trail_start_use_general_ticket_text1));
    private TextView mStartTip2 = ((TextView) this.mActivity.findViewById(R.id.vip_trail_start_use_general_ticket_text3));
    private View mStartUseTicket = this.mActivity.findViewById(R.id.vip_trail_start_use_general_ticket_btn);

    public AlbumPlayGeneralTrailController(AlbumPlayActivity activity) {
        super(activity);
        this.mStartUseTicket.setOnClickListener(this);
        this.mStartOpenVip.setOnClickListener(this);
        this.mStartOpenVipRight.setOnClickListener(this);
        this.mActivity.findViewById(R.id.vip_trail_end_use_ticket_vip).setOnClickListener(this);
    }

    public void setPayInfo(AlbumPayInfoBean payInfo) {
        super.setPayInfo(payInfo);
        if (payInfo.tryLookTime >= 60) {
            this.mStartTip1.setText(this.mActivity.getString(R.string.vip_trail_start_text_header, new Object[]{Integer.valueOf(payInfo.tryLookTime / 60)}));
            return;
        }
        this.mStartTip1.setText(this.mActivity.getString(R.string.vip_trail_start_text_header_second, new Object[]{Integer.valueOf(payInfo.tryLookTime)}));
    }

    public void onClick(View v) {
        super.onClick(v);
        int id = v.getId();
        if (id == R.id.vip_trail_start_use_general_ticket_btn) {
            requestUseTicket();
        } else if (id == R.id.vip_trail_start_use_general_vip_btn || id == R.id.vip_trail_start_open_vip_btns_btn || id == R.id.vip_trail_end_use_ticket_vip) {
            onTrailBuyVip(true, false);
        }
    }

    protected void showStartByTicket(boolean hasTicket) {
        super.showStartByTicket(hasTicket);
        if (!hasTicket || !hasGeneralTicket() || this.mStartTicketViewShowState == ShowState.HAS_SHOWED) {
            return;
        }
        if (this.mIsVip) {
            this.mStartTip2.setVisibility(8);
            this.mStartOpenVip.setVisibility(8);
            return;
        }
        this.mStartTip2.setVisibility(0);
        this.mStartOpenVip.setVisibility(0);
    }

    protected void showRightWhenHasTicket() {
        int i = 8;
        super.showRightWhenHasTicket();
        this.mStartUseGeneralTicketView.setVisibility(8);
        View view = this.mStartOpenVipRight;
        if (!this.mIsVip) {
            i = 0;
        }
        view.setVisibility(i);
    }

    public void vipTrailEndByHasTicket(boolean hasTicket) {
        int i = 8;
        super.vipTrailEndByHasTicket(hasTicket);
        TextView vaildText = (TextView) this.mActivity.findViewById(R.id.vip_trail_end_use_ticket_vaild);
        vaildText.setVisibility(8);
        this.mEndUseTicketTextView.setVisibility(0);
        if (hasTicket && hasGeneralTicket()) {
            this.mEndUseTicketTitleView.setText(R.string.vip_trail_end_general_tip);
            vaildText.setVisibility(0);
            vaildText.setText(this.mActivity.getString(R.string.vip_trail_general_ticket_use_success, new Object[]{this.mPayInfo.validDays}));
            this.mEndUseTicketTextView.setVisibility(8);
            this.mEndUseTicketBtn.setText(R.string.ticket_use_now);
            this.mEndUseTicketTipView.setVisibility(8);
            View view = this.mEndUseTicketByVipFrame;
            if (!this.mIsVip) {
                i = 0;
            }
            view.setVisibility(i);
        }
    }

    private boolean hasGeneralTicket() {
        return this.mPayInfo != null && this.mPayInfo.ticketType == 1;
    }
}
