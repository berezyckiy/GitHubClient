package govinmaxim.githubclient.presenter;

import govinmaxim.githubclient.R;
import govinmaxim.githubclient.api.Service;
import govinmaxim.githubclient.base.BasePresenter;
import govinmaxim.githubclient.base.contracts.UpdateProfileContract;
import govinmaxim.githubclient.model.UpdateInfo;
import govinmaxim.githubclient.model.User;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UpdateProfilePresenter extends BasePresenter<UpdateProfileContract.View>
        implements UpdateProfileContract.Presenter {


    @Override
    public void updateProfile(String login, String password, UpdateInfo updateInfo) {
        Service service = new Service();
        getView().showLoadingIndicator();

        service.updateProfile(login, password, updateInfo).enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                getView().dismissLoadingIndicator();
                if (response.code() == getStatusOk()) {
                    getView().updateSuccessful();
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
