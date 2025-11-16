package view;

import controller.DividaController;
import model.Divida;
import javax.swing.*;
import java.awt.*;
import java.text.SimpleDateFormat;

public class TelaExclusaoDivida extends JInternalFrame {
    private JTextField txtCodigo;
    private JButton btnExcluir, btnBuscar;
    private JTextArea txtDados;
    private DividaController controller;
    private SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
    
    public TelaExclusaoDivida() {
        controller = new DividaController();
        initComponents();
    }
    
    private void initComponents() {
        setSize(600, 400);
        setLayout(new BorderLayout());
        
        JPanel panelBusca = new JPanel(new FlowLayout());
        panelBusca.add(new JLabel("Código da Dívida:"));
        txtCodigo = new JTextField(10);
        btnBuscar = new JButton("Buscar");
        btnBuscar.addActionListener(e -> buscarDivida());
        panelBusca.add(txtCodigo);
        panelBusca.add(btnBuscar);
        
        txtDados = new JTextArea(10, 40);
        txtDados.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(txtDados);
        
        JPanel panelBotoes = new JPanel(new FlowLayout());
        btnExcluir = new JButton("Excluir");
        btnExcluir.setEnabled(false);
        btnExcluir.addActionListener(e -> excluirDivida());
        panelBotoes.add(btnExcluir);
        
        add(panelBusca, BorderLayout.NORTH);
        add(scrollPane, BorderLayout.CENTER);
        add(panelBotoes, BorderLayout.SOUTH);
    }
    
    private void buscarDivida() {
        try {
            int codigo = Integer.parseInt(txtCodigo.getText());
            Divida divida = controller.buscarPorId(codigo);
            if (divida != null) {
                txtDados.setText("Código: " + divida.getCodigo() + "\n" +
                               "Credor: " + divida.getCredor().getNomeCliente() + "\n" +
                               "Devedor: " + divida.getDevedor().getNomeCliente() + "\n" +
                               "Data Atualização: " + sdf.format(divida.getDataAtualizacao()) + "\n" +
                               "Valor: R$ " + String.format("%.2f", divida.getValorDivida()));
                btnExcluir.setEnabled(true);
            } else {
                JOptionPane.showMessageDialog(this, "Dívida não encontrada!", 
                    "Aviso", JOptionPane.WARNING_MESSAGE);
                txtDados.setText("");
                btnExcluir.setEnabled(false);
            }
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Código inválido!", "Erro", JOptionPane.ERROR_MESSAGE);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Erro ao buscar dívida: " + e.getMessage(), 
                "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    private void excluirDivida() {
        try {
            int codigo = Integer.parseInt(txtCodigo.getText());
            int resposta = JOptionPane.showConfirmDialog(this, 
                "Deseja realmente excluir esta dívida?", "Confirmação", 
                JOptionPane.YES_NO_OPTION);
            if (resposta == JOptionPane.YES_OPTION) {
                controller.excluirDivida(codigo);
                JOptionPane.showMessageDialog(this, "Dívida excluída com sucesso!");
                txtCodigo.setText("");
                txtDados.setText("");
                btnExcluir.setEnabled(false);
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Erro ao excluir dívida: " + e.getMessage(), 
                "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }
}

