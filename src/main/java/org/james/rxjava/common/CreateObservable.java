package org.james.rxjava.common;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import rx.Observable;
import rx.Subscriber;
import rx.observables.ConnectableObservable;

public class CreateObservable {

	public static ConnectableObservable<String> from(final InputStream inputStream) {
		return from(new BufferedReader(new InputStreamReader(inputStream)));
	}

	public static ConnectableObservable<String> from(BufferedReader bufferedReader) {
		return Observable.unsafeCreate((Subscriber<? super String> subscriber) -> {
			try {
				String line;

				if (subscriber.isUnsubscribed()) {
					return;
				}

				while (!subscriber.isUnsubscribed() && (line = bufferedReader.readLine()) != null) {
					if ("exit".equals(line)) {
						break;
					}

					subscriber.onNext(line);
				}

			} catch (IOException e) {
				subscriber.onError(e);
			}

			if (!subscriber.isUnsubscribed()) {
				subscriber.onCompleted();
			}
		}).publish();
	}

}
