package ipb.pt.rtub.Instrumento;


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
import android.widget.Button;
import android.widget.ListView;

import java.util.List;

import adapters.InstrumentoListAdapter;
import entities.Evento;
import entities.Instrumento;
import ipb.pt.rtub.R;
import models.EventoRestClient;
import models.InstrumentoRestClient;

public class Instrumento_List_Fragment extends Fragment {

    private int id;
    private String nome;
    private Button buttonNew;
    private boolean isAdmin;

    public Instrumento_List_Fragment() {

    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Bundle bundle = getArguments();
        id = (bundle.getInt("id"));
        nome = (bundle.getString("nome"));
        isAdmin = (bundle.getBoolean("isAdmin"));
        getActivity().setTitle("Instrumentos do Evento " + nome);
        new HttpRequestAsk().execute();

        buttonNew = (Button) getView().findViewById(R.id.buttonNovoInst);
        buttonNew.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Fragment fragment = null;

                switch (v.getId()) {
                    case R.id.buttonNovoInst:
                        fragment = new Instrumento_Create_Fragment();
                        break;
                }
                if(fragment != null) {
                    Bundle bundle = new Bundle();
                    bundle.putInt("id", id);
                    bundle.putString("nome", nome);

                    fragment.setArguments(bundle);
                    FragmentTransaction ft = getActivity().getSupportFragmentManager().beginTransaction();
                    ft.replace(R.id.content_main, fragment);
                    ft.commit();
                }
            }
        });
    }

    private class HttpRequestAsk extends AsyncTask<Void, Void, List<Instrumento>>{

        @Override
        protected List<Instrumento> doInBackground(Void... params) {
            EventoRestClient eventoRestClient = new EventoRestClient();
            Evento e;

            InstrumentoRestClient instrumentoRestClient = new InstrumentoRestClient();
            List<Instrumento> instrumentos = instrumentoRestClient.findInstrumentos(id);

            for(int i = 0; i < instrumentos.size(); i++){

                e = eventoRestClient.find(instrumentos.get(i).getEventoId());
                instrumentos.get(i).setEvento(e);
            }
            return instrumentos;
        }

        FragmentManager fm =getFragmentManager();

        @Override
        protected void onPostExecute(final List<Instrumento> instrumentos) {
            final ListView listViewInstrumento = (ListView) getView().findViewById(R.id.listViewInstrumento);
            ArrayAdapter<Instrumento> adapter = new InstrumentoListAdapter(getActivity(), instrumentos);

            listViewInstrumento.setAdapter(adapter);

            listViewInstrumento.setOnItemClickListener(new AdapterView.OnItemClickListener() {

                @Override
                public void onItemClick(AdapterView<?> adapter, View view, int i, long l) {
                    Instrumento instrumento = instrumentos.get(i);

                    Fragment fragment = null;

                    switch (adapter.getId()) {
                        case R.id.listViewInstrumento:
                            fragment = new Instrumento_Details_Fragment();
                            break;
                    }
                    if(fragment != null) {
                        Bundle bundle = new Bundle();
                        bundle.putInt("id", instrumento.getId());
                        bundle.putString("nome", instrumento.getNome());
                        bundle.putInt("qtd", instrumento.getQuantidade());
                        bundle.putInt("idEvento", instrumento.getEventoId());
                        bundle.putBoolean("isAdmin", isAdmin);
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
        return inflater.inflate(R.layout.instrumento_list, container, false);
    }

}
