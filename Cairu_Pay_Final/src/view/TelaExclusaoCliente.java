package view;

import controller.ClienteController;
import javax.swing.*;
import java.awt.*;

public class TelaExclusaoCliente extends JInternalFrame {
    private JTextField txtId;
    private JButton btnExcluir, btnBuscar;
    private JTextArea txtDados;
    private ClienteController controller;
    
    public TelaExclusaoCliente() {
        controller = new ClienteController();
        initComponents();
    }
    
    private void initComponents() {
        setSize(600, 400);
        setLayout(new BorderLayout());
        
        JPanel panelBusca = new JPanel(new FlowLayout());
        panelBusca.add(new JLabel("ID do Cliente:"));
        txtId = new JTextField(10);
        btnBuscar = new JButton("Buscar");
        btnBuscar.addActionListener(e -> buscarCliente());
        panelBusca.add(txtId);
        panelBusca.add(btnBuscar);
        
        txtDados = new JTextArea(10, 40);
        txtDados.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(txtDados);
        
        JPanel panelBotoes = new JPanel(new FlowLayout());
        btnExcluir = new JButton("Excluir");
        btnExcluir.setEnabled(false);
        btnExcluir.addActionListener(e -> excluirCliente());
        panelBotoes.add(btnExcluir);
        
        add(panelBusca, BorderLayout.NORTH);
        add(scrollPane, BorderLayout.CENTER);
        add(panelBotoes, BorderLayout.SOUTH);
    }
    
    private void buscarCliente() {
        try {
            int id = Integer.parseInt(txtId.getText());
            model.Cliente cliente = controller.buscarPorId(id);
            if (cliente != null) {
                txtDados.setText("ID: " + cliente.getIdCliente() + "\n" +
                               "Nome: " + cliente.getNomeCliente() + "\n" +
                               "Endereço: " + cliente.getEndereco() + "\n" +
                               "UF: " + cliente.getUf() + "\n" +
                               "Telefone: " + cliente.getTelefone() + "\n" +
                               "Documento: " + cliente.getDocumento() + "\n" +
                               "E-mail: " + cliente.getEmail());
                btnExcluir.setEnabled(true);
            } else {
                JOptionPane.showMessageDialog(this, "Cliente não encontrado!", 
                    "Aviso", JOptionPane.WARNING_MESSAGE);
                txtDados.setText("");
                btnExcluir.setEnabled(false);
            }
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "ID inválido!", "Erro", JOptionPane.ERROR_MESSAGE);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Erro ao buscar cliente: " + e.getMessage(), 
                "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    private void excluirCliente() {
        try {
            int id = Integer.parseInt(txtId.getText());
            int resposta = JOptionPane.showConfirmDialog(this, 
                "Deseja realmente excluir este cliente?", "Confirmação", 
                JOptionPane.YES_NO_OPTION);
            if (resposta == JOptionPane.YES_OPTION) {
                controller.excluirCliente(id);
                JOptionPane.showMessageDialog(this, "Cliente excluído com sucesso!");
                txtId.setText("");
                txtDados.setText("");
                btnExcluir.setEnabled(false);
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Erro ao excluir cliente: " + e.getMessage(), 
                "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }
}


