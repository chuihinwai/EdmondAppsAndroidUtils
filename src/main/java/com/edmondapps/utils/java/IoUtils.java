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
package com.edmondapps.utils.java;

import java.io.BufferedReader;
import java.io.Closeable;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

import android.database.Cursor;

/**
 * Collection of common boiler-plates involving I/O operations.
 * 
 * @author Edmond
 * 
 */
public final class IoUtils {
    private IoUtils() {
        throw new AssertionError("nice try");
    }

    /**
     * Constructs a new {@link URL} instance.
     * <p/>
     * This method is designed to avoid cases where the programmer guarantees
     * the {@code String} is a valid {@link URL}, but is forced to catch a
     * {@code MalformedURLException}.
     * 
     * @param url
     *            passed to {@link URL#URL(String)} constructor
     * @return a new {@link URL} instance
     * @throws IllegalArgumentException
     *             if {@code MalformedURLException} is thrown
     */
    public static URL newURL(String url) {
        try {
            return new URL(url);
        } catch (MalformedURLException e) {
            throw new IllegalArgumentException(e);
        }
    }

    /**
     * Constructs an {@link URL} and call {@link URL#openConnection()}.
     * 
     * @see {@link URL#openConnection()}
     * @param url
     *            passed to {@link #newURL(String)}
     * @return An instance of type {@link URLConnection}, it can be any of its
     *         sub-type depending on the generic inference. You must ensure the
     *         type-safety or an Exception will be thrown.
     * @throws IOException
     *             thrown by {@link URL#openConnection()}
     */
    // let it crash run-time!
    @SuppressWarnings("unchecked")
    public static <C extends URLConnection> C newConnection(String url) throws IOException {
        return (C)newURL(url).openConnection();
    }

    /**
     * Wraps the {@code InputStream} returned by
     * {@link HttpURLConnection#getInputStream()} in a {@link BufferedReader}:
     * <p/>
     * {@code new BufferedReader(new InputStreamReader(c.getInputStream()))}
     * 
     * @param c
     *            non-{@code null} reference to a {@link HttpURLConnection}
     * @return A new instance of {@link BufferedReader}.
     * @throws IOException
     *             thrown by {@link HttpURLConnection#getInputStream()}
     */
    public static BufferedReader newReader(HttpURLConnection c) throws IOException {
        return new BufferedReader(new InputStreamReader(c.getInputStream()));
    }

    /**
     * Closes the resource and ignores the potential {@link IOException} thrown
     * by {@link Closeable}.
     * <p/>
     * Should generally be used inside a {@code finally} block.
     * 
     * @param c
     *            {@code null}-safe reference to a resource
     */
    public static void quietClose(Closeable c) {
        if (c != null) {
            try {
                c.close();
            } catch (IOException ignored) {
            }
        }
    }

    /**
     * Directly closes the {@code Cursor} with its {@code close()} method. This
     * method is intended to overload {@link #quietClose(Closeable)} since older
     * versions of the {@link Cursor} class does not implement {@link Closeable}
     * .
     * <p/>
     * Should generally be used inside a {@code finally} block.
     * 
     * @param c
     *            {@code null}-safe reference to a {@code Cursor}
     */
    public static void quietClose(Cursor c) {
        if (c != null) {
            c.close();
        }
    }

    /**
     * Calls {@link HttpURLConnection#disconnect()}.
     * <p/>
     * Should generally be used inside a {@code finally} block.
     * 
     * @param c
     *            {@code null}-safe reference to a {@link HttpURLConnection}
     */
    public static void quietDisconnet(HttpURLConnection c) {
        if (c != null) {
            c.disconnect();
        }
    }

    public interface ProgressCallback {
        /**
         * Called when a progress is made.
         * 
         * @param progress
         *            as per {@link InputStream#read(byte[])}
         * @return true if the operation should continue, false to abort and
         *         return immediately
         */
        boolean onProgress(long progress);
    }

    public static void inputToOutput(InputStream source, OutputStream target) throws IOException {
        inputToOutput(source, target, null);
    }

    /**
     * Reads from an {@link InputStream} and writes its content to the
     * {@link OutputStream}. </br>
     * The target {@link OutputStream} is <b>not</b> flushed. </br>
     * Both the source or the target will not be closed.
     * 
     * @param source
     *            the source {@link InputStream}
     * @param target
     *            the target {@link OutputStream}
     * @param callback
     *            the callback for progress updates
     */
    public static void inputToOutput(InputStream source, OutputStream target, ProgressCallback callback) throws IOException {
        byte[] buffer = new byte[1024];
        long progress = 0;
        for (int read = 0; (read = source.read(buffer)) != -1;) {
            target.write(buffer, 0, read);
            progress += read;
            if (callback != null) {
                if (!callback.onProgress(progress)) {
                    return;
                }
            }
        }
    }
}
