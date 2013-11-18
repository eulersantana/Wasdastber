package br.ufba.roomsmanager.model;

import br.ufba.roomsmanager.bean.UsuarioBean;
import java.util.ArrayList;
import java.util.List;
import javax.faces.application.FacesMessage;
import javax.swing.JOptionPane;

public class Login {

    private String usuario;
    private String senha;

    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    public Login(String usuario, String senha) {
        super();
        this.usuario = usuario;
        this.senha = senha;
    }

    public Login() {
        super();
    }

    public boolean validate() {
        UsuarioBean logar = new UsuarioBean();
        ArrayList user = new ArrayList();
        user = logar.buscaUsuario(new Login(getUsuario(), getSenha()));
        senha = logar.md5(senha);
        if (user.get(0) != null) {
            Usuario pessoa = (Usuario) user.get(0);
//            JOptionPane.showConfirmDialog(null, pessoa.getNome()+" e "+pessoa.getSenha()+" Senha escrita:"+senha);
            if (usuario != null && usuario.equals(pessoa.getEmail()) && senha != null && senha.equals(pessoa.getSenha())) {
                return true;
            }
        }
        return false;
    }

    public String toString() {
        return usuario;
    }
}