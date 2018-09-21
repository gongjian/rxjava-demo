package org.james.rxjava.ch1;

import rx.Observable;

public class HelloWorld {

	public static void main(String[] args) {
		hello("t1", "t2");
	}

	public static void hello(String... names) {
		Observable.from(names).subscribe(System.out::println);
		Observable.from(names).subscribe(x -> System.out.println("hello " + x),
				e -> System.out.println("exception " + e), () -> System.out.println("Done!!!"));
	}

}
