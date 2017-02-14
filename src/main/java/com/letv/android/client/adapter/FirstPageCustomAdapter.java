package com.letv.android.client.adapter;

import android.content.Context;
import android.os.Handler;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerView.Adapter;
import android.support.v7.widget.RecyclerView.ViewHolder;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.letv.android.client.R;
import com.letv.android.client.listener.OnItemMoveListener;
import com.letv.core.bean.HomeBlock;
import com.letv.core.utils.BaseTypeUtils;
import com.letv.core.utils.LogInfo;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class FirstPageCustomAdapter extends Adapter<ViewHolder> implements OnItemMoveListener {
    private static final long ANIM_TIME = 300;
    private static final int TYPE_LOCK_BLOCK = 1;
    private static final int TYPE_LOCK_TITLE = 0;
    private static final int TYPE_UNLOCK_BLOCK = 3;
    private static final int TYPE_UNLOCK_TITLE = 2;
    private Handler delayHandler = new Handler();
    private Context mContext;
    private boolean mHasEdit;
    private int mHeadSize;
    private LayoutInflater mInflater;
    private ItemTouchHelper mItemTouchHelper;
    private List<HomeBlock> mList = new ArrayList();
    private int mLockSize;
    private RecyclerView mRecyclerView;
    private TextView mSaveView;

    public FirstPageCustomAdapter(Context context, ItemTouchHelper helper, List<HomeBlock> list, TextView view, RecyclerView recyclerView) {
        this.mInflater = LayoutInflater.from(context);
        if (!BaseTypeUtils.isListEmpty(list)) {
            for (HomeBlock block : list) {
                if (block != null) {
                    if (TextUtils.equals(block.isLock, "1")) {
                        this.mList.add(this.mLockSize, block);
                        this.mLockSize++;
                    } else {
                        this.mList.add(block);
                    }
                }
            }
            this.mItemTouchHelper = helper;
            this.mContext = context;
            if (this.mLockSize == 0) {
                this.mHeadSize = 1;
            } else {
                this.mHeadSize = 2;
            }
            this.mSaveView = view;
            this.mRecyclerView = recyclerView;
        }
    }

    public int getItemViewType(int position) {
        if (this.mLockSize != 0) {
            if (position == 0) {
                return 0;
            }
            if (position > 0 && position < this.mLockSize + 1) {
                return 1;
            }
            if (position != this.mLockSize + 1) {
                return 3;
            }
            return 2;
        } else if (position != 0) {
            return 3;
        } else {
            return 2;
        }
    }

    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view;
        TextView title;
        if (viewType == 0) {
            view = this.mInflater.inflate(R.layout.first_page_custom_title, parent, false);
            title = (TextView) view.findViewById(2131362147);
            ((TextView) view.findViewById(2131362589)).setVisibility(8);
            title.setText(2131100343);
            return new 1(this, view);
        } else if (viewType == 2) {
            view = this.mInflater.inflate(R.layout.first_page_custom_title, parent, false);
            title = (TextView) view.findViewById(2131362147);
            ((TextView) view.findViewById(2131362589)).setVisibility(0);
            title.setText(2131099905);
            return new 2(this, view);
        } else if (viewType == 1) {
            return new LockBlockViewHolder(this, this.mInflater.inflate(R.layout.first_page_custom_item, parent, false));
        } else {
            if (viewType != 3) {
                return null;
            }
            UnLockBlockViewHolder unLockHolder = new UnLockBlockViewHolder(this, this.mInflater.inflate(R.layout.first_page_unlock_item, parent, false));
            UnLockBlockViewHolder.access$500(unLockHolder).setOnClickListener(new 3(this, unLockHolder));
            UnLockBlockViewHolder.access$500(unLockHolder).setOnLongClickListener(new 4(this, unLockHolder));
            return unLockHolder;
        }
    }

    public boolean getHasEdit() {
        return this.mHasEdit;
    }

    public void setHasEdit(boolean hasEdit) {
        this.mHasEdit = hasEdit;
    }

    public void onBindViewHolder(ViewHolder holder, int position) {
        boolean isFirstUnLock;
        int i = 0;
        if (position == this.mLockSize + this.mHeadSize) {
            isFirstUnLock = true;
        } else {
            isFirstUnLock = false;
        }
        if (holder instanceof LockBlockViewHolder) {
            LockBlockViewHolder.access$700((LockBlockViewHolder) holder).setText(((HomeBlock) this.mList.get(position - 1)).blockname);
        } else if (holder instanceof UnLockBlockViewHolder) {
            LogInfo.log("zhaoxiang", "isFirstUnLock" + isFirstUnLock + "-------" + position);
            UnLockBlockViewHolder.access$500((UnLockBlockViewHolder) holder).setText(((HomeBlock) this.mList.get(position - this.mHeadSize)).blockname);
            TextView access$300 = UnLockBlockViewHolder.access$300((UnLockBlockViewHolder) holder);
            if (isFirstUnLock) {
                i = 8;
            }
            access$300.setVisibility(i);
        }
    }

    public void moveToFirst(int fromPosition) {
        try {
            setSaveViewColor(true);
            int toPosition = this.mLockSize;
            int startPosition = fromPosition - this.mHeadSize;
            HomeBlock block = (HomeBlock) this.mList.get(startPosition);
            this.mList.remove(startPosition);
            this.mList.add(toPosition, block);
            notifyItemMoved(fromPosition, this.mLockSize + this.mHeadSize);
            notifyDataChange();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void setSaveViewColor(boolean hasChange) {
        int color;
        TextView textView = this.mSaveView;
        if (hasChange) {
            color = this.mContext.getResources().getColor(2131493261);
        } else {
            color = this.mContext.getResources().getColor(2131493082);
        }
        textView.setTextColor(color);
    }

    private void notifyDataChange() {
        this.delayHandler.postDelayed(new 5(this), ANIM_TIME);
    }

    public int getItemCount() {
        return BaseTypeUtils.isListEmpty(this.mList) ? 0 : this.mList.size() + this.mHeadSize;
    }

    public void onItemMove(int fromPosition, int toPosition) {
        this.mHasEdit = true;
        if (BaseTypeUtils.getElementFromList(this.mList, fromPosition - this.mHeadSize) != null && BaseTypeUtils.getElementFromList(this.mList, toPosition - this.mHeadSize) != null) {
            Collections.swap(this.mList, fromPosition - this.mHeadSize, toPosition - this.mHeadSize);
            notifyItemMoved(fromPosition, toPosition);
            setSaveViewColor(true);
        }
    }

    public List<HomeBlock> getCurrentList() {
        return this.mList;
    }
}
