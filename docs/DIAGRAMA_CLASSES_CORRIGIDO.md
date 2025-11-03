# Diagrama de Classes - Aura de Chumbo

## Visão Geral

Este documento apresenta o diagrama de classes **atualizado** do jogo "Aura de Chumbo" com todas as refatorações aplicadas.

### Principais Características:

1. **Alvo é classe concreta** com comportamento configurável via construtores
2. **Hierarquia de Armas**: `Arma` (abstrata) → `ArmaBranca` e `ArmaFogo` (herança)
3. **Classe Ponto** encapsula coordenadas X/Y e operações relacionadas
4. **Plano** (renomeado de PlanoDeJogo) gerencia o mundo 2D
5. **Refatoração completa** usando `Ponto` em vez de atributos separados
6. **Relacionamentos clarificados** entre classes

---

## I. Classes de Entidade

### 1. Jogador
Representa o participante humano do jogo.

**Atributos:**
- `- id: String`
- `- nome: String`
- `- posicao: Ponto`
- `- xp: int`
- `- nivel: int`
- `# armasEquipadas: List<Arma>`
- `# armaAtual: Arma`

**Métodos:**
- `+ Jogador(id: String, nome: String, posicaoX: double, posicaoY: double)`
- `+ mover(direcao: double, distancia: double): void`
- `+ ganharXp(valor: int): void`
- `+ subirDeNivel(): void`
- `+ equiparArma(arma: Arma): boolean`
- `+ atirar(direcao: double): Projetil`
- `+ getPosicao(): Ponto`
- `+ getPosicaoX(): double`
- `+ getPosicaoY(): double`
- `+ getNivel(): int`
- `+ getXp(): int`
- `+ getArmaAtual(): Arma`

**Relacionamentos:**
- **Composição** com `Ponto`
- **Agregação** com `Arma`
- **Dependência** com `Projetil` (cria via `atirar()`)

---

### 2. Alvo
Representa qualquer elemento que possa ser atingido pelo jogador.
Pode ser estático ou móvel dependendo do construtor usado.

**Atributos:**
- `- id: String`
- `- posicao: Ponto`
- `- pontosXP: int`
- `- atingido: boolean`
- `- movel: boolean`
- `- velocidade: double`
- `- direcao: double`
- `- resistencia: int`

**Construtores:**
```java
// Alvo estático simples (resistência = 1)
+ Alvo(id: String, posicaoX: double, posicaoY: double, pontosXP: int)

// Alvo móvel (resistência = 1)
+ Alvo(id: String, posicaoX: double, posicaoY: double, pontosXP: int, 
       velocidade: double, direcao: double)

// Alvo completo customizado
+ Alvo(id: String, posicaoX: double, posicaoY: double, pontosXP: int,
       movel: boolean, velocidade: double, direcao: double, resistencia: int)
```

**Métodos:**
- `+ serAtingido(projetil: Projetil): void`
- `+ mover(tempoDelta: double): void` (funciona apenas se `movel == true`)
- `+ getPosicao(): Ponto`
- `+ getPosicaoX(): double`
- `+ getPosicaoY(): double`
- `+ getPontosXP(): int`
- `+ isAtingido(): boolean`
- `+ isMovel(): boolean`

**Relacionamentos:**
- **Composição** com `Ponto`

---

### 3. Arma
Representa uma arma que o jogador pode equipar e usar.

**Atributos:**
- `- nome: String`
- `- dano: int`
- `- alcanceMaximo: double`
- `- nivelMinimo: int`
- `- velocidadeProjetil: double`

**Métodos:**
- `+ Arma(nome: String, dano: int, alcanceMaximo: double, nivelMinimo: int, velocidadeProjetil: double)`
- `+ atirar(origemX: double, origemY: double, direcao: double): Projetil`
- `+ getNome(): String`
- `+ getDano(): int`
- `+ getAlcanceMaximo(): double`
- `+ getNivelMinimo(): int`
- `+ getVelocidadeProjetil(): double`

**Relacionamentos:**
- **Dependência** com `Projetil` (cria via `atirar()`)

