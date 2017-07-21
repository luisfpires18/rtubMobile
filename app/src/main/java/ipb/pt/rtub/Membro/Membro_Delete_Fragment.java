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

import entities.Membro;
import ipb.pt.rtub.R;
import ipb.pt.rtub.StartFragment;
import models.MembroRestClient;

public class Membro_Delete_Fragment extends Fragment {

    public Membro_Delete_Fragment() {

    }

    private Button btnYes, btnNo;
    private int id;


    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        getActivity().setTitle("Membro");

        Bundle bundle = getArguments();
        id = (bundle.getInt("id"));

        btnYes = (Button) getView().findViewById(R.id.memberYes);
        btnYes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new HttpRequestAsk(id).execute();

                Fragment fragment = null;

                switch (v.getId()) {
                    case R.id.memberYes:
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
        btnNo = (Button) getView().findViewById(R.id.memberNo);
        btnNo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Fragment fragment = null;
                switch (v.getId()) {
                    case R.id.memberNo:
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

    private class HttpRequestAsk extends AsyncTask<Void, Void, Membro> {

        private int id;

        public HttpRequestAsk(int id){
            this.id = id;
        }
        @Override
        protected Membro doInBackground(Void... params) {
            MembroRestClient membroRestClient = new MembroRestClient();
            membroRestClient.delete(id);
            return null;
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.membro_delete, container, false);
    }

}
