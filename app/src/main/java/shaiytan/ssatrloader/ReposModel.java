package shaiytan.ssatrloader;


import android.os.AsyncTask;

import com.google.gson.*;

import java.io.*;
import java.net.*;
import java.util.*;

/**
 * Created by Shaiytan on 17.05.2017.
 */

public class ReposModel {
    private ArrayList<RepoRecord> data;
    private String address="https://api.github.com/repositories";
    public ReposModel() {
        data=new ArrayList<>();
    }
    private void load() {
        AsyncTask<Void,Void,ArrayList<RepoRecord>> request=new AsyncTask<Void, Void, ArrayList<RepoRecord>>() {
            @Override
            protected ArrayList<RepoRecord> doInBackground(Void... params) {
                ArrayList<RepoRecord> records = new ArrayList<>();
                try {
                    URL github = new URL(address);
                    HttpURLConnection connection = (HttpURLConnection) github.openConnection();
                    connection.connect();

                    Map<String, List<String>> resphead = connection.getHeaderFields();
                    String nextpage=resphead.get("Link").get(0);
                    nextpage=nextpage.substring(nextpage.indexOf("<") + 1, nextpage.indexOf(">"));
                    address = nextpage;

                    InputStreamReader in = new InputStreamReader(connection.getInputStream());
                    JsonParser parser=new JsonParser();
                    JsonElement root = parser.parse(in);
                    connection.disconnect();

                    parseJson(records, root);

                } catch (Exception e) {
                    records.clear();
                }
                return records;
            }
        };
        try {
            ArrayList<RepoRecord> response = request.execute().get();
            if(!response.isEmpty()) {
                data=response;
            }
        } catch (Exception ignore) { }
    }

    private void parseJson(ArrayList<RepoRecord> records, JsonElement root) {
        JsonArray list = root.getAsJsonArray();
        for (int i = 0; i < list.size(); i++) {
            JsonObject rec = list.get(i).getAsJsonObject();
            long id = rec.get("id").getAsLong();
            JsonElement cur=rec.get("name");
            String name = cur.isJsonNull()?"":cur.getAsString();
            JsonObject owner = rec.get("owner").getAsJsonObject();
            cur=owner.get("login");
            String owner_name = cur.isJsonNull()?"":cur.getAsString();
            cur=owner.get("avatar_url");
            String avatar = cur.isJsonNull()?"":cur.getAsString();
            cur=rec.get("description");
            String description = cur.isJsonNull()?"":cur.getAsString();

            records.add(new RepoRecord(id,name,owner_name,description,avatar));
        }
    }
    public List<RepoRecord> getNextList()
    {
        load();
        return data;
    }
    public List<RepoRecord> getSortedList()
    {
        Collections.sort(data);
        return data;
    }
}
