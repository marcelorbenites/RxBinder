package com.marcelorbenites.rxbinder.sample;

import com.marcelorbenites.rxbinder.RxBoundService;
import java.util.concurrent.TimeUnit;
import rx.Observable;
import rx.Subscription;

/**
 * Created by marcelobenites on 10/2/16.
 */

public class MyService extends RxBoundService<String> {

  private Subscription subscription;

  @Override public void onCreate() {
    super.onCreate();
    subscription = Observable.interval(10, TimeUnit.SECONDS).subscribe(time -> publish("RAAA" + time));
  }

  @Override public void onDestroy() {
    super.onDestroy();
    subscription.unsubscribe();
  }
}