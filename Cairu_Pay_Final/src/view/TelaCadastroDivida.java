package view;

import controller.DividaController;
import controller.ClienteController;
import model.Cliente;
import javax.swing.*;
import java.awt.*;
import java.util.Date;
import java.util.List;

public class TelaCadastroDivida extends JInternalFrame {
    private JComboBox<Cliente> cmbCredor, cmbDevedor;
    private JTextField txtValor;
    private JFormattedTextField txtData;
    private JButton btnSalvar, btnLimpar;
    private DividaController controller;
    private ClienteController clienteController;
    
    public TelaCadastroDivida() {
        controller = new DividaController();
        clienteController = new ClienteController();
        initComponents();
        carregarClientes();
    }
    
    private void initComponents() {
        setSize(600, 300);
        setLayout(new BorderLayout());
        
        JPanel panelCampos = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.anchor = GridBagConstraints.WEST;
        
        // Credor
        gbc.gridx = 0; gbc.gridy = 0;
        panelCampos.add(new JLabel("Credor:"), gbc);
        gbc.gridx = 1; gbc.fill = GridBagConstraints.HORIZONTAL; gbc.weightx = 1.0;
        cmbCredor = new JComboBox<>();
        panelCampos.add(cmbCredor, gbc);
        
        // Devedor
        gbc.gridx = 0; gbc.gridy = 1; gbc.fill = GridBagConstraints.NONE; gbc.weightx = 0;
        panelCampos.add(new JLabel("Devedor:"), gbc);
        gbc.gridx = 1; gbc.fill = GridBagConstraints.HORIZONTAL; gbc.weightx = 1.0;
        cmbDevedor = new JComboBox<>();
        panelCampos.add(cmbDevedor, gbc);
        
        // Data
        gbc.gridx = 0; gbc.gridy = 2; gbc.fill = GridBagConstraints.NONE; gbc.weightx = 0;
        panelCampos.add(new JLabel("Data Atualização:"), gbc);
        gbc.gridx = 1; gbc.fill = GridBagConstraints.HORIZONTAL; gbc.weightx = 1.0;
        txtData = new JFormattedTextField(new java.text.SimpleDateFormat("dd/MM/yyyy"));
        txtData.setValue(new Date());
        panelCampos.add(txtData, gbc);
        
        // Valor
        gbc.gridx = 0; gbc.gridy = 3; gbc.fill = GridBagConstraints.NONE; gbc.weightx = 0;
        panelCampos.add(new JLabel("Valor da Dívida:"), gbc);
        gbc.gridx = 1; gbc.fill = GridBagConstraints.HORIZONTAL; gbc.weightx = 1.0;
        txtValor = new JTextField(20);
        panelCampos.add(txtValor, gbc);
        
        // Botões
        JPanel panelBotoes = new JPanel(new FlowLayout());
        btnSalvar = new JButton("Salvar");
        btnLimpar = new JButton("Limpar");
        
        btnSalvar.addActionListener(e -> salvarDivida());
        btnLimpar.addActionListener(e -> limparCampos());
        
        panelBotoes.add(btnSalvar);
        panelBotoes.add(btnLimpar);
        
        add(panelCampos, BorderLayout.CENTER);
        add(panelBotoes, BorderLayout.SOUTH);
    }
    
    private void carregarClientes() {
        try {
            List<Cliente> clientes = clienteController.listarTodos();
            cmbCredor.removeAllItems();
            cmbDevedor.removeAllItems();
            for (Cliente c : clientes) {
                cmbCredor.addItem(c);
                cmbDevedor.addItem(c);
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Erro ao carregar clientes: " + e.getMessage(), 
                "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    private void salvarDivida() {
        try {
            if (validarCampos()) {
                Cliente credor = (Cliente) cmbCredor.getSelectedItem();
                Cliente devedor = (Cliente) cmbDevedor.getSelectedItem();
                Date data = (Date) txtData.getValue();
                double valor = Double.parseDouble(txtValor.getText().replace(",", "."));
                
                controller.cadastrarDivida(credor.getIdCliente(), devedor.getIdCliente(), data, valor);
                JOptionPane.showMessageDialog(this, "Dívida cadastrada com sucesso!");
                limparCampos();
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Erro ao cadastrar dívida: " + e.getMessage(), 
                "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    private boolean validarCampos() {
        if (cmbCredor.getSelectedItem() == null || cmbDevedor.getSelectedItem() == null) {
            JOptionPane.showMessageDialog(this, "Selecione credor e devedor!", 
                "Validação", JOptionPane.WARNING_MESSAGE);
            return false;
        }
        if (txtValor.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Preencha o valor!", 
                "Validação", JOptionPane.WARNING_MESSAGE);
            return false;
        }
        return true;
    }
    
    private void limparCampos() {
        cmbCredor.setSelectedIndex(0);
        cmbDevedor.setSelectedIndex(0);
        txtData.setValue(new Date());
        txtValor.setText("");
    }
}


