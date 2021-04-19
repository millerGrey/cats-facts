package grey.example.catsfacts.utils;

import android.text.format.DateFormat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.catsfacts.R;
import com.example.catsfacts.databinding.ItemFactBinding;

import grey.example.catsfacts.model.Fact;
import grey.example.catsfacts.ui.main.MainViewModel;

public class FactAdapter extends RecyclerView.Adapter<FactAdapter.FactViewHolder> {

    MainViewModel mViewModel;

    public FactAdapter(MainViewModel viewModel) {
        mViewModel = viewModel;
    }

    @NonNull
    @Override
    public FactViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_fact, parent, false);
        return new FactViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull FactViewHolder holder, int position) {
        Fact fact = mViewModel.getFactList().get(position);
        holder.mBinding.setText(fact.getText());
        holder.mBinding.setDate(DateFormat.format("dd.MM.yy", fact.getDate()).toString());
        holder.mBinding.setPath(fact.getImagePath());
        holder.mBinding.setLongClickListener(view -> {
            mViewModel.deleteItemEvent(position);
            return false;
        });
        holder.mBinding.executePendingBindings();
    }

    @Override
    public int getItemCount() {
        return mViewModel.getFactList().size();
    }

    public static class FactViewHolder extends RecyclerView.ViewHolder {
        public ItemFactBinding mBinding;

        public FactViewHolder(@NonNull View itemView) {
            super(itemView);
            mBinding = ItemFactBinding.bind(itemView);
        }
    }
}
