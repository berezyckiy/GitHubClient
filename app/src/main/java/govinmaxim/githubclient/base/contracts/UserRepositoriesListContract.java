package govinmaxim.githubclient.base.contracts;

import java.util.List;

import govinmaxim.githubclient.base.MvpPresenter;
import govinmaxim.githubclient.base.MvpView;
import govinmaxim.githubclient.model.Repository;

public interface UserRepositoriesListContract {

    interface View extends MvpView {

        void showUserRepositories(List<Repository> repositories);
    }

    interface Presenter extends MvpPresenter<View> {

        void loadUserRepositories(String login);
    }
}
