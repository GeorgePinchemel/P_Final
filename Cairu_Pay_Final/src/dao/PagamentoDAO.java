package dao;

import model.Pagamento;
import model.Divida;
import java.sql.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class PagamentoDAO {
    
    public void inserir(Pagamento pagamento) throws SQLException {
        String sql = "INSERT INTO pagamento (idDivida, dataPagamento, valorPago) VALUES (?, ?, ?)";
        try (Connection conn = Conexao.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, pagamento.getDivida().getCodigo());
            stmt.setDate(2, new java.sql.Date(pagamento.getDataPagamento().getTime()));
            stmt.setDouble(3, pagamento.getValorPago());
            stmt.executeUpdate();
        }
    }
    
    public void excluir(int idpag) throws SQLException {
        String sql = "DELETE FROM pagamento WHERE idpag=?";
        try (Connection conn = Conexao.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, idpag);
            stmt.executeUpdate();
        }
    }
    
    public Pagamento buscarPorId(int idpag) throws SQLException {
        String sql = "SELECT p.*, d.codigo as divida_codigo, d.valorDivida, d.dataAtualizacao, " +
                     "c1.idCliente as credor_id, c1.nomeCliente as credor_nome, " +
                     "c2.idCliente as devedor_id, c2.nomeCliente as devedor_nome " +
                     "FROM pagamento p " +
                     "INNER JOIN divida d ON p.idDivida = d.codigo " +
                     "INNER JOIN cliente c1 ON d.idCredor = c1.idCliente " +
                     "INNER JOIN cliente c2 ON d.idDevedor = c2.idCliente " +
                     "WHERE p.idpag=?";
        try (Connection conn = Conexao.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, idpag);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return criarPagamento(rs);
                }
            }
        }
        return null;
    }
    
    public List<Pagamento> listarTodos() throws SQLException {
        List<Pagamento> pagamentos = new ArrayList<>();
        String sql = "SELECT p.*, d.codigo as divida_codigo, d.valorDivida, d.dataAtualizacao, " +
                     "c1.idCliente as credor_id, c1.nomeCliente as credor_nome, " +
                     "c2.idCliente as devedor_id, c2.nomeCliente as devedor_nome " +
                     "FROM pagamento p " +
                     "INNER JOIN divida d ON p.idDivida = d.codigo " +
                     "INNER JOIN cliente c1 ON d.idCredor = c1.idCliente " +
                     "INNER JOIN cliente c2 ON d.idDevedor = c2.idCliente " +
                     "ORDER BY p.dataPagamento DESC";
        try (Connection conn = Conexao.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                pagamentos.add(criarPagamento(rs));
            }
        }
        return pagamentos;
    }
    
    public double calcularFaturamento(Date dataInicio, Date dataFim) throws SQLException {
        String sql = "SELECT COALESCE(SUM(valorPago), 0) FROM pagamento WHERE dataPagamento BETWEEN ? AND ?";
        try (Connection conn = Conexao.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setDate(1, new java.sql.Date(dataInicio.getTime()));
            stmt.setDate(2, new java.sql.Date(dataFim.getTime()));
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getDouble(1);
                }
            }
        }
        return 0.0;
    }
    
    public double calcularTotalPago(int idDivida) throws SQLException {
        String sql = "SELECT COALESCE(SUM(valorPago), 0) FROM pagamento WHERE idDivida=?";
        try (Connection conn = Conexao.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, idDivida);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getDouble(1);
                }
            }
        }
        return 0.0;
    }
    
    private Pagamento criarPagamento(ResultSet rs) throws SQLException {
        Pagamento pagamento = new Pagamento();
        pagamento.setIdpag(rs.getInt("idpag"));
        pagamento.setDataPagamento(rs.getDate("dataPagamento"));
        pagamento.setValorPago(rs.getDouble("valorPago"));
        
        Divida divida = new Divida();
        divida.setCodigo(rs.getInt("divida_codigo"));
        divida.setValorDivida(rs.getDouble("valorDivida"));
        divida.setDataAtualizacao(rs.getDate("dataAtualizacao"));
        
        model.Cliente credor = new model.Cliente();
        credor.setIdCliente(rs.getInt("credor_id"));
        credor.setNomeCliente(rs.getString("credor_nome"));
        divida.setCredor(credor);
        
        model.Cliente devedor = new model.Cliente();
        devedor.setIdCliente(rs.getInt("devedor_id"));
        devedor.setNomeCliente(rs.getString("devedor_nome"));
        divida.setDevedor(devedor);
        
        pagamento.setDivida(divida);
        return pagamento;
    }
}

