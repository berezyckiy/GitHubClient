package govinmaxim.githubclient.base.contracts;

import govinmaxim.githubclient.base.MvpPresenter;
import govinmaxim.githubclient.base.MvpView;
import govinmaxim.githubclient.model.UpdateInfo;

public interface UpdateProfileContract {

    interface View extends MvpView {

        String getTextName();
        String getTextBlog();
        String getTextCompany();
        String getTextLocation();
        Boolean getHireAble();
        String getTextBio();

        void updateSuccessful();
    }

    interface Presenter extends MvpPresenter<View> {

        void updateProfile(String login, String password, UpdateInfo updateInfo);
    }
}
