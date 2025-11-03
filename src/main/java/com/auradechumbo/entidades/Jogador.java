package com.auradechumbo.entidades;

import com.auradechumbo.ambiente.Ponto;
import java.util.ArrayList;
import java.util.List;

// Classe que representa o jogador
public class Jogador {
    private String id;
    private Ponto posicao;
    private int nivel;
    private int xp;
    private int xpProximoNivel;
    private List<Arma> armasEquipadas;
    private Arma armaAtual;
    
    public Jogador(String id, double x, double y) {
        this.id = id;
        this.posicao = new Ponto(x, y);
        this.nivel = 1;
        this.xp = 0;
        this.xpProximoNivel = 100; // xp necessário pro nível 2
        this.armasEquipadas = new ArrayList<>();
        this.armaAtual = null;
    }
    
    // move o jogador numa direção
    public void mover(double direcao, double distancia) {
        double radianos = Math.toRadians(direcao);
        double dx = Math.cos(radianos) * distancia;
        double dy = Math.sin(radianos) * distancia;
        
        posicao.transladar(dx, dy);
    }
    
    // equipar uma arma (verifica o nível)
    public boolean equiparArma(Arma arma) {
        if(this.nivel >= arma.getNivelMinimo()) {
            if(!armasEquipadas.contains(arma)) {
                armasEquipadas.add(arma);
            }
            this.armaAtual = arma;
            return true;
        }
        return false;
    }
    
    // atirar com a arma atual
    public Projetil atirar(double direcao) {
        if(armaAtual != null) {
            return armaAtual.atirar(posicao.getX(), posicao.getY(), direcao);
        }
        return null;
    }
    
    // ganhar experiência
    public void ganharXp(int pontos) {
        this.xp += pontos;
    }
    
    // subir de nível
    public void subirDeNivel() {
        this.nivel++;
        this.xp = 0;
        this.xpProximoNivel = (int)(xpProximoNivel * 1.5); // aumenta o xp necessário
    }
    
    // getters
    public String getId() { return id; }
    public Ponto getPosicao() { return posicao; }
    public double getPosicaoX() { return posicao.getX(); }
    public double getPosicaoY() { return posicao.getY(); }
    public int getNivel() { return nivel; }
    public int getXp() { return xp; }
    public int getXpProximoNivel() { return xpProximoNivel; }
    public List<Arma> getArmasEquipadas() { return new ArrayList<>(armasEquipadas); }
    public Arma getArmaAtual() { return armaAtual; }
}
