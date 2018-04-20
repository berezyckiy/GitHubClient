package govinmaxim.githubclient.presenter;

import govinmaxim.githubclient.R;
import govinmaxim.githubclient.api.Service;
import govinmaxim.githubclient.base.BasePresenter;
import govinmaxim.githubclient.base.contracts.AuthorizationContract;
import govinmaxim.githubclient.model.User;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AuthorizationPresenter extends BasePresenter<AuthorizationContract.View> implements AuthorizationContract.Presenter {

    @Override
    public void onInputSubmit() {
        String userName = getView().getTextUserName();
        String userPassword = getView().getTextUserPassword();

        if (!userName.equals("") && !userPassword.equals("")) {
            Service service = new Service();
            getView().showLoadingIndicator();

            service.authorizationUser(userName, userPassword).enqueue(new Callback<User>() {
                @Override
                public void onResponse(Call<User> call, Response<User> response) {
                    getView().dismissLoadingIndicator();
                    if (response.raw().code() == getStatusOk()) {
                        getView().authorizationSuccess(response.body());
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
        } else {
            getView().showMessage(R.string.empty_field);
        }
    }
}
