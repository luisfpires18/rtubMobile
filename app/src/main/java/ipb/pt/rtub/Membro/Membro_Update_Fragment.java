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
import android.widget.TextView;

import entities.Membro;
import ipb.pt.rtub.R;
import ipb.pt.rtub.StartFragment;
import models.MembroRestClient;


/**
 * A simple {@link Fragment} subclass.
 */
public class Membro_Update_Fragment extends Fragment {

    private Button buttonUpdate;
    private int id;
    private String nome, nomeTuna, categoria, telefone, email;
    private boolean isAdmin;

    private EditText editTextNome, editTextNomeTuna, editTextCategoria, editTextTelefone;
    private Switch switchAdmin;
    
    public Membro_Update_Fragment() {
        
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        getActivity().setTitle("Membro Update");

        Bundle bundle = getArguments();
        id = (bundle.getInt("id"));
        nome = (bundle.getString("nome"));
        nomeTuna = (bundle.getString("nomeTuna"));
        categoria = (bundle.getString("categoria"));
        telefone = (bundle.getString("telefone"));
        email = (bundle.getString("email"));
        isAdmin = (bundle.getBoolean("isAdmin"));

        TextView textViewId = (TextView) getView().findViewById(R.id.textViewId);
        textViewId.setText(String.valueOf(id));

        editTextNome = (EditText) getView().findViewById(R.id.editNome);
        editTextNome.setText(nome);

        editTextNomeTuna = (EditText) getView().findViewById(R.id.editNomeTuna);
        editTextNomeTuna.setText(nomeTuna);

        editTextCategoria = (EditText) getView().findViewById(R.id.editCategoria);
        editTextCategoria.setText(categoria);

        editTextTelefone = (EditText) getView().findViewById(R.id.editTelefone);
        editTextTelefone.setText(telefone);

        switchAdmin = (Switch) getView().findViewById(R.id.switchUpdate);
        switchAdmin.setChecked(isAdmin);

        TextView textViewEmail = (TextView) getView().findViewById(R.id.textViewEmail);
        textViewEmail.setText(email);

        buttonUpdate = (Button) getView().findViewById(R.id.updateConfirm);
        buttonUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Membro m = new Membro();
                m.setId(id);
                m.setName(editTextNome.getText().toString());
                m.setNomeTuna(editTextNomeTuna.getText().toString());
                m.setCategoria(editTextCategoria.getText().toString());
                m.setTelefone(editTextTelefone.getText().toString());
                m.setEmail(email);
                m.setAdmin(switchAdmin.isChecked());

                new HttpRequestAsk(m).execute();
                Fragment fragment = null;

                switch (v.getId()) {
                    case R.id.updateConfirm:
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
            return membroRestClient.updateMembro(m);
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.membro_update, container, false);
    }

}
