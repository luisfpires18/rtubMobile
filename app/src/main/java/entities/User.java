package entities;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.util.Date;

/**
 * Created by LP18 on 23/06/2017.
 */

public class User {

    private String Id;
    private String Email;
    private String PasswordHash;
    private String UserName;
    private String Nome;
    private String NomeTuna;
    private String Categoria;
    private String Telefone;

    public String getId() {
        return Id;
    }

    public void setId(String id) {
        Id = id;
    }

    public String getEmail() {
        return Email;
    }

    public void setEmail(String email) {
        Email = email;
    }

    public String getPasswordHash() {
        return PasswordHash;
    }

    public void setPasswordHash(String passwordHash) {
        PasswordHash = passwordHash;
    }

    public String getUserName() {
        return UserName;
    }

    public void setUserName(String userName) {
        UserName = userName;
    }

    public String getNome() {
        return Nome;
    }

    public void setNome(String nome) {
        Nome = nome;
    }

    public String getNomeTuna() {
        return NomeTuna;
    }

    public void setNomeTuna(String nomeTuna) {
        NomeTuna = nomeTuna;
    }

    public String getCategoria() {
        return Categoria;
    }

    public void setCategoria(String categoria) {
        Categoria = categoria;
    }

    public String getTelefone() {
        return Telefone;
    }

    public void setTelefone(String telefone) {
        Telefone = telefone;
    }
}
