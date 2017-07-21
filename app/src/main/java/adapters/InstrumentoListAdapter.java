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
import entities.Instrumento;

/**
 * Created by LP18 on 16/06/2017.
 */

public class InstrumentoListAdapter extends ArrayAdapter<Instrumento> {

    private Context context;
    private List<Instrumento> instrumentos;

    public InstrumentoListAdapter(Context context, List<Instrumento> instrumentos){
        super(context, R.layout.instrumento_list_layout, instrumentos);
        this.context = context;
        this.instrumentos = instrumentos;

    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        View view = layoutInflater.inflate(R.layout.instrumento_list_layout, parent, false);

        Instrumento instrumento = instrumentos.get(position);

        TextView textViewInstrumento = (TextView) view.findViewById(R.id.textViewInst);
        textViewInstrumento.setText(String.valueOf(instrumento.getNome()));

        TextView textViewQuantidade = (TextView) view.findViewById(R.id.textViewQtd);
        textViewQuantidade.setText(String.valueOf(instrumento.getQuantidade()));

        return view;
    }
}
