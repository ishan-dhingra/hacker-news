package com.anythingintellect.hackernews.adapter;

import android.databinding.DataBindingUtil;
import android.databinding.ObservableList;
import android.databinding.ViewDataBinding;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.anythingintellect.hackernews.BR;
import com.anythingintellect.hackernews.repo.ItemRepository;
import com.anythingintellect.hackernews.viewmodel.ItemViewModel;

// For simplicity and time constraints I am keeping single adapter and in very basic way.
// There is good amount of space for further improvements. like DI, MultiView Support etc
public class ItemListAdapter extends RecyclerView.Adapter<ItemListAdapter.NewsItemViewHolder> {

    final ObservableList<ItemViewModel> listViewModels;
    final ItemRepository itemRepository;
    final int layout;
    public ItemListAdapter(ObservableList<ItemViewModel> listViewModels, ItemRepository itemRepository, int layout) {
        this.listViewModels = listViewModels;
        this.itemRepository = itemRepository;
        this.layout = layout;
        this.listViewModels.addOnListChangedCallback(itemChangedListener);
        setHasStableIds(true);
    }

    @Override
    public long getItemId(int position) {
        return listViewModels.get(position).getId();
    }

    @Override
    public NewsItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        ViewDataBinding binding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()), viewType,
                parent, false);
        return new NewsItemViewHolder(binding);
    }

    @Override
    public int getItemViewType(int position) {
        return layout;
    }


    @Override
    public void onBindViewHolder(NewsItemViewHolder holder, int position) {
        holder.bind(listViewModels.get(position));
    }

    @Override
    public int getItemCount() {
        return listViewModels.size();
    }

    private final ObservableList.OnListChangedCallback<ObservableList<ItemViewModel>> itemChangedListener = new ObservableList.OnListChangedCallback<ObservableList<ItemViewModel>>() {
        @Override
        public void onChanged(ObservableList<ItemViewModel> news) {

        }

        @Override
        public void onItemRangeChanged(ObservableList<ItemViewModel> news, int start, int count) {
            notifyItemRangeChanged(start, count);
        }

        @Override
        public void onItemRangeInserted(ObservableList<ItemViewModel> news, int start, int count) {
            notifyItemRangeInserted(start, count);
        }

        @Override
        public void onItemRangeMoved(ObservableList<ItemViewModel> news, int i, int i1, int i2) {
        }

        @Override
        public void onItemRangeRemoved(ObservableList<ItemViewModel> news, int start, int count) {
            notifyItemRangeRemoved(start, count);
        }
    };

    public static class NewsItemViewHolder extends RecyclerView.ViewHolder {

        private final ViewDataBinding binding;

        public NewsItemViewHolder(ViewDataBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        void bind(ItemViewModel viewModel) {
            binding.setVariable(BR.vm, viewModel);
            viewModel.loadItem();
        }
    }

}
