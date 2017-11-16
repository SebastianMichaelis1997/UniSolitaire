package Client;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.SocketAddress;
import java.net.UnknownHostException;
import java.util.Scanner;

public class Verbindung {

	public static Socket connect() {

		try {
			System.out.println("connect");
			Socket client = new Socket("localhost", 1345);
			System.out.println("connected");

			return client;
		} catch (UnknownHostException e) {
			System.out.println("Host not found");
		} catch (IOException e) {
			System.out.println("IO Exception");
		}
		return null;
	}

	public static int[] getSeed(int number) {
		// result:
		// [0] Seed
		// [1] pos
		try {
			Socket s = connect();

			Scanner in = new Scanner(s.getInputStream());
			PrintWriter out = new PrintWriter(s.getOutputStream());

			out.println("seed");
			out.println(number);
			int Seed = in.nextInt();
			int pos = (number == 0) ? 0 : in.nextInt();

			s.close();
			return new int[] { Seed, pos };
		} catch (IOException e) {
			e.printStackTrace();
		}
		return new int[] { 0, 0 };
	}

	public static String register(String name) {
		// result:
		// Authcode
		try {
			Socket s = connect();

			Scanner in = new Scanner(s.getInputStream());
			PrintWriter out = new PrintWriter(s.getOutputStream());

			out.println("register");
			out.println(name);
			String AuthCode = in.nextLine();

			s.close();
			in.close();
			return AuthCode;
		} catch (IOException e) {
			e.printStackTrace();
		}
		return "";
	}

	public static boolean InsertData(String AuthCode, int duration, int moves) {
		// result:
		// true - AuthCode erkannt
		try {
			Socket s = connect();

			Scanner in = new Scanner(s.getInputStream());
			PrintWriter out = new PrintWriter(s.getOutputStream());

			out.println(AuthCode);
			if (in.nextInt() == 0) {
				return false;
			}
			out.println(duration);
			out.println(moves);

			s.close();
			return true;
		} catch (IOException e) {
			e.printStackTrace();
			return false;
		}
	}
}
