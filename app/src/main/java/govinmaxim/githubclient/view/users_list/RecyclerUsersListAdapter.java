package govinmaxim.githubclient.view.users_list;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.RequestManager;

import java.util.ArrayList;
import java.util.List;

import govinmaxim.githubclient.R;
import govinmaxim.githubclient.base.BaseRecyclerAdapter;
import govinmaxim.githubclient.model.User;
import govinmaxim.githubclient.base.OnItemClickListener;

public class RecyclerUsersListAdapter extends BaseRecyclerAdapter<User, RecyclerUsersListAdapter.ViewHolder> {

    private List<User> mUsersList = new ArrayList<>();
    private RequestManager mGlide;

    RecyclerUsersListAdapter(int layoutId, RequestManager glide, OnItemClickListener<User> listener) {
        super(layoutId, listener);
        mGlide = glide;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.bind(mUsersList.get(position));
    }

    @Override
    public int getItemCount() {
        return mUsersList.size();
    }

    @Override
    public ViewHolder createViewHolder(View view, OnItemClickListener<User> listener) {
        return new ViewHolder(view, listener);
    }

    @Override
    public void setItemsList(List<User> itemsList) {
        if (itemsList != null) {
            mUsersList.addAll(itemsList);
            notifyDataSetChanged();
        }
    }

    void clearUsersList() {
        mUsersList.clear();
        notifyDataSetChanged();
    }

    int getLastUserId() {
        return mUsersList.get(mUsersList.size() - 1).getId();
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        private TextView mTextLogin;
        private ImageView mImageAvatar;
        private OnItemClickListener<User> mListener;

        ViewHolder(View itemView, OnItemClickListener<User> listener) {
            super(itemView);
            mListener = listener;

            mTextLogin = itemView.findViewById(R.id.text_login);
            mImageAvatar = itemView.findViewById(R.id.image_avatar);
        }

        void bind(final User user) {
            mTextLogin.setText(user.getLogin());
            loadAvatar(user, mImageAvatar);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mListener.onItemClick(user);
                }
            });
        }

        private void loadAvatar(User user, ImageView targetImage) {
            mGlide.load(user.getAvatarUrl()).into(targetImage);
        }
    }
}
