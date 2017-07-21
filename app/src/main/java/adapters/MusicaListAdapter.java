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

import entities.Musica;
import ipb.pt.rtub.R;

/**
 * Created by LP18 on 16/06/2017.
 */

public class MusicaListAdapter extends ArrayAdapter<Musica> {

    private Context context;
    private List<Musica> musicas;

    public MusicaListAdapter(Context context, List<Musica> musicas){
        super(context, R.layout.musica_list_layout, musicas);
        this.context = context;
        this.musicas = musicas;

    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        View view = layoutInflater.inflate(R.layout.musica_list_layout, parent, false);

        Musica musica = musicas.get(position);

        TextView textViewFaixa = (TextView) view.findViewById(R.id.textViewFaixa);
        textViewFaixa.setText(String.valueOf(musica.getFaixa()));

        TextView textViewTitulo = (TextView) view.findViewById(R.id.textViewTitulo);
        textViewTitulo.setText(String.valueOf(musica.getTitulo()));

        return view;
    }
}
