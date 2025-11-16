package controller;

import dao.UsuarioDAO;
import model.Usuario;
import java.sql.SQLException;

public class UsuarioController {
    private UsuarioDAO dao;
    
    public UsuarioController() {
        dao = new UsuarioDAO();
    }
    
    public boolean autenticar(String login, String senha) throws SQLException {
        return dao.validarLogin(login, senha);
    }
    
    public Usuario buscarPorLogin(String login) throws SQLException {
        return dao.buscarPorLogin(login);
    }
    
    public void cadastrarUsuario(String nome, String cargo, String login, String senha, String email) throws SQLException {
        Usuario usuario = new Usuario();
        usuario.setNome(nome);
        usuario.setCargo(cargo);
        usuario.setLogin(login);
        usuario.setSenha(senha);
        usuario.setEmail(email);
        dao.inserir(usuario);
    }
}

