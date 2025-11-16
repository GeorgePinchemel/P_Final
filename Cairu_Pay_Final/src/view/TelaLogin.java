package view;

import controller.UsuarioController;
import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyAdapter;

public class TelaLogin extends JFrame {
    private JTextField txtLogin;
    private JPasswordField txtSenha;
    private JButton btnEntrar, btnSair;
    private UsuarioController controller;
    
    public TelaLogin() {
        controller = new UsuarioController();
        initComponents();
    }
    
    private void initComponents() {
        setTitle("CairuPay - Login");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(400, 250);
        setLocationRelativeTo(null);
        setResizable(false);
        
        JPanel panelPrincipal = new JPanel(new BorderLayout(10, 10));
        panelPrincipal.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        
        // Painel de campos
        JPanel panelCampos = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.anchor = GridBagConstraints.WEST;
        
        // Título
        JLabel lblTitulo = new JLabel("Sistema de Cobrança - CairuPay");
        lblTitulo.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 16));
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        panelCampos.add(lblTitulo, gbc);
        
        // Login
        gbc.gridwidth = 1;
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.anchor = GridBagConstraints.WEST;
        panelCampos.add(new JLabel("Login:"), gbc);
        gbc.gridx = 1;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 1.0;
        txtLogin = new JTextField(20);
        panelCampos.add(txtLogin, gbc);
        
        // Senha
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.fill = GridBagConstraints.NONE;
        gbc.weightx = 0;
        panelCampos.add(new JLabel("Senha:"), gbc);
        gbc.gridx = 1;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 1.0;
        txtSenha = new JPasswordField(20);
        panelCampos.add(txtSenha, gbc);
        
        // Botões
        JPanel panelBotoes = new JPanel(new FlowLayout());
        btnEntrar = new JButton("Entrar");
        btnSair = new JButton("Sair");
        
        btnEntrar.addActionListener(e -> fazerLogin());
        btnSair.addActionListener(e -> System.exit(0));
        
        // Adicionar ação de Enter nos campos
        KeyAdapter enterListener = new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                    fazerLogin();
                }
            }
        };
        
        txtLogin.addKeyListener(enterListener);
        txtSenha.addKeyListener(enterListener);
        
        panelBotoes.add(btnEntrar);
        panelBotoes.add(btnSair);
        
        panelPrincipal.add(panelCampos, BorderLayout.CENTER);
        panelPrincipal.add(panelBotoes, BorderLayout.SOUTH);
        
        add(panelPrincipal);
        
        // Focar no campo de login ao abrir
        SwingUtilities.invokeLater(() -> txtLogin.requestFocus());
    }
    
    private void fazerLogin() {
        try {
            String login = txtLogin.getText().trim();
            String senha = new String(txtSenha.getPassword());
            
            if (login.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Preencha o login!", 
                    "Validação", JOptionPane.WARNING_MESSAGE);
                txtLogin.requestFocus();
                return;
            }
            
            if (senha.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Preencha a senha!", 
                    "Validação", JOptionPane.WARNING_MESSAGE);
                txtSenha.requestFocus();
                return;
            }
            
            if (controller.autenticar(login, senha)) {
                // Login bem-sucedido - abrir tela principal
                this.dispose();
                SwingUtilities.invokeLater(() -> {
                    TelaPrincipal telaPrincipal = new TelaPrincipal();
                    telaPrincipal.setVisible(true);
                });
            } else {
                JOptionPane.showMessageDialog(this, "Login ou senha inválidos!", 
                    "Erro de Autenticação", JOptionPane.ERROR_MESSAGE);
                txtSenha.setText("");
                txtSenha.requestFocus();
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Erro ao fazer login: " + e.getMessage(), 
                "Erro", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        }
    }
    
    public static void main(String[] args) {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        SwingUtilities.invokeLater(() -> {
            new TelaLogin().setVisible(true);
        });
    }
}

