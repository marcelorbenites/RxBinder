package com.marcelorbenites.rxbinder.sample;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.widget.Toast;
import com.marcelorbenites.rxbinder.RxBoundService;
import com.trello.rxlifecycle.components.support.RxAppCompatActivity;
import java.util.Arrays;
import java.util.List;
import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;

/**
 * Created by marcelobenites on 10/1/16.
 */

public class MainActivity extends RxAppCompatActivity {

  @Override protected void onResume() {
    super.onResume();
    RxBoundService.bind(this, MyService.class, Context.BIND_AUTO_CREATE).compose(bindToLifecycle())
        .observeOn(AndroidSchedulers.mainThread())
        .subscribe(string -> Toast.makeText(this, string, Toast.LENGTH_LONG).show());
  }
}