package gui;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

public class Increase {

	public static String[] words = {"a", "b", "c", "d", "r", "e", "cfc"};
	public static List<String> palabras = new ArrayList<>();

	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		Random rd = new Random();
		int index = rd.nextInt(words.length);
		String selectedWord = words[index];
		palabras.add(selectedWord);
		System.out.println(selectedWord);
		System.out.print("¿Qué palabra era? ");
		String answer = sc.nextLine();

		if (!selectedWord.equals(answer)) {
			System.out.println("Incorrecto.");
		} else {
			System.out.println("1 de 1.");
		}
		for (int i = 1; i < 100; i++) {
			index = rd.nextInt(words.length);
			selectedWord = words[index];
			palabras.add(selectedWord);
			// Mostrar todas las palabras anteriores en una línea
			for (String palabra : palabras) {
				System.out.print(palabra + " ");
			}
			System.out.println();

			// Preguntar por todas las palabras anteriores
			int correctAnswers = 0;
			for (int j = 0; j <= i; j++) {
				System.out.print("¿Qué palabra era la número " + (j + 1) + "? ");
				answer = sc.nextLine();

				if (palabras.get(j).equals(answer)) {
					correctAnswers++;
				}
			}
			System.out.println(correctAnswers + " de " + (i + 1) + ".");
		}

		sc.close();
		increase();
	}

	public static void increase() {
		// Implementación vacía
	}
}
