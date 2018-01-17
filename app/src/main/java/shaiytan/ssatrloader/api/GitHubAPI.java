package shaiytan.ssatrloader.api;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Url;
import shaiytan.ssatrloader.models.RepoRecord;

/**
 * Created by Shaiy'tan on 17.01.2018.
 */

public interface GitHubAPI {
    @GET
    Call<ArrayList<RepoRecord>> getRepos(@Url String url);
}
