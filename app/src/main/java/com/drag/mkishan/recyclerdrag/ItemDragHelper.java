package com.drag.mkishan.recyclerdrag;

import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;

/**
 * Created by mkishan on 11/5/2015.
 */
public class ItemDragHelper extends ItemTouchHelper.Callback {
    private final ListItemHelper itemHelper;
    public static final float ALPHA_FULL = 1.0f;
    public ItemDragHelper(ListItemHelper touchHelper){
        this.itemHelper =touchHelper;
    }
    @Override
    public int getMovementFlags(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder) {
        int dragFlags = ItemTouchHelper.UP | ItemTouchHelper.DOWN;
        return makeMovementFlags(dragFlags, ItemTouchHelper.LEFT); // Flag Left is not used
    }
    @Override
    public boolean isLongPressDragEnabled() {
        return true;
    }
    @Override
    public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
       itemHelper.onMove(viewHolder.getAdapterPosition(), target.getAdapterPosition());
        return true;
    }

    @Override
    public boolean isItemViewSwipeEnabled() {
        return false;
    }

    @Override
    public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {

    }
    @Override
    public void onSelectedChanged(RecyclerView.ViewHolder viewHolder, int actionState) {

        if (actionState != ItemTouchHelper.ACTION_STATE_IDLE) {
            if (viewHolder instanceof ListItemTouchHelper) {
                // Let the view holder know that this item is being moved or dragged
                ListItemTouchHelper itemViewHolder = (ListItemTouchHelper) viewHolder;
                itemViewHolder.onItemSelected();
            }
        }

        super.onSelectedChanged(viewHolder, actionState);
    }

    @Override
    public void clearView(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder) {
        super.clearView(recyclerView, viewHolder);

        viewHolder.itemView.setAlpha(ALPHA_FULL);

        if (viewHolder instanceof ListItemTouchHelper) {
            // Tell the view holder it's time to restore the idle state
            ListItemTouchHelper itemViewHolder = (ListItemTouchHelper) viewHolder;
            itemViewHolder.onItemClear();
        }
    }
}
