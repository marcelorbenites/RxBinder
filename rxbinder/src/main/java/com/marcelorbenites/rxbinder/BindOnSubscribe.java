package com.marcelorbenites.rxbinder;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;
import rx.Observable;
import rx.Subscriber;
import rx.subscriptions.Subscriptions;

/**
 * Created by marcelobenites on 10/2/16.
 */

public class BindOnSubscribe implements Observable.OnSubscribe<IBinder> {

  private final Context context;
  private final Intent intent;
  private final int flags;

  public BindOnSubscribe(Context context, Intent intent, int flags) {
    this.context = context;
    this.intent = intent;
    this.flags = flags;
  }

  @Override public void call(Subscriber<? super IBinder> subscriber) {
    final ServiceConnection serviceConnection = new ServiceConnection() {
      @Override public void onServiceConnected(ComponentName name, IBinder service) {
        if (!subscriber.isUnsubscribed()) {
          subscriber.onNext(service);
        }
      }

      @Override public void onServiceDisconnected(ComponentName name) {
        if (!subscriber.isUnsubscribed()) {
          subscriber.onError(new Exception("Service " + name + " disconnected."));
        }
      }
    };

    subscriber.add(Subscriptions.create(() -> {
      context.unbindService(serviceConnection);
    }));

    context.bindService(intent, serviceConnection, flags);
  }
}
