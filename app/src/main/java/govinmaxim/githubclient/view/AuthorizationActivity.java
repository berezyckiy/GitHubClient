package govinmaxim.githubclient.view;

import android.os.Bundle;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import govinmaxim.githubclient.R;
import govinmaxim.githubclient.base.contracts.AuthorizationContract;
import govinmaxim.githubclient.model.User;
import govinmaxim.githubclient.presenter.AuthorizationPresenter;
import govinmaxim.githubclient.storage.Preferences;
import govinmaxim.githubclient.view.users_list.UsersListActivity;

public class AuthorizationActivity extends AppCompatActivity implements AuthorizationContract.View {

    private EditText mEditUserName;
    private TextInputEditText mEditUserPassword;
    private AuthorizationPresenter mPresenter;
    private ProgressBar mLoadingIndicator;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_authorization);
        initView();

        mPresenter = new AuthorizationPresenter();
        mPresenter.attachView(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mPresenter.detachView();
    }

    @Override
    public String getTextUserName() {
        return mEditUserName.getText().toString();
    }

    @Override
    public String getTextUserPassword() {
        return mEditUserPassword.getText().toString();
    }

    @Override
    public void showLoadingIndicator() {
        mLoadingIndicator.setVisibility(ProgressBar.VISIBLE);
    }

    @Override
    public void dismissLoadingIndicator() {
        mLoadingIndicator.setVisibility(ProgressBar.INVISIBLE);
    }

    @Override
    public void showMessage(int messResId) {
        Toast.makeText(this, messResId, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void authorizationSuccess(User user) {
        saveLoginAndPassword();
        startActivity(UsersListActivity.getStartIntent(this, user));
    }

    private void initView() {
        mEditUserName = findViewById(R.id.edit_user_name);
        mEditUserPassword = findViewById(R.id.edit_user_password);
        mLoadingIndicator = findViewById(R.id.progress_loading_indicator);
        findViewById(R.id.button_authorization).setOnClickListener(new ButtonAuthorizationListener());
    }

    private void saveLoginAndPassword() {
        Preferences preferences = new Preferences(this);
        preferences.setLogin(getTextUserName());
        preferences.setPassword(getTextUserPassword());
    }

    private class ButtonAuthorizationListener implements View.OnClickListener {

        @Override
        public void onClick(View v) {
            mPresenter.onInputSubmit();
        }
    }
}
