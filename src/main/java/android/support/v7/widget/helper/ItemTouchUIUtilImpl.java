package android.support.v7.widget.helper;

import android.graphics.Canvas;
import android.support.v4.view.ViewCompat;
import android.support.v7.recyclerview.R;
import android.support.v7.widget.RecyclerView;
import android.view.View;

class ItemTouchUIUtilImpl {

    static class Gingerbread implements ItemTouchUIUtil {
        Gingerbread() {
        }

        private void draw(Canvas c, RecyclerView parent, View view, float dX, float dY) {
            c.save();
            c.translate(dX, dY);
            parent.drawChild(c, view, 0);
            c.restore();
        }

        public void clearView(View view) {
            view.setVisibility(0);
        }

        public void onSelected(View view) {
            view.setVisibility(4);
        }

        public void onDraw(Canvas c, RecyclerView recyclerView, View view, float dX, float dY, int actionState, boolean isCurrentlyActive) {
            if (actionState != 2) {
                draw(c, recyclerView, view, dX, dY);
            }
        }

        public void onDrawOver(Canvas c, RecyclerView recyclerView, View view, float dX, float dY, int actionState, boolean isCurrentlyActive) {
            if (actionState == 2) {
                draw(c, recyclerView, view, dX, dY);
            }
        }
    }

    static class Honeycomb implements ItemTouchUIUtil {
        Honeycomb() {
        }

        public void clearView(View view) {
            ViewCompat.setTranslationX(view, 0.0f);
            ViewCompat.setTranslationY(view, 0.0f);
        }

        public void onSelected(View view) {
        }

        public void onDraw(Canvas c, RecyclerView recyclerView, View view, float dX, float dY, int actionState, boolean isCurrentlyActive) {
            ViewCompat.setTranslationX(view, dX);
            ViewCompat.setTranslationY(view, dY);
        }

        public void onDrawOver(Canvas c, RecyclerView recyclerView, View view, float dX, float dY, int actionState, boolean isCurrentlyActive) {
        }
    }

    static class Lollipop extends Honeycomb {
        Lollipop() {
        }

        public void onDraw(Canvas c, RecyclerView recyclerView, View view, float dX, float dY, int actionState, boolean isCurrentlyActive) {
            if (isCurrentlyActive && view.getTag(R.id.item_touch_helper_previous_elevation) == null) {
                Float originalElevation = Float.valueOf(ViewCompat.getElevation(view));
                ViewCompat.setElevation(view, 1.0f + findMaxElevation(recyclerView, view));
                view.setTag(R.id.item_touch_helper_previous_elevation, originalElevation);
            }
            super.onDraw(c, recyclerView, view, dX, dY, actionState, isCurrentlyActive);
        }

        private float findMaxElevation(RecyclerView recyclerView, View itemView) {
            int childCount = recyclerView.getChildCount();
            float max = 0.0f;
            for (int i = 0; i < childCount; i++) {
                View child = recyclerView.getChildAt(i);
                if (child != itemView) {
                    float elevation = ViewCompat.getElevation(child);
                    if (elevation > max) {
                        max = elevation;
                    }
                }
            }
            return max;
        }

        public void clearView(View view) {
            Object tag = view.getTag(R.id.item_touch_helper_previous_elevation);
            if (tag != null && (tag instanceof Float)) {
                ViewCompat.setElevation(view, ((Float) tag).floatValue());
            }
            view.setTag(R.id.item_touch_helper_previous_elevation, null);
            super.clearView(view);
        }
    }

    ItemTouchUIUtilImpl() {
    }
}
