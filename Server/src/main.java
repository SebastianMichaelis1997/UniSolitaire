package Server;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.Reader;
import java.io.Writer;
import java.net.ServerSocket;
import java.net.Socket;
import java.sql.Date;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Scanner;

import javax.swing.plaf.synth.SynthSeparatorUI;

public class main {

	public static void main(String[] args) {
		System.out.println("Programmstart");
		Map<String, String> AuthCodes = new HashMap<String, String>();
		List<GameData> Games = new ArrayList<GameData>();
		Map<Integer, String> Seeds = new HashMap<Integer, String>();

		ServerSocket server;
		System.out.println("Socket erstellt");
		/*
		 * / Random rnd1 = new Random(123), rnd2 = new Random(123);
		 * 
		 * for (int i = 0; i<10; i++) { System.out.println(rnd1.nextInt());
		 * System.out.println(rnd2.nextInt()); } /
		 */

		try {

			System.out.println("Starte Einlesen...");
			Reader fr;
			BufferedReader br;

			try {
				System.out.println("... AuthCodes");
				fr = new FileReader("AuthCodes.txt");
				br = new BufferedReader(fr);
				String Line;
				while ((Line = br.readLine()) != null) {
					String[] parts = Line.split(";;;");
					AuthCodes.put(parts[0], parts[1]);
				}
				br.close();
				fr.close();
			} catch (FileNotFoundException e) {
				System.out.println("Datei nicht gefunden!");
			}

			try {
				System.out.println("... Games");
				fr = new FileReader("Games.txt");
				br = new BufferedReader(fr);
				String Line;
				while ((Line = br.readLine()) != null) {
					// fr.write(Game.authCode + ";;;" + Game.duration + ";;;" + Game.moves + ";;;" +
					// Game.date + "\n");
					String[] parts = Line.split(";;;");
					GameData g = new GameData();
					g.authCode = parts[0];
					g.date = Date.valueOf(parts[3]);
					g.duration = Integer.parseInt(parts[1]);
					g.moves = Short.parseShort(parts[2]);
					Games.add(g);
				}
				br.close();
				fr.close();
			} catch (FileNotFoundException e) {
				System.out.println("Datei nicht gefunden!");
			}

			server = new ServerSocket(1345);
			System.out.println("Server initialisiert auf Port " + server.getLocalPort());

			boolean run = true;

			while (run) {
				System.out.println("Listening...");
				Socket client = server.accept();
				System.out.println("Client verbunden");

				Scanner in = new Scanner(client.getInputStream());
				PrintWriter out = new PrintWriter(client.getOutputStream(), true);

				String Line = in.nextLine();

				if (Line == "register") {
					System.out.println("Neuen AuthCode registrieren: ");
					// in "register"
					// in Username
					// out AuthCode

					char[] AuthCode = new char[10];
					Random rnd = new Random(System.currentTimeMillis());

					do {
						for (int i = 0; i < AuthCode.length; i++)
							AuthCode[i] = (char) (rnd.nextInt(27) + 65);
					} while (AuthCodes.containsKey(AuthCode.toString()));
					String name;
					AuthCodes.put(AuthCode.toString(), name = in.nextLine());
					System.out.println("AuthCode: " + AuthCode.toString());
					System.out.println("Name: " + name);
					out.println(AuthCode);
				} else if (Line == "seed") {
					System.out.println("Abfrage eines Seeds");
					// in "seed"
					// in Number - Nummer des Seeds, sonst 0, dann neu
					// out Seed
					// out Pos, wenn neu

					if (in.nextLine() == "0") {
						System.out.println("Neuer Seed");

						int[] Seed = new int[15];
						Random rnd = new Random(System.currentTimeMillis());

						for (int i = 0; i < Seed.length; i++)
							// Seed[i] = (char)(rnd.nextInt(27) + 65);
							Seed[i] = rnd.nextInt(10);
						int pos;
						Seeds.put(pos = Seeds.size(), Seed.toString());
						System.out.println("Seed" + Seed);
						System.out.println("Position" + pos);
						out.println(Seed);
						out.println(pos);
					} else {

						System.out.println("Gebe Seed aus");
						int Pos = Integer.parseInt(in.nextLine());
						if (Seeds.containsKey(Pos)) {
							out.println(Seeds.get(Pos));
							System.out.println("Seed: " + Seeds.get(Pos));
						} else {
							System.out.println("Seed nicht gefunden");
							out.println("0");
						}
					}
				} else {
					System.out.println("Gebe Spieldaten ein");
					// in AuthCode
					// out "0", wenn falsch
					// out "1", wenn wahr
					// in Duration
					// in Moves
					System.out.println("Authentifikation...");
					if (!AuthCodes.containsKey(Line)) {
						System.out.println("... nicht erfolgreich");
						System.out.println("AuthCoe: " + Line);
						out.println("0");
						client.close();
						continue;
					}

					System.out.println("... erfolgreich");
					out.println("1");

					System.out.println("Daten: ");
					GameData g = new GameData();

					g.authCode = Line;

					g.date.setTime(System.currentTimeMillis());
					g.duration = Integer.valueOf(in.nextLine());
					g.moves = Short.valueOf(in.nextLine());

					System.out.println("AuthCode: " + Line);
					System.out.println("Zeit: " + g.date);
					System.out.println("Dauer: " + g.duration);
					System.out.println("Züge: " + g.moves);
					Games.add(g);
				}

				client.close();

			}

			System.out.println("Close Server");
			server.close();

			Writer fw = null;
			System.out.println("Sichern der Daten");

			try {
				System.out.println("AuthCodes");
				fw = new FileWriter("AuthCodes.txt");
				for (String AuthCode : AuthCodes.keySet())
					fw.write(AuthCode + ";;;" + AuthCodes.get(AuthCode) + "\n");
				fw.close();
			} catch (IOException e) {
				System.err.println("Could not create File.");
				System.out.println("Datei konnte nicht erstellt werden.");
			}
			try {
				System.out.println("Games");
				fw = new FileWriter("Games.txt");
				for (GameData Game : Games)
					fw.write(Game.authCode + ";;;" + Game.duration + ";;;" + Game.moves + ";;;" + Game.date + "\n");
				fw.close();
			} catch (IOException e) {
				System.err.println("Could not create File.");
				System.out.println("Datei konnte nicht erstellt werden.");
			}

		} catch (IOException e) {
			System.err.println("Shit happens.");
			e.printStackTrace();
			return;
		}
		// */
	}
}
