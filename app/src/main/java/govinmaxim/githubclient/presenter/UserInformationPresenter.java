package govinmaxim.githubclient.presenter;

import govinmaxim.githubclient.R;
import govinmaxim.githubclient.api.Service;
import govinmaxim.githubclient.base.BasePresenter;
import govinmaxim.githubclient.base.contracts.UserInformationContract;
import govinmaxim.githubclient.model.User;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UserInformationPresenter extends BasePresenter<UserInformationContract.View>
        implements UserInformationContract.Presenter {

    @Override
    public void loadUserProfile(String userName, String password, String login) {
        Service service = new Service();
        getView().showLoadingIndicator();

        service.getUserProfile(userName, password, login).enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                getView().dismissLoadingIndicator();
                if (response.code() == getStatusOk()) {
                    getView().showUserProfile(response.body());
                } else {
                    getView().showMessage(R.string.failure);
                }
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {
                getView().dismissLoadingIndicator();
                getView().showMessage(R.string.failure);
            }
        });
    }
}
