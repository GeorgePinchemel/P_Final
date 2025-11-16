package dao;

import model.Divida;
import model.Cliente;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DividaDAO {
    
    public void inserir(Divida divida) throws SQLException {
        String sql = "INSERT INTO divida (idCredor, dataAtualizacao, valorDivida, idDevedor) VALUES (?, ?, ?, ?)";
        try (Connection conn = Conexao.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, divida.getCredor().getIdCliente());
            stmt.setDate(2, new java.sql.Date(divida.getDataAtualizacao().getTime()));
            stmt.setDouble(3, divida.getValorDivida());
            stmt.setInt(4, divida.getDevedor().getIdCliente());
            stmt.executeUpdate();
        }
    }
    
    public void atualizar(Divida divida) throws SQLException {
        String sql = "UPDATE divida SET idCredor=?, dataAtualizacao=?, valorDivida=?, idDevedor=? WHERE codigo=?";
        try (Connection conn = Conexao.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, divida.getCredor().getIdCliente());
            stmt.setDate(2, new java.sql.Date(divida.getDataAtualizacao().getTime()));
            stmt.setDouble(3, divida.getValorDivida());
            stmt.setInt(4, divida.getDevedor().getIdCliente());
            stmt.setInt(5, divida.getCodigo());
            stmt.executeUpdate();
        }
    }
    
    public void excluir(int codigo) throws SQLException {
        String sql = "DELETE FROM divida WHERE codigo=?";
        try (Connection conn = Conexao.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, codigo);
            stmt.executeUpdate();
        }
    }
    
    public Divida buscarPorId(int codigo) throws SQLException {
        String sql = "SELECT d.*, c1.idCliente as credor_id, c1.nomeCliente as credor_nome, " +
                     "c2.idCliente as devedor_id, c2.nomeCliente as devedor_nome " +
                     "FROM divida d " +
                     "INNER JOIN cliente c1 ON d.idCredor = c1.idCliente " +
                     "INNER JOIN cliente c2 ON d.idDevedor = c2.idCliente " +
                     "WHERE d.codigo=?";
        try (Connection conn = Conexao.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, codigo);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return criarDivida(rs);
                }
            }
        }
        return null;
    }
    
    public List<Divida> listarTodas() throws SQLException {
        List<Divida> dividas = new ArrayList<>();
        String sql = "SELECT d.*, c1.idCliente as credor_id, c1.nomeCliente as credor_nome, " +
                     "c2.idCliente as devedor_id, c2.nomeCliente as devedor_nome " +
                     "FROM divida d " +
                     "INNER JOIN cliente c1 ON d.idCredor = c1.idCliente " +
                     "INNER JOIN cliente c2 ON d.idDevedor = c2.idCliente " +
                     "ORDER BY d.codigo";
        try (Connection conn = Conexao.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                dividas.add(criarDivida(rs));
            }
        }
        return dividas;
    }
    
    public List<Divida> listarNaoPagas() throws SQLException {
        List<Divida> dividas = new ArrayList<>();
        String sql = "SELECT d.*, c1.idCliente as credor_id, c1.nomeCliente as credor_nome, " +
                     "c2.idCliente as devedor_id, c2.nomeCliente as devedor_nome " +
                     "FROM divida d " +
                     "INNER JOIN cliente c1 ON d.idCredor = c1.idCliente " +
                     "INNER JOIN cliente c2 ON d.idDevedor = c2.idCliente " +
                     "WHERE d.codigo NOT IN (SELECT DISTINCT idDivida FROM pagamento) " +
                     "ORDER BY d.codigo";
        try (Connection conn = Conexao.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                dividas.add(criarDivida(rs));
            }
        }
        return dividas;
    }
    
    public List<Divida> listarPorDocumento(String documento) throws SQLException {
        List<Divida> dividas = new ArrayList<>();
        String sql = "SELECT d.*, c1.idCliente as credor_id, c1.nomeCliente as credor_nome, " +
                     "c2.idCliente as devedor_id, c2.nomeCliente as devedor_nome " +
                     "FROM divida d " +
                     "INNER JOIN cliente c1 ON d.idCredor = c1.idCliente " +
                     "INNER JOIN cliente c2 ON d.idDevedor = c2.idCliente " +
                     "WHERE c2.documento=? " +
                     "ORDER BY d.codigo";
        try (Connection conn = Conexao.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, documento);
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    dividas.add(criarDivida(rs));
                }
            }
        }
        return dividas;
    }
    
    public boolean possuiPagamentos(int codigo) throws SQLException {
        String sql = "SELECT COUNT(*) FROM pagamento WHERE idDivida=?";
        try (Connection conn = Conexao.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, codigo);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt(1) > 0;
                }
            }
        }
        return false;
    }
    
    public boolean estaPaga(int codigo) throws SQLException {
        String sql = "SELECT COALESCE(SUM(valorPago), 0) FROM pagamento WHERE idDivida=?";
        try (Connection conn = Conexao.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, codigo);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    double totalPago = rs.getDouble(1);
                    Divida divida = buscarPorId(codigo);
                    return divida != null && totalPago >= divida.getValorDivida();
                }
            }
        }
        return false;
    }
    
    private Divida criarDivida(ResultSet rs) throws SQLException {
        Divida divida = new Divida();
        divida.setCodigo(rs.getInt("codigo"));
        
        Cliente credor = new Cliente();
        credor.setIdCliente(rs.getInt("credor_id"));
        credor.setNomeCliente(rs.getString("credor_nome"));
        divida.setCredor(credor);
        
        divida.setDataAtualizacao(rs.getDate("dataAtualizacao"));
        divida.setValorDivida(rs.getDouble("valorDivida"));
        
        Cliente devedor = new Cliente();
        devedor.setIdCliente(rs.getInt("devedor_id"));
        devedor.setNomeCliente(rs.getString("devedor_nome"));
        divida.setDevedor(devedor);
        
        return divida;
    }
}


