package view;

import controller.ClienteController;
import model.Cliente;
import javax.swing.*;
import java.awt.*;

public class TelaAtualizarCliente extends JInternalFrame {
    private JTextField txtId, txtNome, txtEndereco, txtUF, txtTelefone, txtDocumento, txtEmail;
    private JButton btnBuscar, btnAtualizar, btnLimpar;
    private ClienteController controller;
    private Cliente clienteSelecionado;
    
    public TelaAtualizarCliente() {
        controller = new ClienteController();
        initComponents();
    }
    
    private void initComponents() {
        setTitle("Atualizar Cliente");
        setSize(600, 450);
        setLayout(new BorderLayout());
        
        // Painel de busca
        JPanel panelBusca = new JPanel(new FlowLayout(FlowLayout.LEFT));
        panelBusca.add(new JLabel("ID do Cliente:"));
        txtId = new JTextField(10);
        btnBuscar = new JButton("Buscar");
        btnBuscar.addActionListener(e -> buscarCliente());
        panelBusca.add(txtId);
        panelBusca.add(btnBuscar);
        
        // Painel de campos
        JPanel panelCampos = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.anchor = GridBagConstraints.WEST;
        
        // Nome
        gbc.gridx = 0; gbc.gridy = 0;
        panelCampos.add(new JLabel("Nome:"), gbc);
        gbc.gridx = 1; gbc.fill = GridBagConstraints.HORIZONTAL; gbc.weightx = 1.0;
        txtNome = new JTextField(30);
        txtNome.setEnabled(false);
        panelCampos.add(txtNome, gbc);
        
        // Endereço
        gbc.gridx = 0; gbc.gridy = 1; gbc.fill = GridBagConstraints.NONE; gbc.weightx = 0;
        panelCampos.add(new JLabel("Endereço:"), gbc);
        gbc.gridx = 1; gbc.fill = GridBagConstraints.HORIZONTAL; gbc.weightx = 1.0;
        txtEndereco = new JTextField(30);
        txtEndereco.setEnabled(false);
        panelCampos.add(txtEndereco, gbc);
        
        // UF
        gbc.gridx = 0; gbc.gridy = 2; gbc.fill = GridBagConstraints.NONE; gbc.weightx = 0;
        panelCampos.add(new JLabel("UF:"), gbc);
        gbc.gridx = 1; gbc.fill = GridBagConstraints.HORIZONTAL; gbc.weightx = 1.0;
        txtUF = new JTextField(2);
        txtUF.setEnabled(false);
        panelCampos.add(txtUF, gbc);
        
        // Telefone
        gbc.gridx = 0; gbc.gridy = 3; gbc.fill = GridBagConstraints.NONE; gbc.weightx = 0;
        panelCampos.add(new JLabel("Telefone:"), gbc);
        gbc.gridx = 1; gbc.fill = GridBagConstraints.HORIZONTAL; gbc.weightx = 1.0;
        txtTelefone = new JTextField(20);
        txtTelefone.setEnabled(false);
        panelCampos.add(txtTelefone, gbc);
        
        // Documento
        gbc.gridx = 0; gbc.gridy = 4; gbc.fill = GridBagConstraints.NONE; gbc.weightx = 0;
        panelCampos.add(new JLabel("Documento:"), gbc);
        gbc.gridx = 1; gbc.fill = GridBagConstraints.HORIZONTAL; gbc.weightx = 1.0;
        txtDocumento = new JTextField(20);
        txtDocumento.setEnabled(false);
        panelCampos.add(txtDocumento, gbc);
        
        // Email
        gbc.gridx = 0; gbc.gridy = 5; gbc.fill = GridBagConstraints.NONE; gbc.weightx = 0;
        panelCampos.add(new JLabel("E-mail:"), gbc);
        gbc.gridx = 1; gbc.fill = GridBagConstraints.HORIZONTAL; gbc.weightx = 1.0;
        txtEmail = new JTextField(30);
        txtEmail.setEnabled(false);
        panelCampos.add(txtEmail, gbc);
        
        // Botões
        JPanel panelBotoes = new JPanel(new FlowLayout());
        btnAtualizar = new JButton("Atualizar");
        btnAtualizar.setEnabled(false);
        btnAtualizar.addActionListener(e -> atualizarCliente());
        btnLimpar = new JButton("Limpar");
        btnLimpar.addActionListener(e -> limparCampos());
        
        panelBotoes.add(btnAtualizar);
        panelBotoes.add(btnLimpar);
        
        add(panelBusca, BorderLayout.NORTH);
        add(panelCampos, BorderLayout.CENTER);
        add(panelBotoes, BorderLayout.SOUTH);
    }
    
    private void buscarCliente() {
        try {
            int id = Integer.parseInt(txtId.getText().trim());
            clienteSelecionado = controller.buscarPorId(id);
            if (clienteSelecionado != null) {
                txtNome.setText(clienteSelecionado.getNomeCliente());
                txtEndereco.setText(clienteSelecionado.getEndereco());
                txtUF.setText(clienteSelecionado.getUf());
                txtTelefone.setText(clienteSelecionado.getTelefone());
                txtDocumento.setText(clienteSelecionado.getDocumento());
                txtEmail.setText(clienteSelecionado.getEmail());
                
                // Habilitar campos
                txtNome.setEnabled(true);
                txtEndereco.setEnabled(true);
                txtUF.setEnabled(true);
                txtTelefone.setEnabled(true);
                txtDocumento.setEnabled(true);
                txtEmail.setEnabled(true);
                btnAtualizar.setEnabled(true);
            } else {
                JOptionPane.showMessageDialog(this, "Cliente não encontrado!", 
                    "Aviso", JOptionPane.WARNING_MESSAGE);
                limparCampos();
            }
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "ID inválido!", "Erro", JOptionPane.ERROR_MESSAGE);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Erro ao buscar cliente: " + e.getMessage(), 
                "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    private void atualizarCliente() {
        try {
            if (validarCampos()) {
                clienteSelecionado.setNomeCliente(txtNome.getText());
                clienteSelecionado.setEndereco(txtEndereco.getText());
                clienteSelecionado.setUf(txtUF.getText());
                clienteSelecionado.setTelefone(txtTelefone.getText());
                clienteSelecionado.setDocumento(txtDocumento.getText());
                clienteSelecionado.setEmail(txtEmail.getText());
                
                controller.atualizarCliente(clienteSelecionado);
                JOptionPane.showMessageDialog(this, "Cliente atualizado com sucesso!");
                limparCampos();
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Erro ao atualizar cliente: " + e.getMessage(), 
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
        txtId.setText("");
        txtNome.setText("");
        txtEndereco.setText("");
        txtUF.setText("");
        txtTelefone.setText("");
        txtDocumento.setText("");
        txtEmail.setText("");
        clienteSelecionado = null;
        btnAtualizar.setEnabled(false);
        
        // Desabilitar campos
        txtNome.setEnabled(false);
        txtEndereco.setEnabled(false);
        txtUF.setEnabled(false);
        txtTelefone.setEnabled(false);
        txtDocumento.setEnabled(false);
        txtEmail.setEnabled(false);
    }
}

