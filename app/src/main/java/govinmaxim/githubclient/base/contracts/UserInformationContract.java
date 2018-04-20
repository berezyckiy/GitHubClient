package govinmaxim.githubclient.base.contracts;

import govinmaxim.githubclient.base.MvpPresenter;
import govinmaxim.githubclient.base.MvpView;
import govinmaxim.githubclient.model.User;

public interface UserInformationContract {

    interface View extends MvpView {

        void showUserProfile(User user);
    }

    interface Presenter extends MvpPresenter<View> {

        void loadUserProfile(String userName, String password, String login);
    }
}
