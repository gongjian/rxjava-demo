package org.james.rxjava.ch1;

import java.util.concurrent.CountDownLatch;

import rx.Observable;
import rx.Observer;
import rx.Subscription;
import rx.schedulers.Schedulers;

public class ReactiveSum implements Observer<Double> {

	private CountDownLatch latch = new CountDownLatch(1);

	private double sum;
	private Subscription subscription = null;

	public ReactiveSum(Observable<Double> a, Observable<Double> b) {
		this.sum = 0;

		subscribe(a, b);
	}

	private void subscribe(Observable<Double> a, Observable<Double> b) {
		this.subscription = Observable.combineLatest(a, b, (x, y) -> x + y).subscribeOn(Schedulers.io())
				.subscribe(this);

	}

	public void unsubscribe() {
		this.subscription.unsubscribe();
		this.latch.countDown();
	}

	@Override
	public void onCompleted() {
		System.out.println("Exiting last sum was : " + this.sum);
		this.latch.countDown();

	}

	@Override
	public void onError(Throwable e) {
		System.err.println("Got an error!");
		e.printStackTrace();

	}

	@Override
	public void onNext(Double sum) {
		this.sum = sum;
		System.out.println("update : a + b = " + sum);

	}

	public CountDownLatch getLatch() {
		return latch;
	}

}