---

### 4. Projetil
Representa um disparo de uma arma em movimento.

**Atributos:**
- `- id: String`
- `- posicao: Ponto`
- `- direcao: double` (ângulo em graus)
- `- velocidade: double`
- `- dano: int`
- `- alcanceRestante: double`

**Métodos:**
- `+ Projetil(id: String, posicaoX: double, posicaoY: double, direcao: double, velocidade: double, dano: int, alcanceMaximo: double)`
- `+ mover(tempoDelta: double): void`
- `+ estaAtivo(): boolean`
- `+ getPosicao(): Ponto`
- `+ getPosicaoX(): double`
- `+ getPosicaoY(): double`
- `+ getDano(): int`

**Lógica de Alcance:**
- Inicializado com `alcanceRestante = alcanceMaximo` (da arma)
- A cada `mover()`, decrementa `alcanceRestante` pela distância percorrida
- Torna-se inativo quando `alcanceRestante <= 0`

**Relacionamentos:**
- **Composição** com `Ponto`

---

## II. Classes de Ambiente

### 7. Ponto
Classe utilitária que representa uma coordenada 2D (x, y) no plano do jogo.

**Atributos:**
- `- x: double`
- `- y: double`

**Métodos:**
- `+ Ponto(x: double, y: double)`
- `+ transladar(dx: double, dy: double): void` (mutável - modifica o ponto)
- `+ mover(direcao: double, distancia: double): Ponto` (imutável - retorna novo Ponto)
- `+ traduzir(dx: double, dy: double): Ponto` (imutável - retorna novo Ponto)
- `+ distanciaPara(outro: Ponto): double`
- `+ getX(): double`
- `+ getY(): double`
- `+ equals(Object): boolean`
- `+ hashCode(): int`
- `+ toString(): String`

**Uso:**
- Centraliza lógica de coordenadas em um único lugar
- Facilita cálculos de distância e movimento
- Usado por `Jogador`, `Alvo`, `Projetil` e `Plano`

---

### 8. Plano
Define os limites do mundo 2D e gerencia objetos no plano.

**Atributos:**
- `- largura: double`
- `- altura: double`
- `- alvos: List<Alvo>`
- `- projeteis: List<Projetil>`
- `- jogador: Jogador`

**Métodos:**
- `+ Plano(largura: double, altura: double)`
- `+ adicionarAlvo(alvo: Alvo): void`
- `+ removerAlvo(alvo: Alvo): void`
- `+ adicionarProjetil(projetil: Projetil): void`
- `+ removerProjetil(projetil: Projetil): void`
- `+ setJogador(jogador: Jogador): void`
- `+ estaDentroDosLimites(posicaoX: double, posicaoY: double): boolean`
- `+ getAlvosProximos(posicaoX: double, posicaoY: double, raio: double): List<Alvo>`
- `+ detectarColisao(projetil: Projetil): Alvo`
- `+ getLargura(): double`
- `+ getAltura(): double`
- `+ getAlvos(): List<Alvo>`
- `+ getProjeteis(): List<Projetil>`
- `+ getJogador(): Jogador`

**Lógica de Detecção de Colisão:**
```java
// Usa Ponto.distanciaPara() para calcular distância
double distancia = projetil.getPosicao().distanciaPara(alvo.getPosicao());
if (distancia <= RAIO_COLISAO) { // 5.0 unidades
    return alvo; // Colisão detectada
}
```

**Relacionamentos:**
- **Agregação** com `Alvo`, `Projetil`, `Jogador`
- **Usa** `Ponto` para cálculos

---

## III. Classes de Gerenciamento

### 9. GerenciadorDeJogo
Controla o fluxo principal do jogo e coordena interações.

**Atributos:**
- `- jogador: Jogador`
- `- alvosAtivos: List<Alvo>`
- `- projeteisAtivos: List<Projetil>`
- `- plano: Plano`
- `- gerenciadorDeNivel: GerenciadorDeNivel`
- `- jogoAtivo: boolean`

