package com.example.testui.untilities;

import android.graphics.Rect;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class GridDecoration extends RecyclerView.ItemDecoration {
    int spanCount;
    int spacing;

    public GridDecoration(int spacing, int spanCount) {
        this.spacing = spacing;
        this.spanCount = spanCount;
    }

    @Override
    public void getItemOffsets(@NonNull Rect outRect, @NonNull View view, @NonNull RecyclerView parent, @NonNull RecyclerView.State state) {
        super.getItemOffsets(outRect, view, parent, state);

        outRect.left = spacing;
        outRect.right = spacing;
        outRect.top = spacing;
        outRect.bottom = spacing;
    }
}
