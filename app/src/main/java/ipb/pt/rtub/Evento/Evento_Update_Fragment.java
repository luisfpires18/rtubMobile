package ipb.pt.rtub.Evento;


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
import android.widget.TextView;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import entities.Evento;
import ipb.pt.rtub.R;
import ipb.pt.rtub.StartFragment;
import models.EventoRestClient;


public class Evento_Update_Fragment extends Fragment {

    private Button buttonUpdate;
    private int id;
    private String nome, local, tipo, data;

    private EditText editNome, editLocal, editTipo, editData;

    public Evento_Update_Fragment() {
        
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        getActivity().setTitle("Alterar Evento");

        Bundle bundle = getArguments();
        id = (bundle.getInt("id"));
        nome = (bundle.getString("nome"));
        local = (bundle.getString("local"));
        tipo = (bundle.getString("tipo"));
        data = (bundle.getString("data"));

        TextView textViewId = (TextView) getView().findViewById(R.id.textViewId);
        textViewId.setText(String.valueOf(id));

        editNome = (EditText) getView().findViewById(R.id.editNome);
        editNome.setText(nome);

        editLocal = (EditText) getView().findViewById(R.id.editLocal);
        editLocal.setText(local);

        editTipo = (EditText) getView().findViewById(R.id.editTipo);
        editTipo.setText(tipo);

        editData = (EditText) getView().findViewById(R.id.editData);
        editData.setText(data);

        buttonUpdate = (Button) getView().findViewById(R.id.updateConfirmEvento);
        buttonUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Evento e = new Evento();
                e.setId(id);
                e.setNome(editNome.getText().toString());
                e.setLocal(editLocal.getText().toString());
                e.setTipo(editTipo.getText().toString());

                SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
                Date date = null;
                try {
                    date = formatter.parse(editData.getText().toString());
                } catch (ParseException e1) {
                    e1.printStackTrace();
                }
                e.setData(date);


                new HttpRequestAsk(e).execute();
                Fragment fragment = null;

                switch (v.getId()) {
                    case R.id.updateConfirmEvento:
                        fragment = new StartFragment();
                        break;
                }
                if(fragment != null) {
//                    Bundle bundle = new Bundle();
//                    bundle.putInt("id", id);
//                    bundle.putString("nome", nomeEvento);
                    FragmentTransaction ft = getActivity().getSupportFragmentManager().beginTransaction();
                    ft.replace(R.id.content_main, fragment);
                    ft.commit();
                }
            }
        });
    }

    private class HttpRequestAsk extends AsyncTask<Void, Void, Evento> {

        private Evento e;

        public HttpRequestAsk(Evento evento){
            this.e = evento;
        }

        @Override
        protected Evento doInBackground(Void... params) {
            EventoRestClient eventoRestClient = new EventoRestClient();
            return eventoRestClient.updateEvento(e);
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.evento_update, container, false);
    }

}
