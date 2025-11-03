package com.auradechumbo.entidades;

import com.auradechumbo.ambiente.Ponto;

// Alvos que podem ser estáticos ou móveis
public class Alvo {
    private String id;
    private Ponto posicao;
    private boolean atingido;
    private int pontosXP;
    private boolean movel;
    private double velocidade;
    private double direcao;
    private int resistencia; // quantos tiros precisa
    
    // construtor pra alvo estático simples
    public Alvo(String id, double x, double y, int pontosXP) {
        this.id = id;
        this.posicao = new Ponto(x, y);
        this.atingido = false;
        this.pontosXP = pontosXP;
        this.movel = false;
        this.velocidade = 0;
        this.direcao = 0;
        this.resistencia = 1;
    }
    
    // construtor pra alvo móvel
    public Alvo(String id, double x, double y, int pontosXP, double velocidade, double direcao) {
        this(id, x, y, pontosXP);
        this.movel = true;
        this.velocidade = velocidade;
        this.direcao = direcao;
    }
    
    // construtor completo com tudo
    public Alvo(String id, double x, double y, int pontosXP, boolean movel, 
                double velocidade, double direcao, int resistencia) {
        this.id = id;
        this.posicao = new Ponto(x, y);
        this.atingido = false;
        this.pontosXP = pontosXP;
        this.movel = movel;
        this.velocidade = velocidade;
        this.direcao = direcao;
        this.resistencia = resistencia;
    }
    
    public void mover(double tempoDelta) {
        if(!movel) return; // só move se for móvel
        
        double radianos = Math.toRadians(direcao);
        double dx = Math.cos(radianos) * velocidade * tempoDelta;
        double dy = Math.sin(radianos) * velocidade * tempoDelta;
        posicao.transladar(dx, dy);
    }
    
    // quando o alvo é atingido
    public void serAtingido(Projetil projetil) {
        resistencia--;
        if(resistencia <= 0) {
            atingido = true;
        }
    }
    
    // getters
    public String getId() { return id; }
    public Ponto getPosicao() { return posicao; }
    public double getPosicaoX() { return posicao.getX(); }
    public double getPosicaoY() { return posicao.getY(); }
    public boolean foiAtingido() { return atingido; }
    public int getPontosXP() { return pontosXP; }
    public boolean isMovel() { return movel; }
    public double getVelocidade() { return velocidade; }
    public double getDirecao() { return direcao; }
    public int getResistencia() { return resistencia; }
}
