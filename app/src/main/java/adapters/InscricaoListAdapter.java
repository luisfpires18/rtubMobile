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

import entities.Membro;
import ipb.pt.rtub.R;
import entities.Inscricao;
import models.MembroRestClient;

/**
 * Created by LP18 on 16/06/2017.
 */

public class InscricaoListAdapter extends ArrayAdapter<Inscricao> {

    private Context context;
    private List<Inscricao> inscricoes;

    public InscricaoListAdapter(Context context, List<Inscricao> inscricoes){
        super(context, R.layout.inscricao_list_layout, inscricoes);
        this.context = context;
        this.inscricoes = inscricoes;

    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        View view = layoutInflater.inflate(R.layout.inscricao_list_layout, parent, false);

        Inscricao inscricao = inscricoes.get(position);

        TextView textViewMembro = (TextView) view.findViewById(R.id.textViewMembro);
        String nm = inscricao.getMembro().getName() + " (" + inscricao.getMembro().getNomeTuna() + ")";
        textViewMembro.setText(nm);

        TextView textViewCategoria = (TextView) view.findViewById(R.id.textViewCategoria);
        textViewCategoria.setText(inscricao.getMembro().getCategoria());

        TextView textViewInstrumento = (TextView) view.findViewById(R.id.textViewInstrumento);
        textViewInstrumento.setText(String.valueOf(inscricao.getInstrumento()));

        return view;
    }
}
