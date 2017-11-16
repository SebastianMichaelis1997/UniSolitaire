package Client;

import java.io.*;
import java.net.*;
import java.util.Scanner;

public class Client {
	public static void main(String[] args) {
		Socket client = Verbindung.connect();
		while (client == null) {
			System.err.println("Konnte nicht mit dem Server verbinden.");
			client = Verbindung.connect();

		}
		Scanner in;
		String input;
		String name = "";
		String authCode = "";
		do {
			System.out.println("Hallo und herzlich willkommen bei UNI SOLITAIRE!!!!");
			System.out.println(
					"Zum Anmelden bitte 'A' eingeben, zum neu registrieren 'R' eingeben und um das Programm zu beenden 'E' eingeben.");
			in = new Scanner(System.in);
			input = in.nextLine();
		} while (!input.equalsIgnoreCase("a") && !input.equalsIgnoreCase("r") && !input.equalsIgnoreCase("e"));
		if (input.equalsIgnoreCase("r")) {
			System.out.println("Bitte geben sie ihren Namen an");
			input = in.nextLine();
			name = input;
			authCode = Verbindung.register(name = input);
			System.out.println("Hallo " + name);
			System.out.println("Dein AuthCode ist:"+authCode);
		}

	}
}
