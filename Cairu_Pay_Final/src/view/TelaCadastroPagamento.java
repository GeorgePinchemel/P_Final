package view;

import controller.PagamentoController;
import controller.DividaController;
import model.Divida;
import javax.swing.*;
import java.awt.*;
import java.util.Date;

public class TelaCadastroPagamento extends JInternalFrame {
    private JTextField txtCodigoDivida, txtValorPago;
    private JFormattedTextField txtDataPagamento;
    private JTextArea txtInfoDivida;
    private JButton btnBuscarDivida, btnSalvar, btnLimpar;
    private PagamentoController controller;
    private DividaController dividaController;
    private Divida dividaSelecionada;
    
    public TelaCadastroPagamento() {
        controller = new PagamentoController();
        dividaController = new DividaController();
        initComponents();
    }
    
    private void initComponents() {
        setSize(700, 500);
        setLayout(new BorderLayout());
        
        JPanel panelCampos = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.anchor = GridBagConstraints.WEST;
        
        // Código da Dívida
        gbc.gridx = 0; gbc.gridy = 0;
        panelCampos.add(new JLabel("Código da Dívida:"), gbc);
        gbc.gridx = 1; gbc.fill = GridBagConstraints.HORIZONTAL; gbc.weightx = 1.0;
        txtCodigoDivida = new JTextField(10);
        btnBuscarDivida = new JButton("Buscar");
        btnBuscarDivida.addActionListener(e -> buscarDivida());
        JPanel panelBusca = new JPanel(new FlowLayout(FlowLayout.LEFT));
        panelBusca.add(txtCodigoDivida);
        panelBusca.add(btnBuscarDivida);
        gbc.gridx = 1;
        panelCampos.add(panelBusca, gbc);
        
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
        txtDataPagamento.setValue(new Date());
        panelCampos.add(txtDataPagamento, gbc);
        
        // Valor Pago
        gbc.gridx = 0; gbc.gridy = 3; gbc.fill = GridBagConstraints.NONE; gbc.weightx = 0;
        panelCampos.add(new JLabel("Valor Pago:"), gbc);
        gbc.gridx = 1; gbc.fill = GridBagConstraints.HORIZONTAL; gbc.weightx = 1.0;
        txtValorPago = new JTextField(20);
        panelCampos.add(txtValorPago, gbc);
        
        // Botões
        JPanel panelBotoes = new JPanel(new FlowLayout());
        btnSalvar = new JButton("Salvar");
        btnSalvar.setEnabled(false);
        btnLimpar = new JButton("Limpar");
        
        btnSalvar.addActionListener(e -> salvarPagamento());
        btnLimpar.addActionListener(e -> limparCampos());
        
        panelBotoes.add(btnSalvar);
        panelBotoes.add(btnLimpar);
        
        add(panelCampos, BorderLayout.CENTER);
        add(panelBotoes, BorderLayout.SOUTH);
    }
    
    private void buscarDivida() {
        try {
            int codigo = Integer.parseInt(txtCodigoDivida.getText());
            dividaSelecionada = dividaController.buscarPorId(codigo);
            if (dividaSelecionada != null) {
                txtInfoDivida.setText("Credor: " + dividaSelecionada.getCredor().getNomeCliente() + "\n" +
                                    "Devedor: " + dividaSelecionada.getDevedor().getNomeCliente() + "\n" +
                                    "Data Atualização: " + new java.text.SimpleDateFormat("dd/MM/yyyy")
                                        .format(dividaSelecionada.getDataAtualizacao()) + "\n" +
                                    "Valor: R$ " + String.format("%.2f", dividaSelecionada.getValorDivida()));
                btnSalvar.setEnabled(true);
            } else {
                JOptionPane.showMessageDialog(this, "Dívida não encontrada!", 
                    "Aviso", JOptionPane.WARNING_MESSAGE);
                txtInfoDivida.setText("");
                btnSalvar.setEnabled(false);
            }
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Código inválido!", "Erro", JOptionPane.ERROR_MESSAGE);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Erro ao buscar dívida: " + e.getMessage(), 
                "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    private void salvarPagamento() {
        try {
            if (validarCampos()) {
                Date dataPagamento = (Date) txtDataPagamento.getValue();
                double valorPago = Double.parseDouble(txtValorPago.getText().replace(",", "."));
                
                controller.cadastrarPagamento(dividaSelecionada.getCodigo(), dataPagamento, valorPago);
                JOptionPane.showMessageDialog(this, "Pagamento cadastrado com sucesso!");
                limparCampos();
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Erro ao cadastrar pagamento: " + e.getMessage(), 
                "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    private boolean validarCampos() {
        if (dividaSelecionada == null) {
            JOptionPane.showMessageDialog(this, "Busque uma dívida primeiro!", 
                "Validação", JOptionPane.WARNING_MESSAGE);
            return false;
        }
        if (txtValorPago.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Preencha o valor pago!", 
                "Validação", JOptionPane.WARNING_MESSAGE);
            return false;
        }
        return true;
    }
    
    private void limparCampos() {
        txtCodigoDivida.setText("");
        txtInfoDivida.setText("");
        txtDataPagamento.setValue(new Date());
        txtValorPago.setText("");
        dividaSelecionada = null;
        btnSalvar.setEnabled(false);
    }
}

