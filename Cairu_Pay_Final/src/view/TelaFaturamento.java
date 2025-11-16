package view;

import controller.PagamentoController;
import javax.swing.*;
import java.awt.*;
import java.util.Date;

public class TelaFaturamento extends JInternalFrame {
    private JFormattedTextField txtDataInicio, txtDataFim;
    private JButton btnCalcular;
    private JLabel lblResultado;
    private PagamentoController controller;
    
    public TelaFaturamento() {
        controller = new PagamentoController();
        initComponents();
    }
    
    private void initComponents() {
        setSize(500, 300);
        setLayout(new BorderLayout());
        
        JPanel panelCampos = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.anchor = GridBagConstraints.WEST;
        
        // Data Início
        gbc.gridx = 0; gbc.gridy = 0;
        panelCampos.add(new JLabel("Data Início:"), gbc);
        gbc.gridx = 1; gbc.fill = GridBagConstraints.HORIZONTAL; gbc.weightx = 1.0;
        txtDataInicio = new JFormattedTextField(new java.text.SimpleDateFormat("dd/MM/yyyy"));
        txtDataInicio.setValue(new Date());
        panelCampos.add(txtDataInicio, gbc);
        
        // Data Fim
        gbc.gridx = 0; gbc.gridy = 1; gbc.fill = GridBagConstraints.NONE; gbc.weightx = 0;
        panelCampos.add(new JLabel("Data Fim:"), gbc);
        gbc.gridx = 1; gbc.fill = GridBagConstraints.HORIZONTAL; gbc.weightx = 1.0;
        txtDataFim = new JFormattedTextField(new java.text.SimpleDateFormat("dd/MM/yyyy"));
        txtDataFim.setValue(new Date());
        panelCampos.add(txtDataFim, gbc);
        
        // Botão Calcular
        btnCalcular = new JButton("Calcular Faturamento");
        btnCalcular.addActionListener(e -> calcularFaturamento());
        
        // Resultado
        lblResultado = new JLabel("Faturamento: R$ 0,00");
        lblResultado.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 16));
        
        JPanel panelResultado = new JPanel(new FlowLayout());
        panelResultado.add(lblResultado);
        
        JPanel panelBotoes = new JPanel(new FlowLayout());
        panelBotoes.add(btnCalcular);
        
        add(panelCampos, BorderLayout.NORTH);
        add(panelResultado, BorderLayout.CENTER);
        add(panelBotoes, BorderLayout.SOUTH);
    }
    
    private void calcularFaturamento() {
        try {
            Date dataInicio = (Date) txtDataInicio.getValue();
            Date dataFim = (Date) txtDataFim.getValue();
            
            if (dataInicio.after(dataFim)) {
                JOptionPane.showMessageDialog(this, "Data início deve ser anterior à data fim!", 
                    "Validação", JOptionPane.WARNING_MESSAGE);
                return;
            }
            
            double faturamento = controller.calcularFaturamento(dataInicio, dataFim);
            lblResultado.setText("Faturamento: R$ " + String.format("%.2f", faturamento));
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Erro ao calcular faturamento: " + e.getMessage(), 
                "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }
}

