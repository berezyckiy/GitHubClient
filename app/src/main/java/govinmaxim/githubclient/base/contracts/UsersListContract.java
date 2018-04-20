package govinmaxim.githubclient.base.contracts;

import java.util.List;

import govinmaxim.githubclient.base.MvpPresenter;
import govinmaxim.githubclient.base.MvpView;
import govinmaxim.githubclient.model.User;

public interface UsersListContract {

    interface View extends MvpView {

        void showUsersList(List<User> userList);
    }

    interface Presenter extends MvpPresenter<View> {

        void getUsersList(int lastUserId);

        void getFoundUsers(String login, int pageCount);
    }
}
