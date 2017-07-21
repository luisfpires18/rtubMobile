package ipb.pt.rtub.Pedido;


import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Date;

import entities.Pedido;
import ipb.pt.rtub.R;
import models.PedidoRestClient;

public class Pedido_Details_Fragment extends Fragment {

    public Pedido_Details_Fragment() {

    }

    private Button buttonDelete;
    private int id;
    private PedidoRestClient pedidoRestClient = new PedidoRestClient();

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        getActivity().setTitle("Pedido");

        Bundle bundle = getArguments();
        id = (bundle.getInt("id"));
        new HttpRequestAsk(id).execute();

        buttonDelete = (Button) getView().findViewById(R.id.buttonDeleteRequest);
        buttonDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Fragment fragment = null;

                switch (v.getId()) {
                    case R.id.buttonDeleteRequest:
                        fragment = new Pedido_Delete_Fragment();
                        break;
                }
                if(fragment != null) {
                    Bundle bundle = new Bundle();
                    bundle.putInt("id", id);

                    fragment.setArguments(bundle);
                    FragmentTransaction ft = getActivity().getSupportFragmentManager().beginTransaction();
                    ft.replace(R.id.content_main, fragment);
                    ft.commit();
                }
            }
        });
    }

    private class HttpRequestAsk extends AsyncTask<Void, Void, Pedido> {

        private int id;

        public HttpRequestAsk(int id) {
            this.id = id;
        }


        @Override
        protected Pedido doInBackground(Void... params) {
            return pedidoRestClient.find(id);
        }

        @Override
        protected void onPostExecute(final Pedido pedido) {

            TextView textViewId = (TextView) getView().findViewById(R.id.textViewId);
            textViewId.setText(String.valueOf(pedido.getId()));

            TextView textViewTipo = (TextView) getView().findViewById(R.id.textViewTipo);
            textViewTipo.setText(String.valueOf(pedido.getTipo()));

            TextView textViewDesc = (TextView) getView().findViewById(R.id.textViewDesc);
            textViewDesc.setText(String.valueOf(pedido.getDescricao()));

            TextView textViewNome = (TextView) getView().findViewById(R.id.textViewNome);
            textViewNome.setText(String.valueOf(pedido.getNome()));

            TextView textViewContacto = (TextView) getView().findViewById(R.id.textViewContato);
            textViewContacto.setText(String.valueOf(pedido.getContacto()));

        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.pedido_details, container, false);
    }

}
