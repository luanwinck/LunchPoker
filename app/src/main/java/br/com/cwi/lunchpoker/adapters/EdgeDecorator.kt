package br.com.cwi.lunchpoker.adapters

import android.graphics.Rect
import android.support.v7.widget.RecyclerView
import android.view.View

class EdgeDecorator(edgePadding: Int) : RecyclerView.ItemDecoration() {

    private val edgePadding = edgePadding

    override fun getItemOffsets(outRect: Rect, view: View, parent: RecyclerView, state: RecyclerView.State) {
        super.getItemOffsets(outRect, view, parent, state)

        val itemCount = state.itemCount

        val itemPosition = parent.getChildAdapterPosition(view)

        // no position, leave it alone
        if (itemPosition == RecyclerView.NO_POSITION) {
            return
        }

        // first item
        if (itemPosition == 0) {
            outRect.set(view.getPaddingLeft(), edgePadding, view.getPaddingRight(), view.getPaddingBottom())
        } else if (itemCount > 0 && itemPosition == itemCount - 1) {
            outRect.set(view.getPaddingLeft(), view.getPaddingTop(), view.getPaddingRight(), edgePadding)
        } else {
            outRect.set(view.getPaddingLeft(), view.getPaddingTop(), view.getPaddingRight(), view.getPaddingBottom())
        }// every other item
        // last item
    }
}