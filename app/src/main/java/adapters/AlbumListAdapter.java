package adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

import entities.Album;
import ipb.pt.rtub.R;
public class AlbumListAdapter extends ArrayAdapter<Album> {

    private Context context;
    private List<Album> albums;

    public AlbumListAdapter(Context context, List<Album> albums){
        super(context, R.layout.album_list_layout, albums);
        this.context = context;
        this.albums = albums;

    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        View view = layoutInflater.inflate(R.layout.album_list_layout, parent, false);

        Album album = albums.get(position);

        TextView textViewNome = (TextView) view.findViewById(R.id.textViewNome);
        textViewNome.setText(album.getNomeAlbum());

        TextView textViewAno = (TextView) view.findViewById(R.id.textViewAno);
        textViewAno.setText(String.valueOf(album.getReleaseDate()));


        return view;
    }
}
