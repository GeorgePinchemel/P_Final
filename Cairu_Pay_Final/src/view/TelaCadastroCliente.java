package view;

import controller.ClienteController;
import javax.swing.*;
import java.awt.*;

public class TelaCadastroCliente extends JInternalFrame {
    private JTextField txtNome, txtEndereco, txtUF, txtTelefone, txtDocumento, txtEmail;
    private JButton btnSalvar, btnLimpar;
    private ClienteController controller;
    
    public TelaCadastroCliente() {
        controller = new ClienteController();
        initComponents();
    }
    
    private void initComponents() {
        setSize(600, 400);
        setLayout(new BorderLayout());
        
        JPanel panelCampos = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.anchor = GridBagConstraints.WEST;
        
        // Nome
        gbc.gridx = 0; gbc.gridy = 0;
        panelCampos.add(new JLabel("Nome:"), gbc);
        gbc.gridx = 1; gbc.fill = GridBagConstraints.HORIZONTAL; gbc.weightx = 1.0;
        txtNome = new JTextField(30);
        panelCampos.add(txtNome, gbc);
        
        // Endereço
        gbc.gridx = 0; gbc.gridy = 1; gbc.fill = GridBagConstraints.NONE; gbc.weightx = 0;
        panelCampos.add(new JLabel("Endereço:"), gbc);
        gbc.gridx = 1; gbc.fill = GridBagConstraints.HORIZONTAL; gbc.weightx = 1.0;
        txtEndereco = new JTextField(30);
        panelCampos.add(txtEndereco, gbc);
        
        // UF
        gbc.gridx = 0; gbc.gridy = 2; gbc.fill = GridBagConstraints.NONE; gbc.weightx = 0;
        panelCampos.add(new JLabel("UF:"), gbc);
        gbc.gridx = 1; gbc.fill = GridBagConstraints.HORIZONTAL; gbc.weightx = 1.0;
        txtUF = new JTextField(2);
        panelCampos.add(txtUF, gbc);
        
        // Telefone
        gbc.gridx = 0; gbc.gridy = 3; gbc.fill = GridBagConstraints.NONE; gbc.weightx = 0;
        panelCampos.add(new JLabel("Telefone:"), gbc);
        gbc.gridx = 1; gbc.fill = GridBagConstraints.HORIZONTAL; gbc.weightx = 1.0;
        txtTelefone = new JTextField(20);
        panelCampos.add(txtTelefone, gbc);
        
        // Documento
        gbc.gridx = 0; gbc.gridy = 4; gbc.fill = GridBagConstraints.NONE; gbc.weightx = 0;
        panelCampos.add(new JLabel("Documento:"), gbc);
        gbc.gridx = 1; gbc.fill = GridBagConstraints.HORIZONTAL; gbc.weightx = 1.0;
        txtDocumento = new JTextField(20);
        panelCampos.add(txtDocumento, gbc);
        
        // Email
        gbc.gridx = 0; gbc.gridy = 5; gbc.fill = GridBagConstraints.NONE; gbc.weightx = 0;
        panelCampos.add(new JLabel("E-mail:"), gbc);
        gbc.gridx = 1; gbc.fill = GridBagConstraints.HORIZONTAL; gbc.weightx = 1.0;
        txtEmail = new JTextField(30);
        panelCampos.add(txtEmail, gbc);
        
        // Botões
        JPanel panelBotoes = new JPanel(new FlowLayout());
        btnSalvar = new JButton("Salvar");
        btnLimpar = new JButton("Limpar");
        
        btnSalvar.addActionListener(e -> salvarCliente());
        btnLimpar.addActionListener(e -> limparCampos());
        
        panelBotoes.add(btnSalvar);
        panelBotoes.add(btnLimpar);
        
        add(panelCampos, BorderLayout.CENTER);
        add(panelBotoes, BorderLayout.SOUTH);
    }
    
    private void salvarCliente() {
        try {
            if (validarCampos()) {
                controller.cadastrarCliente(
                    txtNome.getText(),
                    txtEndereco.getText(),
                    txtUF.getText(),
                    txtTelefone.getText(),
                    txtDocumento.getText(),
                    txtEmail.getText()
                );
                JOptionPane.showMessageDialog(this, "Cliente cadastrado com sucesso!");
                limparCampos();
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Erro ao cadastrar cliente: " + e.getMessage(), 
                "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    private boolean validarCampos() {
        if (txtNome.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Preencha o nome!", "Validação", JOptionPane.WARNING_MESSAGE);
            return false;
        }
        if (txtDocumento.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Preencha o documento!", "Validação", JOptionPane.WARNING_MESSAGE);
            return false;
        }
        return true;
    }
    
    private void limparCampos() {
        txtNome.setText("");
        txtEndereco.setText("");
        txtUF.setText("");
        txtTelefone.setText("");
        txtDocumento.setText("");
        txtEmail.setText("");
    }
}

