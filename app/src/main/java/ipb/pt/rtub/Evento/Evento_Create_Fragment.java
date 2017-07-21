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

import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import entities.Evento;
import ipb.pt.rtub.R;
import ipb.pt.rtub.StartFragment;
import models.EventoRestClient;


public class Evento_Create_Fragment extends Fragment {


    private Button buttonCreate;
    private EditText editTextNome, editTextLocal, editTextTipo, editTextData;

    public Evento_Create_Fragment() {

    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        getActivity().setTitle("Criar Evento");

        buttonCreate = (Button) getView().findViewById(R.id.createButtonEvento);
        buttonCreate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editTextNome = (EditText) getView().findViewById(R.id.editTextNome);
                editTextLocal = (EditText) getView().findViewById(R.id.editTextLocal);
                editTextTipo = (EditText) getView().findViewById(R.id.editTextTipo);
                editTextData = (EditText) getView().findViewById(R.id.editTextData);

                Evento e = new Evento();
                e.setNome(editTextNome.getText().toString());
                e.setLocal(editTextLocal.getText().toString());
                e.setTipo(editTextTipo.getText().toString());
                String data = editTextData.getText().toString();


                SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
                Date date = null;
                try {
                    date = formatter.parse(data);
                    e.setData(date);

                } catch (ParseException e1) {
                    e1.printStackTrace();
                }


                new HttpRequestAsk(e).execute();

                Fragment fragment = null;
                switch (v.getId()) {
                    case R.id.createButtonEvento:
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

    private class HttpRequestAsk extends AsyncTask<Void, Void, Evento> {

        private Evento e;

        public HttpRequestAsk(Evento evento){
            this.e = evento;
        }
        @Override
        protected Evento doInBackground(Void... params) {
            EventoRestClient eventoRestClient = new EventoRestClient();
            eventoRestClient.createEvento(e);
            return null;
        }
    }



    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.evento_create, container, false);
    }

}
