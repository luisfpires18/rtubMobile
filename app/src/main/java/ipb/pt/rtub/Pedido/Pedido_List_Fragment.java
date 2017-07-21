package ipb.pt.rtub.Pedido;


import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.List;

import adapters.PedidoListAdapter;
import entities.Pedido;
import ipb.pt.rtub.R;
import models.PedidoRestClient;

public class Pedido_List_Fragment extends Fragment {

    public Pedido_List_Fragment() {

    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        getActivity().setTitle("Pedidos");
        new HttpRequestAsk().execute();
    }

    private class HttpRequestAsk extends AsyncTask<Void, Void, List<Pedido>>{

        @Override
        protected List<Pedido> doInBackground(Void... params) {
            PedidoRestClient pedidoRestClient = new PedidoRestClient();
            return pedidoRestClient.findAll();
        }

        FragmentManager fm =getFragmentManager();

        @Override
        protected void onPostExecute(final List<Pedido> pedidos) {
            final ListView listViewPedido = (ListView) getView().findViewById(R.id.listViewPedido);
            ArrayAdapter<Pedido> adapter = new PedidoListAdapter(getActivity(), pedidos);

            listViewPedido.setAdapter(adapter);

            listViewPedido.setOnItemClickListener(new AdapterView.OnItemClickListener() {

                @Override
                public void onItemClick(AdapterView<?> adapter, View view, int i, long l) {
                    Pedido pedido = pedidos.get(i);

                    Fragment fragment = null;

                    switch (adapter.getId()) {
                        case R.id.listViewPedido:
                            fragment = new Pedido_Details_Fragment();
                            break;
                    }
                    if(fragment != null) {
                        Bundle bundle = new Bundle();
                        bundle.putInt("id", pedido.getId());
                        fragment.setArguments(bundle);
                        FragmentTransaction ft = getActivity().getSupportFragmentManager().beginTransaction();
                        ft.replace(R.id.content_main, fragment);
                        ft.commit();
                    }
                }
            });
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.pedido_list, container, false);
    }

}
