package adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

import entities.Premio;
import ipb.pt.rtub.R;

/**
 * Created by LP18 on 16/06/2017.
 */

public class PremioListAdapter extends ArrayAdapter<Premio> {

    private Context context;
    private List<Premio> premios;

    public PremioListAdapter(Context context, List<Premio> premios){
        super(context, R.layout.premio_list_layout, premios);
        this.context = context;
        this.premios = premios;

    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        View view = layoutInflater.inflate(R.layout.premio_list_layout, parent, false);

        Premio premio = premios.get(position);

        TextView textViewDescricao = (TextView) view.findViewById(R.id.textViewDescricao);
        textViewDescricao.setText(premio.getDescricao());


        return view;
    }
}
