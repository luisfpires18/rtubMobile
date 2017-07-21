package ipb.pt.rtub.Membro;


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
import android.widget.Switch;

import entities.Membro;
import entities.Pedido;
import ipb.pt.rtub.R;
import ipb.pt.rtub.StartFragment;
import models.MembroRestClient;
import models.PedidoRestClient;


public class Membro_Create_Fragment extends Fragment {

    private Button buttonCreate;
    private EditText editTextNome, editTextNomeTuna, editTextCategoria, editTextContacto, editTextEmail;

    public Membro_Create_Fragment() {
        // Required empty public constructor
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        getActivity().setTitle("Criar Membro");

        buttonCreate = (Button) getView().findViewById(R.id.createButton);
        buttonCreate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editTextNome = (EditText) getView().findViewById(R.id.editTextNome);
                editTextNomeTuna = (EditText) getView().findViewById(R.id.editTextNomeTuna);
                editTextCategoria = (EditText) getView().findViewById(R.id.editTextCategoria);
                editTextContacto = (EditText) getView().findViewById(R.id.editTextContacto);
                editTextEmail = (EditText) getView().findViewById(R.id.editTextEmail);

                Membro m = new Membro();
                m.setName(editTextNome.getText().toString());
                m.setNomeTuna(editTextNomeTuna.getText().toString());
                m.setCategoria(editTextCategoria.getText().toString());
                m.setTelefone(editTextContacto.getText().toString());
                m.setEmail(editTextEmail.getText().toString());
                m.setAdmin(false);
                new HttpRequestAsk(m).execute();

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

    private class HttpRequestAsk extends AsyncTask<Void, Void, Membro> {

        private Membro m;

        public HttpRequestAsk(Membro membro){
            this.m = membro;
        }
        @Override
        protected Membro doInBackground(Void... params) {
            MembroRestClient membroRestClient = new MembroRestClient();
            membroRestClient.createMembro(m);
            return null;
        }
    }



    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.membro_create, container, false);
    }

}
