package adapters;

import android.content.Context;
import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import ipb.pt.rtub.R;
import entities.Membro;

/**
 * Created by LP18 on 16/06/2017.
 */

public class MembroListAdapter extends ArrayAdapter<Membro> {

    private Context context;
    private List<Membro> membros;

    public MembroListAdapter(Context context, List<Membro> membros){
        super(context, R.layout.membro_list_layout, membros);
        this.context = context;
        this.membros = membros;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        View view = layoutInflater.inflate(R.layout.membro_list_layout, parent, false);

        Membro membro = membros.get(position);

        TextView textViewNome = (TextView) view.findViewById(R.id.textViewNome);
        textViewNome.setText(membro.getName());

        TextView textViewNomeTuna = (TextView) view.findViewById(R.id.textViewNomeTuna);
        textViewNomeTuna.setText(membro.getNomeTuna());


        return view;
    }

}
