package govinmaxim.githubclient.presenter;

import java.util.List;

import govinmaxim.githubclient.R;
import govinmaxim.githubclient.api.Service;
import govinmaxim.githubclient.base.BasePresenter;
import govinmaxim.githubclient.base.contracts.UserRepositoriesListContract;
import govinmaxim.githubclient.model.Repository;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UserRepositoriesListPresenter extends BasePresenter<UserRepositoriesListContract.View>
        implements UserRepositoriesListContract.Presenter {

    @Override
    public void loadUserRepositories(String login) {
        Service service = new Service();
        getView().showLoadingIndicator();

        service.getUserRepositories(login).enqueue(new Callback<List<Repository>>() {
            @Override
            public void onResponse(Call<List<Repository>> call, Response<List<Repository>> response) {
                getView().dismissLoadingIndicator();
                if (response.code() == getStatusOk()) {
                    getView().showUserRepositories(response.body());
                } else {
                    getView().showMessage(R.string.failure);
                }
            }

            @Override
            public void onFailure(Call<List<Repository>> call, Throwable t) {
                getView().dismissLoadingIndicator();
                getView().showMessage(R.string.failure);
            }
        });
    }
}
