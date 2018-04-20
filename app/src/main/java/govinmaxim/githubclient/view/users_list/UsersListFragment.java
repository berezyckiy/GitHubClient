package govinmaxim.githubclient.view.users_list;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;

import java.util.List;

import govinmaxim.githubclient.R;
import govinmaxim.githubclient.model.User;
import govinmaxim.githubclient.base.OnItemClickListener;

public class UsersListFragment extends Fragment implements SwipeRefreshLayout.OnRefreshListener {

    //pagination variables
    private static final int START_ID = 0;
    private int mFoundUsersPageCount;
    private boolean mIsLoading = false;
    private boolean mIsLastPage = false;
    private String mFoundLogin;

    private OnUsersListFragmentListener mListener;
    private View mRootView;
    private RecyclerView mRecycler;
    private RecyclerUsersListAdapter mAdapter;
    private LinearLayoutManager mLayoutManager;
    private SwipeRefreshLayout mSwipeRefresh;
    private RecyclerAllUsersScrollingListener mAllUsersScrollingListener;
    private RecyclerFoundUsersScrollingListener mFoundUsersScrollingListener;

    public interface OnUsersListFragmentListener {

        void getUsersList(int lastUserId);

        void searchUser(String login, int pageCount);

        void openUserInformation(String login);

        void setTabText(int titleResId);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        mRootView = inflater.inflate(R.layout.fragment_users_list, container, false);
        return mRootView;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initRecycler();
        initSwipeRefreshLayout();
        initSearchView();

        loadAllUsersFirstPage();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnUsersListFragmentListener) {
            mListener = (OnUsersListFragmentListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnUsersListFragmentListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    @Override
    public void onRefresh() {
        mRecycler.removeOnScrollListener(mFoundUsersScrollingListener);
        mRecycler.addOnScrollListener(mAllUsersScrollingListener);

        mSwipeRefresh.setRefreshing(true);
        mAdapter.clearUsersList();
        mIsLastPage = false;

        mListener.getUsersList(START_ID);
        mListener.setTabText(R.string.users_list);
    }

    private void initRecycler() {
        mRecycler = mRootView.findViewById(R.id.recycler_users_list);
        mLayoutManager = new GridLayoutManager(getContext(), 2);
        mRecycler.setLayoutManager(mLayoutManager);
        mAdapter = new RecyclerUsersListAdapter(R.layout.view_holder_user_item,
                Glide.with(this), new OnUserClickListener());
        mRecycler.setAdapter(mAdapter);
        mAllUsersScrollingListener = new RecyclerAllUsersScrollingListener();
        mFoundUsersScrollingListener = new RecyclerFoundUsersScrollingListener();
        mRecycler.addOnScrollListener(mAllUsersScrollingListener);
    }

    private void initSwipeRefreshLayout() {
        mSwipeRefresh = mRootView.findViewById(R.id.swipe_refresh_layout);
        mSwipeRefresh.setOnRefreshListener(this);
        mSwipeRefresh.setColorSchemeResources(R.color.colorPrimary,
                android.R.color.holo_green_dark,
                android.R.color.holo_orange_dark,
                android.R.color.holo_blue_dark);
    }

    private void initSearchView() {
        SearchView mSearch = mRootView.findViewById(R.id.search_view);
        mSearch.setQueryHint(getString(R.string.search_by_login));
        mSearch.setOnQueryTextListener(new OnSearchLoginTextListener());
    }

    public void showUsersList(List<User> usersList) {
        if (usersList.size() == 0) {
            mIsLastPage = true;
        }
        mAdapter.setItemsList(usersList);
        mSwipeRefresh.setRefreshing(false);
        mIsLoading = false;
    }

    private void loadAllUsersFirstPage() {
        mSwipeRefresh.setRefreshing(true);
        mAdapter.clearUsersList();
        mListener.getUsersList(START_ID);
    }

    private void loadAllUsersNextPage() {
        mSwipeRefresh.setRefreshing(true);
        mListener.getUsersList(mAdapter.getLastUserId());
    }

    private void loadFoundUsers(String login, int pageCount) {
        mSwipeRefresh.setRefreshing(true);
        mListener.searchUser(login, pageCount);
    }

    private class OnUserClickListener implements OnItemClickListener<User> {

        @Override
        public void onItemClick(User user) {
            mListener.openUserInformation(user.getLogin());
        }
    }

    private class OnSearchLoginTextListener implements SearchView.OnQueryTextListener {

        @Override
        public boolean onQueryTextSubmit(String query) {
            mFoundUsersPageCount = 1;
            mFoundLogin = query;

            mRecycler.removeOnScrollListener(mAllUsersScrollingListener);
            mRecycler.addOnScrollListener(mFoundUsersScrollingListener);

            mIsLastPage = false;
            mAdapter.clearUsersList();
            mSwipeRefresh.setRefreshing(true);
            mListener.searchUser(query, mFoundUsersPageCount);
            mListener.setTabText(R.string.found_users);

            mFoundUsersPageCount++;
            return false;
        }

        @Override
        public boolean onQueryTextChange(String newText) {
            return false;
        }
    }

    private class RecyclerAllUsersScrollingListener extends RecyclerView.OnScrollListener {

        @Override
        public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
            super.onScrolled(recyclerView, dx, dy);

            int visibleItemCount = mLayoutManager.getChildCount();
            int totalItemCount = mLayoutManager.getItemCount();
            int firstVisibleItemPosition = mLayoutManager.findFirstVisibleItemPosition();

            if (!mIsLoading && !mIsLastPage) {
                if ((visibleItemCount + firstVisibleItemPosition) >= totalItemCount
                        && firstVisibleItemPosition >= 0) {

                    mIsLoading = true;
                    loadAllUsersNextPage();
                }
            }
        }
    }

    private class RecyclerFoundUsersScrollingListener extends RecyclerView.OnScrollListener {

        @Override
        public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
            super.onScrolled(recyclerView, dx, dy);

            int visibleItemCount = mLayoutManager.getChildCount();
            int totalItemCount = mLayoutManager.getItemCount();
            int firstVisibleItemPosition = mLayoutManager.findFirstVisibleItemPosition();

            if (!mIsLoading && !mIsLastPage) {
                if ((visibleItemCount + firstVisibleItemPosition) >= totalItemCount
                        && firstVisibleItemPosition >= 0) {

                    mIsLoading = true;
                    loadFoundUsers(mFoundLogin, mFoundUsersPageCount);
                    mFoundUsersPageCount++;
                }
            }
        }
    }
}
