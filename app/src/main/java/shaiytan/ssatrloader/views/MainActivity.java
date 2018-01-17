package shaiytan.ssatrloader.views;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.*;

import shaiytan.ssatrloader.R;
import shaiytan.ssatrloader.models.*;

public class MainActivity
        extends AppCompatActivity
        implements AdapterView.OnItemClickListener {
    private ListView reposlist;
    private ReposModel model;
    private Button sortbtn;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        model = new ReposModel();
        reposlist = findViewById(R.id.reposlist);
        model.getNextList(list -> reposlist.setAdapter(new ReposAdapter(this, list)));
        reposlist.setOnItemClickListener(this);
        sortbtn = findViewById(R.id.sortclick);
        sortbtn.setOnClickListener(this::sortClick);
        FloatingActionButton loadbtn = findViewById(R.id.loadclick);
        loadbtn.setOnClickListener(this::loadMoreClick);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        RepoRecord rec = (RepoRecord) parent.getAdapter().getItem(position);
        Intent intent = new Intent(this, RepoActivity.class);
        intent.putExtra("repository", rec);
        startActivity(intent);
    }

    public void loadMoreClick(View view) {
        model.getNextList(list -> reposlist.setAdapter(new ReposAdapter(this, list)));
        sortbtn.setVisibility(View.VISIBLE);
    }

    public void sortClick(View view) {
        reposlist.setAdapter(new ReposAdapter(this, model.getSortedList()));
        sortbtn.setVisibility(View.INVISIBLE);
    }
}
