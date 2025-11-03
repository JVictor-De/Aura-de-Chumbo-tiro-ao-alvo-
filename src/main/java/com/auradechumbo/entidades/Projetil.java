package com.auradechumbo.entidades;

import com.auradechumbo.ambiente.Ponto;

// Projétil que sai quando atira
public class Projetil {
    private String id;
    private Ponto posicao;
    private double direcao;
    private double velocidade;
    private int dano;
    private double alcanceRestante;
    
    public Projetil(String id, double x, double y, double direcao, 
                    double velocidade, int dano, double alcanceMaximo) {
        this.id = id;
        this.posicao = new Ponto(x, y);
        this.direcao = direcao;
        this.velocidade = velocidade;
        this.dano = dano;
        this.alcanceRestante = alcanceMaximo;
    }
    
    public void mover(double tempoDelta) {
        double distancia = velocidade * tempoDelta;
        
        double radianos = Math.toRadians(direcao);
        double dx = Math.cos(radianos) * distancia;
        double dy = Math.sin(radianos) * distancia;
        
        posicao.transladar(dx, dy);
        alcanceRestante -= distancia;
    }
    
    public boolean estaAtivo() {
        return alcanceRestante > 0;
    }
    
    // getters
    public String getId() { return id; }
    public Ponto getPosicao() { return posicao; }
    public double getPosicaoX() { return posicao.getX(); }
    public double getPosicaoY() { return posicao.getY(); }
    public double getDirecao() { return direcao; }
    public double getVelocidade() { return velocidade; }
    public int getDano() { return dano; }
    public double getAlcanceRestante() { return alcanceRestante; }
}
