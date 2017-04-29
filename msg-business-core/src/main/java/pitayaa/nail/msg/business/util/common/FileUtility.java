package pitayaa.nail.msg.business.util.common;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.imageio.ImageIO;

import org.apache.commons.io.FileUtils;
import org.apache.tika.metadata.Metadata;
import org.apache.tika.parser.AutoDetectParser;
import org.apache.tika.sax.BodyContentHandler;

public class FileUtility {

	public static void saveBufferedImageToPNGFile(BufferedImage image,
			String fileName) {
		File output = new File(fileName);
		if (!isFolderExist(getOnlyPathName(fileName))) {
			createDirectory(getOnlyPathName(fileName));
		}
		try {
			ImageIO.write(image, "png", output);
		} catch (IOException ex) {
			Logger.getLogger(FileUtility.class.getName()).log(Level.SEVERE,
					null, ex);
		}
	}

	public static void saveBufferedImageToGIFFile(BufferedImage image,
			String fileName) {
		File output = new File(fileName);
		try {
			ImageIO.write(image, "gif", output);
		} catch (IOException ex) {
			Logger.getLogger(FileUtility.class.getName()).log(Level.SEVERE,
					null, ex);
		}
	}

	public static void saveBufferedImageToJPEGFile(BufferedImage image,
			String fileName) {
		File output = new File(fileName);
		try {
			ImageIO.write(image, "jpeg", output);
		} catch (IOException ex) {
			Logger.getLogger(FileUtility.class.getName()).log(Level.SEVERE,
					null, ex);
		}
	}

	public static boolean isFileExist(String fileName) {
		File f = new File(fileName);
		return f.exists();
	}

	// get only path include last /
	public static String getOnlyPathName(String path) {
		int l = Math.max(path.lastIndexOf("\\"), path.lastIndexOf("/") + 1);

		// String path
		return path.substring(0, l);
	}

	public static boolean isFolderExist(String folderName) {
		File f = new File(folderName);
		if (f.exists()) {
			return true;
		} else {
			return false;
		}
	}

	// create Directory
	// return true if create successfully else return false
	public static boolean createDirectory(String directoryName) {
		// Create a directory;
		File folder = new File(directoryName);
		boolean success = folder.mkdir();
		
		if (!success) {
			// Create directory and all ancestor directories
			success = folder.mkdirs();
		}
		
		return success;
	}

	public static boolean deleteFile(String fileName) {
		File f = new File(fileName);
		return f.delete();

	}

	public static void copyFile(String fromFileName, String toFileName)
			throws IOException {
		File fromFile = new File(fromFileName);
		File toFile = new File(toFileName);

		if (!fromFile.exists()) {
			throw new IOException("FileCopy: " + "no such source file: "
					+ fromFileName);
		}
		if (!fromFile.isFile()) {
			throw new IOException("FileCopy: " + "can't copy directory: "
					+ fromFileName);
		}
		if (!fromFile.canRead()) {
			throw new IOException("FileCopy: " + "source file is unreadable: "
					+ fromFileName);
		}
		if (toFile.isDirectory()) {
			toFile = new File(toFile, fromFile.getName());
		}
		if (toFile.exists()) {
			if (!toFile.canWrite()) {
				throw new IOException("FileCopy: "
						+ "destination file is unwriteable: " + toFileName);
			}
			return;
		} else {
			String parent = toFile.getParent();
			if (parent == null) {
				parent = System.getProperty("user.dir");
			}
			File dir = new File(parent);
			if (!dir.exists()) {
				throw new IOException("FileCopy: "
						+ "destination directory doesn't exist: " + parent);
			}
			if (dir.isFile()) {
				throw new IOException("FileCopy: "
						+ "destination is not a directory: " + parent);
			}
			if (!dir.canWrite()) {
				throw new IOException("FileCopy: "
						+ "destination directory is unwriteable: " + parent);
			}
		}

		FileInputStream from = null;
		FileOutputStream to = null;
		try {
			from = new FileInputStream(fromFile);
			to = new FileOutputStream(toFile);
			byte[] buffer = new byte[4096];
			int bytesRead;

			while ((bytesRead = from.read(buffer)) != -1) {
				to.write(buffer, 0, bytesRead); // write

			}
		} finally {
			if (from != null) {
				try {
					from.close();
				} catch (IOException e) {
					;
				}
			}
			if (to != null) {
				try {
					to.close();
				} catch (IOException e) {
					;
				}
			}
		}
	}

	public static void saveFile(File file, String fileName) throws Exception {
		try {

			File theFile = new File(fileName);

			FileUtils.copyFile(file, theFile);

		} catch (Exception e) {
			throw new Exception(e.getMessage());
		}
	}

	public static void createFileFromData(String filename, byte[] data)
			throws IOException {
		FileOutputStream fout = new FileOutputStream(filename);
		fout.write(data);
		fout.close();
	}

	public static String getFileNameFromPath(String path) {

		String filename = path.substring(path.lastIndexOf("/") + 1);
		return filename;
	}

	/*
	 * Add number to filename when this file is existed
	 */
	public static String getNonExistFileName(String rootPath, String fileName) {
		int j = 0;
		String tmpFileName = fileName;
		String onlyName = tmpFileName
				.substring(0, tmpFileName.lastIndexOf("."));
		String ext = tmpFileName.substring(tmpFileName.lastIndexOf("."),
				tmpFileName.length());
		if (!isFileExist(rootPath + tmpFileName)) {
			return tmpFileName;
		}
		do {
			tmpFileName = onlyName + "(" + String.valueOf(j) + ")" + ext;
			j++;
		} while (isFileExist(rootPath + tmpFileName));
		return tmpFileName;
	}

	public static String getSafeFileName(String input) {
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < input.length(); i++) {
			char c = input.charAt(i);
			if (c != '/' && c != '\\' && c != 0) {
				sb.append(c);
			}
		}
		return sb.toString();
	}

	public static String getMime(File file) {
		FileInputStream is = null;
		String mime = null;
		try {
			is = new FileInputStream(file);

			BodyContentHandler contenthandler = new BodyContentHandler();
			Metadata metadata = new Metadata();
			metadata.set(Metadata.RESOURCE_NAME_KEY, file.getName());
			AutoDetectParser parser = new AutoDetectParser();
			parser.parse(is, contenthandler, metadata);
			mime = metadata.get(Metadata.CONTENT_TYPE);

			return mime;
		} catch (Exception e) {
			return null;
		} finally {
			if (is != null) {
				try {
					is.close();
					return mime;
				} catch (Exception e) {
					return null;
				}
			}
		}
	}

	public static boolean deleteFile(String filePath, String fileName,
			boolean checkExist) throws Exception {
		File orgFile = new File(filePath + File.separator + fileName);

		try {
			if (checkExist) {
				if (checkFileExist(filePath, fileName)) {
					return orgFile.delete();
				} else {
					return true;
				}
			} else {
				return orgFile.delete();
			}

		} catch (Exception ex) {
			throw ex;
		}
	}

	public static boolean checkFileExist(String filePath, String fileName)
			throws Exception {
		File orgFile = new File(filePath);

		if (!orgFile.exists()) {
			throw new Exception("File path '" + filePath + "' not exist.");
		}
		if (!orgFile.isDirectory()) {
			throw new Exception("File path '" + filePath + "' isn't a folder.");
		}

		for (String item : orgFile.list()) {
			if (fileName.equals(item)) {
				return true;
			}
		}

		return false;
	}
}