**Métodos:**
- `+ GerenciadorDeJogo(larguraPlano: double, alturaPlano: double)`
- `+ iniciarJogo(nomeJogador: String): void`
- `+ atualizar(tempoDelta: double): void`
- `+ criarAlvo(tipo: String, x: double, y: double, pontosXP: int): Alvo`
- `+ adicionarArma(arma: Arma): void`
- `+ jogadorAtirar(direcao: double): void`
- `+ moverJogador(direcao: double, distancia: double): void`
- `+ encerrarJogo(): void`
- `+ getJogador(): Jogador`
- `+ getAlvosAtivos(): List<Alvo>`
- `+ getProjeteisAtivos(): List<Projetil>`
- `+ getPlano(): Plano`
- `+ getGerenciadorDeNivel(): GerenciadorDeNivel`
- `+ isJogoAtivo(): boolean`

**Loop Principal `atualizar(tempoDelta)`:**
```
1. Para cada alvo ativo:
   - Se for móvel e não atingido: mover(tempoDelta)

2. Para cada projétil ativo:
   - mover(tempoDelta)
   - Se !estaAtivo(): remover
   - Se detectou colisão:
     * alvo.serAtingido(projetil)
     * jogador.ganharXp(alvo.getPontosXP())
     * Se verificarSubidaDeNivel(): jogador.subirDeNivel()
     * Remover projétil

3. Remover alvos onde isAtingido() == true
```

**Relacionamentos:**
- **Composição** com `Plano`, `GerenciadorDeNivel`
- **Agregação** com `Jogador`, `Alvo`, `Projetil`

---

### 10. GerenciadorDeNivel
Responsável pelas regras de progressão do jogador.

**Atributos:**
- `- xpPorNivel: Map<Integer, Integer>`
- `- armasPorNivel: Map<Integer, List<Arma>>`

**Métodos:**
- `+ GerenciadorDeNivel()`
- `+ verificarSubidaDeNivel(jogador: Jogador): boolean`
- `+ getNivelParaXp(xpAtual: int): int`
- `+ temNivelMinimoParaArma(jogador: Jogador, arma: Arma): boolean`
- `+ getXpParaProximoNivel(nivelAtual: int): int`
- `+ registrarArmaPorNivel(nivel: int, arma: Arma): void`
- `+ getArmasDesbloqueadasNoNivel(nivel: int): List<Arma>`

**Tabela de Progressão Padrão:**
- Nível 1 → 2: 100 XP
- Nível 2 → 3: 250 XP
- Nível 3 → 4: 500 XP
- Nível 4 → 5: 1000 XP
- Nível 5+: 2000 XP

**Relacionamentos:**
- **Usado por** `Jogador` e `GerenciadorDeJogo`

---

## Diagrama de Relacionamentos

```
┌─────────────────────┐
│  GerenciadorDeJogo  │ ◆──────► GerenciadorDeNivel
└─────────────────────┘
         │ ◆
         │ (composição)
         ▼
    ┌─────────┐
    │  Plano  │ ◇────► Ponto (uso em cálculos)
    └─────────┘
    │ ◇  │ ◇  │ ◇
    │    │    │ (agregação)
    ▼    ▼    ▼
┌──────┐┌──────┐┌──────────┐
│Jogador│ Alvo  │ Projetil │
└──────┘└──────┘└──────────┘
│ ◇ │ ◆ │ ◆     │ ◆
│   │   │       │
▼   ▼   ▼       ▼
┌──────┐┌──────┐┌──────┐
│ Arma ││Ponto ││Ponto │
└──────┘└──────┘└──────┘
   ▲ (abstrata)
   │
   ├──────────┬──────────┐
   │          │          │
ArmaBranca ArmaFogo  (outras)
   │          │
   └────┬─────┘
        │ (cria)
        ▼
   ┌──────────┐
   │ Projetil │
   └──────────┘
```

**Legenda:**
- `◆` = Composição (parte não existe sem o todo)
- `◇` = Agregação (parte pode existir independentemente)
- `▲` = Herança
- `→` = Dependência/Uso

---

## Hierarquia de Herança

