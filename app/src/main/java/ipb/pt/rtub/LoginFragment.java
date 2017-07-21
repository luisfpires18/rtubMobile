package ipb.pt.rtub;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import entities.User;

public class LoginFragment extends Fragment {


    public LoginFragment() {
        // Required empty public constructor
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        getActivity().setTitle("Início");

        final EditText etUsername = (EditText) getView().findViewById(R.id.etUsername);
        final EditText etPassword = (EditText) getView().findViewById(R.id.etPassword);
        final Button bLogin = (Button) getView().findViewById(R.id.bSignIn);


        bLogin.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                final String username = etUsername.getText().toString();
                final String password = etPassword.getText().toString();

                Response.Listener<String> responseListener = new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonResponse = new JSONObject(response);
                            boolean success = jsonResponse.getBoolean("success");
                            boolean isAdmin = jsonResponse.getBoolean("isAdmin");
                            boolean isMember = jsonResponse.getBoolean("isMember");

                            int id = jsonResponse.getInt("id");
                            String nome = jsonResponse.getString("nome");
                            String nomeTuna = jsonResponse.getString("nomeTuna");
                            String categoria = jsonResponse.getString("categoria");
                            String telefone = jsonResponse.getString("telefone");

                            if(success){
                                if(isAdmin){
                                    Intent intent = new Intent(getActivity(), AdminActivity.class);
                                    Bundle bundle = new Bundle();
                                    bundle.putString("username", username);
                                    bundle.putBoolean("isAdmin", isAdmin);
                                    bundle.putBoolean("isMember", isMember);
                                    bundle.putInt("id", id);
                                    bundle.putString("nome", nome);
                                    bundle.putString("nomeTuna", nomeTuna);
                                    bundle.putString("categoria", categoria);
                                    bundle.putString("telefone", telefone);
                                    intent.putExtras(bundle);
                                    getActivity().startActivity(intent);
                                }else{
                                    Intent intent = new Intent(getActivity(), MemberActivity.class);
                                    Bundle bundle = new Bundle();
                                    bundle.putString("username", username);
                                    bundle.putBoolean("isAdmin", isAdmin);
                                    bundle.putBoolean("isMember", isMember);
                                    bundle.putInt("id", id);
                                    bundle.putString("nome", nome);
                                    bundle.putString("nomeTuna", nomeTuna);
                                    bundle.putString("categoria", categoria);
                                    bundle.putString("telefone", telefone);
                                    intent.putExtras(bundle);
                                    getActivity().startActivity(intent);
                                }
                            }else{
                                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                                builder.setMessage("Falha no login")
                                        .setNegativeButton("Tentar novamente", null)
                                        .create()
                                        .show();
                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                };

                LoginRequest loginRequest = new LoginRequest(username,password, responseListener);
                RequestQueue queue = Volley.newRequestQueue(getActivity());
                queue.add(loginRequest);
            }
        });

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_login, container, false);
    }

}
