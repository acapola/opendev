/**
 * Ariel
 * BitBucketOutputStream.java
 *
 * TODO a short description of the class
 *
 *
 * @author	nicholasink
 * @version	May 12, 2009
 */

package util;

import java.io.*;

/**
 * A repository for unwanted bytes. Basically a replacement for
 * <code>/dev/null</code>
 */
public class BitBucketOutputStream extends OutputStream {
	/**
	 * Sets System.out to use the BitBucketOutputStream as the output stream. In
	 * effect, redirecting standard out to oblivion.
	 * 
	 * @see restoreSystemOut
	 */
	public static void nullSystemOut() {
		System.setOut(new PrintStream(new BitBucketOutputStream(), true));
	}

	/**
	 * Recreates a System.out similar to the default System.out, and restores
	 * the default behavior.
	 * 
	 * @see #nullSystemOut
	 */
	public static void restoreSystemOut() {
		FileOutputStream fdOut = new FileOutputStream(FileDescriptor.out);
		System.setOut(new PrintStream(new BufferedOutputStream(fdOut, 128),
				true));
	}

	/**
	 * Sets System.err to use the BitBucketOutputStream as the output stream. In
	 * effect redirecting standard error to oblivion.
	 * 
	 * @see restoreSystemErr
	 */
	public static void nullSystemErr() {
		System.setErr(new PrintStream(new BitBucketOutputStream(), true));
	}

	/**
	 * Recreates a System.err similar to the default System.err, and restores
	 * the default behavior.
	 * 
	 * @see #nullSystemErr
	 */
	public static void restoreSystemErr() {
		FileOutputStream fdErr = new FileOutputStream(FileDescriptor.err);
		System.setErr(new PrintStream(new BufferedOutputStream(fdErr, 128),
				true));
	}

	/**
	 * Does nothing with the specified byte.
	 * 
	 * @param b
	 *            the <code>byte</code> to ignore.
	 * @exception IOException
	 *                if an I/O error occurs.
	 */
	public void write(int b) throws IOException {
	}
}
