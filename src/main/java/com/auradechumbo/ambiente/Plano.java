package com.auradechumbo.ambiente;

import com.auradechumbo.entidades.Alvo;
import com.auradechumbo.entidades.Projetil;
import java.util.ArrayList;
import java.util.List;

// O mapa do jogo
public class Plano {
    private static final double RAIO_COLISAO = 5.0;
    
    private double largura;
    private double altura;
    private List<Alvo> alvos;
    private List<Projetil> projeteis;
    
    public Plano(double largura, double altura) {
        this.largura = largura;
        this.altura = altura;
        this.alvos = new ArrayList<>();
        this.projeteis = new ArrayList<>();
    }
    
    public void adicionarAlvo(Alvo alvo) {
        alvos.add(alvo);
    }
    
    public void adicionarProjetil(Projetil projetil) {
        projeteis.add(projetil);
    }
    
    // detecta colisão entre projétil e alvos
    public Alvo detectarColisao(Projetil projetil) {
        for(Alvo alvo : alvos) {
            if(alvo.foiAtingido()) continue;
            
            double distancia = projetil.getPosicao().distanciaPara(alvo.getPosicao());
            if(distancia <= RAIO_COLISAO) {
                return alvo;
            }
        }
        return null;
    }
    
    public double getLargura() { return largura; }
    public double getAltura() { return altura; }
    public List<Alvo> getAlvos() { return alvos; }
    public List<Projetil> getProjeteis() { return projeteis; }
}