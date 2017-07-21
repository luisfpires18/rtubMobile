package ipb.pt.rtub.Membro;


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

import entities.User;
import ipb.pt.rtub.Membro.Membro_List_Fragment;
import ipb.pt.rtub.R;
import ipb.pt.rtub.StartFragment;
import models.UserRestClient;


public class User_Create_Fragment extends Fragment {

    private Button buttonCreate;
    private EditText editTextEmail, editTextNome, editTextNomeTuna, editTextCategoria, editTextTelefone, editTextPassword ;

    public User_Create_Fragment() {
        // Required empty public constructor
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        getActivity().setTitle("Criar Utilizador");

        buttonCreate = (Button) getView().findViewById(R.id.createButton);
        buttonCreate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editTextEmail = (EditText) getView().findViewById(R.id.editTextEmail);
                editTextNome = (EditText) getView().findViewById(R.id.editTextNome);
                editTextNomeTuna = (EditText) getView().findViewById(R.id.editTextNomeTuna);
                editTextCategoria = (EditText) getView().findViewById(R.id.editTextCategoria);
                editTextTelefone = (EditText) getView().findViewById(R.id.editTextTelefone);
                editTextPassword = (EditText) getView().findViewById(R.id.editTextPassword);


                User u = new User();
                u.setEmail(editTextEmail.getText().toString());
                u.setNome(editTextNome.getText().toString());
                u.setNomeTuna(editTextNomeTuna.getText().toString());
                u.setCategoria(editTextCategoria.getText().toString());
                u.setTelefone(editTextTelefone.getText().toString());
                u.setPasswordHash(editTextPassword.getText().toString());

                new HttpRequestAsk(u).execute();

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

    private class HttpRequestAsk extends AsyncTask<Void, Void, User> {

        private User u;

        public HttpRequestAsk(User user){
            this.u = user;
        }
        @Override
        protected User doInBackground(Void... params) {
            UserRestClient userRestClient = new UserRestClient();
            userRestClient.createUser(u);
            return null;
        }
    }



    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.user_create, container, false);
    }

}
