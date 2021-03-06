/*
 * Copyright 2013 Edmond Chui
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 * http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.edmondapps.utils.android;

import android.util.Log;

/**
 * A forwarding class for {@link android.util.Log}. Use
 * {@link #setShouldLog(boolean)} to control all the logging calls.
 * 
 * @author Edmond
 * 
 */
public final class Logs {
    private Logs() {
        throw new AssertionError("nice try");
    }

    private static boolean sShouldLog = false;

    /**
     * 
     * @param sShouldLog
     *            disables all logging calls if false
     */
    public static void setShouldLog(boolean shouldLog) {
        Logs.sShouldLog = shouldLog;
    }

    /**
     * @see #setShouldLog(boolean)
     */
    public static boolean getShouldLog() {
        return sShouldLog;
    }

    /**
     * @see android.util.Log#v(String, String)
     */
    public static void v(String tag, String msg) {
        if (sShouldLog) {
            Log.d(tag, msg);
        }
    }

    /**
     * @see android.util.Log#v(String, String, Throwable)
     */
    public static void v(String tag, String msg, Throwable tr) {
        if (sShouldLog) {
            Log.v(tag, msg, tr);
        }
    }

    /**
     * @see android.util.Log#d(String, String)
     */
    public static void d(String tag, String msg) {
        if (sShouldLog) {
            Log.d(tag, msg);
        }
    }

    /**
     * @see android.util.Log#d(String, String, Throwable)
     */
    public static void d(String tag, String msg, Throwable tr) {
        if (sShouldLog) {
            Log.d(tag, msg, tr);
        }
    }

    /**
     * @see android.util.Log#i(String, String)
     */
    public static void i(String tag, String msg) {
        if (sShouldLog) {
            Log.i(tag, msg);
        }
    }

    /**
     * @see android.util.Log#i(String, String, Throwable)
     */
    public static void i(String tag, String msg, Throwable tr) {
        if (sShouldLog) {
            Log.i(tag, msg, tr);
        }
    }

    /**
     * @see android.util.Log#w(String, String)
     */
    public static void w(String tag, String msg) {
        if (sShouldLog) {
            Log.w(tag, msg);
        }
    }

    /**
     * @see android.util.Log#w(String, Throwable)
     */
    public static void w(String tag, Throwable tr) {
        if (sShouldLog) {
            Log.w(tag, tr);
        }
    }

    /**
     * @see android.util.Log#w(String, String, Throwable)
     */
    public static void w(String tag, String msg, Throwable tr) {
        if (sShouldLog) {
            Log.w(tag, msg, tr);
        }
    }

    /**
     * @see android.util.Log#e(String, String)
     */
    public static void e(String tag, String msg) {
        if (sShouldLog) {
            Log.e(tag, msg);
        }
    }

    /**
     * @see android.util.Log#e(String, String, Throwable)
     */
    public static void e(String tag, String msg, Throwable tr) {
        if (sShouldLog) {
            Log.e(tag, msg, tr);
        }
    }

    public static void e(String tag, Throwable tr) {
        String message = tr.getMessage();
        e(tag, message == null ? tr.toString() : message, tr);
    }
}
