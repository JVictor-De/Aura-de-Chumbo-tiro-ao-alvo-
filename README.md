# Aura de Chumbo

Jogo 2D de tiro desenvolvido em Java.

## O que é

Um jogo onde você controla um personagem que precisa acertar alvos usando diferentes armas. Tem sistema de XP e você vai subindo de nível.

## Funcionalidades

### Armas
- **Armas de Fogo**: pistola, rifle, sniper (cada uma com alcance diferente)
- **Armas Brancas**: faca, espada (corpo a corpo, alcance fixo de 1.0)
  
### Alvos
- Alvos estáticos que ficam parados
- Alvos móveis que se movem pelo mapa
- Alguns alvos precisam de vários tiros pra destruir (resistência)

### Progressão
- Ganha XP quando destrói os alvos
- Sistema de níveis (quanto mais alto o nível, mais XP precisa)
- Armas melhores só ficam disponíveis em níveis mais altos

## Como rodar

### Compilar
```powershell
# opção 1: compilar direto (pode dar erro no PowerShell)
javac -d out -sourcepath src\main\java src\main\java\com\auradechumbo\**\*.java src\main\java\com\auradechumbo\*.java

# opção 2: criar lista de arquivos primeiro (recomendado)
Get-ChildItem -Path src\main\java -Recurse -Filter *.java | ForEach-Object { $_.FullName } | Out-File -Encoding utf8 sources.txt
javac -d out -sourcepath src\main\java "@sources.txt"
```

### Executar
```powershell
java -cp out com.auradechumbo.Main
```

## Estrutura do projeto

```
com.auradechumbo/
├── entidades/      # classes do jogo: Jogador, Alvo, Arma*, Projetil
├── ambiente/       # Plano (o mapa), Ponto (coordenadas X/Y)
└── gerenciamento/  # GerenciadorDeJogo, GerenciadorDeNivel
```

### Classes principais

- **Jogador** - o personagem que você controla
- **Arma** (abstrata) - classe base pra todas as armas
  - **ArmaBranca** - facas, espadas (corpo a corpo)
  - **ArmaFogo** - pistolas, rifles (à distância)
- **Alvo** - os inimigos que você precisa destruir
- **Projetil** - os tiros
- **Ponto** - classe auxiliar pra trabalhar com posições X,Y
- **Plano** - o mapa do jogo
- **GerenciadorDeJogo** - controla o loop principal
- **GerenciadorDeNivel** - gerencia XP e subida de nível

## Conceitos de POO usados

- **Herança**: `Arma` é abstrata, `ArmaBranca` e `ArmaFogo` herdam dela
- **Polimorfismo**: o método `atirar()` funciona diferente em cada tipo de arma
- **Encapsulamento**: atributos privados com getters, sem setters diretos
- **Abstração**: `Arma` define o "contrato", as subclasses implementam
- **Agregação**: `Jogador` pode equipar várias armas (mas elas existem independente)
- **Composição**: `GerenciadorDeJogo` "possui" o `Plano` (se o jogo acaba, o plano também)
- **Factory Pattern**: `Arma.atirar()` cria os projéteis

## Mais infos

- Diagrama de classes completo: `docs/DIAGRAMA_CLASSES_CORRIGIDO.md`
- Instruções pro GitHub Copilot: `.github/copilot-instructions.md`

## Stack

- Java 8 ou superior
- Sem dependências externas (Java puro)
- Compilado com `javac` (sem Maven nem Gradle)

## Obs

Projeto focado em demonstrar conceitos de POO na prática. Por enquanto roda só no console (sem interface gráfica). Desenvolvido como trabalho da disciplina de Programação Orientada a Objetos do IBMEC.
# Aura-de-Chumbo-tiro-ao-alvo-
