package shaiytan.ssatrloader;

import android.app.Activity;
import android.view.*;
import android.widget.*;

import java.util.List;

/**
 * Created by Shaiytan on 17.05.2017.
 */
public class ReposAdapter extends BaseAdapter
{
    private Activity context;
    private List<RepoRecord> data;

    public ReposAdapter(Activity context, List<RepoRecord> data) {
        this.context = context;
        this.data = data;
        if (data.isEmpty())Toast.makeText(this.context, "Cannot Download Data", Toast.LENGTH_SHORT).show();
    }

    @Override
    public int getCount() {
        return data.size();
    }

    @Override
    public Object getItem(int position) {
        return data.get(position);
    }

    @Override
    public long getItemId(int position) {
        return data.get(position).getId();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View itemView = convertView;
        if(itemView==null){
            LayoutInflater inflater = context.getLayoutInflater();
            itemView=inflater.inflate(R.layout.list_item,null,true);
        }
        TextView name = itemView.findViewById(R.id.name);
        TextView owner = itemView.findViewById(R.id.owner);
        name.setText(data.get(position).getName());
        owner.setText("Owner: "+data.get(position).getOwnerName());
        return itemView;
    }
}
