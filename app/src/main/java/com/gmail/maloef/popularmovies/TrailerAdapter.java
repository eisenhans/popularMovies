package com.gmail.maloef.popularmovies;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.gmail.maloef.popularmovies.domain.Trailer;

import java.util.List;

/**
 * Created by Markus on 20.11.2015.
 */
public class TrailerAdapter extends RecyclerView.Adapter<TrailerAdapter.ViewHolder> {

    // Provide a direct reference to each of the views within a data item
    // Used to cache the views within the item layout for fast access
    public static class ViewHolder extends RecyclerView.ViewHolder {
        // Your holder should contain a member variable
        // for any view that will be set as you render a row
        public ImageView iconImageView;
        public TextView nameTextView;

        // We also create a constructor that accepts the entire item row
        // and does the view lookups to find each subview
        public ViewHolder(View itemView) {
            // Stores the itemView in a public final member variable that can be used
            // to access the context from any ViewHolder instance.
            super(itemView);

            iconImageView = (ImageView) itemView.findViewById(R.id.list_item_trailer_icon);
            nameTextView = (TextView) itemView.findViewById(R.id.list_item_trailer_name);
        }
    }

    private final List<Trailer> trailers;

    public TrailerAdapter(List<Trailer> trailers) {
        this.trailers = trailers;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View trailerView = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_trailer, parent, false);
//        Context context = parent.getContext();
//        LayoutInflater inflater = LayoutInflater.from(context);
//
//        // Inflate the custom layout
//        View trailerView = inflater.inflate(R.layout.list_item_trailer, parent, false);

        // Return a new holder instance
        return new ViewHolder(trailerView);
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, int position) {
        // Get the data model based on position
        Trailer trailer = trailers.get(position);

        // Set item views based on the data model

        viewHolder.iconImageView.setImageResource(R.drawable.pic02);
        viewHolder.nameTextView.setText(trailer.name);

//        Glide.with(viewHolder.iconImageView.getContext())
//                .load(R.drawable.pic03)
//                .fitCenter()
//                .into(viewHolder.iconImageView);
    }

    @Override
    public int getItemCount() {
        return trailers.size();
    }

//    @Override
//    public View getView(int position, View convertView, ViewGroup parent) {
//        Trailer trailer = getItem(position);
//
//        // Adapters recycle views to AdapterViews.
//        // If this is a new View object we're getting, then inflate the layout.
//        // If not, this view already has the layout inflated from a previous call to getView,
//        // and we modify the View widgets as usual.
//        if (convertView == null) {
//            convertView = LayoutInflater.from(getContext()).inflate(R.layout.list_item_trailer, parent, false);
//        }
//
//        ImageView iconView = (ImageView) convertView.findViewById(R.id.list_item_trailer_icon);
//        iconView.setImageResource(R.drawable.pic02);
//
//        TextView trailerNameView = (TextView) convertView.findViewById(R.id.list_item_trailer_name);
//        trailerNameView.setText(trailer.name);
//
//        return convertView;
//    }
}
