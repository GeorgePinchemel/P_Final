package controller;

import dao.DividaDAO;
import dao.ClienteDAO;
import model.Divida;
import model.Cliente;
import java.sql.SQLException;
import java.util.Date;
import java.util.List;

public class DividaController {
    private DividaDAO dao;
    private ClienteDAO clienteDAO;
    
    public DividaController() {
        dao = new DividaDAO();
        clienteDAO = new ClienteDAO();
    }
    
    public void cadastrarDivida(int idCredor, int idDevedor, Date dataAtualizacao, 
                                double valorDivida) throws SQLException {
        if (idCredor == idDevedor) {
            throw new SQLException("O credor deve ser diferente do devedor!");
        }
        
        Cliente credor = clienteDAO.buscarPorId(idCredor);
        Cliente devedor = clienteDAO.buscarPorId(idDevedor);
        
        if (credor == null) {
            throw new SQLException("Credor não encontrado!");
        }
        if (devedor == null) {
            throw new SQLException("Devedor não encontrado!");
        }
        
        Divida divida = new Divida();
        divida.setCredor(credor);
        divida.setDevedor(devedor);
        divida.setDataAtualizacao(dataAtualizacao);
        divida.setValorDivida(valorDivida);
        
        dao.inserir(divida);
    }
    
    public Divida buscarPorId(int codigo) throws SQLException {
        return dao.buscarPorId(codigo);
    }
    
    public List<Divida> listarTodas() throws SQLException {
        return dao.listarTodas();
    }
    
    public List<Divida> listarNaoPagas() throws SQLException {
        return dao.listarNaoPagas();
    }
    
    public List<Divida> listarPorDocumento(String documento) throws SQLException {
        return dao.listarPorDocumento(documento);
    }
    
    public void excluirDivida(int codigo) throws SQLException {
        if (dao.possuiPagamentos(codigo)) {
            throw new SQLException("Não é possível excluir dívida com pagamentos associados!");
        }
        dao.excluir(codigo);
    }
    
    public boolean estaPaga(int codigo) throws SQLException {
        return dao.estaPaga(codigo);
    }
}

