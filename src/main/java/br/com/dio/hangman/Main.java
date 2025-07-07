package br.com.dio.hangman;

import br.com.dio.hangman.exception.GameIsFinishedException;
import br.com.dio.hangman.exception.LetterAlreadyInputtedException;
import br.com.dio.hangman.model.HangmanChar;
import br.com.dio.hangman.model.HangmanGame;
import br.com.dio.hangman.model.HangmanGameStatus;

import java.util.Scanner;
import java.util.stream.Stream;

public class Main {

    private final static Scanner scanner = new Scanner(System.in);

    public static void main(String... args) {
        var characters = Stream.of(args)
                .map(a -> a.toLowerCase().charAt(0))
                .map(HangmanChar::new).toList();

        var hangmanGame = new HangmanGame(characters);

        System.out.println("Bem-vindo ao Jogo da Forca!");
        System.out.println(hangmanGame);

        while (true) {
            System.out.println("\nSelecione uma das opções abaixo:");
            System.out.println("1. Informar uma letra");
            System.out.println("2. Verificar o status do jogo");
            System.out.println("3. Sair do jogo");
            var option = scanner.nextInt();

            switch (option) {
                case 1 -> inputCharacter(hangmanGame);
                case 2 -> showGameStatus(hangmanGame);
                case 3 -> {
                    System.out.println("Saindo do jogo. Até mais!");
                    return;
                }
                default -> System.out.println("Opção inválida. Tente novamente.");
            }

            // ✅ Verifica o estado após cada jogada
            if (hangmanGame.getHangmanGameStatus() != HangmanGameStatus.PENDING) {
                System.out.println(hangmanGame); // Mostra o estado final
                if (hangmanGame.getHangmanGameStatus() == HangmanGameStatus.WIN) {
                    System.out.println("Parabéns, você ganhou!");
                } else {
                    System.out.println("Que pena, você perdeu!");
                }
                return;
            }
        }
    }

    private static void showGameStatus(HangmanGame hangmanGame) {
        System.out.println("Status: " + hangmanGame.getHangmanGameStatus());
        System.out.println(hangmanGame);
    }

    private static void inputCharacter(HangmanGame hangmanGame) {
        System.out.println("Informe uma letra:");
        var character = scanner.next().toLowerCase().charAt(0);
        try {
            String palavraAtual = hangmanGame.inputCharacter(character);
            System.out.println("Palavra: " + palavraAtual);
        } catch (LetterAlreadyInputtedException e) {
            System.out.println(e.getMessage());
        } catch (GameIsFinishedException e) {
            // Antes de sair, mostra a palavra final (todas as letras visíveis)
            System.out.println("Palavra: " + hangmanGame.getCurrentWordDisplay());
            System.out.println(e.getMessage());
            System.exit(0);
        }
        System.out.println(hangmanGame);
    }
}
