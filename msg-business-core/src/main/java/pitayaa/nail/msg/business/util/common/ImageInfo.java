package pitayaa.nail.msg.business.util.common;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Iterator;

import javax.imageio.ImageIO;
import javax.imageio.ImageReader;
import javax.imageio.stream.FileImageInputStream;

import lombok.Data;

@Data
public class ImageInfo {
	public static String GIF = "gif";
	private String format;
	private Integer height;
	private Integer width;

	public static ImageInfo getImageInfo(File imgFile)
			throws FileNotFoundException, IOException {
		ImageInfo imgInfo = new ImageInfo();
		BufferedImage in = null;
		Iterator<ImageReader> readers = ImageIO
				.getImageReaders(new FileImageInputStream(imgFile));
		do {
			ImageReader r = readers.next();
			r.setInput(new FileImageInputStream(imgFile));
			in = r.read(0, null);
			imgInfo.setFormat(r.getFormatName());
			imgInfo.setHeight(r.getHeight(r.getMinIndex()));
			imgInfo.setWidth(r.getWidth(r.getMinIndex()));
		} while (in == null && readers.hasNext());
		return imgInfo;
	}

}