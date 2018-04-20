package govinmaxim.githubclient.view.user_repositories;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import govinmaxim.githubclient.R;
import govinmaxim.githubclient.base.BaseRecyclerAdapter;
import govinmaxim.githubclient.model.Repository;
import govinmaxim.githubclient.base.OnItemClickListener;

public class RecyclerRepositoriesListAdapter extends BaseRecyclerAdapter<Repository, RecyclerRepositoriesListAdapter.ViewHolder> {

    private List<Repository> mRepositoriesList = new ArrayList<>();

    public RecyclerRepositoriesListAdapter(int layoutId, OnItemClickListener<Repository> listener) {
        super(layoutId ,listener);
    }

    @Override
    public ViewHolder createViewHolder(View view, OnItemClickListener<Repository> listener) {
        return new ViewHolder(view, listener);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.bind(mRepositoriesList.get(position));
    }

    @Override
    public int getItemCount() {
        return mRepositoriesList.size();
    }

    @Override
    public void setItemsList(List<Repository> itemsList) {
        if (itemsList != null) {
            mRepositoriesList.clear();
            mRepositoriesList.addAll(itemsList);
            notifyDataSetChanged();
        }
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        private OnItemClickListener<Repository> mListener;
        private TextView mTextName;
        private TextView mTextDescription;
        private Button mButtonOpenInBrowser;

        ViewHolder(View itemView, OnItemClickListener<Repository> listener) {
            super(itemView);
            mListener = listener;

            mTextName = itemView.findViewById(R.id.text_name);
            mTextDescription = itemView.findViewById(R.id.text_description);
            mButtonOpenInBrowser = itemView.findViewById(R.id.button_open_repository_in_browser);
        }

        void bind(final Repository repository) {
            mTextName.setText(repository.getName());
            mTextDescription.setText(repository.getDescription());

            mButtonOpenInBrowser.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mListener.onItemClick(repository);
                }
            });
        }
    }
}
