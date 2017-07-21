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
import android.widget.EditText;
import android.widget.Switch;
import android.widget.TextView;

import entities.Inscricao;
import entities.Membro;
import ipb.pt.rtub.Evento.Evento_List_Fragment;
import ipb.pt.rtub.Membro.Membro_List_Fragment;
import ipb.pt.rtub.R;
import ipb.pt.rtub.StartFragment;
import models.EventoRestClient;
import models.InscricaoRestClient;
import models.MembroRestClient;


public class Inscricao_Update_Fragment extends Fragment {

    private Button buttonUpdate;
    private int idInscricao, idMembro, idEvento;
    private String instrumento, notas, nomeEvento;
    private boolean isAdmin;

    private EditText editInstrumento, editNotas;

    public Inscricao_Update_Fragment() {
        
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        Bundle bundle = getArguments();
        idInscricao = (bundle.getInt("idInscricao"));
        instrumento = (bundle.getString("instrumento"));
        notas = (bundle.getString("notas"));
        idMembro = (bundle.getInt("idMembro"));
        idEvento = (bundle.getInt("idEvento"));
        nomeEvento = (bundle.getString("nomeEvento"));
        isAdmin = (bundle.getBoolean("isAdmin"));

        getActivity().setTitle("Alterar Inscrição em " + nomeEvento);

        TextView textView1 = (TextView) getView().findViewById(R.id.textView1);
        TextView textView7 = (TextView) getView().findViewById(R.id.textView7);
        TextView textView9 = (TextView) getView().findViewById(R.id.textView9);

        TextView textViewId = (TextView) getView().findViewById(R.id.textViewId);
        TextView textViewIdMembro = (TextView) getView().findViewById(R.id.textViewIdMembro);
        TextView textViewIdEvento = (TextView) getView().findViewById(R.id.textViewIdEvento);

        if(isAdmin){
            textViewId.setText(String.valueOf(idInscricao));
            textViewIdMembro.setText(String.valueOf(idMembro));
            textViewIdEvento.setText(String.valueOf(idEvento));
        }
        else{
            textView1.setVisibility(View.GONE);
            textViewId.setVisibility(View.GONE);

            textView7.setVisibility(View.GONE);
            textViewIdMembro.setVisibility(View.GONE);

            textView9.setVisibility(View.GONE);
            textViewIdEvento.setVisibility(View.GONE);
        }

        editInstrumento = (EditText) getView().findViewById(R.id.editInstrumento);
        editInstrumento.setText(instrumento);

        editNotas = (EditText) getView().findViewById(R.id.editNotas);
        editNotas.setText(notas);



        buttonUpdate = (Button) getView().findViewById(R.id.updateConfirmInscricao);
        buttonUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Inscricao i = new Inscricao();
                i.setId(idInscricao);
                i.setMembroId(idMembro);
                i.setEventoId(idEvento);
                i.setInstrumento(editInstrumento.getText().toString());
                i.setNotas(editNotas.getText().toString());

                new HttpRequestAsk(i).execute();
                Fragment fragment = null;

                switch (v.getId()) {
                    case R.id.updateConfirmInscricao:
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

    private class HttpRequestAsk extends AsyncTask<Void, Void, Inscricao> {

        private Inscricao i;

        public HttpRequestAsk(Inscricao inscricao){
            this.i = inscricao;
        }

        @Override
        protected Inscricao doInBackground(Void... params) {
            InscricaoRestClient inscricaoRestClient = new InscricaoRestClient();
            EventoRestClient eventoRestClient = new EventoRestClient();
            MembroRestClient membroRestClient = new MembroRestClient();

            i.setEvento(eventoRestClient.find(i.getEventoId()));
            i.setMembro(membroRestClient.find(i.getMembroId()));


            return inscricaoRestClient.updateInscricao(i);
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.inscricao_update, container, false);
    }

}
