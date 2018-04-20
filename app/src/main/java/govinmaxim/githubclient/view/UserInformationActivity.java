package govinmaxim.githubclient.view;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import govinmaxim.githubclient.R;
import govinmaxim.githubclient.base.contracts.UserInformationContract;
import govinmaxim.githubclient.model.User;
import govinmaxim.githubclient.presenter.UserInformationPresenter;
import govinmaxim.githubclient.storage.Preferences;
import govinmaxim.githubclient.view.user_repositories.UserRepositoriesActivity;

public class UserInformationActivity extends AppCompatActivity implements UserInformationContract.View {

    private static final String EXTRA_USER_LOGIN = "Login";
    private static final String NULL_FIELD_REPLACEMENT = "Empty";

    private ImageView mImageAvatar;
    private TextView mTextLogin;
    private TextView mTextName;
    private TextView mTextCompany;
    private TextView mTextEmail;
    private ProgressBar mLoadingIndicator;

    private UserInformationPresenter mPresenter;

    public static Intent getStartIntent(Context context, String login) {
        Intent intent = new Intent(context, UserInformationActivity.class);
        intent.putExtra(EXTRA_USER_LOGIN, login);
        return intent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_information);
        initView();

        Preferences preferences = new Preferences(this);

        mPresenter = new UserInformationPresenter();
        mPresenter.attachView(this);
        mPresenter.loadUserProfile(preferences.getLogin(), preferences.getPassword(),
                getIntent().getStringExtra(EXTRA_USER_LOGIN));
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mPresenter.detachView();
    }

    @Override
    public void showUserProfile(User user) {
        Glide.with(this).load(user.getAvatarUrl()).into(mImageAvatar);
        mTextLogin.setText(user.getLogin());
        mTextName.setText(checkAndReplaceNullField(user.getName()));
        mTextCompany.setText(checkAndReplaceNullField(user.getCompany()));
        mTextEmail.setText(checkAndReplaceNullField(user.getEmail()));
    }

    private void initView() {
        mImageAvatar = findViewById(R.id.image_avatar);
        mTextLogin = findViewById(R.id.text_login);
        mTextName = findViewById(R.id.text_name);
        mTextCompany = findViewById(R.id.text_company);
        mTextEmail = findViewById(R.id.text_email);
        mLoadingIndicator = findViewById(R.id.progress_loading_indicator);

        findViewById(R.id.button_navigation_to_repos_list).
                setOnClickListener(new OnButtonReposListClickListener());
    }

    private String checkAndReplaceNullField(String receivedValue) {
        if (receivedValue == null) {
            return NULL_FIELD_REPLACEMENT;
        } else {
            return receivedValue;
        }
    }

    @Override
    public void showLoadingIndicator() {
        mLoadingIndicator.setVisibility(View.VISIBLE);
    }

    @Override
    public void dismissLoadingIndicator() {
        mLoadingIndicator.setVisibility(View.INVISIBLE);
    }

    @Override
    public void showMessage(int messResId) {
        Toast.makeText(this, messResId, Toast.LENGTH_SHORT).show();
    }

    private class OnButtonReposListClickListener implements View.OnClickListener {

        @Override
        public void onClick(View v) {
            startActivity(UserRepositoriesActivity.getStartIntent(getApplicationContext(),
                    getIntent().getStringExtra(EXTRA_USER_LOGIN)));
        }
    }
}
