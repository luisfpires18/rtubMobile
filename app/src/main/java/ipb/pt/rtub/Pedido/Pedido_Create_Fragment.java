package ipb.pt.rtub.Pedido;


import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import entities.Pedido;
import ipb.pt.rtub.MainActivity;
import ipb.pt.rtub.R;
import ipb.pt.rtub.StartFragment;
import models.PedidoRestClient;


public class Pedido_Create_Fragment extends Fragment {


    private Button buttonCreate;
    private EditText editTextTipo, editTextDescricao, editTextNome, editTextContacto;

    public Pedido_Create_Fragment() {
        // Required empty public constructor
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        getActivity().setTitle("Pedidos");

        buttonCreate = (Button) getView().findViewById(R.id.createButton);
        buttonCreate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editTextTipo = (EditText) getView().findViewById(R.id.editTextTipo);
                editTextDescricao = (EditText) getView().findViewById(R.id.editTextDescricao);
                editTextNome = (EditText) getView().findViewById(R.id.editTextNome);
                editTextContacto = (EditText) getView().findViewById(R.id.editTextContacto);

                Pedido p = new Pedido();
                p.setTipo(editTextTipo.getText().toString());
                p.setDescricao(editTextDescricao.getText().toString());
                p.setNome(editTextNome.getText().toString());
                p.setContacto(editTextContacto.getText().toString());
                new HttpRequestAsk(p).execute();

                Fragment fragment = null;
                switch (v.getId()) {
                    case R.id.createButton:
                        fragment = new StartFragment();
                        break;
                }
                if(fragment != null) {
                    FragmentTransaction ft = getActivity().getSupportFragmentManager().beginTransaction();
                    ft.replace(R.id.content_main, fragment);
                    ft.commit();
                }

            }
        });
    }

    private class HttpRequestAsk extends AsyncTask<Void, Void, Pedido> {

        private Pedido p;

        public HttpRequestAsk(Pedido pedido){
            this.p = pedido;
        }
        @Override
        protected Pedido doInBackground(Void... params) {
            PedidoRestClient pedidoRestClient = new PedidoRestClient();
            pedidoRestClient.createPedido(p);
            return null;
        }
    }



    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.pedido_create, container, false);
    }

}
