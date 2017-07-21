package ipb.pt.rtub.Inscricao;


import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

import entities.Inscricao;
import entities.Membro;
import ipb.pt.rtub.R;
import ipb.pt.rtub.StartFragment;
import models.InscricaoRestClient;


public class Inscricao_Create_Fragment extends Fragment {


    private Button buttonCreate;
    private EditText editTextInstrumento, editTextNotas;
    private int id, idMembro, userLogged;
    private String nome;
    private boolean isAdmin, isMember, inscrito = false;
    private ArrayList<Membro> membros;
    private Spinner spinner;

    public Inscricao_Create_Fragment() {

    }

    public void msgDefault(String msg){
        Toast.makeText(getContext(), msg, Toast.LENGTH_LONG).show();
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Bundle bundle = getArguments();
        id = (bundle.getInt("id"));
        nome = (bundle.getString("nome"));
        userLogged = (bundle.getInt("userLogged"));
        isAdmin = (bundle.getBoolean("isAdmin"));
        isMember = (bundle.getBoolean("isMember"));
        membros =(bundle.getParcelableArrayList("membros"));

        getActivity().setTitle("Fazer Inscrição em " + nome);


        editTextInstrumento = (EditText) getView().findViewById(R.id.editTextInstrumento);
        editTextNotas = (EditText) getView().findViewById(R.id.editTextNotas);

        TextView textViewMembroId = (TextView) getView().findViewById(R.id.MembroID);
        spinner = (Spinner) getView().findViewById(R.id.spinner);

        if(!isAdmin){
            textViewMembroId.setVisibility(View.GONE);
            spinner.setVisibility(View.GONE);
        }else{
            ArrayAdapter<Membro> adapter = new ArrayAdapter<Membro>(getContext(), android.R.layout.simple_list_item_1, membros);
            spinner.setAdapter(adapter);

            spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener(){

                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    idMembro = membros.get(position).getId();
                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {

                }
            });
        }

        buttonCreate = (Button) getView().findViewById(R.id.createButtonInscricao);
        buttonCreate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                Inscricao i = new Inscricao();
                i.setInstrumento(editTextInstrumento.getText().toString());
                i.setNotas(editTextNotas.getText().toString());
                i.setEventoId(id);
                if(isAdmin)
                    i.setMembroId(idMembro);
                else
                    i.setMembroId(userLogged);


                AsyncTask<Void, Void, Inscricao> execute =  new HttpRequestAsk(i).execute();
                try {
                    execute.get();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } catch (ExecutionException e) {
                    e.printStackTrace();
                }

                if(inscrito){
                    AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                    builder.setMessage("Falha na inscrição. Já está inscrito.")
                            .setNegativeButton("Tentar novamente", null)
                            .create()
                            .show();
                }

                Fragment fragment = null;

                switch (v.getId()) {
                    case R.id.createButtonInscricao:
                        fragment = new StartFragment();
                        break;
                }
                if(fragment != null) {
                    Bundle bundle = new Bundle();
                    bundle.putBoolean("isAdmin", isAdmin);
                    bundle.putBoolean("isMember", isMember);
                    fragment.setArguments(bundle);
                    FragmentTransaction ft = getActivity().getSupportFragmentManager().beginTransaction();
                    ft.replace(R.id.content_main, fragment);
                    ft.commit();
                }
            }
        });
    }

    private class HttpRequestAsk extends AsyncTask<Void, Void, Inscricao> {

        private Inscricao i;

        public HttpRequestAsk(Inscricao inscricao){
            this.i = inscricao;
        }


        @Override
        protected Inscricao doInBackground(Void... params) {

            InscricaoRestClient inscricaoRestClient = new InscricaoRestClient();

            List<Inscricao> inDB = inscricaoRestClient.findInscrito(i.getEventoId(), i.getMembroId());

            if(inDB.size() > 0) {
                inscrito = true;
                i = null;
                return i;
            }
            else{
                inscricaoRestClient.createInscricao(i);
                inscrito = false;
                return null;
            }
        }
    }



    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.inscricao_create, container, false);
    }

}
