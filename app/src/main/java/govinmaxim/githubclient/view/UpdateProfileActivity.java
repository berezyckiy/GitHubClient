package govinmaxim.githubclient.view;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Switch;
import android.widget.Toast;

import govinmaxim.githubclient.R;
import govinmaxim.githubclient.api.Service;
import govinmaxim.githubclient.base.contracts.UpdateProfileContract;
import govinmaxim.githubclient.model.UpdateInfo;
import govinmaxim.githubclient.model.User;
import govinmaxim.githubclient.presenter.UpdateProfilePresenter;
import govinmaxim.githubclient.storage.Preferences;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UpdateProfileActivity extends AppCompatActivity implements UpdateProfileContract.View {

    private EditText mTextName;
    private EditText mTextBlog;
    private EditText mTextCompany;
    private EditText mTextLocation;
    private EditText mTextBio;
    private Switch mSwitchHireAble;
    private ProgressBar mLoadingIndicator;

    private UpdateProfilePresenter mPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_profile);

        initView();

        mPresenter = new UpdateProfilePresenter();
        mPresenter.attachView(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mPresenter.detachView();
    }

    @Override
    public String getTextName() {
        return mTextName.getText().toString();
    }

    @Override
    public String getTextBlog() {
        return mTextBlog.getText().toString();
    }

    @Override
    public String getTextCompany() {
        return mTextCompany.getText().toString();
    }

    @Override
    public String getTextLocation() {
        return mTextLocation.getText().toString();
    }

    @Override
    public Boolean getHireAble() {
        return mSwitchHireAble.isChecked();
    }

    @Override
    public String getTextBio() {
        return mTextBio.getText().toString();
    }

    @Override
    public void updateSuccessful() {
        showMessage(R.string.update_successful);
        finish();
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

    private void initView() {
        mTextName = findViewById(R.id.edit_new_name);
        mTextBlog = findViewById(R.id.edit_new_blog);
        mTextCompany = findViewById(R.id.edit_new_company);
        mTextLocation = findViewById(R.id.edit_new_location);
        mTextBio = findViewById(R.id.edit_new_biography);
        mSwitchHireAble = findViewById(R.id.switch_hireable);
        mLoadingIndicator = findViewById(R.id.progress_loading_indicator);

        findViewById(R.id.button_update_profile)
                .setOnClickListener(new OnButtonUpdateListener());
    }

    private UpdateInfo getNewInfo() {
        UpdateInfo updateInfo = new UpdateInfo();

        updateInfo.setName(getTextName());
        updateInfo.setBlog(getTextBlog());
        updateInfo.setCompany(getTextCompany());
        updateInfo.setLocation(getTextLocation());
        updateInfo.setHireable(getHireAble());
        updateInfo.setBio(getTextBio());

        return updateInfo;
    }

    private class OnButtonUpdateListener implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            Preferences preferences = new Preferences(getApplicationContext());
            mPresenter.updateProfile(preferences.getLogin(), preferences.getPassword(), getNewInfo());
        }
    }
}
