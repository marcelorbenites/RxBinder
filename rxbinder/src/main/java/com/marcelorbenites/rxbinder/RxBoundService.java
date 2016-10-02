package com.marcelorbenites.rxbinder;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;
import rx.Observable;
import rx.subjects.BehaviorSubject;

/**
 * Created by marcelobenites on 10/2/16.
 */

public abstract class RxBoundService<T> extends Service {

  private final BehaviorSubject<T> subject = BehaviorSubject.create();

  public static <T> Observable<T> bind(Context context,
      Class<? extends RxBoundService<? extends T>> serviceClass, int flags) {
    return Observable.create(new BindOnSubscribe(context, new Intent(context, serviceClass), flags))
        .flatMap(iBinder -> ((RxBinder<T>) iBinder).get());
  }

  @Nullable @Override public IBinder onBind(Intent intent) {
    return new RxBinder(subject);
  }

  protected void publish(T event) {
    subject.onNext(event);
  }
}