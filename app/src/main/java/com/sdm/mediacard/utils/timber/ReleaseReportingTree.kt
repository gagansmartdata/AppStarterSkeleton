package com.sdm.mediacard.utils.timber

import android.util.Log
import timber.log.Timber

class ReleaseReportingTree : Timber.Tree() {

    override fun log(priority: Int, tag: String?, message: String, t: Throwable?) {
        if (priority == Log.VERBOSE || priority == Log.DEBUG) {
            return
        }
        // log your crash to your favourite
        // Sending crash report to Firebase CrashAnalytics

        // FirebaseCrash.report(message);
        // FirebaseCrash.report(new Exception(message));
        if (t != null) {
            if (priority == Log.ERROR) {
                //Log.e(tag,t.getMessage());
            } else if (priority == Log.WARN) {
                //Log.w(tag,t.getMessage());
            }
        }
    }
}