### Arma (abstrata)
```
              Arma
               ▲
               │ (herança)
        ┌──────┴──────┐
        │             │
   ArmaBranca      ArmaFogo
   (corpo a corpo) (à distância)
   alcance = 1.0   alcance variável
   vel. fixa       vel. configurável
```

**Características:**
- **Arma** é abstrata - não pode ser instanciada diretamente
- **ArmaBranca** e **ArmaFogo** implementam `atirar()` de formas diferentes
- Polimorfismo: ambas podem ser tratadas como `Arma`

---

## Decisões de Design

### 1. **Alvo como Classe Concreta**
✅ Implementado com comportamento configurável:
- Construtores diferentes para alvos estáticos, móveis e customizados
- Atributo `movel` controla se o método `mover()` tem efeito
- Atributo `resistencia` define quantos acertos são necessários

**Vantagens:**
- Simplicidade: sem necessidade de herança
- Flexibilidade: comportamento definido em tempo de construção
- Fácil manutenção: toda lógica em uma classe

### 2. **Hierarquia de Armas com Herança**
✅ Implementado com classe abstrata `Arma`:
- **Arma** como base abstrata com método `atirar()` abstrato
- **ArmaBranca**: alcance fixo (1.0), velocidade alta fixa
- **ArmaFogo**: alcance e velocidade configuráveis

**Vantagens:**
- Polimorfismo: ambas podem ser tratadas como `Arma`
- Especialização: cada tipo implementa `atirar()` de forma apropriada
- Extensibilidade: fácil adicionar novos tipos (ArmaExplosiva, etc.)

### 3. **Classe Ponto para Coordenadas**
✅ Encapsula lógica de posicionamento:
- Substitui pares `posicaoX/posicaoY` por um único objeto
- Centraliza cálculos de distância e movimento
- Oferece métodos mutáveis e imutáveis conforme necessidade

**Benefícios:**
- Código mais limpo e orientado a objetos
- Reutilização de lógica matemática
- Facilita futuras extensões (vetores, ângulos, etc.)

### 4. **Renomeação PlanoDeJogo → Plano**
✅ Nomenclatura mais concisa:
- Nome mais curto e direto
- Mantém clareza do propósito
- Consistente com padrão de nomes do projeto

### 5. **Agregação vs. Composição**
- **Jogador ◆ Ponto**: Composição (posição é parte do jogador)
- **Jogador ◇ Arma**: Agregação (arma pode existir sem jogador)
- **GerenciadorDeJogo ◆ Plano**: Composição (plano existe apenas no contexto do jogo)
- **Plano ◇ Alvo/Projetil**: Agregação (plano gerencia, não "possui")

### 6. **Sistema de Alcance**
✅ Projétil herda alcance da arma:
```java
Arma pistola = new Arma("Pistola", 10, 200.0, 1, 100.0);
Projetil p = pistola.atirar(x, y, dir);
// p.alcanceRestante inicia com 200.0

p.mover(1.0); // Move por 1 segundo
// alcanceRestante é decrementado pela distância percorrida
```

---

## Exemplo de Uso Completo

```java
// 1. Criar gerenciador de jogo
GerenciadorDeJogo jogo = new GerenciadorDeJogo(800, 600);

// 2. Iniciar jogo
jogo.iniciarJogo("Player1");
Jogador jogador = jogo.getJogador();

// 3. Criar armas de fogo
ArmaFogo pistola = new ArmaFogo("Pistola", 10, 200.0, 1, 100.0);
ArmaFogo rifle = new ArmaFogo("Rifle", 25, 400.0, 3, 150.0);

// 4. Criar armas brancas
ArmaBranca faca = new ArmaBranca("Faca", 15, 1);
ArmaBranca espada = new ArmaBranca("Espada", 30, 2);

// 5. Adicionar armas ao jogo
jogo.adicionarArma(pistola);
jogo.adicionarArma(rifle);
jogo.adicionarArma(faca);
jogo.adicionarArma(espada);

// 6. Equipar arma
jogador.equiparArma(pistola); // Arma de fogo
// ou
jogador.equiparArma(faca);    // Arma branca

// 5. Criar alvos
jogo.criarAlvo("estatico", 100, 100, 50);  // Alvo fixo
jogo.criarAlvo("movel", 300, 200, 75);     // Alvo móvel

// 6. Loop do jogo (60 FPS)
while (jogo.isJogoAtivo()) {
    jogo.atualizar(0.016); // ~16ms por frame
    
    // Jogador atira
    if (deveAtirar) {
        jogo.jogadorAtirar(45.0); // Direção em graus
    }
    
    // Move jogador
    if (deveMovor) {
        jogo.moverJogador(0.0, 5.0); // Direita, 5 unidades
    }
}

// 7. Encerrar
jogo.encerrarJogo();
```

