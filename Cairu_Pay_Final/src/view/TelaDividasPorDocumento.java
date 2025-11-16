package view;

import controller.DividaController;
import model.Divida;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.text.SimpleDateFormat;
import java.util.List;

public class TelaDividasPorDocumento extends JInternalFrame {
    private JTextField txtDocumento;
    private JTable tabela;
    private DefaultTableModel modeloTabela;
    private DividaController controller;
    private SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
    
    public TelaDividasPorDocumento() {
        controller = new DividaController();
        initComponents();
    }
    
    private void initComponents() {
        setSize(1000, 500);
        setLayout(new BorderLayout());
        
        JPanel panelBusca = new JPanel(new FlowLayout());
        panelBusca.add(new JLabel("Documento do Devedor:"));
        txtDocumento = new JTextField(20);
        JButton btnBuscar = new JButton("Buscar");
        btnBuscar.addActionListener(e -> buscarDividas());
        panelBusca.add(txtDocumento);
        panelBusca.add(btnBuscar);
        
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
        
        add(panelBusca, BorderLayout.NORTH);
        add(scrollPane, BorderLayout.CENTER);
    }
    
    private void buscarDividas() {
        try {
            String documento = txtDocumento.getText().trim();
            if (documento.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Informe o documento!", 
                    "Validação", JOptionPane.WARNING_MESSAGE);
                return;
            }
            
            modeloTabela.setRowCount(0);
            List<Divida> dividas = controller.listarPorDocumento(documento);
            if (dividas.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Nenhuma dívida encontrada para este documento!", 
                    "Aviso", JOptionPane.INFORMATION_MESSAGE);
            } else {
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
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Erro ao buscar dívidas: " + e.getMessage(), 
                "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }
}

