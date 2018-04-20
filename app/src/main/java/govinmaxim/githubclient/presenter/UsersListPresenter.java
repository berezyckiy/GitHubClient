package govinmaxim.githubclient.presenter;

import java.util.List;

import govinmaxim.githubclient.R;
import govinmaxim.githubclient.api.Service;
import govinmaxim.githubclient.base.BasePresenter;
import govinmaxim.githubclient.base.contracts.UsersListContract;
import govinmaxim.githubclient.model.FoundUsers;
import govinmaxim.githubclient.model.User;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UsersListPresenter extends BasePresenter<UsersListContract.View> implements UsersListContract.Presenter {

    @Override
    public void getUsersList(int lastUserId) {
        Service service = new Service();

        service.getUsersList(lastUserId).enqueue(new Callback<List<User>>() {
            @Override
            public void onResponse(Call<List<User>> call, Response<List<User>> response) {
                if (response.raw().code() == getStatusOk()) {
                    getView().showUsersList(response.body());
                } else {
                    getView().showMessage(R.string.failure);
                }
            }

            @Override
            public void onFailure(Call<List<User>> call, Throwable t) {
                getView().showMessage(R.string.failure);
            }
        });
    }

    @Override
    public void getFoundUsers(String login, int pageCount) {
        Service service = new Service();

        service.getFoundUsers(login, pageCount).enqueue(new Callback<FoundUsers>() {
            @Override
            public void onResponse(Call<FoundUsers> call, Response<FoundUsers> response) {
                if (response.raw().code() == getStatusOk()) {
                    getView().showUsersList(response.body().getItems());
                } else {
                    getView().showMessage(R.string.failure);
                }
            }

            @Override
            public void onFailure(Call<FoundUsers> call, Throwable t) {
                getView().showMessage(R.string.failure);
            }
        });
    }
}
