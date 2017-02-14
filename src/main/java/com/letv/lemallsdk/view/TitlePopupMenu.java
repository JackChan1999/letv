package com.letv.lemallsdk.view;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap.Config;
import android.graphics.Rect;
import android.graphics.drawable.BitmapDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TextView;
import com.letv.lemallsdk.R;
import com.letv.lemallsdk.model.MenuEntity;
import com.letv.lemallsdk.util.ScreenUtil;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.DisplayImageOptions.Builder;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;
import com.nostra13.universalimageloader.core.display.SimpleBitmapDisplayer;
import java.util.ArrayList;

public class TitlePopupMenu extends PopupWindow {
    protected final int LIST_PADDING;
    private Context mContext;
    private boolean mIsDirty;
    private OnItemOnClickListener mItemOnClickListener;
    private ListView mListView;
    private final int[] mLocation;
    private Rect mRect;
    private int mScreenHeight;
    private int mScreenWidth;
    private ArrayList<MenuEntity> menus;
    private MenusAdapter menusAdapter;
    private DisplayImageOptions options;
    private int popupGravity;

    public interface OnItemOnClickListener {
        void onItemClick(MenuEntity menuEntity, int i);
    }

    private class MenusAdapter extends BaseAdapter {
        private MenusAdapter() {
        }

        public View getView(int position, View convertView, ViewGroup parent) {
            ViewHolder holder;
            MenuEntity item = (MenuEntity) TitlePopupMenu.this.menus.get(position);
            if (convertView == null) {
                convertView = LayoutInflater.from(TitlePopupMenu.this.mContext).inflate(R.layout.lemallsdk_title_popup_menu_item, null);
                holder = new ViewHolder();
                holder.menu_icon = (ImageView) convertView.findViewById(R.id.lemallsdk_menu_icon);
                holder.menu_name = (TextView) convertView.findViewById(R.id.lemallsdk_menu_name);
                convertView.setTag(holder);
            } else {
                holder = (ViewHolder) convertView.getTag();
            }
            ImageLoader.getInstance().displayImage(item.getIcon(), holder.menu_icon, TitlePopupMenu.this.options);
            holder.menu_name.setText(item.getTitle());
            return convertView;
        }

        public long getItemId(int position) {
            return (long) position;
        }

        public Object getItem(int position) {
            return TitlePopupMenu.this.menus.get(position);
        }

        public int getCount() {
            return TitlePopupMenu.this.menus.size();
        }
    }

    static class ViewHolder {
        ImageView menu_icon;
        TextView menu_name;

        ViewHolder() {
        }
    }

    public TitlePopupMenu(Context context) {
        this(context, -2, -2);
    }

    @SuppressLint({"InflateParams"})
    public TitlePopupMenu(Context context, int width, int height) {
        this.LIST_PADDING = 10;
        this.mRect = new Rect();
        this.mLocation = new int[2];
        this.popupGravity = 0;
        this.menus = new ArrayList();
        this.menusAdapter = new MenusAdapter();
        this.mContext = context;
        this.options = new Builder().showImageOnLoading(R.drawable.lemallsdk_icon_default).showImageForEmptyUri(R.drawable.lemallsdk_icon_default).showImageOnFail(R.drawable.lemallsdk_icon_default).cacheInMemory(true).cacheOnDisc(true).bitmapConfig(Config.RGB_565).imageScaleType(ImageScaleType.IN_SAMPLE_INT).displayer(new SimpleBitmapDisplayer()).build();
        setFocusable(true);
        setTouchable(true);
        setOutsideTouchable(true);
        this.mScreenWidth = ScreenUtil.getScreenWidth(this.mContext);
        this.mScreenHeight = ScreenUtil.getScreenHeight(this.mContext);
        setWidth(width);
        setHeight(height);
        setAnimationStyle(R.style.AnimationPreview);
        update();
        setBackgroundDrawable(new BitmapDrawable());
        setContentView(LayoutInflater.from(this.mContext).inflate(R.layout.lemallsdk_title_popup_menu, null));
        initUI();
    }

    private void initUI() {
        this.mListView = (ListView) getContentView().findViewById(R.id.lemallsdk_title_list);
        this.mListView.setOnItemClickListener(new OnItemClickListener() {
            public void onItemClick(AdapterView<?> adapterView, View arg1, int index, long arg3) {
                TitlePopupMenu.this.dismiss();
                if (TitlePopupMenu.this.mItemOnClickListener != null) {
                    TitlePopupMenu.this.mItemOnClickListener.onItemClick((MenuEntity) TitlePopupMenu.this.menus.get(index), index);
                }
            }
        });
    }

    public void show(View view) {
        view.getLocationOnScreen(this.mLocation);
        this.mRect.set(this.mLocation[0], this.mLocation[1], this.mLocation[0] + view.getWidth(), this.mLocation[1] + view.getHeight());
        if (this.mIsDirty) {
            populateItems();
        }
        showAtLocation(view, this.popupGravity, (this.mScreenWidth - 10) - (getWidth() / 2), this.mRect.bottom);
    }

    private void populateItems() {
        this.mIsDirty = false;
        this.mListView.setAdapter(this.menusAdapter);
    }

    public void addItem(MenuEntity item) {
        if (item != null) {
            this.menus.add(item);
            if (this.menusAdapter != null) {
                this.menusAdapter.notifyDataSetChanged();
            }
            this.mIsDirty = true;
        }
    }

    public void cleanItem() {
        if (!this.menus.isEmpty()) {
            this.menus.clear();
            if (this.menusAdapter != null) {
                this.menusAdapter.notifyDataSetChanged();
            }
            this.mIsDirty = true;
        }
    }

    public MenuEntity getMenuEntity(int position) {
        if (position < 0 || position > this.menus.size()) {
            return null;
        }
        return (MenuEntity) this.menus.get(position);
    }

    public void setItemOnClickListener(OnItemOnClickListener onItemOnClickListener) {
        this.mItemOnClickListener = onItemOnClickListener;
    }
}
