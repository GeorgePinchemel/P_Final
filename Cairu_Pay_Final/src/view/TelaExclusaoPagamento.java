package view;

import controller.PagamentoController;
import model.Pagamento;
import javax.swing.*;
import java.awt.*;
import java.text.SimpleDateFormat;

public class TelaExclusaoPagamento extends JInternalFrame {
    private JTextField txtId;
    private JButton btnExcluir, btnBuscar;
    private JTextArea txtDados;
    private PagamentoController controller;
    private SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
    
    public TelaExclusaoPagamento() {
        controller = new PagamentoController();
        initComponents();
    }
    
    private void initComponents() {
        setSize(600, 400);
        setLayout(new BorderLayout());
        
        JPanel panelBusca = new JPanel(new FlowLayout());
        panelBusca.add(new JLabel("ID do Pagamento:"));
        txtId = new JTextField(10);
        btnBuscar = new JButton("Buscar");
        btnBuscar.addActionListener(e -> buscarPagamento());
        panelBusca.add(txtId);
        panelBusca.add(btnBuscar);
        
        txtDados = new JTextArea(10, 40);
        txtDados.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(txtDados);
        
        JPanel panelBotoes = new JPanel(new FlowLayout());
        btnExcluir = new JButton("Excluir");
        btnExcluir.setEnabled(false);
        btnExcluir.addActionListener(e -> excluirPagamento());
        panelBotoes.add(btnExcluir);
        
        add(panelBusca, BorderLayout.NORTH);
        add(scrollPane, BorderLayout.CENTER);
        add(panelBotoes, BorderLayout.SOUTH);
    }
    
    private void buscarPagamento() {
        try {
            int id = Integer.parseInt(txtId.getText());
            Pagamento pagamento = controller.buscarPorId(id);
            if (pagamento != null) {
                txtDados.setText("ID: " + pagamento.getIdpag() + "\n" +
                               "Código Dívida: " + pagamento.getDivida().getCodigo() + "\n" +
                               "Credor: " + pagamento.getDivida().getCredor().getNomeCliente() + "\n" +
                               "Devedor: " + pagamento.getDivida().getDevedor().getNomeCliente() + "\n" +
                               "Data Pagamento: " + sdf.format(pagamento.getDataPagamento()) + "\n" +
                               "Valor Pago: R$ " + String.format("%.2f", pagamento.getValorPago()));
                btnExcluir.setEnabled(true);
            } else {
                JOptionPane.showMessageDialog(this, "Pagamento não encontrado!", 
                    "Aviso", JOptionPane.WARNING_MESSAGE);
                txtDados.setText("");
                btnExcluir.setEnabled(false);
            }
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "ID inválido!", "Erro", JOptionPane.ERROR_MESSAGE);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Erro ao buscar pagamento: " + e.getMessage(), 
                "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    private void excluirPagamento() {
        try {
            int id = Integer.parseInt(txtId.getText());
            int resposta = JOptionPane.showConfirmDialog(this, 
                "Deseja realmente excluir este pagamento?", "Confirmação", 
                JOptionPane.YES_NO_OPTION);
            if (resposta == JOptionPane.YES_OPTION) {
                controller.excluirPagamento(id);
                JOptionPane.showMessageDialog(this, "Pagamento excluído com sucesso!");
                txtId.setText("");
                txtDados.setText("");
                btnExcluir.setEnabled(false);
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Erro ao excluir pagamento: " + e.getMessage(), 
                "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }
}

