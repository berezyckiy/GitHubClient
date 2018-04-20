package govinmaxim.githubclient.base;

public interface MvpView {

    void showLoadingIndicator();
    void dismissLoadingIndicator();

    void showMessage(int messResId);
}
