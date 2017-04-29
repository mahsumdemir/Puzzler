package com.mahsum.puzzle.loadImage;

import android.graphics.Bitmap;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.mahsum.puzzle.R;

import java.util.ArrayList;

class SavedPuzzlesAdapter extends RecyclerView.Adapter {

    private final ArrayList<Bitmap> savedBitmaps;

    public SavedPuzzlesAdapter(ArrayList<Bitmap> savedBitmaps) {
        this.savedBitmaps = savedBitmaps;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{
        private View listItem;
        private ImageView imageView;
        public ViewHolder(View listItem) {
            super(listItem);
            this.listItem = listItem;
            imageView = (ImageView) listItem.findViewById(R.id.image);
        }

        private void setImage(Bitmap image){
            imageView.setImageBitmap(image);
        }
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View listItem = LayoutInflater.from(parent.getContext()).inflate(R.layout.saved_puzzles_list_item, parent, false);
        return new ViewHolder(listItem);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        ViewHolder viewHolder = (ViewHolder) holder;
        viewHolder.setImage(savedBitmaps.get(position));
    }

    @Override
    public int getItemCount() {
        return savedBitmaps.size();
    }
}
