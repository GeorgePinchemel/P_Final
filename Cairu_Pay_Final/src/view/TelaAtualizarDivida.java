package view;

import controller.DividaController;
import controller.ClienteController;
import model.Divida;
import model.Cliente;
import javax.swing.*;
import java.awt.*;
import java.util.Date;
import java.util.List;

public class TelaAtualizarDivida extends JInternalFrame {
    private JTextField txtCodigo;
    private JComboBox<Cliente> cmbCredor, cmbDevedor;
    private JFormattedTextField txtData;
    private JTextField txtValor;
    private JButton btnBuscar, btnAtualizar, btnLimpar;
    private DividaController controller;
    private ClienteController clienteController;
    private Divida dividaSelecionada;
    
    public TelaAtualizarDivida() {
        controller = new DividaController();
        clienteController = new ClienteController();
        initComponents();
        carregarClientes();
    }
    
    private void initComponents() {
        setTitle("Atualizar Dívida");
        setSize(600, 350);
        setLayout(new BorderLayout());
        
        // Painel de busca
        JPanel panelBusca = new JPanel(new FlowLayout(FlowLayout.LEFT));
        panelBusca.add(new JLabel("Código da Dívida:"));
        txtCodigo = new JTextField(10);
        btnBuscar = new JButton("Buscar");
        btnBuscar.addActionListener(e -> buscarDivida());
        panelBusca.add(txtCodigo);
        panelBusca.add(btnBuscar);
        
        // Painel de campos
        JPanel panelCampos = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.anchor = GridBagConstraints.WEST;
        
        // Credor
        gbc.gridx = 0; gbc.gridy = 0;
        panelCampos.add(new JLabel("Credor:"), gbc);
        gbc.gridx = 1; gbc.fill = GridBagConstraints.HORIZONTAL; gbc.weightx = 1.0;
        cmbCredor = new JComboBox<>();
        cmbCredor.setEnabled(false);
        panelCampos.add(cmbCredor, gbc);
        
        // Devedor
        gbc.gridx = 0; gbc.gridy = 1; gbc.fill = GridBagConstraints.NONE; gbc.weightx = 0;
        panelCampos.add(new JLabel("Devedor:"), gbc);
        gbc.gridx = 1; gbc.fill = GridBagConstraints.HORIZONTAL; gbc.weightx = 1.0;
        cmbDevedor = new JComboBox<>();
        cmbDevedor.setEnabled(false);
        panelCampos.add(cmbDevedor, gbc);
        
        // Data
        gbc.gridx = 0; gbc.gridy = 2; gbc.fill = GridBagConstraints.NONE; gbc.weightx = 0;
        panelCampos.add(new JLabel("Data Atualização:"), gbc);
        gbc.gridx = 1; gbc.fill = GridBagConstraints.HORIZONTAL; gbc.weightx = 1.0;
        txtData = new JFormattedTextField(new java.text.SimpleDateFormat("dd/MM/yyyy"));
        txtData.setEnabled(false);
        panelCampos.add(txtData, gbc);
        
        // Valor
        gbc.gridx = 0; gbc.gridy = 3; gbc.fill = GridBagConstraints.NONE; gbc.weightx = 0;
        panelCampos.add(new JLabel("Valor da Dívida:"), gbc);
        gbc.gridx = 1; gbc.fill = GridBagConstraints.HORIZONTAL; gbc.weightx = 1.0;
        txtValor = new JTextField(20);
        txtValor.setEnabled(false);
        panelCampos.add(txtValor, gbc);
        
        // Botões
        JPanel panelBotoes = new JPanel(new FlowLayout());
        btnAtualizar = new JButton("Atualizar");
        btnAtualizar.setEnabled(false);
        btnAtualizar.addActionListener(e -> atualizarDivida());
        btnLimpar = new JButton("Limpar");
        btnLimpar.addActionListener(e -> limparCampos());
        
        panelBotoes.add(btnAtualizar);
        panelBotoes.add(btnLimpar);
        
        add(panelBusca, BorderLayout.NORTH);
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
    
    private void buscarDivida() {
        try {
            int codigo = Integer.parseInt(txtCodigo.getText().trim());
            dividaSelecionada = controller.buscarPorId(codigo);
            if (dividaSelecionada != null) {
                // Selecionar credor e devedor nos comboboxes
                selecionarClienteNoCombo(cmbCredor, dividaSelecionada.getCredor());
                selecionarClienteNoCombo(cmbDevedor, dividaSelecionada.getDevedor());
                
                txtData.setValue(dividaSelecionada.getDataAtualizacao());
                txtValor.setText(String.format("%.2f", dividaSelecionada.getValorDivida()));
                
                // Habilitar campos
                cmbCredor.setEnabled(true);
                cmbDevedor.setEnabled(true);
                txtData.setEnabled(true);
                txtValor.setEnabled(true);
                btnAtualizar.setEnabled(true);
            } else {
                JOptionPane.showMessageDialog(this, "Dívida não encontrada!", 
                    "Aviso", JOptionPane.WARNING_MESSAGE);
                limparCampos();
            }
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Código inválido!", "Erro", JOptionPane.ERROR_MESSAGE);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Erro ao buscar dívida: " + e.getMessage(), 
                "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    private void selecionarClienteNoCombo(JComboBox<Cliente> combo, Cliente cliente) {
        for (int i = 0; i < combo.getItemCount(); i++) {
            Cliente c = combo.getItemAt(i);
            if (c.getIdCliente() == cliente.getIdCliente()) {
                combo.setSelectedIndex(i);
                break;
            }
        }
    }
    
    private void atualizarDivida() {
        try {
            if (validarCampos()) {
                Cliente credor = (Cliente) cmbCredor.getSelectedItem();
                Cliente devedor = (Cliente) cmbDevedor.getSelectedItem();
                Date data = (Date) txtData.getValue();
                double valor = Double.parseDouble(txtValor.getText().replace(",", "."));
                
                dividaSelecionada.setCredor(credor);
                dividaSelecionada.setDevedor(devedor);
                dividaSelecionada.setDataAtualizacao(data);
                dividaSelecionada.setValorDivida(valor);
                
                controller.atualizarDivida(dividaSelecionada);
                JOptionPane.showMessageDialog(this, "Dívida atualizada com sucesso!");
                limparCampos();
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Erro ao atualizar dívida: " + e.getMessage(), 
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
        Cliente credor = (Cliente) cmbCredor.getSelectedItem();
        Cliente devedor = (Cliente) cmbDevedor.getSelectedItem();
        if (credor.getIdCliente() == devedor.getIdCliente()) {
            JOptionPane.showMessageDialog(this, "O credor deve ser diferente do devedor!", 
                "Validação", JOptionPane.WARNING_MESSAGE);
            return false;
        }
        return true;
    }
    
    private void limparCampos() {
        txtCodigo.setText("");
        cmbCredor.setSelectedIndex(0);
        cmbDevedor.setSelectedIndex(0);
        txtData.setValue(new Date());
        txtValor.setText("");
        dividaSelecionada = null;
        btnAtualizar.setEnabled(false);
        
        // Desabilitar campos
        cmbCredor.setEnabled(false);
        cmbDevedor.setEnabled(false);
        txtData.setEnabled(false);
        txtValor.setEnabled(false);
    }
}

