// BearImageAdapter.java

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import algonquin.cst2335.androidfinalproj.R;
import algonquin.cst2335.androidfinalproj.bearimages.db.BearImage;

public class BearImageAdapter extends RecyclerView.Adapter<BearImageAdapter.BearImageViewHolder> {

    private List<BearImage> bearImages;

    public BearImageAdapter(List<BearImage> bearImages) {
        this.bearImages = bearImages;
    }

    @NonNull
    @Override
    public BearImageViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_bear_image, parent, false);
        return new BearImageViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull BearImageViewHolder holder, int position) {
        BearImage bearImage = bearImages.get(position);
        holder.tvImageSize.setText("Image Size: " + bearImage.getWidth() + "x" + bearImage.getHeight());
        // You can use an image loading library like Glide or Picasso to load and display the image
        // Glide.with(holder.itemView).load(bearImage.getImageUrl()).into(holder.imageView);
    }

    @Override
    public int getItemCount() {
        return bearImages.size();
    }

    static class BearImageViewHolder extends RecyclerView.ViewHolder {
        TextView tvImageSize;
        // Add ImageView here for displaying the image

        public BearImageViewHolder(@NonNull View itemView) {
            super(itemView);
            tvImageSize = itemView.findViewById(R.id.tvImageSize);
            // Find and initialize ImageView here
        }
    }
}
