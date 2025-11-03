package com.auradechumbo.ambiente;

// Ponto 2D no plano
public class Ponto {
    private double x;
    private double y;
    
    public Ponto(double x, double y) {
        this.x = x;
        this.y = y;
    }
    
    // modifica o ponto atual
    public void transladar(double dx, double dy) {
        this.x += dx;
        this.y += dy;
    }
    
    // cria um novo ponto deslocado
    public Ponto mover(double direcao, double distancia) {
        double radianos = Math.toRadians(direcao);
        double novoX = this.x + Math.cos(radianos) * distancia;
        double novoY = this.y + Math.sin(radianos) * distancia;
        return new Ponto(novoX, novoY);
    }
    
    // calcula distância euclidiana
    public double distanciaPara(Ponto outro) {
        double dx = outro.x - this.x;
        double dy = outro.y - this.y;
        return Math.sqrt(dx * dx + dy * dy);
    }
    
    public double getX() { return x; }
    public double getY() { return y; }
}