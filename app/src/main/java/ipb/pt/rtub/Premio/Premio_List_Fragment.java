package ipb.pt.rtub.Premio;


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

import java.util.ArrayList;
import java.util.List;

import adapters.*;
import entities.*;
import ipb.pt.rtub.Evento.Evento_Create_Fragment;
import ipb.pt.rtub.R;
import models.*;

public class Premio_List_Fragment extends Fragment {

    private Button buttonNew;
    private int idEvento;
    private String nomeEvento;
    private boolean isAdmin;

    public Premio_List_Fragment() {

    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Bundle bundle = getArguments();
        idEvento = (bundle.getInt("id"));
        nomeEvento = (bundle.getString("nome"));
        isAdmin = (bundle.getBoolean("isAdmin"));

        getActivity().setTitle("Premios do Evento " + nomeEvento);
        new HttpRequestAsk().execute();

        if(isAdmin){
            buttonNew = (Button) getView().findViewById(R.id.buttonNovoPremio);
            buttonNew.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Fragment fragment = null;

                    switch (v.getId()) {
                        case R.id.buttonNovoPremio:
                            fragment = new Premio_Create_Fragment();
                            break;
                    }
                    if(fragment != null) {

                        Bundle bundle = new Bundle();
                        bundle.putInt("id", idEvento);
                        fragment.setArguments(bundle);
                        FragmentTransaction ft = getActivity().getSupportFragmentManager().beginTransaction();
                        ft.replace(R.id.content_main, fragment);
                        ft.commit();
                    }
                }
            });
        }else{
            buttonNew = (Button) getView().findViewById(R.id.buttonNovoPremio);
            buttonNew.setVisibility(View.INVISIBLE);
        }
    }

    private class HttpRequestAsk extends AsyncTask<Void, Void, List<Premio>>{

        @Override
        protected List<Premio> doInBackground(Void... params) {
            PremioRestClient premioRestClient = new PremioRestClient();
            return premioRestClient.findPremios(idEvento);
        }

        FragmentManager fm =getFragmentManager();

        @Override
        protected void onPostExecute(final List<Premio> premios) {
            final ListView listViewPremio = (ListView) getView().findViewById(R.id.listViewPremio);
            ArrayAdapter<Premio> adapter = new PremioListAdapter(getActivity(), premios);

            listViewPremio.setAdapter(adapter);
            if(isAdmin){
                listViewPremio.setOnItemClickListener(new AdapterView.OnItemClickListener() {

                    @Override
                    public void onItemClick(AdapterView<?> adapter, View view, int i, long l) {
                        Premio premio = premios.get(i);

                        Fragment fragment = null;

                        switch (adapter.getId()) {
                            case R.id.listViewPremio:
                                fragment = new Premio_Delete_Fragment();
                                break;
                        }
                        if(fragment != null) {
                            Bundle bundle = new Bundle();
                            bundle.putInt("id", premio.getId());
                            fragment.setArguments(bundle);
                            FragmentTransaction ft = getActivity().getSupportFragmentManager().beginTransaction();
                            ft.replace(R.id.content_main, fragment);
                            ft.commit();
                        }
                    }
                });
            }

        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.premio_list, container, false);
    }

}
