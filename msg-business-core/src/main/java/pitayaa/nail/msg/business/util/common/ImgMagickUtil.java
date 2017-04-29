package pitayaa.nail.msg.business.util.common;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;


public class ImgMagickUtil {
	/**
	 * Run the exec() command, logs errors if exit status is nonzero, and
	 * returns true if exit status is 0 (success).
	 * 
	 * @param command
	 *            the command line
	 * @return true if command executed successfully, false otherwise
	 */
	private static boolean exec(String[] command) {
		Process proc = null;

		ProcessBuilder pb = new ProcessBuilder(command);
		// if (isWindows()) {
		// pb.environment().clear();
		// pb.environment().put("Path", System.getenv("Path"));
		// }

		int exitStatus = 0;

		InputStream stream = null;

		try {
			proc = pb.start();
			stream = proc.getErrorStream();

			byte[] b = new byte[1024];

			while (stream.read(b) != -1) {
				System.out.println(new String(b));
			}

			exitStatus = proc.waitFor();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (InterruptedException e) {
			e.printStackTrace();
		} finally {
			if (stream != null) {
				try {
					stream.close();
				} catch (Exception ex) {
					ex.printStackTrace();
				}
			}
			if (null != proc) {
				try {
					proc.destroy();
				} catch (Exception ex) {
					ex.printStackTrace();
				}
			}
		}

		if (exitStatus != 0) {
			System.out.println("Error executing command: " + exitStatus);
		}

		return (exitStatus == 0);
	}

	/**
	 * Checks if O.S is Windows.
	 * 
	 * @return true, if is windows
	 */
	private static boolean isWindows() {
		return System.getProperty("os.name").toLowerCase().startsWith("win");
	}

	/**
	 * Create directories.
	 * 
	 * @param fileWithPath
	 *            the file with path
	 * @return true, if successful
	 */
	private static boolean makedirs(String fileWithPath) {
		if (fileWithPath != null) {
			final int indexFolders = fileWithPath.lastIndexOf("/") > 0 ? fileWithPath
					.lastIndexOf("/") : fileWithPath.lastIndexOf("\\");
			if (indexFolders != -1) {
				String dirsName = fileWithPath.substring(0, indexFolders);
				File dirs = new File(dirsName);
				return dirs.mkdirs();
			}
		}
		return false;
	}

	/**
	 * Uses ImageMagick to resize image. Returns true on success, false on
	 * failure. Does not check if either file exists.
	 * 
	 * @param in
	 *            the input image file name
	 * @param out
	 *            the output image file name
	 * @param width
	 *            the width
	 * @param height
	 *            the height
	 * @param quality
	 *            the quality
	 * @return true, if successful
	 */
	public static boolean resize(String in, String out, Integer width,
			Integer height, Integer quality, Boolean animated) {
		makedirs(out);

		File inFile = new File(in);
		ImageInfo imgInfo = new ImageInfo();

		try {
			imgInfo = ImageInfo.getImageInfo(inFile);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

		if (imgInfo.getFormat() != null
				&& ImageInfo.GIF.equals(imgInfo.getFormat().toLowerCase())) {
			if (animated != null && animated)
				return resizeAnimatedGif(in, out, width, height);
			else
				return resizeStillGif(in, out, width, height);
		}

		if (quality == null || quality < 0 || quality > 100) {
			quality = 90;
		}

		List<String> command = new ArrayList<String>();
		boolean isWindows = isWindows();

		if (isWindows) {
			command.add("cmd.exe");
			command.add("/c");
		}
		// else { command.add("/bin/sh"); /*bash* command.add("-c"); }

		command.add("convert");
		command.add(in);
		command.add("-resize");
		command.add((width == null ? "" : width) + "x"
				+ (height == null ? "" : height) + (isWindows ? "^>" : ">"));
		command.add("-quality");
		command.add("" + quality);
		command.add(out);

		// System.out.println(command);

		return exec(command.toArray(new String[0]));
	}

	/**
	 * Uses ImageMagick to resize animated .gif image. Returns true on success,
	 * false on failure. Does not check if either file exists.
	 * 
	 * @param in
	 *            the in
	 * @param out
	 *            the out
	 * @param width
	 *            the width
	 * @param height
	 *            the height
	 * @return true, if successful
	 */
	private static boolean resizeAnimatedGif(String in, String out,
			Integer width, Integer height) {
		List<String> command = new ArrayList<String>();
		if (isWindows()) {
			command.add("cmd.exe");
			command.add("/c");
		} /*
		 * else { command.add("/bin/sh"); command.add("-c"); }
		 */
		command.add("convert");
		command.add(in);
		command.add("-coalesce");
		command.add("-thumbnail");
		command.add((width == null ? "" : width) + "x"
				+ (height == null ? "" : height) + (isWindows() ? "^>" : ">"));
		command.add("-quality");
		command.add("100");
		command.add(out);

		// System.out.println(command);

		return exec(command.toArray(new String[0]));
	}

	/**
	 * Uses ImageMagick to resize animated .gif image into stil .gif. Returns
	 * true on success, false on failure. Does not check if either file exists.
	 * 
	 * @param in
	 *            the in
	 * @param out
	 *            the out
	 * @param width
	 *            the width
	 * @param height
	 *            the height
	 * @return true, if successful
	 */
	private static boolean resizeStillGif(String in, String out, Integer width,
			Integer height) {
		List<String> command = new ArrayList<String>();
		if (isWindows()) {
			command.add("cmd.exe");
			command.add("/c");
		} /*
		 * else { command.add("/bin/sh"); command.add("-c"); }
		 */
		command.add("convert");
		command.add(in);
		command.add("-layers");
		command.add("flatten");
		command.add("-thumbnail");
		command.add((width == null ? "" : width) + "x"
				+ (height == null ? "" : height) + (isWindows() ? "^>" : ">"));
		command.add(out);

		// System.out.println(command);

		return exec(command.toArray(new String[0]));
	}

	/**
	 * Uses ImageMagick to crop image. Returns true on success, false on
	 * failure. Does not check if either file exists.
	 * 
	 * @param in
	 *            the in
	 * @param out
	 *            the out
	 * @param x
	 *            the x
	 * @param y
	 *            the y
	 * @param width
	 *            the width
	 * @param height
	 *            the height
	 * @return true, if successful
	 */
	public static boolean crop(String in, String out, int x, int y, int width,
			int height) {
		makedirs(out);
		File inFile = new File(in);
		ImageInfo imgInfo = new ImageInfo();
		try {
			imgInfo = ImageInfo.getImageInfo(inFile);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		List<String> command = new ArrayList<String>();
		if (isWindows()) {
			command.add("cmd.exe");
			command.add("/c");
		} /*
		 * else { command.add("/bin/sh"); command.add("-c"); }
		 */
		command.add("convert");
		command.add(in);
		command.add("-crop");
		if (imgInfo.getFormat() != null
				&& ImageInfo.GIF.equals(imgInfo.getFormat().toLowerCase())) {
			command.add("-coalesce");
		}
		command.add(width + "x" + height + (x >= 0 ? "+" + x : x)
				+ (y >= 0 ? "+" + y : y));
		command.add(out);

		// System.out.println(command);

		return exec(command.toArray(new String[0]));
	}

	/**
	 * Uses ImageMagick to crop then resize image. Returns true on success,
	 * false on failure. Does not check if either file exists.
	 * 
	 * @param in
	 *            the in
	 * @param out
	 *            the out
	 * @param width
	 *            the width
	 * @param height
	 *            the height
	 * @return true, if successful
	 */
	public static boolean cropAndResize(String in, String out, Integer width,
			Integer height) {
		if (width == null || height == null) {
			return false;
		}
		makedirs(out);
		int imgWidth = 0;
		int imgHeight = 0;
		int cropWidth = 0;
		int cropHeight = 0;
		int x = 0;
		int y = 0;
		File inFile = new File(in);
		ImageInfo imgInfo = new ImageInfo();
		try {
			imgInfo = ImageInfo.getImageInfo(inFile);
			imgWidth = imgInfo.getWidth();
			imgHeight = imgInfo.getHeight();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

		double imgRatio = (double) imgWidth / (double) imgHeight;
		double thbRatio = (double) width / (double) height;
		if (imgWidth > width && imgHeight > height) {
			if (thbRatio < imgRatio) {
				cropHeight = imgHeight;
				cropWidth = (int) (cropHeight * thbRatio);
				x = (imgWidth / 2) - (cropWidth / 2);
			} else {
				cropWidth = imgWidth;
				cropHeight = (int) (cropWidth / thbRatio);
				y = (imgHeight / 2) - (cropHeight / 2);
			}
		} else {
			if (imgWidth >= width && imgHeight < height) {
				cropWidth = width;
				cropHeight = imgHeight;
				x = (imgWidth / 2) - (cropWidth / 2);
			} else if (imgWidth < width && imgHeight >= height) {
				cropWidth = imgWidth;
				cropHeight = height;
				y = (imgHeight / 2) - (cropHeight / 2);
			} else {
				cropWidth = imgWidth;
				cropHeight = imgHeight;
			}
		}
		List<String> command = new ArrayList<String>();

		if (isWindows()) {
			command.add("cmd.exe");
			command.add("/c");
		} /*
		 * else { command.add("/bin/sh"); command.add("-c"); }
		 */
		// command.add("C:/Program Files/ImageMagick-6.7.8-Q16/" +
		// "convert");
		command.add("convert");
		command.add(in);
		if (imgInfo.getFormat() != null
				&& ImageInfo.GIF.equals(imgInfo.getFormat().toLowerCase())) {
			command.add("-layers");
			command.add("flatten");
		}
		command.add("-crop");
		command.add(cropWidth + "x" + cropHeight + "+" + x + "+" + y);
		command.add("-thumbnail");
		command.add(width + "x" + height + (isWindows() ? "^>" : ">"));
		command.add("-quality");
		command.add("90");
		command.add(out);

		System.out.println(command);

		return exec(command.toArray(new String[0]));
	}

	/**
	 * Uses ImageMagick to rotate image. Returns true on success, false on
	 * failure. Does not check if either file exists.
	 * 
	 * @param in
	 *            the in
	 * @param out
	 *            the out
	 * @param degreeToRotate
	 *            the degree to rotate
	 * @return true, if successful
	 */
	public static boolean rotate(String in, String out, Integer degreeToRotate) {
		makedirs(out);
		List<String> command = new ArrayList<String>();
		if (isWindows()) {
			command.add("cmd.exe");
			command.add("/c");
		} /*
		 * else { command.add("/bin/sh"); command.add("-c"); }
		 */
		command.add("convert");
		command.add(in);
		command.add("-rotate");
		command.add("" + degreeToRotate);
		command.add(out);

		// System.out.println(command);

		return exec(command.toArray(new String[0]));
	}
}
