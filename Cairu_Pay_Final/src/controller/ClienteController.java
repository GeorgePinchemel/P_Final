package controller;

import dao.ClienteDAO;
import model.Cliente;
import java.sql.SQLException;
import java.util.List;

public class ClienteController {
    private ClienteDAO dao;
    
    public ClienteController() {
        dao = new ClienteDAO();
    }
    
    public void cadastrarCliente(String nome, String endereco, String uf, 
                                String telefone, String documento, String email) throws SQLException {
        Cliente cliente = new Cliente();
        cliente.setNomeCliente(nome);
        cliente.setEndereco(endereco);
        cliente.setUf(uf);
        cliente.setTelefone(telefone);
        cliente.setDocumento(documento);
        cliente.setEmail(email);
        dao.inserir(cliente);
    }
    
    public Cliente buscarPorId(int id) throws SQLException {
        return dao.buscarPorId(id);
    }
    
    public Cliente buscarPorDocumento(String documento) throws SQLException {
        return dao.buscarPorDocumento(documento);
    }
    
    public List<Cliente> listarTodos() throws SQLException {
        return dao.listarTodos();
    }
    
    public void excluirCliente(int id) throws SQLException {
        if (dao.possuiDividas(id)) {
            throw new SQLException("Não é possível excluir cliente com dívidas associadas!");
        }
        dao.excluir(id);
    }
}

