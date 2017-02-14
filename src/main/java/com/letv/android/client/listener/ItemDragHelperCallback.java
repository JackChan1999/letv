package com.letv.android.client.listener;

import android.graphics.Canvas;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerView.LayoutManager;
import android.support.v7.widget.RecyclerView.ViewHolder;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.support.v7.widget.helper.ItemTouchHelper.Callback;
import com.letv.android.client.adapter.channel.ChannelFragmentAdapter;
import com.letv.core.utils.LogInfo;
import com.letv.hackdex.VerifyLoad;
import com.letv.hotfixlib.HotFix;

public class ItemDragHelperCallback extends Callback {
    public ItemDragHelperCallback() {
        if (HotFix.PREVENT_VERIFY) {
            System.out.println(VerifyLoad.class);
        }
    }

    public int getMovementFlags(RecyclerView recyclerView, ViewHolder viewHolder) {
        int dragFlags;
        LayoutManager manager = recyclerView.getLayoutManager();
        if ((manager instanceof GridLayoutManager) || (manager instanceof StaggeredGridLayoutManager)) {
            dragFlags = 15;
        } else {
            dragFlags = 3;
        }
        return Callback.makeMovementFlags(dragFlags, 0);
    }

    public boolean onMove(RecyclerView recyclerView, ViewHolder viewHolder, ViewHolder target) {
        if (viewHolder.getItemViewType() != target.getItemViewType() && !(recyclerView.getAdapter() instanceof OnGridItemMoveListener)) {
            return false;
        }
        if (recyclerView.getAdapter() instanceof OnItemMoveListener) {
            ((OnItemMoveListener) recyclerView.getAdapter()).onItemMove(viewHolder.getAdapterPosition(), target.getAdapterPosition());
        }
        return true;
    }

    public void onMoved(RecyclerView recyclerView, ViewHolder viewHolder, int fromPos, ViewHolder target, int toPos, int x, int y) {
        if (recyclerView.getAdapter() instanceof OnGridItemMoveListener) {
            OnGridItemMoveListener listener = (OnGridItemMoveListener) recyclerView.getAdapter();
            ChannelFragmentAdapter adapter = (ChannelFragmentAdapter) recyclerView.getAdapter();
            int itemSize = adapter.getItemCount();
            int topSize = adapter.getTopSize();
            if (fromPos > toPos) {
                if (toPos == topSize + 1) {
                    callBack(listener, viewHolder, fromPos, target, toPos, true, false);
                } else {
                    callBack(listener, viewHolder, fromPos, target, toPos, false, false);
                }
            } else if (toPos == itemSize - 1) {
                callBack(listener, viewHolder, fromPos, target, toPos, false, true);
            } else {
                callBack(listener, viewHolder, fromPos, target, toPos, false, false);
            }
        }
    }

    private void callBack(OnGridItemMoveListener listener, ViewHolder viewHolder, int fromPos, ViewHolder target, int toPos, boolean addFirst, boolean isLast) {
        if (isLast || addFirst) {
            listener.onItemMove(fromPos, toPos, addFirst, isLast);
        } else if (viewHolder.getItemViewType() == target.getItemViewType()) {
            listener.onItemMove(fromPos, toPos, false, false);
        }
    }

    public void onSwiped(ViewHolder viewHolder, int direction) {
    }

    public void onChildDraw(Canvas c, RecyclerView recyclerView, ViewHolder viewHolder, float dX, float dY, int actionState, boolean isCurrentlyActive) {
        if (isCurrentlyActive && actionState == 2 && (recyclerView.getAdapter() instanceof OnGridItemMoveListener) && viewHolder.getItemViewType() == 2) {
            LogInfo.log("zhaoxiang", "----------");
        }
        super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive);
    }

    public void onChildDrawOver(Canvas c, RecyclerView recyclerView, ViewHolder viewHolder, float dX, float dY, int actionState, boolean isCurrentlyActive) {
        super.onChildDrawOver(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive);
    }

    public void onSelectedChanged(ViewHolder viewHolder, int actionState) {
        if (actionState != 0 && (viewHolder instanceof OnDragVHListener)) {
            ((OnDragVHListener) viewHolder).onItemSelected();
        }
        super.onSelectedChanged(viewHolder, actionState);
    }

    public void clearView(RecyclerView recyclerView, ViewHolder viewHolder) {
        if (viewHolder instanceof OnDragVHListener) {
            ((OnDragVHListener) viewHolder).onItemFinish();
        }
        super.clearView(recyclerView, viewHolder);
    }

    public boolean isLongPressDragEnabled() {
        return false;
    }

    public boolean isItemViewSwipeEnabled() {
        return false;
    }
}
