package entities;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by LP18 on 17/06/2017.
 */

public class Membro implements Parcelable{

    private int id;
    private String name, nomeTuna, categoria, telefone, email;
    boolean isAdmin;

    public Membro(){

    }
    protected Membro(Parcel in) {
        id = in.readInt();
        name = in.readString();
        nomeTuna = in.readString();
        categoria = in.readString();
        telefone = in.readString();
        email = in.readString();
        isAdmin = in.readByte() != 0;
    }

    public static final Creator<Membro> CREATOR = new Creator<Membro>() {
        @Override
        public Membro createFromParcel(Parcel in) {
            return new Membro(in);
        }

        @Override
        public Membro[] newArray(int size) {
            return new Membro[size];
        }
    };

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNomeTuna() {
        return nomeTuna;
    }

    public void setNomeTuna(String nomeTuna) {
        this.nomeTuna = nomeTuna;
    }

    public String getCategoria() {
        return categoria;
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }

    public String getTelefone() {
        return telefone;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public boolean isAdmin() {
        return isAdmin;
    }

    public void setAdmin(boolean admin) {
        isAdmin = admin;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeString(name);
    }

    @Override
    public String toString(){
        return name + " (" + nomeTuna + ")";
    }
}
