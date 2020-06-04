package com.item.tool;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.color.ColorSpace;
import java.awt.image.BufferedImage;
import java.awt.image.ColorConvertOp;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

import javax.imageio.ImageIO;

/**
 * 获取灰度像素的比较数组、获取两个图的汉明距离、通过汉明距离计算相似度，取值范围 [0.0, 1.0]
 * 
 * @BelongsProject: maven-demo
 * @BelongsPackage: com.aliyun.picture.demo
 * @Author: Guoyh
 * @CreateTime: 2018-10-12 15:25
 * @Description: 对比图片相似度
 */
public class ImageContrastUtil {
	// 对比方法
	public static Double imageComparison(InputStream sampleInputStream, InputStream contrastInputStream)
			throws IOException {
		// 获取灰度像素的比较数组
		int[] photoArrayTwo = getPhotoArray(contrastInputStream);
		int[] photoArrayOne = getPhotoArray(sampleInputStream);

		// 获取两个图的汉明距离
		int hammingDistance = getHammingDistance(photoArrayOne, photoArrayTwo);
		// 通过汉明距离计算相似度，取值范围 [0.0, 1.0]
		double similarity = calSimilarity(hammingDistance);

		// 返回相似精度
		return similarity;
	}

	// 将任意Image类型图像转换为BufferedImage类型，方便后续操作
	public static BufferedImage convertToBufferedFrom(Image srcImage) {
		BufferedImage bufferedImage = new BufferedImage(srcImage.getWidth(null), srcImage.getHeight(null),
				BufferedImage.TYPE_INT_ARGB);
		Graphics2D g = bufferedImage.createGraphics();
		g.drawImage(srcImage, null, null);
		g.dispose();
		return bufferedImage;
	}

	// 转换至灰度图
	public static BufferedImage toGrayscale(Image image) {
		BufferedImage sourceBuffered = convertToBufferedFrom(image);
		ColorSpace cs = ColorSpace.getInstance(ColorSpace.CS_GRAY);
		ColorConvertOp op = new ColorConvertOp(cs, null);
		BufferedImage grayBuffered = op.filter(sourceBuffered, null);
		return grayBuffered;
	}

	// 缩放至32x32像素缩略图
	public static Image scale(Image image) {
		image = image.getScaledInstance(32, 32, Image.SCALE_SMOOTH);
		return image;
	}

	// 获取像素数组
	public static int[] getPixels(Image image) {
		int width = image.getWidth(null);
		int height = image.getHeight(null);
		int[] pixels = convertToBufferedFrom(image).getRGB(0, 0, width, height, null, 0, width);
		return pixels;
	}

	// 获取灰度图的平均像素颜色值
	public static int getAverageOfPixelArray(int[] pixels) {
		Color color;
		long sumRed = 0;
		for (int i = 0; i < pixels.length; i++) {
			color = new Color(pixels[i], true);
			sumRed += color.getRed();
		}
		int averageRed = (int) (sumRed / pixels.length);
		return averageRed;
	}

	// 获取灰度图的像素比较数组（平均值的离差）
	public static int[] getPixelDeviateWeightsArray(int[] pixels, final int averageColor) {
		Color color;
		int[] dest = new int[pixels.length];
		for (int i = 0; i < pixels.length; i++) {
			color = new Color(pixels[i], true);
			dest[i] = color.getRed() - averageColor > 0 ? 1 : 0;
		}
		return dest;
	}

	// 获取两个缩略图的平均像素比较数组的汉明距离（距离越大差异越大）
	public static int getHammingDistance(int[] a, int[] b) {
		int sum = 0;
		for (int i = 0; i < a.length; i++) {
			sum += a[i] == b[i] ? 0 : 1;
		}
		return sum;
	}

	// 获取灰度像素的比较数组
	public static int[] getPhotoArray(InputStream inputStream) throws IOException {
		Image image = ImageIO.read(inputStream);
//        Image image = ImageIO.read(imageFile);
		// 转换至灰度
		image = toGrayscale(image);
		// 缩小成32x32的缩略图
		image = scale(image);
		// 获取灰度像素数组
		int[] pixels = getPixels(image);
		// 获取平均灰度颜色
		int averageColor = getAverageOfPixelArray(pixels);
		// 获取灰度像素的比较数组（即图像指纹序列）
		pixels = getPixelDeviateWeightsArray(pixels, averageColor);

		return pixels;
	}

	// 通过汉明距离计算相似度
	public static double calSimilarity(int hammingDistance) {
		int length = 32 * 32;
		double similarity = (length - hammingDistance) / (double) length;

		// 使用指数曲线调整相似度结果
		similarity = java.lang.Math.pow(similarity, 2);
		return similarity;
	}

	/**
	 * @param args
	 * @return void
	 * @author Guoyh
	 * @date 2018/10/12 15:27
	 */
	public static void main(String[] args) throws Exception {

		// (数据类型)(最小值+Math.random()*(最大值-最小值+1))
		Double imageComparison = imageComparison(new FileInputStream("D:/image/1.png"),
				new FileInputStream("D:/image/9.png"));
		System.out.println("两张图片的相似度为：" + imageComparison * 100 + "%");
	}
}
