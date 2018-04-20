package govinmaxim.githubclient.base;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

public abstract class BaseRecyclerAdapter<T, VH extends RecyclerView.ViewHolder> extends RecyclerView.Adapter<VH> {

    private OnItemClickListener<T> mListener;
    private int mLayoutId;

    public BaseRecyclerAdapter(int layoutId ,OnItemClickListener<T> mListener) {
        this.mListener = mListener;
        mLayoutId = layoutId;
    }

    @NonNull
    @Override
    public VH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return createViewHolder(inflateView(parent), mListener);
    }

    private View inflateView(ViewGroup view) {
        return LayoutInflater.from(view.getContext()).inflate(mLayoutId, view, false);
    }

    public abstract VH createViewHolder(View view, OnItemClickListener<T> listener);

    public abstract void setItemsList(List<T> itemsList);
}
