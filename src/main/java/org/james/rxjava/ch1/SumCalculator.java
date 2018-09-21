package org.james.rxjava.ch1;

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

		/*return input.map(new Func1<String, Matcher>() {
			public Matcher call(String str) {
				return pattern.matcher(str);
			}
		}).filter(new Func1<Matcher, Boolean>() {
			public Boolean call(Matcher matcher) {
				return matcher.matches() && matcher.group(1) != null;
			}
		}).map(new Func1<Matcher, String>() {
			public String call(Matcher matcher) {
				return matcher.group(1);
			}
		}).filter(new Func1<String, Boolean>() {
			public Boolean call(String str) {
				return str != null;
			}
		}).map(new Func1<String, Double>() {
			public Double call(String str) {
				return Double.parseDouble(str);
			}
		});*/
		
		return input.map(pattern::matcher)
				.filter(m -> m.matches() && m.group(1) != null)
				.map(matcher -> matcher.group(1))
				.map(Double::parseDouble);
	}

	public void run() {
		ConnectableObservable<String> input = CreateObservable.from(System.in);

		Observable<Double> a = varStream("a", input);
		Observable<Double> b = varStream("b", input);

		ReactiveSum sum = new ReactiveSum(a, b);
		
		input.connect();
		
		try {
			sum.getLatch().await();
		} catch (InterruptedException e) {}
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
