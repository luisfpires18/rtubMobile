package ipb.pt.rtub.Premio;


import android.content.Intent;
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

import entities.Premio;
import ipb.pt.rtub.MainActivity;
import ipb.pt.rtub.R;
import ipb.pt.rtub.StartFragment;
import models.PremioRestClient;


public class Premio_Create_Fragment extends Fragment {


    private Button buttonCreate;
    private EditText editTextDescricao;
    private int idEvento;

    public Premio_Create_Fragment() {
        // Required empty public constructor
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Bundle bundle = getArguments();
        idEvento = (bundle.getInt("id"));

        getActivity().setTitle("Criar Prémio");

        buttonCreate = (Button) getView().findViewById(R.id.createButton);
        buttonCreate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editTextDescricao = (EditText) getView().findViewById(R.id.editTextDescricao);

                Premio p = new Premio();
                p.setDescricao(editTextDescricao.getText().toString());
                p.setEventoId(idEvento);

                new HttpRequestAsk(p).execute();

                Fragment fragment = null;
                switch (v.getId()) {
                    case R.id.createButton:
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

    private class HttpRequestAsk extends AsyncTask<Void, Void, Premio> {

        private Premio p;

        public HttpRequestAsk(Premio premio){
            this.p = premio;
        }
        @Override
        protected Premio doInBackground(Void... params) {
            PremioRestClient premioRestClient = new PremioRestClient();
            premioRestClient.createPremio(p);
            return null;
        }
    }



    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.premio_create, container, false);
    }

}
