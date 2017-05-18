package shaiytan.ssatrloader;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.*;
import android.view.View;
import android.widget.*;

public class MainActivity extends AppCompatActivity implements AdapterView.OnItemClickListener, View.OnClickListener {
    private ListViewCompat reposlist;
    private ReposModel model;
    private AppCompatButton sortbtn;
    private AppCompatButton loadbtn;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        model=new ReposModel();
        reposlist = (ListViewCompat) findViewById(R.id.reposlist);
        reposlist.setAdapter(new ReposAdapter(this,model.getNextList()));
        reposlist.setOnItemClickListener(this);
        sortbtn = (AppCompatButton) findViewById(R.id.sortclick);
        sortbtn.setOnClickListener(this);
        loadbtn = (AppCompatButton) findViewById(R.id.loadclick);
        loadbtn.setOnClickListener(this);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        RepoRecord rec = (RepoRecord) parent.getAdapter().getItem(position);
        Intent intent = new Intent(this, RepoActivity.class);
        intent.putExtra("repository",rec);
        startActivity(intent);
    }

    public void loadMoreClick(View view) {
        reposlist.setAdapter(new ReposAdapter(this,model.getNextList()));
        sortbtn.setEnabled(true);
    }

    public void sortClick(View view) {
        reposlist.setAdapter(new ReposAdapter(this,model.getSortedList()));
        sortbtn.setEnabled(false);
    }

    @Override
    public void onClick(View v) {
        if(v.getId()==R.id.loadclick)
        {
            loadMoreClick(v);
        }
        if(v.getId()==R.id.sortclick)
        {
            sortClick(v);
        }
    }
}
