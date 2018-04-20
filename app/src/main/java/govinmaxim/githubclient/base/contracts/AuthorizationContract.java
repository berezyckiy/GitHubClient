package govinmaxim.githubclient.base.contracts;

import govinmaxim.githubclient.base.MvpPresenter;
import govinmaxim.githubclient.base.MvpView;
import govinmaxim.githubclient.model.User;

public interface AuthorizationContract {

    interface View extends MvpView {

        String getTextUserName();
        String getTextUserPassword();

        void authorizationSuccess(User user);
    }

    interface Presenter extends MvpPresenter<View> {

        void onInputSubmit();
    }
}
