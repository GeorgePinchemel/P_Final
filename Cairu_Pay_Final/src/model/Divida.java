package model;

import java.util.Date;

public class Divida {
    private int codigo;
    private Cliente credor;
    private Date dataAtualizacao;
    private double valorDivida;
    private Cliente devedor;

    public Divida() {
    }

    public Divida(int codigo, Cliente credor, Date dataAtualizacao, double valorDivida, Cliente devedor) {
        this.codigo = codigo;
        this.credor = credor;
        this.dataAtualizacao = dataAtualizacao;
        this.valorDivida = valorDivida;
        this.devedor = devedor;
    }

    public int getCodigo() {
        return codigo;
    }

    public void setCodigo(int codigo) {
        this.codigo = codigo;
    }

    public Cliente getCredor() {
        return credor;
    }

    public void setCredor(Cliente credor) {
        this.credor = credor;
    }

    public Date getDataAtualizacao() {
        return dataAtualizacao;
    }

    public void setDataAtualizacao(Date dataAtualizacao) {
        this.dataAtualizacao = dataAtualizacao;
    }

    public double getValorDivida() {
        return valorDivida;
    }

    public void setValorDivida(double valorDivida) {
        this.valorDivida = valorDivida;
    }

    public Cliente getDevedor() {
        return devedor;
    }

    public void setDevedor(Cliente devedor) {
        this.devedor = devedor;
    }
}


