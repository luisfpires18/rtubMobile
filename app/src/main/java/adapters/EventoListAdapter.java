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

import ipb.pt.rtub.R;
import entities.Evento;

/**
 * Created by LP18 on 16/06/2017.
 */

public class EventoListAdapter extends ArrayAdapter<Evento> {

    private Context context;
    private List<Evento> eventos;

    public EventoListAdapter(Context context, List<Evento> eventos){
        super(context, R.layout.evento_list_layout, eventos);
        this.context = context;
        this.eventos = eventos;

    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        View view = layoutInflater.inflate(R.layout.evento_list_layout, parent, false);

        Evento evento = eventos.get(position);

        TextView textViewNome = (TextView) view.findViewById(R.id.textViewNome);
        textViewNome.setText(evento.getNome());

//        TextView textViewLocal = (TextView) view.findViewById(R.id.textViewLocal);
//        textViewLocal.setText(evento.getLocal());

        TextView textViewTipo = (TextView) view.findViewById(R.id.textViewTipo);
        textViewTipo.setText(evento.getTipo());
//
//        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
//        Date d = evento.getData();
//        String data = formatter.format(d);
//        TextView textViewData = (TextView) view.findViewById(R.id.textViewData);
//        textViewData.setText(data);

        return view;
    }
}
