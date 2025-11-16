package view;

import javax.swing.*;
import java.awt.*;

public class TelaPrincipal extends JFrame {
    private JDesktopPane desktopPane;
    private JMenuBar menuBar;
    
    public TelaPrincipal() {
        initComponents();
    }
    
    private void initComponents() {
        setTitle("CairuPay - Sistema de Cobrança");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1200, 800);
        setLocationRelativeTo(null);
        
        desktopPane = new JDesktopPane();
        desktopPane.setBackground(new Color(240, 240, 240));
        setContentPane(desktopPane);
        
        criarMenu();
    }
    
    private void criarMenu() {
        menuBar = new JMenuBar();
        
        // Menu Cliente
        JMenu menuCliente = new JMenu("Cliente");
        JMenuItem itemCadastrarCliente = new JMenuItem("Cadastrar");
        JMenuItem itemConsultarCliente = new JMenuItem("Consultar");
        JMenuItem itemExcluirCliente = new JMenuItem("Excluir");
        
        itemCadastrarCliente.addActionListener(e -> abrirTela(new TelaCadastroCliente(), "Cadastro de Cliente"));
        itemConsultarCliente.addActionListener(e -> abrirTela(new TelaConsultaCliente(), "Consulta de Cliente"));
        itemExcluirCliente.addActionListener(e -> abrirTela(new TelaExclusaoCliente(), "Exclusão de Cliente"));
        
        menuCliente.add(itemCadastrarCliente);
        menuCliente.add(itemConsultarCliente);
        menuCliente.add(itemExcluirCliente);
        
        // Menu Dívida
        JMenu menuDivida = new JMenu("Dívida");
        JMenuItem itemCadastrarDivida = new JMenuItem("Cadastrar");
        JMenuItem itemConsultarDivida = new JMenuItem("Consultar");
        JMenuItem itemExcluirDivida = new JMenuItem("Excluir");
        JMenuItem itemDividasNaoPagas = new JMenuItem("Dívidas Não Pagas");
        JMenuItem itemDividasPorDocumento = new JMenuItem("Dívidas por Documento");
        
        itemCadastrarDivida.addActionListener(e -> abrirTela(new TelaCadastroDivida(), "Cadastro de Dívida"));
        itemConsultarDivida.addActionListener(e -> abrirTela(new TelaConsultaDivida(), "Consulta de Dívida"));
        itemExcluirDivida.addActionListener(e -> abrirTela(new TelaExclusaoDivida(), "Exclusão de Dívida"));
        itemDividasNaoPagas.addActionListener(e -> abrirTela(new TelaDividasNaoPagas(), "Dívidas Não Pagas"));
        itemDividasPorDocumento.addActionListener(e -> abrirTela(new TelaDividasPorDocumento(), "Dívidas por Documento"));
        
        menuDivida.add(itemCadastrarDivida);
        menuDivida.add(itemConsultarDivida);
        menuDivida.add(itemExcluirDivida);
        menuDivida.addSeparator();
        menuDivida.add(itemDividasNaoPagas);
        menuDivida.add(itemDividasPorDocumento);
        
        // Menu Pagamento
        JMenu menuPagamento = new JMenu("Pagamento");
        JMenuItem itemCadastrarPagamento = new JMenuItem("Cadastrar");
        JMenuItem itemConsultarPagamento = new JMenuItem("Consultar");
        JMenuItem itemExcluirPagamento = new JMenuItem("Excluir");
        
        itemCadastrarPagamento.addActionListener(e -> abrirTela(new TelaCadastroPagamento(), "Cadastro de Pagamento"));
        itemConsultarPagamento.addActionListener(e -> abrirTela(new TelaConsultaPagamento(), "Consulta de Pagamento"));
        itemExcluirPagamento.addActionListener(e -> abrirTela(new TelaExclusaoPagamento(), "Exclusão de Pagamento"));
        
        menuPagamento.add(itemCadastrarPagamento);
        menuPagamento.add(itemConsultarPagamento);
        menuPagamento.add(itemExcluirPagamento);
        
        // Menu Relatórios
        JMenu menuRelatorios = new JMenu("Relatórios");
        JMenuItem itemFaturamento = new JMenuItem("Faturamento por Período");
        
        itemFaturamento.addActionListener(e -> abrirTela(new TelaFaturamento(), "Faturamento por Período"));
        
        menuRelatorios.add(itemFaturamento);
        
        menuBar.add(menuCliente);
        menuBar.add(menuDivida);
        menuBar.add(menuPagamento);
        menuBar.add(menuRelatorios);
        
        setJMenuBar(menuBar);
    }
    
    private void abrirTela(JInternalFrame tela, String titulo) {
        tela.setTitle(titulo);
        tela.setVisible(true);
        tela.setClosable(true);
        tela.setMaximizable(true);
        tela.setResizable(true);
        
        // Centralizar a tela
        Dimension desktopSize = desktopPane.getSize();
        Dimension frameSize = tela.getSize();
        tela.setLocation((desktopSize.width - frameSize.width) / 2,
                        (desktopSize.height - frameSize.height) / 2);
        
        desktopPane.add(tela);
        try {
            tela.setSelected(true);
        } catch (java.beans.PropertyVetoException e) {
            e.printStackTrace();
        }
    }
    
}

