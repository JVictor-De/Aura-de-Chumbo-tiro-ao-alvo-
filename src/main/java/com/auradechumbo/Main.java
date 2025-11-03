package com.auradechumbo;

import com.auradechumbo.entidades.*;
import com.auradechumbo.gerenciamento.GerenciadorDeJogo;

// Programa principal
public class Main {
    public static void main(String[] args) {
        // criar o mundo do jogo (800x600)
        GerenciadorDeJogo jogo = new GerenciadorDeJogo(800, 600);
        
        jogo.iniciarJogo("Jogador1");
        System.out.println("Jogo iniciado!");
        
        // criar as armas de fogo
        ArmaFogo pistola = new ArmaFogo("Pistola", 10, 200.0, 1, 100.0);
        ArmaFogo rifle = new ArmaFogo("Rifle", 25, 400.0, 3, 150.0);
        ArmaFogo sniper = new ArmaFogo("Sniper", 50, 800.0, 5, 200.0);
        
        // armas corpo a corpo
        ArmaBranca faca = new ArmaBranca("Faca", 15, 1);
        ArmaBranca espada = new ArmaBranca("Espada", 30, 2);
        
        // adicionar no arsenal
        jogo.adicionarArma(pistola);
        jogo.adicionarArma(rifle);
        jogo.adicionarArma(sniper);
        jogo.adicionarArma(faca);
        jogo.adicionarArma(espada);
        
        // dar uma arma inicial pro jogador
        Jogador jogador = jogo.getJogador();
        jogador.equiparArma(pistola);
        System.out.println("Jogador equipou: " + pistola.getNome());
        
        // testando arma branca
        System.out.println("\nTestando arma branca:");
        jogador.equiparArma(faca);
        System.out.println("Jogador equipou: " + faca.getNome());
        System.out.println("Alcance da faca: " + faca.getAlcanceMaximo() + " unidades");
        System.out.println("Dano da faca: " + faca.getDano());
        
        // criar uns alvos pra testar
        jogo.criarAlvo("estatico", 100, 100, 50);
        jogo.criarAlvo("movel", 300, 200, 75);
        jogo.criarAlvo("movel", 500, 400, 100);
        System.out.println("Alvos criados: " + jogo.getAlvosAtivos().size());
        
        // rodar alguns frames pra testar
        System.out.println("\n=== Simulando 5 frames do jogo ===");
        for(int frame = 1; frame <= 5; frame++) {
            System.out.println("\n--- Frame " + frame + " ---");
            
            // atirar de vez em quando
            if(frame % 2 == 1) {
                jogo.jogadorAtirar(45.0);
                System.out.println("Jogador atirou!");
            }
            
            // atualizar (aproximadamente 60 FPS)
            jogo.atualizar(0.016);
            
            // printar status
            System.out.println("Alvos ativos: " + jogo.getAlvosAtivos().size());
            System.out.println("Projéteis ativos: " + jogo.getProjeteisAtivos().size());
            System.out.println("XP do jogador: " + jogador.getXp());
            System.out.println("Nível do jogador: " + jogador.getNivel());
        }
        
        // testar sistema de level up
        System.out.println("\n=== Testando progressão de nível ===");
        jogador.ganharXp(100);
        System.out.println("XP atual: " + jogador.getXp());
        
        if(jogo.getGerenciadorDeNivel().verificarSubidaDeNivel(jogador)) {
            jogador.subirDeNivel();
            System.out.println("Jogador subiu para nível " + jogador.getNivel() + "!");
        }
        
        // tentar equipar rifle (precisa de nível 3)
        System.out.println("\nTentando equipar Rifle (requer nível 3)...");
        if(jogador.equiparArma(rifle)) {
            System.out.println("Rifle equipado!");
        } else {
            System.out.println("Nível insuficiente. Nível atual: " + jogador.getNivel());
        }
        
        // dar XP suficiente pra chegar no nível 3
        jogador.ganharXp(250);
        if(jogo.getGerenciadorDeNivel().verificarSubidaDeNivel(jogador)) {
            jogador.subirDeNivel();
            System.out.println("Jogador subiu para nível " + jogador.getNivel() + "!");
        }
        
        // tentar de novo
        if(jogador.equiparArma(rifle)) {
            System.out.println("Rifle equipado com sucesso!");
            System.out.println("Alcance do rifle: " + rifle.getAlcanceMaximo());
            System.out.println("Dano do rifle: " + rifle.getDano());
        }
        
        // comparação entre tipos de arma
        System.out.println("\n=== Comparando Armas ===");
        System.out.println("\nArmas de Fogo:");
        System.out.println("- Pistola: Dano=" + pistola.getDano() + ", Alcance=" + pistola.getAlcanceMaximo() + ", Velocidade=" + pistola.getVelocidadeProjetil());
        System.out.println("- Rifle: Dano=" + rifle.getDano() + ", Alcance=" + rifle.getAlcanceMaximo() + ", Velocidade=" + rifle.getVelocidadeProjetil());
        
        System.out.println("\nArmas Brancas:");
        System.out.println("- Faca: Dano=" + faca.getDano() + ", Alcance=" + faca.getAlcanceMaximo() + " (corpo a corpo)");
        System.out.println("- Espada: Dano=" + espada.getDano() + ", Alcance=" + espada.getAlcanceMaximo() + " (corpo a corpo)");
        
        System.out.println("\nNota: Armas brancas têm alcance fixo de 1.0 unidade.");
        
        jogo.encerrarJogo();
        System.out.println("\nJogo encerrado.");
    }
}
