package shaiytan.ssatrloader.views;

import android.content.Intent;
import android.os.*;
import android.support.v7.app.AppCompatActivity;
import android.widget.*;

import com.squareup.picasso.Picasso;

import shaiytan.ssatrloader.R;
import shaiytan.ssatrloader.models.RepoRecord;


public class RepoActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_repo);
        Intent intent = getIntent();
        RepoRecord repository = (RepoRecord) intent.getSerializableExtra("repository");

        TextView name = findViewById(R.id.reponame);
        name.setText(repository.getName());

        TextView owner = findViewById(R.id.ownername);
        owner.setText(repository.getOwnerName());

        ImageView avatar = findViewById(R.id.owneravatar);
        Picasso.with(this).load(repository.getAvatar()).into(avatar);

        TextView description = findViewById(R.id.description);
        description.setText(repository.getDescription());

    }
}
