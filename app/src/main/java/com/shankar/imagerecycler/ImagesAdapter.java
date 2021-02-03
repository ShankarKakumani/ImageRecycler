package com.shankar.imagerecycler;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.target.SizeReadyCallback;
import com.bumptech.glide.request.transition.Transition;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

public class ImagesAdapter extends RecyclerView.Adapter<ImagesAdapter.ImagesViewHolder> {

    public final ArrayList<Uri> imagesList;
    RecyclerView imageRecycler;
    LinearLayoutManager linearLayoutManager;

    public ImagesAdapter(ArrayList<Uri> uri, RecyclerView imageRecycler, LinearLayoutManager imageLinearLayoutManager) {
        this.imagesList = uri;
        this.imageRecycler = imageRecycler;
        this.linearLayoutManager = imageLinearLayoutManager;
    }

    Integer h,w;
    @NonNull
    @Override
    public ImagesViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.layout_images, parent, false);
        return new ImagesViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ImagesViewHolder holder, int position) {

        Glide.with(holder.images.getContext())
                .load(imagesList.get(position))
                .into(holder.images);


        imageRecycler.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
            }

            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);

                int firstVisibleItem = linearLayoutManager.findFirstVisibleItemPosition();

                Glide.with(holder.images.getContext())
                        .asBitmap()
                        .load(imagesList.get(firstVisibleItem))
                        .into(holder.images)
                        .getSize((width, height) -> {
                            ViewGroup.LayoutParams params = holder.images.getLayoutParams();
                            params.height = height;
                            holder.images.setLayoutParams(params);
                            holder.images.setMaxHeight(400);
                        });

            }
        });
    }

    @Override
    public int getItemCount() {
        return imagesList.size();
    }

    public static class ImagesViewHolder extends RecyclerView.ViewHolder {
        ImageView images;
        LinearLayout linearRecycler;

        public ImagesViewHolder(View itemView) {
            super(itemView);
            images = itemView.findViewById(R.id.imageViewRecycler);
            linearRecycler = itemView.findViewById(R.id.linearRecycler);
        }
    }
}