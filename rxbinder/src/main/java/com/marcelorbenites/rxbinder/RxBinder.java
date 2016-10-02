package com.marcelorbenites.rxbinder;

import android.os.Binder;
import rx.Observable;
import rx.subjects.Subject;

/**
 * Created by marcelobenites on 10/2/16.
 */
public class RxBinder<T> extends Binder {

  private final Subject<T, T> subject;

  public RxBinder(Subject<T, T> subject) {
    this.subject = subject;
  }

  public Observable<T> get() {
    return subject.asObservable();
  }
}
