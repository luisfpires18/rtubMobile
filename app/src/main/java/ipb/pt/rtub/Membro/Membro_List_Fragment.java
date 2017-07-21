package ipb.pt.rtub.Membro;


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

import adapters.MembroListAdapter;
import entities.Membro;
import ipb.pt.rtub.R;
import models.MembroRestClient;

public class Membro_List_Fragment extends Fragment {

    private Button buttonNew, buttonNewUser;
    private boolean isAdmin = false, isMember = false;

    public Membro_List_Fragment() {

    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        getActivity().setTitle("Membros");
        new HttpRequestAsk().execute();

        Bundle bundle = getArguments();
        if(bundle != null){
            isAdmin = (bundle.getBoolean("isAdmin"));
            isMember = (bundle.getBoolean("isMember"));
        }


        buttonNew = (Button) getView().findViewById(R.id.buttonNovoMembro);
        buttonNewUser = (Button) getView().findViewById(R.id.buttonNovoUser);
        if(isAdmin){

            buttonNew.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Fragment fragment = null;

                    switch (v.getId()) {
                        case R.id.buttonNovoMembro:
                            fragment = new Membro_Create_Fragment();
                            break;
                    }
                    if(fragment != null) {
                        FragmentTransaction ft = getActivity().getSupportFragmentManager().beginTransaction();
                        ft.replace(R.id.content_main, fragment);
                        ft.commit();
                    }
                }
            });

            buttonNewUser.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Fragment fragment = null;

                    switch (v.getId()) {
                        case R.id.buttonNovoUser:
                            fragment = new User_Create_Fragment();
                            break;
                    }
                    if(fragment != null) {
                        FragmentTransaction ft = getActivity().getSupportFragmentManager().beginTransaction();
                        ft.replace(R.id.content_main, fragment);
                        ft.commit();
                    }
                }
            });
        }else{
            buttonNew.setVisibility(View.GONE);
            buttonNewUser.setVisibility(View.GONE);
        }
    }

    private class HttpRequestAsk extends AsyncTask<Void, Void, List<Membro>>{

        @Override
        protected List<Membro> doInBackground(Void... params) {
            MembroRestClient membroRestClient = new MembroRestClient();
            return membroRestClient.findAll();
        }

        @Override
        protected void onPostExecute(final List<Membro> membros) {
            final ListView listViewMembro = (ListView) getView().findViewById(R.id.listViewMembro);
            ArrayAdapter<Membro> adapter = new MembroListAdapter(getActivity(), membros);

            listViewMembro.setAdapter(adapter);

            listViewMembro.setOnItemClickListener(new AdapterView.OnItemClickListener() {

                @Override
                public void onItemClick(AdapterView<?> adapter, View view, int i, long l) {
                    Membro membro = membros.get(i);

                    Fragment fragment = null;

                    switch (adapter.getId()) {
                        case R.id.listViewMembro:
                            fragment = new Membro_Details_Fragment();
                            break;
                    }
                    if(fragment != null) {
                        Bundle bundle = new Bundle();
                        bundle.putInt("id", membro.getId());
                        bundle.putString("nome", membro.getName());
                        bundle.putString("nomeTuna", membro.getNomeTuna());
                        bundle.putString("categoria", membro.getCategoria());
                        bundle.putString("telefone", membro.getTelefone());
                        bundle.putString("email", membro.getEmail());
                        bundle.putBoolean("isAdmin", membro.isAdmin());
                        bundle.putBoolean("isMember", isMember);
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
        return inflater.inflate(R.layout.membro_list, container, false);
    }

}
