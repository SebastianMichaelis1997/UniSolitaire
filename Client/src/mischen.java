package Client;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class mischen {

	public static List<Integer> perform(int seed) {
		Random rnd = new Random(seed);

		List<Integer> Karten = new ArrayList<Integer>();
		List<Integer> gemischt = new ArrayList<Integer>();

		for (int i = 0; i < 104; i++)
			Karten.add(i);

		while (Karten.size() > 0) {
			int pos;
			gemischt.add(Karten.get(pos = rnd.nextInt(Karten.size())));
			Karten.remove(pos);
		}

		return gemischt;
	}

	public static void show(List<Integer> stack) {
		for (int i = 0; i < stack.size(); i++)
			System.out.println(stack.get(i) + "   ");
	}

}
