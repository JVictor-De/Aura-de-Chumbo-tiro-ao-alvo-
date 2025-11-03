package com.auradechumbo.entidades;

// Armas corpo a corpo (faca, espada, etc)
public class ArmaBranca extends Arma {
    
    public ArmaBranca(String nome, int dano, int nivelMinimo) {
        super(nome, dano, 1.0, nivelMinimo); // alcance sempre 1.0
    }
    
    @Override
    public Projetil atirar(double origemX, double origemY, double direcao) {
        // arma branca tem velocidade bem alta pra ser quase instantâneo
        return new Projetil(
            nome + "_proj_" + System.currentTimeMillis(),
            origemX,
            origemY,
            direcao,
            1000.0, // bem rápido
            dano,
            alcanceMaximo
        );
    }
}
