package org.james.rxjava.ch2;

import java.util.function.Predicate;

public class Main {

	public static void main(String[] args) {
		Predicate<Integer> even = (number) -> number % 2 == 0;
		
		int i = 50;
		while (i-- > 0) {
			System.out.println("Is five even? - " + even.test(i));
		}
	}

}
