package ipb.pt.rtub.Inscricao;


import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Parcelable;
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

import java.util.ArrayList;
import java.util.List;

import adapters.InscricaoListAdapter;
import entities.Evento;
import entities.Inscricao;
import entities.Membro;
import ipb.pt.rtub.R;
import models.EventoRestClient;
import models.InscricaoRestClient;
import models.MembroRestClient;

public class Inscricao_List_Fragment extends Fragment {

    private int id;
    private String nome, username, email;
    private boolean isMember, isAdmin;


    public Inscricao_List_Fragment() {

    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Bundle bundle = getArguments();
        id = (bundle.getInt("id"));
        nome = (bundle.getString("nome"));
        isAdmin = (bundle.getBoolean("isAdmin"));
        isMember = (bundle.getBoolean("isMember"));
        username = (bundle.getString("username"));

        getActivity().setTitle("Inscritos em " + nome);

        new HttpRequestAsk().execute();
    }

    private class HttpRequestAsk extends AsyncTask<Void, Void, List<Inscricao>>{

        @Override
        protected List<Inscricao> doInBackground(Void... params) {
            MembroRestClient membroRestClient = new MembroRestClient();
            Membro m;

            EventoRestClient eventoRestClient = new EventoRestClient();
            Evento e;

            InscricaoRestClient inscricaoRestClient = new InscricaoRestClient();
            List<Inscricao> inscritos = inscricaoRestClient.findInscritos(id);

            for(int i = 0; i < inscritos.size(); i++){
                m = membroRestClient.find(inscritos.get(i).getMembroId());
                inscritos.get(i).setMembro(m);

                e = eventoRestClient.find(inscritos.get(i).getEventoId());
                inscritos.get(i).setEvento(e);
            }
            return inscritos;
        }

        FragmentManager fm =getFragmentManager();

        @Override
        protected void onPostExecute(final List<Inscricao> inscricoes) {
            final ListView listViewInscricao = (ListView) getView().findViewById(R.id.listViewInscricao);
            ArrayAdapter<Inscricao> adapter = new InscricaoListAdapter(getActivity(), inscricoes);

            listViewInscricao.setAdapter(adapter);

            listViewInscricao.setOnItemClickListener(new AdapterView.OnItemClickListener() {

                @Override
                public void onItemClick(AdapterView<?> adapter, View view, int i, long l) {
                    Inscricao inscricao = inscricoes.get(i);

                    email = inscricao.getMembro().getEmail();

                    Fragment fragment = null;

                    switch (adapter.getId()) {
                        case R.id.listViewInscricao:
                            fragment = new Inscricao_Details_Fragment();
                            break;
                    }
                    if(fragment != null) {
                        Bundle bundle = new Bundle();
                        bundle.putInt("id", inscricao.getId());
                        bundle.putString("instrumento", inscricao.getInstrumento());
                        bundle.putString("notas", inscricao.getNotas());
                        bundle.putInt("idMembro", inscricao.getMembroId());
                        bundle.putInt("idEvento", inscricao.getEventoId());
                        bundle.putBoolean("isAdmin", isAdmin);
                        bundle.putBoolean("isMember", isMember);
                        bundle.putString("username", username);
                        bundle.putString("email", email);
                        bundle.putString("nomeEvento", nome);

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
        return inflater.inflate(R.layout.inscricao_list, container, false);
    }

}
