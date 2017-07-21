package ipb.pt.rtub.Membro;


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
import android.widget.TextView;

import entities.Membro;
import ipb.pt.rtub.R;
import models.MembroRestClient;

public class Membro_Details_Fragment extends Fragment {

    public Membro_Details_Fragment() {

    }

    private Button buttonUpdate, buttonDelete;
    private int id;
    private String nome, nomeTuna, categoria, telefone, email;
    private boolean isAdmin, isMember;

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        getActivity().setTitle("Membro");

        Bundle bundle = getArguments();
        id = (bundle.getInt("id"));
        nome = (bundle.getString("nome"));
        nomeTuna = (bundle.getString("nomeTuna"));
        categoria = (bundle.getString("categoria"));
        telefone = (bundle.getString("telefone"));
        email = (bundle.getString("email"));
        isAdmin = (bundle.getBoolean("isAdmin"));

        isMember = (bundle.getBoolean("isMember"));

        new HttpRequestAsk(id).execute();

        buttonDelete = (Button) getView().findViewById(R.id.buttonMemberDelete);
        buttonUpdate = (Button) getView().findViewById(R.id.buttonMemberUpdate);

        if(isMember){
            buttonDelete.setVisibility(View.GONE);
            buttonUpdate.setVisibility(View.GONE);
        }else{
            buttonDelete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Fragment fragment = null;

                    switch (v.getId()) {
                        case R.id.buttonMemberDelete:
                            fragment = new Membro_Delete_Fragment();
                            break;
                    }
                    if (fragment != null) {
                        Bundle bundle = new Bundle();
                        bundle.putInt("id", id);

                        fragment.setArguments(bundle);
                        FragmentTransaction ft = getActivity().getSupportFragmentManager().beginTransaction();
                        ft.replace(R.id.content_main, fragment);
                        ft.commit();
                    }
                }
            });


            buttonUpdate.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Fragment fragment = null;

                    switch (v.getId()) {
                        case R.id.buttonMemberUpdate:
                            fragment = new Membro_Update_Fragment();
                            break;
                    }
                    if (fragment != null) {
                        Bundle bundle = new Bundle();
                        bundle.putInt("id", id);
                        bundle.putString("nome", nome);
                        bundle.putString("nomeTuna", nomeTuna);
                        bundle.putString("categoria", categoria);
                        bundle.putString("telefone", telefone);
                        bundle.putString("email", email);
                        bundle.putBoolean("isAdmin", isAdmin);

                        fragment.setArguments(bundle);
                        FragmentTransaction ft = getActivity().getSupportFragmentManager().beginTransaction();
                        ft.replace(R.id.content_main, fragment);
                        ft.commit();
                    }
                }
            });
        }
    }

        private class HttpRequestAsk extends AsyncTask<Void, Void, Membro> {

            private int id;

            public HttpRequestAsk(int id) {
                this.id = id;
            }


            @Override
            protected Membro doInBackground(Void... params) {
                MembroRestClient membroRestClient = new MembroRestClient();
                return membroRestClient.find(id);
            }

            @Override
            protected void onPostExecute(final Membro membro) {

                TextView labelId = (TextView) getView().findViewById(R.id.textView1);
                TextView textViewId = (TextView) getView().findViewById(R.id.textViewId);
                if(!isMember){
                    textViewId.setText(String.valueOf(membro.getId()));
                }else{
                    labelId.setVisibility(View.GONE);
                    textViewId.setVisibility(View.GONE);
                }

                TextView textViewNome = (TextView) getView().findViewById(R.id.textViewNome);
                textViewNome.setText(membro.getName());

                TextView textViewNomeTuna = (TextView) getView().findViewById(R.id.textViewNomeTuna);
                textViewNomeTuna.setText(membro.getNomeTuna());

                TextView textViewCategoria = (TextView) getView().findViewById(R.id.textViewCategoria);
                textViewCategoria.setText(membro.getCategoria());

                TextView textViewTelefone = (TextView) getView().findViewById(R.id.textViewTelefone);
                textViewTelefone.setText(membro.getTelefone());

                TextView textViewEmail = (TextView) getView().findViewById(R.id.textViewEmail);
                textViewEmail.setText(membro.getEmail());

            }
        }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.membro_details, container, false);
    }

}
