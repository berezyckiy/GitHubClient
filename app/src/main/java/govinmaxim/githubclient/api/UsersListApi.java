package govinmaxim.githubclient.api;

import java.util.List;

import govinmaxim.githubclient.model.FoundUsers;
import govinmaxim.githubclient.model.Repository;
import govinmaxim.githubclient.model.User;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface UsersListApi {

    @GET("users")
    Call<List<User>> getUsersList(@Query("since") int lastUserId);

    @GET("search/users")
    Call<FoundUsers> getFoundUsers(@Query("q") String login, @Query("page") int page);

    @GET("users/{login}/repos")
    Call<List<Repository>> getUserRepositories(@Path("login") String login);
}
