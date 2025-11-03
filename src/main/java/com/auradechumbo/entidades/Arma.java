package com.auradechumbo.entidades;

// Classe base pra todas as armas (abstrata)
public abstract class Arma {
    protected String nome;
    protected int dano;
    protected double alcanceMaximo;
    protected int nivelMinimo;
    
    public Arma(String nome, int dano, double alcanceMaximo, int nivelMinimo) {
        this.nome = nome;
        this.dano = dano;
        this.alcanceMaximo = alcanceMaximo;
        this.nivelMinimo = nivelMinimo;
    }
    
    // cada tipo de arma implementa seu próprio atirar
    public abstract Projetil atirar(double origemX, double origemY, double direcao);
    
    // getters
    public String getNome() { return nome; }
    public int getDano() { return dano; }
    public double getAlcanceMaximo() { return alcanceMaximo; }
    public int getNivelMinimo() { return nivelMinimo; }
}
