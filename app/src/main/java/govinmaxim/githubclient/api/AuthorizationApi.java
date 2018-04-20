package govinmaxim.githubclient.api;

import govinmaxim.githubclient.model.UpdateInfo;
import govinmaxim.githubclient.model.User;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.PATCH;
import retrofit2.http.Path;

public interface AuthorizationApi {

    @GET("user")
    Call<User> logInUser();

    @GET("users/{login}")
    Call<User> getUserProfile(@Path("login") String login);

    @PATCH("user")
    Call<User> updateProfile(@Body UpdateInfo updateInfo);
}
