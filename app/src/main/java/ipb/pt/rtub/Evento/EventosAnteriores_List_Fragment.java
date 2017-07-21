package ipb.pt.rtub.Evento;


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

import java.text.SimpleDateFormat;
import java.util.List;

import adapters.EventoListAdapter;
import entities.Evento;
import ipb.pt.rtub.Premio.Premio_List_Fragment;
import ipb.pt.rtub.R;
import models.EventoRestClient;

public class EventosAnteriores_List_Fragment extends Fragment {

    private boolean isAdmin = false;

    public EventosAnteriores_List_Fragment() {

    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        getActivity().setTitle("Eventos Realizados");
        new HttpRequestAsk().execute();

        Bundle bundle = getArguments();
        if(bundle != null){
            isAdmin = (bundle.getBoolean("isAdmin"));
        }
    }

    private class HttpRequestAsk extends AsyncTask<Void, Void, List<Evento>>{

        @Override
        protected List<Evento> doInBackground(Void... params) {
            EventoRestClient eventoRestClient = new EventoRestClient();
            return eventoRestClient.eventosAnteriores();
        }

        FragmentManager fm =getFragmentManager();

        @Override
        protected void onPostExecute(final List<Evento> eventos) {
            final ListView listViewEvento = (ListView) getView().findViewById(R.id.listViewEvento);
            ArrayAdapter<Evento> adapter = new EventoListAdapter(getActivity(), eventos);

            listViewEvento.setAdapter(adapter);



            listViewEvento.setOnItemClickListener(new AdapterView.OnItemClickListener() {

                @Override
                public void onItemClick(AdapterView<?> adapter, View view, int i, long l) {
                    Evento evento = eventos.get(i);

                    Fragment fragment = null;

                    switch (adapter.getId()) {
                        case R.id.listViewEvento:
                            fragment = new Premio_List_Fragment();
                            break;
                    }
                    if(fragment != null) {
                        Bundle bundle = new Bundle();
                        bundle.putInt("id", evento.getId());
                        bundle.putString("nome", evento.getNome());
                        bundle.putBoolean("isAdmin", isAdmin);

                        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
                        String d = formatter.format(evento.getData());
                        bundle.putString("data", d);

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
        return inflater.inflate(R.layout.evento_anteriores_list, container, false);
    }

}
