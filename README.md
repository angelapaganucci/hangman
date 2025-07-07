# Jogo da Forca - Implementação em Java

## Descrição

Este projeto é uma implementação do clássico jogo da forca em Java, utilizando conceitos de orientação a objetos e manipulação de strings para exibição do boneco da forca e das letras informadas. O jogo permite que o usuário informe letras para tentar adivinhar uma palavra secreta, exibe o boneco conforme as tentativas erradas e informa o status do jogo (vencido, perdido ou em andamento).

## Funcionalidades

- Entrada de letras pelo usuário via console.
- Verificação de letras já digitadas para evitar repetição.
- Controle de tentativas erradas (máximo de 6).
- Atualização dinâmica do boneco da forca com cada erro.
- Exibição da palavra com letras reveladas progressivamente.
- Tratamento de exceções para jogos finalizados e letras já usadas.
- Mensagens informativas ao usuário para vitórias, derrotas e erros.

## Tecnologias Utilizadas

- Java 17 (ou superior)
- Pacotes Java padrão (java.util, java.lang, java.time)
- Classes e exceções customizadas para organização do código.

## Estrutura do Projeto

- **Main**: Classe principal que controla o fluxo do jogo e interação com o usuário.
- **HangmanGame**: Contém a lógica do jogo, controle do estado, exibição do boneco e letras.
- **HangmanChar**: Representa cada caractere da palavra e dos elementos do boneco, controlando visibilidade e posição.
- **Exceções Customizadas**: 
  - `GameIsFinishedException` para finalização do jogo.
  - `LetterAlreadyInputtedException` para letras repetidas.
- **Enum `HangmanGameStatus`**: Indica os estados do jogo (PENDING, WIN, LOSE).

## Como Jogar

1. Compile e execute a aplicação.
2. Informe a palavra que será usada no jogo (pode ser passada como argumento na execução).
3. Escolha as opções do menu para informar letras, verificar status ou sair.
4. Ao informar uma letra:
   - Se correta, ela aparecerá nas posições certas.
   - Se errada, o boneco da forca será atualizado.
5. O jogo termina quando a palavra for completamente descoberta (vitória) ou o número máximo de erros for atingido (derrota).
6. Mensagens indicam o fim do jogo e o resultado.

## Melhorias Futuras

- Adicionar suporte a palavras com acentuação.

- Interface gráfica para melhor experiência do usuário.

- Sistema de dicas ou níveis de dificuldade.

- Armazenamento de histórico de jogos.

## Contato
Caso tenha dúvidas, sugestões ou queira contribuir, entre em contato:

- Email: angelapaganucci@hotmail.com
- GitHub: https://github.com/angelapaganucci
