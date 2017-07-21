package ipb.pt.rtub.Instrumento;


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

import entities.Evento;
import entities.Instrumento;
import entities.Membro;
import ipb.pt.rtub.R;
import models.EventoRestClient;
import models.InstrumentoRestClient;
import models.MembroRestClient;

public class Instrumento_Details_Fragment extends Fragment {

    private int id, idEvento, qtd;
    private String nomeInstrumento;
    private String nomeEvento;
    private boolean isAdmin;

    private Button buttonAlterar, buttonApagar;

    public Instrumento_Details_Fragment() {

    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        Bundle bundle = getArguments();
        id = (bundle.getInt("id"));
        nomeInstrumento = (bundle.getString("nome"));
        qtd = (bundle.getInt("qtd"));
        idEvento = (bundle.getInt("idEvento"));
        isAdmin = (bundle.getBoolean("isAdmin"));

        getActivity().setTitle(nomeInstrumento);

        new HttpRequestAsk(id).execute();

        buttonAlterar = (Button) getView().findViewById(R.id.buttonAlterarInstrumento);
        buttonAlterar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Fragment fragment = null;

                switch (v.getId()) {
                    case R.id.buttonAlterarInstrumento:
                        fragment = new Instrumento_Update_Fragment();
                        break;
                }
                if(fragment != null) {
                    Bundle bundle = new Bundle();
                    bundle.putInt("id", id);
                    bundle.putString("nome", nomeInstrumento);
                    bundle.putInt("qtd", qtd);
                    bundle.putInt("idEvento", idEvento);

                    bundle.putString("nomeEvento", nomeEvento);
                    bundle.putBoolean("isAdmin", isAdmin);

                    fragment.setArguments(bundle);
                    FragmentTransaction ft = getActivity().getSupportFragmentManager().beginTransaction();
                    ft.replace(R.id.content_main, fragment);
                    ft.commit();
                }
            }
        });

        buttonApagar = (Button) getView().findViewById(R.id.buttonApagarInstrumento);
        buttonApagar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Fragment fragment = null;

                switch (v.getId()) {
                    case R.id.buttonApagarInstrumento:
                        fragment = new Instrumento_Delete_Fragment();
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
    }

    private class HttpRequestAsk extends AsyncTask<Void, Void, Instrumento> {

        private int id;

        public HttpRequestAsk(int id) {
            this.id = id;
        }


        @Override
        protected Instrumento doInBackground(Void... params) {
            InstrumentoRestClient instrumentoRestClient = new InstrumentoRestClient();
            Instrumento i = instrumentoRestClient.find(id);

            EventoRestClient eventoRestClient = new EventoRestClient();
            Evento e = eventoRestClient.find(i.getEventoId());

            nomeEvento = e.getNome();
            return i;
        }

        @Override
        protected void onPostExecute(final Instrumento instrumento) {

            TextView labelId = (TextView) getView().findViewById(R.id.textView1);
            TextView textViewId = (TextView) getView().findViewById(R.id.textViewIdInstrumento);
            if(isAdmin){
                textViewId.setText(String.valueOf(instrumento.getId()));
            }else{
                labelId.setVisibility(View.GONE);
                textViewId.setVisibility(View.GONE);
            }

            TextView textViewNomeInstrumento = (TextView) getView().findViewById(R.id.textViewNomeInstrumento);
            textViewNomeInstrumento.setText(instrumento.getNome());

            TextView textViewQuantidade = (TextView) getView().findViewById(R.id.textViewQuantidade);
            textViewQuantidade.setText(String.valueOf(instrumento.getQuantidade()));

            TextView textViewEventoInstrumento = (TextView) getView().findViewById(R.id.textViewEventoInstrumento);
            textViewEventoInstrumento.setText(nomeEvento);

        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.instrumento_details, container, false);
    }

}
