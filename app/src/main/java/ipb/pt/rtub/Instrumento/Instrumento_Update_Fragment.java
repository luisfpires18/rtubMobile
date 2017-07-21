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
import android.widget.EditText;
import android.widget.TextView;

import entities.Instrumento;
import ipb.pt.rtub.Evento.Evento_List_Fragment;
import ipb.pt.rtub.R;
import ipb.pt.rtub.StartFragment;
import models.InstrumentoRestClient;


public class Instrumento_Update_Fragment extends Fragment {

    private Button buttonUpdate;
    private int id, idEvento, qtd;
    private String nomeInstrumento, nomeEvento;
    private boolean isAdmin;

    private EditText editTextInstrumento, editTextNomeQtd;

    public Instrumento_Update_Fragment() {
        
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


        Bundle bundle = getArguments();
        id = (bundle.getInt("id"));
        nomeInstrumento = (bundle.getString("nome"));
        qtd = (bundle.getInt("qtd"));
        idEvento = (bundle.getInt("idEvento"));

        nomeEvento = (bundle.getString("nomeEvento"));
        isAdmin = (bundle.getBoolean("isAdmin"));

        getActivity().setTitle("Alterar Instrumento do evento " + nomeEvento);

        TextView textView1 = (TextView) getView().findViewById(R.id.textView1);
        TextView textViewId = (TextView) getView().findViewById(R.id.textViewId);

        TextView textView9 = (TextView) getView().findViewById(R.id.textView9);
        TextView textViewIdEvento = (TextView) getView().findViewById(R.id.textViewIdEvento);

        if(isAdmin){
            textViewId.setText(String.valueOf(id));
            textViewIdEvento.setText(nomeEvento);
        }else{
            textView1.setVisibility(View.GONE);
            textView9.setVisibility(View.GONE);

            textViewId.setVisibility(View.GONE);
            textViewIdEvento.setVisibility(View.GONE);
        }



        editTextInstrumento = (EditText) getView().findViewById(R.id.editInst);
        editTextInstrumento.setText(nomeInstrumento);

        editTextNomeQtd = (EditText) getView().findViewById(R.id.editQtd);
        editTextNomeQtd.setText(String.valueOf(qtd));



        buttonUpdate = (Button) getView().findViewById(R.id.updateConfirmInstrumento);
        buttonUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Instrumento i = new Instrumento();
                i.setId(id);
                i.setEventoId(idEvento);
                i.setNome(editTextInstrumento.getText().toString());
                i.setQuantidade(Integer.parseInt(editTextNomeQtd.getText().toString()));

                new HttpRequestAsk(i).execute();
                Fragment fragment = null;

                switch (v.getId()) {
                    case R.id.updateConfirmInstrumento:
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

    private class HttpRequestAsk extends AsyncTask<Void, Void, Instrumento> {

        private Instrumento i;

        public HttpRequestAsk(Instrumento instrumento){
            this.i = instrumento;
        }

        @Override
        protected Instrumento doInBackground(Void... params) {
            InstrumentoRestClient instrumentoRestClient = new InstrumentoRestClient();
            return instrumentoRestClient.updateInstrumento(i);
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.instrumento_update, container, false);
    }

}
