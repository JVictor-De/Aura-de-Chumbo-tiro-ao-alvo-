package com.auradechumbo.gerenciamento;

import com.auradechumbo.entidades.*;
import com.auradechumbo.ambiente.*;
import java.util.ArrayList;
import java.util.List;

// Gerenciador principal do jogo
public class GerenciadorDeJogo {
    private Plano plano;
    private Jogador jogador;
    private GerenciadorDeNivel gerenciadorDeNivel;
    private List<Arma> armasDisponiveis;
    
    public GerenciadorDeJogo(double larguraPlano, double alturaPlano) {
        this.plano = new Plano(larguraPlano, alturaPlano);
        this.gerenciadorDeNivel = new GerenciadorDeNivel();
        this.armasDisponiveis = new ArrayList<>();
    }
    
    public void iniciarJogo(String nomeJogador) {
        // coloca jogador no centro do mapa
        double x = plano.getLargura() / 2;
        double y = plano.getAltura() / 2;
        this.jogador = new Jogador(nomeJogador, x, y);
    }
    
    public void adicionarArma(Arma arma) {
        armasDisponiveis.add(arma);
    }
    
    // criar alvo (factory method)
    public void criarAlvo(String tipo, double x, double y, int xp) {
        Alvo alvo;
        if(tipo.equals("movel")) {
            // alvo móvel com velocidade e direção aleatórias
            double vel = 50.0 + Math.random() * 50.0;
            double dir = Math.random() * 360.0;
            alvo = new Alvo("alvo_" + System.currentTimeMillis(), x, y, xp, vel, dir);
        } else {
            // alvo estático
            alvo = new Alvo("alvo_" + System.currentTimeMillis(), x, y, xp);
        }
        plano.adicionarAlvo(alvo);
    }
    
    public void jogadorAtirar(double direcao) {
        if(jogador != null && jogador.getArmaAtual() != null) {
            Projetil p = jogador.atirar(direcao);
            if(p != null) {
                plano.adicionarProjetil(p);
            }
        }
    }
    
    // loop principal do jogo
    public void atualizar(double tempoDelta) {
        // 1. mover alvos móveis
        for(Alvo alvo : plano.getAlvos()) {
            if(alvo.isMovel()) {
                alvo.mover(tempoDelta);
            }
        }
        
        // 2. mover projéteis
        for(Projetil p : plano.getProjeteis()) {
            p.mover(tempoDelta);
        }
        
        // 3. remover projéteis que expiraram
        plano.getProjeteis().removeIf(p -> !p.estaAtivo());
        
        // 4. detectar colisões
        List<Alvo> alvosAtingidos = new ArrayList<>();
        List<Projetil> projeteisUsados = new ArrayList<>();
        
        for(Projetil p : plano.getProjeteis()) {
            Alvo alvo = plano.detectarColisao(p);
            if(alvo != null) {
                alvo.serAtingido(p);
                alvosAtingidos.add(alvo);
                projeteisUsados.add(p);
                
                // dar xp pro jogador
                if(alvo.foiAtingido()) {
                    jogador.ganharXp(alvo.getPontosXP());
                    
                    // verificar level up
                    if(gerenciadorDeNivel.verificarSubidaDeNivel(jogador)) {
                        jogador.subirDeNivel();
                    }
                }
            }
        }
        
        // 5. remover alvos destruídos e projéteis usados
        plano.getAlvos().removeIf(a -> a.foiAtingido());
        plano.getProjeteis().removeAll(projeteisUsados);
    }
    
    public void encerrarJogo() {
        // limpar tudo
        plano.getAlvos().clear();
        plano.getProjeteis().clear();
    }
    
    // getters
    public Jogador getJogador() { return jogador; }
    public Plano getPlano() { return plano; }
    public GerenciadorDeNivel getGerenciadorDeNivel() { return gerenciadorDeNivel; }
    public List<Arma> getArmasDisponiveis() { return new ArrayList<>(armasDisponiveis); }
    public List<Alvo> getAlvosAtivos() { return plano.getAlvos(); }
    public List<Projetil> getProjeteisAtivos() { return plano.getProjeteis(); }
}
