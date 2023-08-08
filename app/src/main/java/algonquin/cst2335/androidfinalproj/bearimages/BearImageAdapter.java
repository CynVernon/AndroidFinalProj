// BearImageAdapter.java
package algonquin.cst2335.androidfinalproj.bearimages;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import algonquin.cst2335.androidfinalproj.databinding.ItemBearImageBinding;

public class BearImageAdapter extends RecyclerView.Adapter<BearImageAdapter.BearImageViewHolder> {

    private List<BearImage> bearImages;

    public BearImageAdapter(List<BearImage> bearImages) {
        this.bearImages = bearImages;
    }

    @NonNull
    @Override
    public BearImageViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        ItemBearImageBinding binding = ItemBearImageBinding.inflate(layoutInflater, parent, false);
        return new BearImageViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull BearImageViewHolder holder, int position) {
        BearImage bearImage = bearImages.get(position);
        holder.bind(bearImage);
    }

    @Override
    public int getItemCount() {
        return bearImages.size();
    }

    static class BearImageViewHolder extends RecyclerView.ViewHolder {
        private ItemBearImageBinding binding;

        BearImageViewHolder(ItemBearImageBinding binding) {
            super(binding.tvImageSize.getRootView());
            this.binding = binding;
        }

        void bind(BearImage bearImage) {
            binding.tvImageSize.setText("Image Size: " + bearImage.getWidth() + "x" + bearImage.getHeight());

        }
    }
}
