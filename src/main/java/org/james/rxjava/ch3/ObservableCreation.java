package org.james.rxjava.ch3;

import java.util.Arrays;
import java.util.List;

import rx.Observable;

public class ObservableCreation {

	public static void main(String[] args) {
		List<String> list = Arrays.asList("a", "b", "c", "d", "e");

		Observable<String> listObservable = Observable.from(list);
		listObservable.subscribe(System.out::println);

		Observable<Integer> arrayObservable = Observable.from(new Integer[] { 1, 2, 3, 4 });
		arrayObservable.subscribe(System.out::println);
		
		Observable.just('S').subscribe(System.out::println);
		
		Observable.just('R', 'x', 'J', 'a', 'v', 'a')
			.subscribe(System.out::print, System.out::println, System.out::println);

	}

}
