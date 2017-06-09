package com.governorapp.util;

import android.content.Context;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class FileUtil {

	private static final int FILESIZE = 8 * 1024;

	/**
	 * 在SD卡上创建文件
	 * 
	 * @param fileName
	 * @return
	 * @throws IOException
	 */
	public static File createSDFile(String fileName) throws IOException {
		File file = new File(fileName);
		file.createNewFile();
		return file;
	}

	/**
	 * 在SD卡上创建目录
	 * 
	 * @param dirName
	 * @return
	 */
	public static File createSDDir(String dirName) {
		File dir = new File(dirName);
		dir.mkdir();
		return dir;
	}

	/**
	 * 判断SD卡上的文件夹是否存在
	 * 
	 * @param filePath
	 * @return
	 */
	public static boolean isFileExist(String filePath) {
		File file = new File(filePath);
		return file.exists();
	}

	/**
	 * 将一个InputStream里面的数据写入到SD卡中
	 * 
	 * @param path
	 * @param fileName
	 * @param input
	 * @return
	 */
	public static File write2SDFromInput(String path, String fileName,
			InputStream input) {
		File file = null;
		OutputStream output = null;
		try {
			createSDDir(path);
			file = createSDFile(path + fileName);
			output = new FileOutputStream(file);
			byte[] buffer = new byte[FILESIZE];

			int length;
			while ((length = (input.read(buffer))) > 0) {
				output.write(buffer, 0, length);
			}

			output.flush();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				output.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return file;
	}

	/**
	 * A helper method for writing string data to file
	 *
	 * @param inputString the input {@link String}
	 * @param destFile the dest file to write to
	 */
	public static void writeToFile(String inputString, File destFile) throws IOException {
		writeToFile(new ByteArrayInputStream(inputString.getBytes()), destFile);
	}

	public static void copyFile(File srcFile , File dstFile) throws IOException {
		InputStream is = new FileInputStream(srcFile) ;
		OutputStream os = new FileOutputStream(dstFile) ;
		StreamUtil.copyStreams(is , os);
	}

	/**
	 * A helper method for writing stream data to file
	 *
	 * @param input the unbuffered input stream
	 * @param destFile the dest file to write to
	 */
	public static void writeToFile(InputStream input, File destFile) throws IOException {
		InputStream origStream = null;
		OutputStream destStream = null;
		try {
			origStream = new BufferedInputStream(input);
			destStream = new BufferedOutputStream(new FileOutputStream(destFile , true));
			StreamUtil.copyStreams(origStream, destStream);
		} finally {
			StreamUtil.close(origStream);
			StreamUtil.flushAndCloseStream(destStream);
		}
	}

	public static File createTempFile(String prefix, String suffix) throws IOException {
		File returnFile = File.createTempFile(prefix, suffix);
		verifyDiskSpace(returnFile);
		return returnFile;
	}
	private static long mMinDiskSpaceMb = 100;
	private static void verifyDiskSpace(File file) {
		// Based on empirical testing File.getUsableSpace is a low cost operation (~ 100 us for
		// local disk, ~ 100 ms for network disk). Therefore call it every time tmp file is
		// created
		long usableSpace = file.getUsableSpace();
		long minDiskSpace = mMinDiskSpaceMb * 1024 * 1024;
		if (usableSpace < minDiskSpace) {
			/*throw new LowDiskSpaceException(String.format(
					"Available space on %s is %.2f MB. Min is %d MB", file.getAbsolutePath(),
					file.getUsableSpace() / (1024.0 * 1024.0), mMinDiskSpaceMb));*/
		}
	}

	/**
	 * Gets the extension for given file name.
	 *
	 * @param fileName
	 * @return the extension or empty String if file has no extension
	 */
	public static String getExtension(String fileName) {
		int index = fileName.lastIndexOf('.');
		if (index == -1) {
			return "";
		} else {
			return fileName.substring(index);
		}
	}

	/**
	 * Helper function to create a temp directory in the system default temporary file directory.
	 *
	 * @param prefix The prefix string to be used in generating the file's name; must be at least
	 *            three characters long
	 * @return the created directory
	 * @throws IOException if file could not be created
	 */
	public static File createTempDir(String prefix) throws IOException {
		return createTempDir(prefix, null);
	}
	/**
	 * Helper function to create a temp directory.
	 *
	 * @param prefix The prefix string to be used in generating the file's name; must be at least
	 *            three characters long
	 * @param parentDir The parent directory in which the directory is to be created. If
	 *            <code>null</code> the system default temp directory will be used.
	 * @return the created directory
	 * @throws IOException if file could not be created
	 */
	public static File createTempDir(String prefix, File parentDir) throws IOException {
		// create a temp file with unique name, then make it a directory
		File tmpDir = File.createTempFile(prefix, "", parentDir);
		return deleteFileAndCreateDirWithSameName(tmpDir);
	}

	private static File deleteFileAndCreateDirWithSameName(File tmpDir) throws IOException {
		tmpDir.delete();
		return createDir(tmpDir);
	}

	private static File createDir(File tmpDir) throws IOException {
		if (!tmpDir.mkdirs()) {
			throw new IOException("unable to create directory");
		}
		return tmpDir;
	}

	/**
	 * Recursively delete given file or directory and all its contents.
	 *
	 * @param rootDir the directory or file to be deleted; can be null
	 */
	public static void recursiveDelete(File rootDir) {
		if (rootDir != null) {
			if (rootDir.isDirectory()) {
				File[] childFiles = rootDir.listFiles();
				if (childFiles != null) {
					for (File child : childFiles) {
						recursiveDelete(child);
					}
				}
			}
			//because android file system bug
			final File to = new File(rootDir.getAbsolutePath() + System.currentTimeMillis());
			rootDir.renameTo(to);
			to.delete();
//			rootDir.delete();
		}
	}

	public static InputStream getInputStreamFromAsset(Context context, String path){
		InputStream is = null;
		try {
			is = context.getAssets().open(path);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return is;
	}

}
