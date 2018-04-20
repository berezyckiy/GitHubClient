package govinmaxim.githubclient.view.user_repositories;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import java.util.List;

import govinmaxim.githubclient.R;
import govinmaxim.githubclient.base.contracts.UserRepositoriesListContract;
import govinmaxim.githubclient.model.Repository;
import govinmaxim.githubclient.presenter.UserRepositoriesListPresenter;
import govinmaxim.githubclient.base.OnItemClickListener;

public class UserRepositoriesActivity extends AppCompatActivity implements UserRepositoriesListContract.View {

    private static final String EXTRA_USER_LOGIN = "Login";

    private RecyclerView mRecycler;
    private RecyclerRepositoriesListAdapter mAdapter;
    private UserRepositoriesListPresenter mPresenter;
    private ProgressBar mLoadingIndicator;

    public static Intent getStartIntent(Context context, String login) {
        Intent intent = new Intent(context, UserRepositoriesActivity.class);
        intent.putExtra(EXTRA_USER_LOGIN, login);
        return intent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_repositories);
        initView();
        initRecycler();

        mPresenter = new UserRepositoriesListPresenter();
        mPresenter.attachView(this);
        mPresenter.loadUserRepositories(getIntent().getStringExtra(EXTRA_USER_LOGIN));
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mPresenter.detachView();
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
    public void showUserRepositories(List<Repository> repositories) {
        mAdapter.setItemsList(repositories);
    }

    private void initView() {
        mLoadingIndicator = findViewById(R.id.progress_loading_indicator);
    }

    private void initRecycler() {
        mRecycler = findViewById(R.id.recycler_user_repositories);
        mRecycler.setLayoutManager(new LinearLayoutManager(this));
        mAdapter = new RecyclerRepositoriesListAdapter(R.layout.view_holder_repository_item,
                new OnOpenInBrowserClickListener());
        mRecycler.setAdapter(mAdapter);
    }

    private class OnOpenInBrowserClickListener implements OnItemClickListener<Repository> {

        @Override
        public void onItemClick(Repository repository) {
            String url = repository.getHtmlUrl();
            startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(url)));
        }
    }
}
