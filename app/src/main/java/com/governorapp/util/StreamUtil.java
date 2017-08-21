package com.governorapp.util;

/**
 * Created by macoli on 17/1/13.
 */

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.Closeable;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintStream;
import java.io.Reader;
import java.io.Writer;
/**
 * Utility class for managing input streams.
 */
public class StreamUtil {
    // 16K buffer size
    private static final int BUF_SIZE = 16 * 1024;
    private StreamUtil() {
    }

    /**
     * Retrieves a {@link String} from a character stream.
     *
     * @param stream the {@link InputStream}
     * @return a {@link String} containing the stream contents
     * @throws IOException if failure occurred reading the stream
     */
    public static String getStringFromStream(InputStream stream) throws IOException {
        Reader ir = new BufferedReader(new InputStreamReader(stream));
        int irChar = -1;
        StringBuilder builder = new StringBuilder();
        while ((irChar = ir.read()) != -1) {
            builder.append((char)irChar);
        }
        return builder.toString();
    }

    /**
     * Copies contents of origStream to destStream.
     * <p/>
     * Recommended to provide a buffered stream for input and output
     *
     * @param inStream the {@link InputStream}
     * @param outStream the {@link OutputStream}
     * @throws IOException
     */
    public static void copyStreams(InputStream inStream, OutputStream outStream)
            throws IOException {
        byte[] buf = new byte[BUF_SIZE];
        int size = -1;
        while ((size = inStream.read(buf)) != -1) {
            outStream.write(buf, 0, size);
        }
    }
    /**
     * Copies contents of inStream to writer.
     * <p/>
     * Recommended to provide a buffered stream for input and output
     *
     * @param inStream the {@link InputStream}
     * @param writer the {@link Writer} destination
     * @throws IOException
     */
    public static void copyStreamToWriter(InputStream inStream, Writer writer) throws IOException {
        byte[] buf = new byte[BUF_SIZE];
        int size = -1;
        while ((size = inStream.read(buf)) != -1) {
            writer.write(new String(buf, 0, size));
        }
    }

    public static String readStreams(InputStream is) throws IOException {
        byte[] buf = new byte[BUF_SIZE] ;
        int size = -1 ;
        StringBuilder sb = new StringBuilder() ;
        while ((size = is.read(buf)) != -1){
            sb.append(new String(buf)) ;
        }
        return sb.toString() ;
    }

    /**
     * Gets the stack trace as a {@link String}.
     *
     * @param throwable the {@link Throwable} to convert.
     * @return a {@link String} stack trace
     */
    public static String getStackTrace(Throwable throwable) {
        // dump the print stream results to the ByteArrayOutputStream, so contents can be evaluated
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        PrintStream bytePrintStream = new PrintStream(outputStream);
        throwable.printStackTrace(bytePrintStream);
        return outputStream.toString();
    }
    /**
     * @deprecated use {@link #close(Closeable)} instead.
     */
    @Deprecated
    public static void closeStream(OutputStream out) {
        close(out);
    }
    /**
     * @deprecated use {@link #close(Closeable)} instead.
     */
    @Deprecated
    public static void closeStream(InputStream in) {
        close(in);
    }
    /**
     * Attempts to flush the given output stream, and then closes it.
     *
     * @param outStream the {@link OutputStream}. No action taken if outStream is null.
     */
    public static void flushAndCloseStream(OutputStream outStream) {
        if (outStream != null) {
            try {
                outStream.flush();
            } catch (IOException e) {
                // ignore
            }
            try {
                outStream.close();
            } catch (IOException e) {
                // ignore
            }
        }
    }

    /**
     * Closes the given {@link Closeable}.
     *
     * @param closeable the {@link Closeable}. No action taken if <code>null</code>.
     */
    public static void close(Closeable closeable) {
        if (closeable != null) {
            try {
                closeable.close();
            } catch (IOException e) {
                // ignore
            }
        }
    }

}