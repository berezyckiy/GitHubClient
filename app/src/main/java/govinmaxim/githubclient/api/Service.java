package govinmaxim.githubclient.api;

import java.io.IOException;
import java.util.List;

import govinmaxim.githubclient.model.FoundUsers;
import govinmaxim.githubclient.model.Repository;
import govinmaxim.githubclient.model.UpdateInfo;
import govinmaxim.githubclient.model.User;
import okhttp3.Credentials;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Service implements GitHubService {

    private static final String GIT_HUB_URL = "https://api.github.com/";
    private AuthorizationApi mAuthorizationApi;
    private UsersListApi mUsersListApi;

    @Override
    public Call<User> authorizationUser(String userName, String password) {
        if (mAuthorizationApi == null) {
            buildAuthorization(userName, password);
        }
        return mAuthorizationApi.logInUser();
    }

    @Override
    public Call<List<User>> getUsersList(int lastUserId) {
        if (mUsersListApi == null) {
            buildUsersListService();
        }
        return mUsersListApi.getUsersList(lastUserId);
    }

    @Override
    public Call<FoundUsers> getFoundUsers(String login, int pageCount) {
        if (mUsersListApi == null) {
            buildUsersListService();
        }

        return mUsersListApi.getFoundUsers(login, pageCount);
    }

    @Override
    public Call<User> getUserProfile(String userName, String password, String searchLogin) {
        if (mAuthorizationApi == null) {
            buildAuthorization(userName, password);
        }
        return mAuthorizationApi.getUserProfile(searchLogin);
    }

    @Override
    public Call<List<Repository>> getUserRepositories(String login) {
        if (mUsersListApi == null) {
            buildUsersListService();
        }
        return mUsersListApi.getUserRepositories(login);
    }

    @Override
    public Call<User> updateProfile(String userName, String password, UpdateInfo updateInfo) {
        if (mAuthorizationApi == null) {
            buildAuthorization(userName, password);
        }
        return mAuthorizationApi.updateProfile(updateInfo);
    }

    private void buildAuthorization(final String userName, final String password) {
        OkHttpClient.Builder clientBuilder = new OkHttpClient.Builder();

        clientBuilder.addInterceptor(new Interceptor() {
            @Override
            public Response intercept(Interceptor.Chain chain) throws IOException {
                Request original = chain.request();

                Request request = original.newBuilder()
                        .header("Authorization", Credentials.basic(userName, password))
                        .build();

                return chain.proceed(request);
            }
        });

        OkHttpClient client = clientBuilder.build();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(GIT_HUB_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .client(client)
                .build();

        mAuthorizationApi = retrofit.create(AuthorizationApi.class);
    }

    private void buildUsersListService() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(GIT_HUB_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        mUsersListApi = retrofit.create(UsersListApi.class);
    }
}
