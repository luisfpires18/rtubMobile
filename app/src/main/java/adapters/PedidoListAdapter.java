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
import entities.Pedido;

/**
 * Created by LP18 on 16/06/2017.
 */

public class PedidoListAdapter extends ArrayAdapter<Pedido> {

    private Context context;
    private List<Pedido> pedidos;

    public PedidoListAdapter(Context context, List<Pedido> pedidos){
        super(context, R.layout.pedido_list_layout, pedidos);
        this.context = context;
        this.pedidos = pedidos;

    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        View view = layoutInflater.inflate(R.layout.pedido_list_layout, parent, false);

        Pedido pedido = pedidos.get(position);

        TextView textViewTipo = (TextView) view.findViewById(R.id.textViewTipo);
        textViewTipo.setText(pedido.getTipo());

        TextView textViewDescricao = (TextView) view.findViewById(R.id.textViewDescricao);
        textViewDescricao.setText(pedido.getDescricao());

        TextView textViewNome = (TextView) view.findViewById(R.id.textViewNome);
        textViewNome.setText(pedido.getNome());

        TextView textViewContacto = (TextView) view.findViewById(R.id.textViewContacto);
        textViewContacto.setText(pedido.getContacto());


        return view;
    }
}
