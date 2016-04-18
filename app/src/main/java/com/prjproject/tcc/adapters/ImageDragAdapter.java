package com.prjproject.tcc.adapters;


import android.graphics.Rect;
import android.support.percent.PercentRelativeLayout;
import android.support.v7.widget.RecyclerView;
import android.view.DragEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.prjproject.tcc.R;
import com.prjproject.tcc.model.Activity;

import java.util.ArrayList;

/**
 * Created by Emanuel on 2016-03-13.
 */
public class ImageDragAdapter extends RecyclerView.Adapter{
    private ArrayList<Activity> items;
    private static OnLongItemClickListener listener;


    public ImageDragAdapter(ArrayList<Activity> items) {
        this.items = items;
    }

    // Define the listener interface
    public interface OnLongItemClickListener {
        void onLongItemClick(View itemView, int position);
    }
    // Define the method that allows the parent activity or fragment to define the listener
    public void setOnLongItemClickListener(OnLongItemClickListener listener) {
        this.listener = listener;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        RecyclerView.ViewHolder viewHolder;
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View v = inflater.inflate(R.layout.layout_view_holder, parent, false);
        viewHolder = new ViewHolderImage(v);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        ViewHolderImage vh = (ViewHolderImage) holder;
        Activity act = (Activity) items.get(position);
        vh.getImageView().setImageBitmap(act.getActivityImage());
    }

    @Override
    public int getItemCount() {
        return items == null ? 0 : items.size();//this.items.size();
    }

    public void addItem(Activity item) {
        items.add(item);
    }

    public void removeItem(int position) {
        items.remove(position);
    }

    public Object getItem(int position) {
        return items == null ? null : items.get(position);
    }

    private class ViewHolderImage extends RecyclerView.ViewHolder {
        private ImageView image;

        public ViewHolderImage(final View imageView) {
            super(imageView);
            image = (ImageView) imageView.findViewById(R.id.image_item);
            imageView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    if (listener != null)
                        listener.onLongItemClick(imageView, getLayoutPosition());
                    return true;
                }
            });


            /*
            imageView.setOnDragListener(new View.OnDragListener() {
                @Override
                public boolean onDrag(View v, DragEvent event) {
                    switch (event.getAction()) {
                        case DragEvent.ACTION_DRAG_STARTED:
                            // Do nothing
                            break;

                        case DragEvent.ACTION_DRAG_ENTERED:
                            int x_cord = (int) event.getX();
                            int y_cord = (int) event.getY();
                            break;

                        case DragEvent.ACTION_DRAG_EXITED:
                            break;

                        case DragEvent.ACTION_DRAG_LOCATION:
                            x_cord = (int) event.getX();
                            y_cord = (int) event.getY();
                            break;

                        case DragEvent.ACTION_DRAG_ENDED:

                            // Do nothing
                            break;

                        case DragEvent.ACTION_DROP:
                            x_cord = (int) event.getX();
                            y_cord = (int) event.getY();

                            View droppedParentLayout = findViewAt(((ViewGroup)v.getParent().getParent()), x_cord,y_cord);
                            if(droppedParentLayout != null && droppedParentLayout.getId() == R.id.layout_undistributed){
                                View droppedChildLayout = findViewAt((ViewGroup)droppedParentLayout, x_cord,y_cord);
                                if(droppedChildLayout != null){
                                    switch (droppedChildLayout.getId()){
                                        case R.id.layout_morning:
                                            break;
                                        case R.id.layout_afternoon:
                                            break;
                                        case R.id.layout_evening:
                                            break;
                                        case R.id.layout_dawn:
                                            break;

                                    }

                                }
                            }



                            break;
                        default:
                            break;
                    }
                    return true;
                }
            });*/
        }
        private View findViewAt(ViewGroup viewGroup, int x, int y) {
            for(int i = 0; i < viewGroup.getChildCount(); i++) {
                View child = viewGroup.getChildAt(i);
                /*if (child instanceof ViewGroup) {
                    View foundView = findViewAt((ViewGroup) child, x, y);
                    if (foundView != null) {
                        return foundView;
                    }
                } else*/ {
                    int[] location = new int[2];
                    child.getLocationOnScreen(location);
                    Rect rect = new Rect(location[0], location[1], location[0] + child.getWidth(), location[1] + child.getHeight());
                    if (rect.contains(x, y)) {
                        return child;
                    }
                }
            }

            return null;
        }

        public ImageView getImageView() {
            return image;
        }

        public void setImageView(ImageView image) {
            this.image = image;
        }
    }
}
