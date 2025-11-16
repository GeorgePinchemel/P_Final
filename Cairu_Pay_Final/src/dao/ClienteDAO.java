package dao;

import model.Cliente;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ClienteDAO {
    
    public void inserir(Cliente cliente) throws SQLException {
        String sql = "INSERT INTO cliente (nomeCliente, endereco, uf, telefone, documento, email) VALUES (?, ?, ?, ?, ?, ?)";
        try (Connection conn = Conexao.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, cliente.getNomeCliente());
            stmt.setString(2, cliente.getEndereco());
            stmt.setString(3, cliente.getUf());
            stmt.setString(4, cliente.getTelefone());
            stmt.setString(5, cliente.getDocumento());
            stmt.setString(6, cliente.getEmail());
            stmt.executeUpdate();
        }
    }
    
    public void atualizar(Cliente cliente) throws SQLException {
        String sql = "UPDATE cliente SET nomeCliente=?, endereco=?, uf=?, telefone=?, documento=?, email=? WHERE idCliente=?";
        try (Connection conn = Conexao.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, cliente.getNomeCliente());
            stmt.setString(2, cliente.getEndereco());
            stmt.setString(3, cliente.getUf());
            stmt.setString(4, cliente.getTelefone());
            stmt.setString(5, cliente.getDocumento());
            stmt.setString(6, cliente.getEmail());
            stmt.setInt(7, cliente.getIdCliente());
            stmt.executeUpdate();
        }
    }
    
    public void excluir(int idCliente) throws SQLException {
        String sql = "DELETE FROM cliente WHERE idCliente=?";
        try (Connection conn = Conexao.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, idCliente);
            stmt.executeUpdate();
        }
    }
    
    public Cliente buscarPorId(int idCliente) throws SQLException {
        String sql = "SELECT * FROM cliente WHERE idCliente=?";
        try (Connection conn = Conexao.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, idCliente);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return criarCliente(rs);
                }
            }
        }
        return null;
    }
    
    public Cliente buscarPorDocumento(String documento) throws SQLException {
        String sql = "SELECT * FROM cliente WHERE documento=?";
        try (Connection conn = Conexao.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, documento);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return criarCliente(rs);
                }
            }
        }
        return null;
    }
    
    public List<Cliente> listarTodos() throws SQLException {
        List<Cliente> clientes = new ArrayList<>();
        String sql = "SELECT * FROM cliente ORDER BY nomeCliente";
        try (Connection conn = Conexao.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                clientes.add(criarCliente(rs));
            }
        }
        return clientes;
    }
    
    public boolean possuiDividas(int idCliente) throws SQLException {
        String sql = "SELECT COUNT(*) FROM divida WHERE idCredor=? OR idDevedor=?";
        try (Connection conn = Conexao.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, idCliente);
            stmt.setInt(2, idCliente);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt(1) > 0;
                }
            }
        }
        return false;
    }
    
    private Cliente criarCliente(ResultSet rs) throws SQLException {
        Cliente cliente = new Cliente();
        cliente.setIdCliente(rs.getInt("idCliente"));
        cliente.setNomeCliente(rs.getString("nomeCliente"));
        cliente.setEndereco(rs.getString("endereco"));
        cliente.setUf(rs.getString("uf"));
        cliente.setTelefone(rs.getString("telefone"));
        cliente.setDocumento(rs.getString("documento"));
        cliente.setEmail(rs.getString("email"));
        return cliente;
    }
}

