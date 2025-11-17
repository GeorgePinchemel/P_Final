package view;

import controller.PagamentoController;
import controller.DividaController;
import model.Pagamento;
import model.Divida;
import javax.swing.*;
import java.awt.*;
import java.util.Date;

public class TelaAtualizarPagamento extends JInternalFrame {
    private JTextField txtId;
    private JTextField txtCodigoDivida;
    private JFormattedTextField txtDataPagamento;
    private JTextField txtValorPago;
    private JTextArea txtInfoDivida;
    private JButton btnBuscarPagamento, btnBuscarDivida, btnAtualizar, btnLimpar;
    private PagamentoController controller;
    private DividaController dividaController;
    private Pagamento pagamentoSelecionado;
    private Divida dividaSelecionada;
    
    public TelaAtualizarPagamento() {
        controller = new PagamentoController();
        dividaController = new DividaController();
        initComponents();
    }
    
    private void initComponents() {
        setTitle("Atualizar Pagamento");
        setSize(700, 500);
        setLayout(new BorderLayout());
        
        // Painel de busca
        JPanel panelBusca = new JPanel(new FlowLayout(FlowLayout.LEFT));
        panelBusca.add(new JLabel("ID do Pagamento:"));
        txtId = new JTextField(10);
        btnBuscarPagamento = new JButton("Buscar Pagamento");
        btnBuscarPagamento.addActionListener(e -> buscarPagamento());
        panelBusca.add(txtId);
        panelBusca.add(btnBuscarPagamento);
        
        // Painel de campos
        JPanel panelCampos = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.anchor = GridBagConstraints.WEST;
        
        // Código da Dívida
        gbc.gridx = 0; gbc.gridy = 0;
        panelCampos.add(new JLabel("Código da Dívida:"), gbc);
        gbc.gridx = 1; gbc.fill = GridBagConstraints.HORIZONTAL; gbc.weightx = 1.0;
        txtCodigoDivida = new JTextField(10);
        txtCodigoDivida.setEnabled(false);
        btnBuscarDivida = new JButton("Buscar Dívida");
        btnBuscarDivida.setEnabled(false);
        btnBuscarDivida.addActionListener(e -> buscarDivida());
        JPanel panelBuscaDivida = new JPanel(new FlowLayout(FlowLayout.LEFT));
        panelBuscaDivida.add(txtCodigoDivida);
        panelBuscaDivida.add(btnBuscarDivida);
        gbc.gridx = 1;
        panelCampos.add(panelBuscaDivida, gbc);
        
        // Informações da Dívida
        gbc.gridx = 0; gbc.gridy = 1;
        panelCampos.add(new JLabel("Informações da Dívida:"), gbc);
        gbc.gridx = 1; gbc.fill = GridBagConstraints.BOTH; gbc.weightx = 1.0; gbc.weighty = 1.0;
        txtInfoDivida = new JTextArea(5, 30);
        txtInfoDivida.setEditable(false);
        JScrollPane scrollInfo = new JScrollPane(txtInfoDivida);
        panelCampos.add(scrollInfo, gbc);
        
        // Data Pagamento
        gbc.gridx = 0; gbc.gridy = 2; gbc.fill = GridBagConstraints.NONE; gbc.weightx = 0; gbc.weighty = 0;
        panelCampos.add(new JLabel("Data Pagamento:"), gbc);
        gbc.gridx = 1; gbc.fill = GridBagConstraints.HORIZONTAL; gbc.weightx = 1.0;
        txtDataPagamento = new JFormattedTextField(new java.text.SimpleDateFormat("dd/MM/yyyy"));
        txtDataPagamento.setEnabled(false);
        panelCampos.add(txtDataPagamento, gbc);
        
        // Valor Pago
        gbc.gridx = 0; gbc.gridy = 3; gbc.fill = GridBagConstraints.NONE; gbc.weightx = 0;
        panelCampos.add(new JLabel("Valor Pago:"), gbc);
        gbc.gridx = 1; gbc.fill = GridBagConstraints.HORIZONTAL; gbc.weightx = 1.0;
        txtValorPago = new JTextField(20);
        txtValorPago.setEnabled(false);
        panelCampos.add(txtValorPago, gbc);
        
        // Botões
        JPanel panelBotoes = new JPanel(new FlowLayout());
        btnAtualizar = new JButton("Atualizar");
        btnAtualizar.setEnabled(false);
        btnAtualizar.addActionListener(e -> atualizarPagamento());
        btnLimpar = new JButton("Limpar");
        btnLimpar.addActionListener(e -> limparCampos());
        
        panelBotoes.add(btnAtualizar);
        panelBotoes.add(btnLimpar);
        
        add(panelBusca, BorderLayout.NORTH);
        add(panelCampos, BorderLayout.CENTER);
        add(panelBotoes, BorderLayout.SOUTH);
    }
    
