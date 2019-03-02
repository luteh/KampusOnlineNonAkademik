package com.luteh.kampusonlinenonakademik.log;

import android.util.Log;
import timber.log.Timber;

/**
 * Created by Luthfan Maftuh on 11/01/2019.
 * Email luthfanmaftuh@gmail.com
 */
public class ReleaseTree extends Timber.Tree {
    @Override
    protected void log(int priority, String tag, String message, Throwable t) {
        if (priority == Log.VERBOSE || priority == Log.DEBUG) {
            return;
        }

        // log your crash to your favourite
        // Sending crash report to Firebase CrashAnalytics

        // FirebaseCrash.report(message);
        // FirebaseCrash.report(new Exception(message));
    }
}
