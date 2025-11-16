package controller;

import dao.PagamentoDAO;
import dao.DividaDAO;
import util.CalculadoraMulta;
import model.Pagamento;
import model.Divida;
import java.sql.SQLException;
import java.util.Date;
import java.util.List;

public class PagamentoController {
    private PagamentoDAO dao;
    private DividaDAO dividaDAO;
    
    public PagamentoController() {
        dao = new PagamentoDAO();
        dividaDAO = new DividaDAO();
    }
    
    public void cadastrarPagamento(int idDivida, Date dataPagamento, double valorPago) throws SQLException {
        Divida divida = dividaDAO.buscarPorId(idDivida);
        
        if (divida == null) {
            throw new SQLException("Dívida não encontrada!");
        }
        
        if (dividaDAO.estaPaga(idDivida)) {
            throw new SQLException("Esta dívida já está paga!");
        }
        
        // Calcular valor com multa se necessário
        double valorComMulta = CalculadoraMulta.calcularValorComMulta(divida, dataPagamento);
        
        // Verificar se o valor pago é suficiente (considerando multa)
        double totalPago = dao.calcularTotalPago(idDivida);
        double valorRestante = valorComMulta - totalPago;
        
        if (valorPago < valorRestante) {
            throw new SQLException("O valor pago é insuficiente! Valor necessário: R$ " + 
                String.format("%.2f", valorRestante) + " (Valor original: R$ " + 
                String.format("%.2f", divida.getValorDivida()) + 
                (valorComMulta > divida.getValorDivida() ? 
                    ", Multa/Juros: R$ " + String.format("%.2f", valorComMulta - divida.getValorDivida()) + ")" : ")"));
        }
        
        // Atualizar data de atualização da dívida se necessário
        if (dataPagamento.after(divida.getDataAtualizacao())) {
            divida.setDataAtualizacao(dataPagamento);
            divida.setValorDivida(valorComMulta);
            dividaDAO.atualizar(divida);
        }
        
        Pagamento pagamento = new Pagamento();
        pagamento.setDivida(divida);
        pagamento.setDataPagamento(dataPagamento);
        pagamento.setValorPago(valorPago);
        
        dao.inserir(pagamento);
    }
    
    public Pagamento buscarPorId(int idpag) throws SQLException {
        return dao.buscarPorId(idpag);
    }
    
    public List<Pagamento> listarTodos() throws SQLException {
        return dao.listarTodos();
    }
    
    public void excluirPagamento(int idpag) throws SQLException {
        dao.excluir(idpag);
    }
    
    public double calcularFaturamento(Date dataInicio, Date dataFim) throws SQLException {
        return dao.calcularFaturamento(dataInicio, dataFim);
    }
}

