package entities;

import com.fasterxml.jackson.annotation.JsonFormat;

/**
 * Created by LP18 on 02/07/2017.
 */

public class Musica {

    private int id;
    private String titulo;
    private int faixa, idAlbum;
    private String interprete;

    @JsonFormat()
    private byte[] musica;
    private Album album;


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public int getFaixa() {
        return faixa;
    }

    public void setFaixa(int faixa) {
        this.faixa = faixa;
    }

    public String getInterprete() {
        return interprete;
    }

    public void setInterprete(String interprete) {
        this.interprete = interprete;
    }

    public byte[] getMusica() {
        return musica;
    }

    public void setMusica(byte[] musica) {
        this.musica = musica;
    }

    public int getIdAlbum() {
        return idAlbum;
    }

    public void setIdAlbum(int idAlbum) {
        this.idAlbum = idAlbum;
    }

    public Album getAlbum() {
        return album;
    }

    public void setAlbum(Album album) {
        this.album = album;
    }
}