---

## Sistema de Coordenadas

### Convenções
- **Origem (0,0)**: Canto superior esquerdo
- **Eixo X**: Cresce para direita
- **Eixo Y**: Cresce para baixo
- **Direção**: Graus (0° = direita, 90° = baixo, 180° = esquerda, 270° = cima)

### Movimento
```java
// Fórmula usada em Ponto.transladar()
double radianos = Math.toRadians(direcao);
x += Math.cos(radianos) * distancia;
y += Math.sin(radianos) * distancia;
```

### Colisão
```java
// Distância euclidiana
double distancia = Math.sqrt(dx*dx + dy*dy);

// Usando Ponto:
double distancia = ponto1.distanciaPara(ponto2);

// Colisão se <= RAIO_COLISAO (5.0)
```

---

## Arquivos de Referência

- **Implementação completa**: `src/main/java/com/auradechumbo/`
- **Exemplo funcional**: `src/main/java/com/auradechumbo/Main.java`
- **README geral**: `README.md`
- **Instruções Copilot**: `.github/copilot-instructions.md`

---

## Resumo das Classes

| Classe | Pacote | Tipo | Responsabilidade |
|--------|--------|------|------------------|
| `Jogador` | entidades | Entidade | Participante humano com XP e armas |
| `Alvo` | entidades | Entidade | Elemento atingível (estático/móvel) |
| `Arma` | entidades | Abstrata | Base para todas as armas |
| `ArmaBranca` | entidades | Entidade | Arma corpo a corpo (alcance=1.0) |
| `ArmaFogo` | entidades | Entidade | Arma à distância (alcance variável) |
| `Projetil` | entidades | Entidade | Disparo em movimento |
| `Ponto` | ambiente | Utilitário | Coordenada 2D com operações |
| `Plano` | ambiente | Ambiente | Mundo 2D e gerenciamento de objetos |
| `GerenciadorDeJogo` | gerenciamento | Controle | Loop principal e coordenação |
| `GerenciadorDeNivel` | gerenciamento | Controle | Progressão e desbloqueios |

---

## Exemplos de Polimorfismo

### Tratamento Polimórfico de Armas
```java
// Lista polimórfica - pode conter qualquer tipo de arma
List<Arma> arsenal = new ArrayList<>();
arsenal.add(new ArmaFogo("Pistola", 10, 200.0, 1, 100.0));
arsenal.add(new ArmaBranca("Faca", 15, 1));
arsenal.add(new ArmaFogo("Rifle", 25, 400.0, 3, 150.0));
arsenal.add(new ArmaBranca("Espada", 30, 2));

// Usar todas as armas de forma uniforme
for (Arma arma : arsenal) {
    System.out.println(arma.getNome() + ": " + 
                       "Dano=" + arma.getDano() + 
                       ", Alcance=" + arma.getAlcanceMaximo());
    
    // Método atirar() é chamado polimorficamente
    Projetil p = arma.atirar(x, y, direcao);
}
```

### Verificação de Tipo (quando necessário)
```java
Arma arma = jogador.getArmaAtual();

if (arma instanceof ArmaFogo) {
    ArmaFogo af = (ArmaFogo) arma;
    System.out.println("Velocidade: " + af.getVelocidadeProjetil());
} else if (arma instanceof ArmaBranca) {
    System.out.println("Arma corpo a corpo - alcance limitado!");
}
```
