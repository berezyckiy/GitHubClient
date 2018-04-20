package govinmaxim.githubclient.base;

public abstract class BasePresenter<V extends MvpView> implements MvpPresenter<V> {

    private static final int STATUS_OK = 200;

    private V mView;

    @Override
    public void attachView(V view) {
        mView = view;
    }

    @Override
    public void detachView() {
        mView = null;
    }

    public V getView() {
        return mView;
    }

    public static int getStatusOk() {
        return STATUS_OK;
    }
}
