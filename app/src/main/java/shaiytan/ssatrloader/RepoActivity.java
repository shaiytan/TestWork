package shaiytan.ssatrloader;

import android.content.Intent;
import android.graphics.*;
import android.os.*;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.*;

import java.io.*;
import java.net.URL;

public class RepoActivity extends AppCompatActivity {
    private RepoRecord repository;
    private AppCompatTextView name;
    private AppCompatTextView owner;
    private AppCompatTextView description;
    private AppCompatImageView avatar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_repo);
        Intent intent = getIntent();
        repository = (RepoRecord) intent.getSerializableExtra("repository");

        name= (AppCompatTextView) findViewById(R.id.reponame);
        name.setText(repository.getName());

        owner= (AppCompatTextView) findViewById(R.id.ownername);
        owner.setText(repository.getOwnerName());

        avatar= (AppCompatImageView) findViewById(R.id.owneravatar);
        avatar.setImageBitmap(loadBitmap(repository.getAvatar()));

        description= (AppCompatTextView) findViewById(R.id.description);
        description.setText(repository.getDescription());

    }
    private Bitmap loadBitmap(String url)
    {
        AsyncTask<String,Void,Bitmap> loader=new AsyncTask<String, Void, Bitmap>() {
            @Override
            protected Bitmap doInBackground(String... url) {
                Bitmap avatar=null;
                try {
                    InputStream in=new URL(url[0]).openStream();
                    avatar= BitmapFactory.decodeStream(in);
                } catch (IOException e) { }

                return avatar;
            }
        };
        Bitmap ret= null;
        try {
            ret = loader.execute(url).get();
        } catch (Exception e) { }
        return ret;
    }

}
