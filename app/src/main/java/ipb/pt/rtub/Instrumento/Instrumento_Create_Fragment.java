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
import android.widget.Spinner;

import entities.Instrumento;
import ipb.pt.rtub.Evento.Evento_List_Fragment;
import ipb.pt.rtub.R;
import ipb.pt.rtub.StartFragment;
import models.InstrumentoRestClient;


public class Instrumento_Create_Fragment extends Fragment {


    private Button buttonCreate;
    private EditText editTextInstrumento, editTextQuantidade;
    private int id;
    private String nome;

    public Instrumento_Create_Fragment() {

    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Bundle bundle = getArguments();
        id = (bundle.getInt("id"));
        nome = (bundle.getString("nome"));

        getActivity().setTitle("Adicionar instrumento em " + nome);

        buttonCreate = (Button) getView().findViewById(R.id.createButtonInstrumento);
        buttonCreate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editTextInstrumento = (EditText) getView().findViewById(R.id.editTextInstrumento);
                editTextQuantidade = (EditText) getView().findViewById(R.id.editTextQuantidade);

                Instrumento i = new Instrumento();
                i.setNome(editTextInstrumento.getText().toString());
                i.setQuantidade(Integer.parseInt(editTextQuantidade.getText().toString()));
                i.setEventoId(id);

                new HttpRequestAsk(i).execute();

                Fragment fragment = null;
                switch (v.getId()) {
                    case R.id.createButtonInstrumento:
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

    private class HttpRequestAsk extends AsyncTask<Void, Void, Instrumento> {

        private Instrumento i;

        public HttpRequestAsk(Instrumento instrumento){
            this.i = instrumento;
        }
        @Override
        protected Instrumento doInBackground(Void... params) {
            InstrumentoRestClient instrumentoRestClient = new InstrumentoRestClient();
            instrumentoRestClient.createInstrumento(i);
            return null;
        }
    }



    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.instrumento_create, container, false);
    }

}
