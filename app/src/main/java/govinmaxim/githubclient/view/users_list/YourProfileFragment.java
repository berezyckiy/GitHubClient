package govinmaxim.githubclient.view.users_list;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.transition.Transition;

import govinmaxim.githubclient.R;
import govinmaxim.githubclient.model.User;

public class YourProfileFragment extends Fragment {
    private static final String NULL_FIELD_REPLACEMENT = "Empty";

    private OnProfileFragmentListener mListener;
    private View mRootView;

    private ImageView mImageAvatar;
    private TextView mTextLogin;
    private TextView mTextName;
    private TextView mTextCompany;
    private TextView mTextEmail;
    private TextView mTextPrivateGists;
    private TextView mTextTotalPrivateRepos;
    private TextView mTextOwnedPrivateRepos;

    public interface OnProfileFragmentListener {

        void getAuthorizationProfile();

        void onButtonReposListClicked();

        void onOpenUpdateProfileScreen();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        mRootView = inflater.inflate(R.layout.fragment_your_profile, container, false);
        return mRootView;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initView();

        mListener.getAuthorizationProfile();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnProfileFragmentListener) {
            mListener = (OnProfileFragmentListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnYourProfileFragmentListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    private void initView() {
        mImageAvatar = mRootView.findViewById(R.id.image_avatar);
        mTextLogin = mRootView.findViewById(R.id.text_login);
        mTextName = mRootView.findViewById(R.id.text_name);
        mTextCompany = mRootView.findViewById(R.id.text_company);
        mTextEmail = mRootView.findViewById(R.id.text_email);

        mRootView.findViewById(R.id.button_navigation_to_repos_list)
                .setOnClickListener(new OnButtonReposListClickListener());
        mRootView.findViewById(R.id.button_open_update_profile_screen)
                .setOnClickListener(new OnOpenUpdateProfileClickListener());

        mTextPrivateGists = mRootView.findViewById(R.id.text_private_gists_count);
        mTextTotalPrivateRepos = mRootView.findViewById(R.id.text_total_private_repos_count);
        mTextOwnedPrivateRepos = mRootView.findViewById(R.id.text_owned_private_repos_count);
    }

    public void showAuthorizationProfile(User user) {
        Glide.with(this).load(user.getAvatarUrl()).into(mImageAvatar);
        mTextLogin.setText(user.getLogin());
        mTextName.setText(checkAndReplaceNullField(user.getName()));
        mTextCompany.setText(checkAndReplaceNullField(user.getCompany()));
        mTextEmail.setText(checkAndReplaceNullField(user.getEmail()));
        mTextPrivateGists.setText(String.valueOf(user.getPrivateGists()));
        mTextTotalPrivateRepos.setText(String.valueOf(user.getTotalPrivateRepos()));
        mTextOwnedPrivateRepos.setText(String.valueOf(user.getOwnedPrivateRepos()));
    }

    private String checkAndReplaceNullField(String receivedValue) {
        if (receivedValue == null) {
            return NULL_FIELD_REPLACEMENT;
        } else {
            return receivedValue;
        }
    }

    private class OnButtonReposListClickListener implements View.OnClickListener {

        @Override
        public void onClick(View v) {
            mListener.onButtonReposListClicked();
        }
    }

    private class OnOpenUpdateProfileClickListener implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            mListener.onOpenUpdateProfileScreen();
        }
    }
}
