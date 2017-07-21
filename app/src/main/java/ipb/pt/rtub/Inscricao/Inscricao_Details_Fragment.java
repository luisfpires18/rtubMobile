package ipb.pt.rtub.Inscricao;


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

import java.util.Objects;

import entities.Evento;
import entities.Inscricao;
import entities.Membro;
import ipb.pt.rtub.R;
import models.EventoRestClient;
import models.InscricaoRestClient;
import models.MembroRestClient;

public class Inscricao_Details_Fragment extends Fragment {

    private int id, idMembro, idEvento;
    private String instrumento, notas, nomeMembro, nomeEvento, username, email;

    private Button buttonAlterar, buttonApagar;

    private boolean isMember, isAdmin;

    public Inscricao_Details_Fragment() {

    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        getActivity().setTitle("Inscricao");

        Bundle bundle = getArguments();
        id = (bundle.getInt("id"));
        instrumento = (bundle.getString("instrumento"));
        notas = (bundle.getString("notas"));
        idMembro = (bundle.getInt("idMembro"));
        idEvento = (bundle.getInt("idEvento"));
        username = (bundle.getString("username"));
        email = (bundle.getString("email"));
        isAdmin = (bundle.getBoolean("isAdmin"));
        isMember = (bundle.getBoolean("isMember"));
        nomeEvento = (bundle.getString("nomeEvento"));

        new HttpRequestAsk(id).execute();

        buttonAlterar = (Button) getView().findViewById(R.id.buttonAlterarInscricao);
        buttonApagar = (Button) getView().findViewById(R.id.buttonApagarInscricao);

        if(username.equals(email) || isAdmin){
            buttonAlterar.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Fragment fragment = null;

                    switch (v.getId()) {
                        case R.id.buttonAlterarInscricao:
                            fragment = new Inscricao_Update_Fragment();
                            break;
                    }
                    if(fragment != null) {
                        Bundle bundle = new Bundle();
                        bundle.putInt("idInscricao", id);
                        bundle.putString("instrumento", instrumento);
                        bundle.putString("notas", notas);
                        bundle.putInt("idMembro", idMembro);
                        bundle.putInt("idEvento", idEvento);

                        bundle.putBoolean("isAdmin", isAdmin);
                        bundle.putBoolean("isMember", isMember);
                        bundle.putString("nomeEvento", nomeEvento);

                        fragment.setArguments(bundle);
                        FragmentTransaction ft = getActivity().getSupportFragmentManager().beginTransaction();
                        ft.replace(R.id.content_main, fragment);
                        ft.commit();
                    }
                }
            });

            buttonApagar.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Fragment fragment = null;

                    switch (v.getId()) {
                        case R.id.buttonApagarInscricao:
                            fragment = new Inscricao_Delete_Fragment();
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
        }else{
                buttonAlterar.setVisibility(View.GONE);
                buttonApagar.setVisibility(View.GONE);
        }
    }

    private class HttpRequestAsk extends AsyncTask<Void, Void, Inscricao> {

        private int id;

        public HttpRequestAsk(int id) {
            this.id = id;
        }


        @Override
        protected Inscricao doInBackground(Void... params) {
            InscricaoRestClient inscricaoRestClient = new InscricaoRestClient();
            Inscricao i = inscricaoRestClient.find(id);

            EventoRestClient eventoRestClient = new EventoRestClient();
            Evento e = eventoRestClient.find(i.getEventoId());

            MembroRestClient membroRestClient = new MembroRestClient();
            Membro m = membroRestClient.find(i.getMembroId());

            nomeMembro = m.getName() + " (" + m.getNomeTuna() + ")";
            nomeEvento = e.getNome();
            return i;
        }

        @Override
        protected void onPostExecute(final Inscricao inscricao) {

            TextView textView1 = (TextView) getView().findViewById(R.id.textView1);
            TextView textView7 = (TextView) getView().findViewById(R.id.textView7);
            TextView textView9 = (TextView) getView().findViewById(R.id.textView9);

            TextView textViewId = (TextView) getView().findViewById(R.id.textViewId);
            TextView textViewMembro = (TextView) getView().findViewById(R.id.textViewMembro);
            TextView textViewEvento = (TextView) getView().findViewById(R.id.textViewEvento);

            if(isAdmin){
                textViewId.setText(String.valueOf(inscricao.getId()));
                textViewMembro.setText(nomeMembro);
                textViewEvento.setText(nomeEvento);
            }
            else{
                textView1.setVisibility(View.GONE);
                textViewId.setVisibility(View.GONE);

                textView7.setVisibility(View.GONE);
                textViewMembro.setVisibility(View.GONE);

                textView9.setVisibility(View.GONE);
                textViewEvento.setVisibility(View.GONE);
            }


            TextView textViewInstrumento = (TextView) getView().findViewById(R.id.textViewInstrumento);
            textViewInstrumento.setText(inscricao.getInstrumento());

            TextView textViewNotas = (TextView) getView().findViewById(R.id.textViewNotas);
            TextView textView5 = (TextView) getView().findViewById(R.id.textView5);
            if(inscricao.getNotas() != "")
                textViewNotas.setText(inscricao.getNotas());
            else{
                textViewNotas.setVisibility(View.GONE);
                textView5.setVisibility(View.GONE);
            }



        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.inscricao_details, container, false);
    }

}
