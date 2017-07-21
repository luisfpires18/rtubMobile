package ipb.pt.rtub.Premio;


import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import entities.Premio;
import ipb.pt.rtub.R;
import ipb.pt.rtub.StartFragment;
import models.PremioRestClient;

public class Premio_Delete_Fragment extends Fragment {

    public Premio_Delete_Fragment() {

    }

    private Button btnYes, btnNo;
    private int id;


    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        getActivity().setTitle("Premio");

        Bundle bundle = getArguments();
        id = (bundle.getInt("id"));

        btnYes = (Button) getView().findViewById(R.id.premioYes);
        btnYes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new HttpRequestAsk(id).execute();

                Fragment fragment = null;

                switch (v.getId()) {
                    case R.id.premioYes:
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
        btnNo = (Button) getView().findViewById(R.id.premioNo);
        btnNo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Fragment fragment = null;
                switch (v.getId()) {
                    case R.id.premioNo:
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

        private int id;

        public HttpRequestAsk(int id){
            this.id = id;
        }
        @Override
        protected Premio doInBackground(Void... params) {
            PremioRestClient premioRestClient = new PremioRestClient();
            premioRestClient.delete(id);
            return null;
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.premio_delete, container, false);
    }

}
