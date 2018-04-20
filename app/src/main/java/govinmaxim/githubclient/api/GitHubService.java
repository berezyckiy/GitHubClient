package govinmaxim.githubclient.api;

import java.util.List;

import govinmaxim.githubclient.model.FoundUsers;
import govinmaxim.githubclient.model.Repository;
import govinmaxim.githubclient.model.UpdateInfo;
import govinmaxim.githubclient.model.User;
import retrofit2.Call;

public interface GitHubService {

    Call<User> authorizationUser(String userName, String password);

    Call<List<User>> getUsersList(int lastUserId);

    Call<FoundUsers> getFoundUsers(String login, int pageCount);

    Call<User> getUserProfile(String userName, String password, String searchLogin);

    Call<List<Repository>> getUserRepositories(String login);

    Call<User> updateProfile(String userName, String password, UpdateInfo updateInfo);
}
