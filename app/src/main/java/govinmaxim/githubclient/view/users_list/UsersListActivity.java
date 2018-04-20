package govinmaxim.githubclient.view.users_list;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import java.util.List;

import govinmaxim.githubclient.R;
import govinmaxim.githubclient.base.contracts.UsersListContract;
import govinmaxim.githubclient.model.User;
import govinmaxim.githubclient.presenter.UsersListPresenter;
import govinmaxim.githubclient.view.UpdateProfileActivity;
import govinmaxim.githubclient.view.UserInformationActivity;
import govinmaxim.githubclient.view.user_repositories.UserRepositoriesActivity;

public class UsersListActivity extends AppCompatActivity
        implements UsersListContract.View, YourProfileFragment.OnProfileFragmentListener, UsersListFragment.OnUsersListFragmentListener {

    private static final String EXTRA_USER = "AuthUser";

    private TabLayout mTabLayout;
    private ViewPager mViewPager;
    private UsersListPresenter mPresenter;

    public static Intent getStartIntent(Context context, User user) {
        Intent intent = new Intent(context, UsersListActivity.class);
        intent.putExtra(EXTRA_USER, user);
        return intent;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_users_list);

        initTabLayout();
        initViewPager();

        mPresenter = new UsersListPresenter();
        mPresenter.attachView(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mPresenter.detachView();
    }

    @Override
    public void getAuthorizationProfile() {
        User user = (User) getIntent().getSerializableExtra(EXTRA_USER);
        for (Fragment fragment : getSupportFragmentManager().getFragments()) {
            if (fragment instanceof YourProfileFragment) {
                ((YourProfileFragment) fragment).showAuthorizationProfile(user);
            }
        }
    }

    @Override
    public void showUsersList(List<User> usersList) {
        for (Fragment fragment : getSupportFragmentManager().getFragments()) {
            if (fragment instanceof UsersListFragment) {
                ((UsersListFragment) fragment).showUsersList(usersList);
            }
        }
    }

    @Override
    public void showLoadingIndicator() {
    }

    @Override
    public void dismissLoadingIndicator() {
    }

    @Override
    public void showMessage(int messResId) {
        Toast.makeText(this, messResId, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onButtonReposListClicked() {
        User user = (User) getIntent().getSerializableExtra(EXTRA_USER);
        startActivity(UserRepositoriesActivity.getStartIntent(this, user.getLogin()));
    }

    @Override
    public void onOpenUpdateProfileScreen() {
        startActivity(new Intent(this, UpdateProfileActivity.class));
    }

    @Override
    public void getUsersList(int lastUserId) {
        mPresenter.getUsersList(lastUserId);
    }

    @Override
    public void searchUser(String login, int pageCount) {
        mPresenter.getFoundUsers(login, pageCount);
    }

    @Override
    public void openUserInformation(String login) {
        startActivity(UserInformationActivity.getStartIntent(this, login));
    }

    @Override
    public void setTabText(int titleResId) {
        mTabLayout.getTabAt(mTabLayout.getSelectedTabPosition()).setText(titleResId);
    }

    private void initTabLayout() {
        mTabLayout = findViewById(R.id.tab_layout);
        mTabLayout.addTab(mTabLayout.newTab().setText(R.string.users_list));
        mTabLayout.addTab(mTabLayout.newTab().setText(R.string.your_profile));
        mTabLayout.setTabGravity(TabLayout.GRAVITY_FILL);
        mTabLayout.addOnTabSelectedListener(new TabSelectedListener());
    }

    private void initViewPager() {
        mViewPager = findViewById(R.id.view_pager);
        PagerAdapter mPagerAdapter = new PagerAdapter(getSupportFragmentManager(), mTabLayout.getTabCount());
        mViewPager.setAdapter(mPagerAdapter);
        mViewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(mTabLayout));
    }

    private class TabSelectedListener implements TabLayout.OnTabSelectedListener {
        @Override
        public void onTabSelected(TabLayout.Tab tab) {
            mViewPager.setCurrentItem(tab.getPosition());
        }

        @Override
        public void onTabUnselected(TabLayout.Tab tab) {
        }

        @Override
        public void onTabReselected(TabLayout.Tab tab) {
        }
    }

    public class PagerAdapter extends FragmentStatePagerAdapter {
        private static final int USERS_LIST_TAB = 0;
        private static final int YOUR_PROFILE_TAB = 1;

        private int mTabsCount;

        PagerAdapter(FragmentManager fragmentManager, int tabsCount) {
            super(fragmentManager);
            mTabsCount = tabsCount;
        }

        @Override
        public Fragment getItem(int position) {
            switch (position) {
                case USERS_LIST_TAB:
                    return new UsersListFragment();
                case YOUR_PROFILE_TAB:
                    return new YourProfileFragment();
                default:
                    return null;
            }
        }

        @Override
        public int getCount() {
            return mTabsCount;
        }
    }
}
