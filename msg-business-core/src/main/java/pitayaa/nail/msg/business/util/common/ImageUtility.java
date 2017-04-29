package pitayaa.nail.msg.business.util.common;

import java.io.File;

public class ImageUtility {

	public static final int INT_THUMB_SIZE_60 = 60;

	public static final int INT_THUMB_SIZE_120 = 120;

	public static final int INT_THUMB_SIZE_154 = 154;

	public static final int INT_THUMB_SIZE_185 = 185;

	public static final int INT_THUMB_SIZE_200 = 200;

	public static final int INT_THUMB_SIZE_320 = 320;

	public static final int INT_DEFAULT_ENTRY_TOP_IMAGE_WIDTH = 260;

	public static final int INT_DEFAULT_ENTRY_TOP_IMAGE_HEIGHT = 195;

	public static final int INT_DEFAULT_PROMOTION_IMAGE_WIDTH = 514;

	public static final int INT_DEFAULT_PROMOTION_IMAGE_HEIGHT = 284;

	/** Big image detail on Web. */
	public static final int INT_DEFAULT_IMAGE_WIDTH = 960;
	public static final int INT_DEFAULT_IMAGE_HEIGHT = 720;

	/** Small thumbnail image feed/chat/list image on Web. */
	public static final int INT_THUMB_SIZE_128 = 128;
	public static final int INT_THUMB_SIZE_96 = 96;

	/** Big thumbnail image feed/chat on Web */
	public static final int INT_THUMB_SIZE_400 = 400;
	public static final int INT_THUMB_SIZE_300 = 300;

	public static final int INT_THUMB_SIZE_240 = 240;
	public static final int INT_THUMB_SIZE_180 = 180;

	/** Avatar big thumbnail image feed/chat on Web. */
	public static final int INT_THUMB_SIZE_150 = 150;

	/** Small thumbnail image feed/chat/list image in Mobile. */
	public static final int INT_THUMB_SIZE_70 = 70;

	/** integer constant express size of thumb nail image 80. */
	public static final int INT_THUMB_SIZE_80 = 80;

	/** integer constant express size of thumb nail image 130. */
	public static final int INT_THUMB_SIZE_130 = 130;

	/** Big thumbnail image feed/chat on Mobile */
	public static final int INT_THUMB_SIZE_640 = 640;
	public static final int INT_THUMB_SIZE_480 = 480;

	/** Avatar header and autocomplete chat */
	public static final int INT_THUMB_SIZE_20 = 20;

	/** chat bar */
	public static final int INT_THUMB_SIZE_30 = 30;

	/** Avatar feed/chat/notify/home/profile */
	public static final int INT_THUMB_SIZE_50 = 50;

	public static final int IMAGE_THUMB_WIDTH = 248;
	public static final int IMAGE_THUMB_HEIGHT = 164;

	public static Boolean createImageThumbnail(String srcImage,
			String outPutFileName, Integer thumbWidth, Integer thumbHeight)
			throws Exception {
		try {
			Boolean res = ImgMagickUtil.resize(srcImage, outPutFileName,
					thumbWidth, thumbHeight, null, false);
			if (res == null || !res) {
				return false;
			}
			return true;
		} catch (Exception ex) {
			throw ex;
		}
	}

	public static Boolean createThumbnail(String srcImage,
			String outPutFileName, Integer thumbWidth, Integer thumbHeight) {
		Boolean createGif = checkSizeCreateGif(thumbWidth, thumbHeight);
		Boolean res = ImgMagickUtil.resize(srcImage, outPutFileName,
				thumbWidth, thumbHeight, null, createGif);

		if (res == null || !res) {
			return false;
		}

		return true;
	}

	public static Boolean resizeAndCropThumbnail(String srcImage,
			String outPutFileName, Integer thumbWidth, Integer thumbHeight) {
		Boolean res = ImgMagickUtil.cropAndResize(srcImage, outPutFileName,
				thumbWidth, thumbHeight);
		if (res == null || !res) {
			return false;
		}
		return true;

	}

	public static Boolean cropThumbnail(String srcImage, String outPutFileName,
			int xx, int yy, Integer thumbWidth, Integer thumbHeight) {
		Boolean res = ImgMagickUtil.crop(srcImage, outPutFileName, xx, yy,
				thumbWidth, thumbHeight);
		if (res == null || !res) {
			return false;
		}
		return true;

	}

	public static ImageInfo getImageInfo(String srcImage) {
		ImageInfo info = new ImageInfo();
		File f = new File(srcImage);
		try {
			info = ImageInfo.getImageInfo(f);
		} catch (Exception e) {
			return null;
		}
		return info;
	}

	public static ImageInfo getImageInfo(File f) {
		ImageInfo info = new ImageInfo();
		try {
			info = ImageInfo.getImageInfo(f);
		} catch (Exception e) {
			return null;
		}
		return info;
	}

	public static Boolean checkSizeCropThumb(int width, int height) {
		if ((width == ImageUtility.INT_THUMB_SIZE_128 && height == ImageUtility.INT_THUMB_SIZE_96)
				|| (width == ImageUtility.INT_THUMB_SIZE_70 && height == ImageUtility.INT_THUMB_SIZE_70)
				|| (width == ImageUtility.INT_THUMB_SIZE_240 && height == ImageUtility.INT_THUMB_SIZE_180)) {
			return true;
		}
		return false;
	}

	public static Boolean checkSizeCreateGif(int width, int height) {
		if ((width == ImageUtility.INT_THUMB_SIZE_400 && height == ImageUtility.INT_THUMB_SIZE_300)
				|| (width == ImageUtility.INT_DEFAULT_IMAGE_WIDTH && height == ImageUtility.INT_DEFAULT_IMAGE_HEIGHT)) {
			return true;
		}
		return false;
	}
}
