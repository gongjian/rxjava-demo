package org.james.rxjava.ch2;

import java.util.regex.Pattern;

import org.james.rxjava.common.CreateObservable;

import rx.Observable;
import rx.observables.ConnectableObservable;

public class SumCalculator {

	/**
	 * The Observable returned by this method, only reacts to values in the form
	 * <varName> = <value> or <varName> : <value>.
	 * It emits the <value>.
	 */
	public static Observable<Double> varStream(final String varName,
			Observable<String> input) {
		final Pattern pattern = Pattern.compile("^\\s*" + varName
				+ "\\s*[:|=]\\s*(-?\\d+\\.?\\d*)$");

		return input.map(pattern::matcher)
				.filter(m -> m.matches() && m.group(1) != null)
				.map(matcher -> matcher.group(1))
				.map(Double::parseDouble);
	}

	public void run() {
		ConnectableObservable<String> input = CreateObservable.from(System.in);

		Observable<Double> a = varStream("a", input);
		Observable<Double> b = varStream("b", input);

		reactiveSum(a, b);
		
		input.connect();
		
		//try {
			//sum.getLatch().await();
		//} catch (InterruptedException e) {}
	}
	
	public static void reactiveSum(Observable<Double> a, Observable<Double> b) {
		Observable.combineLatest(a, b, (x, y) -> x + y)
			.subscribe(sum -> System.out.println("update: a + b = " + sum),
					error -> {
						System.out.println("Got an error!");
						error.printStackTrace();
					}, () -> System.out.println("Existing..."));
	}
	
	/**
	 * Here the input is executed on a separate thread, so we block the current one until it sends
	 * a `completed` notification.
	 */
	public static void main(String[] args) {
		System.out.println();
		System.out.println("Reacitve Sum. Type 'a: <number>' and 'b: <number>' to try it.");
		
		new SumCalculator().run();
	}

}
