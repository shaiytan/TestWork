package shaiytan.ssatrloader.models;

import com.google.gson.*;

import java.util.*;

import retrofit2.*;
import retrofit2.converter.gson.GsonConverterFactory;
import shaiytan.ssatrloader.api.GitHubAPI;

/**
 * Created by Shaiytan on 17.05.2017.
 */

public class ReposModel {
    private ArrayList<RepoRecord> data = new ArrayList<>();
    private String address = "https://api.github.com/repositories";
    private GitHubAPI api;

    public ReposModel() {
        Gson gson = new GsonBuilder()
                .registerTypeAdapter(RepoRecord.class, (JsonDeserializer<RepoRecord>) (json, typeOfT, context) -> {
                    JsonObject rec = json.getAsJsonObject();
                    long id = rec.get("id").getAsLong();
                    JsonElement cur = rec.get("name");
                    String name = cur.isJsonNull() ? "" : cur.getAsString();
                    JsonObject owner = rec.get("owner").getAsJsonObject();
                    cur = owner.get("login");
                    String owner_name = cur.isJsonNull() ? "" : cur.getAsString();
                    cur = owner.get("avatar_url");
                    String avatar = cur.isJsonNull() ? "" : cur.getAsString();
                    cur = rec.get("description");
                    String description = cur.isJsonNull() ? "" : cur.getAsString();
                    return new RepoRecord(id, name, owner_name, description, avatar);
                }).create();
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(address + "/")
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();
        api = retrofit.create(GitHubAPI.class);
    }

    public void getNextList(OnLoadListener listenter) {
        api.getRepos(address).enqueue(new Callback<ArrayList<RepoRecord>>() {
            @Override
            public void onResponse(Call<ArrayList<RepoRecord>> call, Response<ArrayList<RepoRecord>> response) {
                String nextpage = response.headers().get("Link");
                if (nextpage != null) {
                    nextpage = nextpage.substring(nextpage.indexOf("<") + 1, nextpage.indexOf(">"));
                    address = nextpage;
                }
                data = response.body();
                listenter.onLoad(data);
            }

            @Override
            public void onFailure(Call<ArrayList<RepoRecord>> call, Throwable t) {
                t.printStackTrace();
            }
        });
    }

    public List<RepoRecord> getSortedList() {
        Collections.sort(data,
                (first, second) ->
                        first.getName().compareToIgnoreCase(second.getName()));
        return data;
    }

    public interface OnLoadListener {
        void onLoad(List<RepoRecord> list);
    }
}
