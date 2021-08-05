package com.example.youtube_template.src.main.search.adapter

import android.content.Context
import android.graphics.Rect
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.example.youtube_template.util.dpToPx

class CategoryDecoration(context: Context) : RecyclerView.ItemDecoration() {
    private val size12 : Int = dpToPx(context, 12)

    override fun getItemOffsets(outRect: Rect, view: View, parent: RecyclerView, state: RecyclerView.State) {
        super.getItemOffsets(outRect, view, parent, state)

        val position = parent.getChildAdapterPosition(view)
        if (position == 0 || position == 1){
            outRect.top = size12
            outRect.bottom = size12
        } else {
            outRect.bottom = size12
        }

        if (position % 2 == 0){
            outRect.left = size12
            outRect.right = size12
        } else {
            outRect.right = size12
        }
    }
}