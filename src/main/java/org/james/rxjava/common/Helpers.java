package org.james.rxjava.common;

import java.util.Arrays;
import java.util.concurrent.TimeUnit;
import rx.Observable;
import rx.Subscription;
import rx.schedulers.Schedulers;

public final class Helpers {
	
	public static <T> Subscription subscribePrint(Observable<T> observable, String name) {
		return observable.subscribe(
				(v) -> System.out.println(name + " : " + v), 
				(e) -> {
					System.err.println("Error from " + name);
					System.err.println(e.getMessage());
				}, 
				() -> System.out.println(name + " ended!"));
	}
	
	public static void main(String... args) throws InterruptedException {
		//subscribePrint(Observable.interval(500L, TimeUnit.MILLISECONDS, Schedulers.trampoline()), "Interval Observable");
		//subscribePrint(Observable.timer(3, TimeUnit.SECONDS, Schedulers.trampoline()), "Time Observable");
		//subscribePrint(Observable.interval(0, 1, TimeUnit.SECONDS, Schedulers.trampoline()), "Timed interval observable");
		//subscribePrint(Observable.error(new Exception("Test Error!")), "Error observable");
		//subscribePrint(Observable.empty(), "Empty observable");
		//subscribePrint(Observable.never(), "Never observable");
		//subscribePrint(Observable.range(3, 9), "Range observable");
		
		//Thread.sleep(20000);
		
		Observable<Long> interval = Observable.interval(100L, TimeUnit.MILLISECONDS);
		
		/*ConnectableObservable<Long> published = interval.publish();
		Subscription sub1 = subscribePrint(published, "First");
		Subscription sub2 = subscribePrint(published, "Second");
		published.connect();
		
		Thread.sleep(500L);
		
		Subscription sub3 = subscribePrint(published, "Third");
		Thread.sleep(500L);
		
		sub1.unsubscribe();
		sub2.unsubscribe();
		sub3.unsubscribe();*/
		
		
		/*Observable<Long> refCount = interval.publish().refCount();
		Subscription sub1 = subscribePrint(refCount, "First");
		Subscription sub2 = subscribePrint(refCount, "Second");
		
		Thread.sleep(500L);
		
		sub1.unsubscribe();
		sub2.unsubscribe();
		
		Subscription sub3 = subscribePrint(refCount, "Third");
		Thread.sleep(500L);
		sub3.unsubscribe();*/
		
		/*Observable<Integer> flatMapped = Observable.just(5, 432)
				.flatMap(x -> Observable.range(x, 3), (x, y) -> x + y);
		
		subscribePrint(flatMapped, "flatMapped");*/
		
		/*List<Number> list = Arrays.asList(3, 2);
		Observable<Timestamped<Number>> timestamp = Observable
		.from(list)
		.timestamp();
		subscribePrint(timestamp, "Timestamps");*/
		
		/*Observable.just(1, 13, 32, 45, 21, 8, 98, 103, 55)
				.takeLast(4).subscribe(System.out::println);*/
		
		/*Observable<Integer> scan = Observable
				.range(1, 10)
				.scan((p, v) -> p + v);
				subscribePrint(scan, "Sum");
				subscribePrint(scan.last(), "Final sum");*/
		
		Observable<String> timedZip = Observable.zip(
				Observable.from(Arrays.asList("Z", "I", "P", "P")),
				Observable.interval(300L, TimeUnit.MILLISECONDS, Schedulers.trampoline()),
				(value, i) -> value);
		subscribePrint(timedZip, "Timed zip");
		
		
		/*Observable<String> timeZip = Observable.from(Arrays.asList("Z", "I", "P", "P"))
				.zipWith(Observable.interval(300L, TimeUnit.MILLISECONDS, Schedulers.trampoline()), 
						(value, skip) -> value);
		
		subscribePrint(timeZip, "Timed Zip");*/
		
	

		//testGroupBy();
		
	}
	
	public static void testGroupBy() {
		/*List<String> ablums = Arrays.asList(
				"The Piper at the Gates of Dawn",
				"A Saucerful of Secrets",
				"More", "Ummagumma", "Atom Heart Mother",
				"Meddle", "Obscured by Clouds",
				"The Dark Side of the Moon",
				"Wish You Were Here", "Animals", "The Wall");
		
		Observable.from(ablums).groupBy(album -> album.split(" ").length)
			.subscribe(obs -> subscribePrint(obs, obs.getKey() + " words!"));*/
		
		Observable.range(0, 10).groupBy(v -> v % 3)
			.subscribe(obs -> subscribePrint(obs, obs.getKey() + ""));
		
	}
	
	

}