    private void buscarPagamento() {
        try {
            int id = Integer.parseInt(txtId.getText().trim());
            pagamentoSelecionado = controller.buscarPorId(id);
            if (pagamentoSelecionado != null) {
                dividaSelecionada = pagamentoSelecionado.getDivida();
                txtCodigoDivida.setText(String.valueOf(dividaSelecionada.getCodigo()));
                
                txtInfoDivida.setText("Credor: " + dividaSelecionada.getCredor().getNomeCliente() + "\n" +
                                    "Devedor: " + dividaSelecionada.getDevedor().getNomeCliente() + "\n" +
                                    "Data Atualização: " + new java.text.SimpleDateFormat("dd/MM/yyyy")
                                        .format(dividaSelecionada.getDataAtualizacao()) + "\n" +
                                    "Valor: R$ " + String.format("%.2f", dividaSelecionada.getValorDivida()));
                
                txtDataPagamento.setValue(pagamentoSelecionado.getDataPagamento());
                txtValorPago.setText(String.format("%.2f", pagamentoSelecionado.getValorPago()));
                
                // Habilitar campos
                txtDataPagamento.setEnabled(true);
                txtValorPago.setEnabled(true);
                btnAtualizar.setEnabled(true);
            } else {
                JOptionPane.showMessageDialog(this, "Pagamento não encontrado!", 
                    "Aviso", JOptionPane.WARNING_MESSAGE);
                limparCampos();
            }
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "ID inválido!", "Erro", JOptionPane.ERROR_MESSAGE);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Erro ao buscar pagamento: " + e.getMessage(), 
                "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    private void buscarDivida() {
        try {
            int codigo = Integer.parseInt(txtCodigoDivida.getText().trim());
            dividaSelecionada = dividaController.buscarPorId(codigo);
            if (dividaSelecionada != null) {
                txtInfoDivida.setText("Credor: " + dividaSelecionada.getCredor().getNomeCliente() + "\n" +
                                    "Devedor: " + dividaSelecionada.getDevedor().getNomeCliente() + "\n" +
                                    "Data Atualização: " + new java.text.SimpleDateFormat("dd/MM/yyyy")
                                        .format(dividaSelecionada.getDataAtualizacao()) + "\n" +
                                    "Valor: R$ " + String.format("%.2f", dividaSelecionada.getValorDivida()));
            } else {
                JOptionPane.showMessageDialog(this, "Dívida não encontrada!", 
                    "Aviso", JOptionPane.WARNING_MESSAGE);
                txtInfoDivida.setText("");
            }
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Código inválido!", "Erro", JOptionPane.ERROR_MESSAGE);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Erro ao buscar dívida: " + e.getMessage(), 
                "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    private void atualizarPagamento() {
        try {
            if (validarCampos()) {
                Date dataPagamento = (Date) txtDataPagamento.getValue();
                double valorPago = Double.parseDouble(txtValorPago.getText().replace(",", "."));
                
                pagamentoSelecionado.setDataPagamento(dataPagamento);
                pagamentoSelecionado.setValorPago(valorPago);
                
                controller.atualizarPagamento(pagamentoSelecionado);
                JOptionPane.showMessageDialog(this, "Pagamento atualizado com sucesso!");
                limparCampos();
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Erro ao atualizar pagamento: " + e.getMessage(), 
                "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    private boolean validarCampos() {
        if (pagamentoSelecionado == null) {
            JOptionPane.showMessageDialog(this, "Busque um pagamento primeiro!", 
                "Validação", JOptionPane.WARNING_MESSAGE);
            return false;
        }
        if (txtValorPago.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Preencha o valor pago!", 
                "Validação", JOptionPane.WARNING_MESSAGE);
            return false;
        }
        try {
            double valorPago = Double.parseDouble(txtValorPago.getText().replace(",", "."));
            if (valorPago <= 0) {
                JOptionPane.showMessageDialog(this, "O valor pago deve ser maior que zero!", 
                    "Validação", JOptionPane.WARNING_MESSAGE);
                return false;
            }
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Valor inválido!", 
                "Validação", JOptionPane.WARNING_MESSAGE);
            return false;
        }
        return true;
    }
    
    private void limparCampos() {
        txtId.setText("");
        txtCodigoDivida.setText("");
        txtInfoDivida.setText("");
        txtDataPagamento.setValue(new Date());
        txtValorPago.setText("");
        pagamentoSelecionado = null;
        dividaSelecionada = null;
        btnAtualizar.setEnabled(false);
        
        // Desabilitar campos
        txtDataPagamento.setEnabled(false);
        txtValorPago.setEnabled(false);
    }
}

