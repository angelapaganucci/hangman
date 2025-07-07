package br.com.dio.hangman.model;

import br.com.dio.hangman.exception.GameIsFinishedException;
import br.com.dio.hangman.exception.LetterAlreadyInputtedException;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

public class HangmanGame {

    private static final int HANGMAN_INITIAL_LINE_LENGTH = 9;

    private final int lineSize;
    private final int hangmanInitialSize;
    private final List<HangmanChar> hangmanPaths;
    private final List<HangmanChar> characters;
    private final List<Character> failAttempts = new ArrayList<>();
    private int currentHangmanStep = 0; // controla o passo atual do boneco

    private String hangman;
    private HangmanGameStatus hangmanGameStatus;

    public HangmanGame(final List<HangmanChar> characters) {
        var characterSpace = "-".repeat(characters.size());

        buildHangmanDesign(characterSpace);
        this.lineSize = hangman.indexOf(System.lineSeparator()) + System.lineSeparator().length();

        this.hangmanPaths = buildHangmanPathsPositions();
        this.characters = setCharacterSpacesPositionInGame(characters);
        this.hangmanInitialSize = hangman.length();

        this.hangmanGameStatus = HangmanGameStatus.PENDING;  // <<< inicializa aqui!!!
    }


    public HangmanGameStatus getHangmanGameStatus() {
        return hangmanGameStatus;
    }

    public String inputCharacter(final char character) {
        if (this.hangmanGameStatus != HangmanGameStatus.PENDING) {
            var message = this.hangmanGameStatus == HangmanGameStatus.WIN ?
                    "Parabéns, você ganhou!" :
                    "Que pena, você perdeu!";
            throw new GameIsFinishedException(message);
        }

        if (this.failAttempts.contains(character) ||
                this.characters.stream().anyMatch(c -> c.getCharacter() == character && c.isVisible())) {
            throw new LetterAlreadyInputtedException("Você já informou a letra " + character);
        }

        var found = this.characters.stream()
                .filter(c -> c.getCharacter() == character)
                .toList();

        if (found.isEmpty()) {
            failAttempts.add(character);
            if (failAttempts.size() >= 6) {
                this.hangmanGameStatus = HangmanGameStatus.LOSE;
            }
            if (currentHangmanStep < hangmanPaths.size()) {
                rebuildHangman(hangmanPaths.get(currentHangmanStep));
                currentHangmanStep++;
            }
            return getCurrentWordDisplay();
        }

        this.characters.forEach(c -> {
            if (c.getCharacter() == character) {
                c.enableVisibility();
            }
        });

        if (this.characters.stream().noneMatch(HangmanChar::isInvisible)) {
            this.hangmanGameStatus = HangmanGameStatus.WIN;
            rebuildHangman(found.toArray(HangmanChar[]::new));
            throw new GameIsFinishedException("Parabéns, você ganhou!");
        }

        rebuildHangman(found.toArray(HangmanChar[]::new));
        return getCurrentWordDisplay();
    }

    @Override
    public String toString() {
        return hangman;
    }

    public String getCurrentWordDisplay() {
        return characters.stream()
                .map(HangmanChar::toString) // usa o método que retorna _ ou letra
                .reduce("", (a, b) -> a + b + " "); // concatena com espaço para melhor visualização
    }

    private List<HangmanChar> buildHangmanPathsPositions() {
        return new ArrayList<>(
                List.of(
                        new HangmanChar('O', lineSize * 3 + 6),   // cabeça
                        new HangmanChar('|', lineSize * 4 + 6),   // corpo
                        new HangmanChar('/', lineSize * 4 + 5),   // braço esquerdo
                        new HangmanChar('\\', lineSize * 4 + 7),  // braço direito
                        new HangmanChar('/', lineSize * 5 + 5),   // perna esquerda
                        new HangmanChar('\\', lineSize * 5 + 7)   // perna direita
                )
        );
    }


    private List<HangmanChar> setCharacterSpacesPositionInGame(final List<HangmanChar> characters) {
        final var LINE_LETTER = 6;
        for (int i = 0; i < characters.size(); i++) {
            characters.get(i).setPosition(this.lineSize * LINE_LETTER + HANGMAN_INITIAL_LINE_LENGTH + i);
        }
        return characters;
    }

    private void rebuildHangman(final HangmanChar... hangmanChars) {
        var hangmanBuilder = new StringBuilder(this.hangman);
        Stream.of(hangmanChars).forEach(
                h -> hangmanBuilder.setCharAt(h.getPosition(), h.getCharacter()));
        var failMessage = this.failAttempts.isEmpty() ? "" : " Tentativas: " + this.failAttempts;
        this.hangman = hangmanBuilder.substring(0, hangmanInitialSize) + failMessage;
    }

    private void buildHangmanDesign(final String characterSpaces) {
        hangman = "  _____            " + System.lineSeparator() +  // linha 0
                "  |   |            " + System.lineSeparator() +  // linha 1
                "  |   |            " + System.lineSeparator() +  // linha 2
                "  |                " + System.lineSeparator() +  // linha 3 (cabeça)
                "  |                " + System.lineSeparator() +  // linha 4 (tronco e braços)
                "  |                " + System.lineSeparator() +  // linha 5 (pernas)
                "  |                " + System.lineSeparator() +  // linha 6
                "======== " + characterSpaces + System.lineSeparator(); // linha 7
    }

}
