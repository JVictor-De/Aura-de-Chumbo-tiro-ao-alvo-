package com.auradechumbo.entidades;

// Armas de fogo (pistola, rifle, sniper)
public class ArmaFogo extends Arma {
    private double velocidadeProjetil;
    
    public ArmaFogo(String nome, int dano, double alcanceMaximo, 
                    int nivelMinimo, double velocidadeProjetil) {
        super(nome, dano, alcanceMaximo, nivelMinimo);
        this.velocidadeProjetil = velocidadeProjetil;
    }
    
    @Override
    public Projetil atirar(double origemX, double origemY, double direcao) {
        return new Projetil(
            nome + "_proj_" + System.currentTimeMillis(),
            origemX,
            origemY,
            direcao,
            velocidadeProjetil,
            dano,
            alcanceMaximo
        );
    }
    
    public double getVelocidadeProjetil() {
        return velocidadeProjetil;
    }
}
