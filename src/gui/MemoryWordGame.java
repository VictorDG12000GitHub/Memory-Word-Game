package gui;
import java.util.Scanner;

public class MemoryWordGame {

	public static Scanner scanner= new Scanner(System.in);
	public static String[] diccionario = {"A", "Ability", "Able", "Above", "Abroad"};
	public static String[] dictionary = {"A", "Ability", "Able", "Above", "Abroad"};


	public static void main(String args[]) {

		int mode=0;
		System.out.print("(1.Español, 2.English, 3.Deutsch, 4.Italiano, 5.Français)\nIn which language do you want to play? ");
		
		int option= scanner.nextInt();
		switch(option) {
			case 1:
				System.out.println("(1.Modo en aumento, 2.Modo concreto).\n¿Qué modo quieres? ");
				mode=scanner.nextInt();
				modesMenu(1,mode);
				break;
			case 2:
				System.out.println("(1.Increasing mode, 2.Concrete mode).\\nWhich mode do you want? ");
				mode=scanner.nextInt();
				modesMenu(2,mode);
				break;
			case 3:
				System.out.println("(1.Erhöhungsmodus, 2.Betonmodus).\\nWelchen Modus möchten Sie? ");
				mode=scanner.nextInt();                
				modesMenu(3,mode);
				break;
			case 4:
				System.out.println("(1.Modalità crescente, 2.Modalità calcestruzzo).\\nQuale modalità desideri? ");
				mode=scanner.nextInt();
				modesMenu(4,mode);
				break;
			case 5:
				System.out.println("(1.Mode croissant, 2.Mode béton).\\nQuel mode souhaitez-vous? ");
				mode=scanner.nextInt();
				modesMenu(5,mode);
				break;
			default:
				System.out.println("Invalid option selected.");
				break;
		}
	}

	public static void modesMenu(int option,int mode) {
		switch(option) {
			case 1:
				System.out.println("(1.Modo en aumento, 2.Modo concreto).\n¿Qué modo quieres? ");
				switch(mode) {
					case 1:
						randomGenIncrease();
						break;
					case 2:
						randomGenConcrete();
						break;
					default:
						System.out.println("Tu selección no está en el rango así que se contará como 1.");
						break;
				}
				break;
			case 2:
				System.out.println("(1.Increasing mode, 2.Concrete mode).\\nWhich mode do you want? ");
				
		        System.out.flush();
				switch(mode) {
				case 1:
					randomGenIncrease();
					break;
				case 2:
					randomGenConcrete();
					break;
				default:
					System.out.println("Tu selección no está en el rango así que se contará como 1.");
					break;
			}
				break;
			case 3:
				System.out.println("(1.Erhöhungsmodus, 2.Betonmodus).\\nWelchen Modus möchten Sie? ");
				switch(mode) {
				case 1:
					randomGenIncrease();
					break;
				case 2:
					randomGenConcrete();
					break;
				default:
					System.out.println("Tu selección no está en el rango así que se contará como 1.");
					break;
			}
				break;
			case 4:
				System.out.println("(1.Modalità crescente, 2.Modalità calcestruzzo).\\nQuale modalità desideri? ");
				switch(mode) {
				case 1:
					randomGenIncrease();
					break;
				case 2:
					randomGenConcrete();
					break;
				default:
					System.out.println("Tu selección no está en el rango así que se contará como 1.");
					break;
			}
				break;
			case 5:
				System.out.println("(1.Mode croissant, 2.Mode béton).\\nQuel mode souhaitez-vous? ");
				switch(mode) {
				case 1:
					randomGenIncrease();
					break;
				case 2:
					randomGenConcrete();
					break;
				default:
					System.out.println("Tu selección no está en el rango así que se contará como 1.");
					break;
			}
				break;
			default:
				System.out.println("Invalid option selected.");
				break;
		}
	}
	public static void randomGenIncrease() {
		
	}
	public static void randomGenConcrete() {
		
	}
}