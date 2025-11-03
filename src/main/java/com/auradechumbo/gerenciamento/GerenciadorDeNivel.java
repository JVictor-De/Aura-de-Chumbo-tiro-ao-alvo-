package com.auradechumbo.gerenciamento;

import com.auradechumbo.entidades.Jogador;

// Controla a progressão de níveis
public class GerenciadorDeNivel {
    private static final double FATOR_XP = 1.5;
    
    // verifica se o jogador pode subir de nível
    public boolean verificarSubidaDeNivel(Jogador jogador) {
        return jogador.getXp() >= jogador.getXpProximoNivel();
    }
    
    // calcula quanto XP precisa pro próximo nível
    public int calcularXpProximoNivel(int nivelAtual) {
        return (int)(100 * Math.pow(FATOR_XP, nivelAtual - 1));
    }
}
