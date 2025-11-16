package view;

import controller.DividaController;
import model.Divida;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.text.SimpleDateFormat;
import java.util.List;

public class TelaDividasNaoPagas extends JInternalFrame {
    private JTable tabela;
    private DefaultTableModel modeloTabela;
    private DividaController controller;
    private SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
    
    public TelaDividasNaoPagas() {
        controller = new DividaController();
        initComponents();
        carregarDados();
    }
    
    private void initComponents() {
        setSize(1000, 500);
        setLayout(new BorderLayout());
        
        String[] colunas = {"Código", "Credor", "Devedor", "Data Atualização", "Valor"};
        modeloTabela = new DefaultTableModel(colunas, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        
        tabela = new JTable(modeloTabela);
        tabela.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        JScrollPane scrollPane = new JScrollPane(tabela);
        
        JButton btnAtualizar = new JButton("Atualizar");
        btnAtualizar.addActionListener(e -> carregarDados());
        
        JPanel panelBotoes = new JPanel(new FlowLayout());
        panelBotoes.add(btnAtualizar);
        
        add(scrollPane, BorderLayout.CENTER);
        add(panelBotoes, BorderLayout.SOUTH);
    }
    
    private void carregarDados() {
        try {
            modeloTabela.setRowCount(0);
            List<Divida> dividas = controller.listarNaoPagas();
            for (Divida d : dividas) {
                Object[] row = {
                    d.getCodigo(),
                    d.getCredor().getNomeCliente(),
                    d.getDevedor().getNomeCliente(),
                    sdf.format(d.getDataAtualizacao()),
                    String.format("%.2f", d.getValorDivida())
                };
                modeloTabela.addRow(row);
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Erro ao carregar dados: " + e.getMessage(), 
                "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }
}


