package com.item.tool;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.awt.image.CropImageFilter;
import java.awt.image.FilteredImageSource;
import java.awt.image.ImageFilter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import javax.imageio.stream.ImageInputStream;

import com.sun.image.codec.jpeg.JPEGCodec;
import com.sun.image.codec.jpeg.JPEGImageEncoder;

public class ImageCutUtil {

	// �����ӡ��־
	public static boolean IS_DEBUG = false;

	/**
	 * 
	 * @param srcImg  ԭͼƬ·��
	 * @param destImg ���ͼƬ·��
	 * @param left    ��߾�
	 * @param top     �ϱ߾�
	 * @param width   �ؼ����
	 * @param height  �ؼ��߶�
	 * @return
	 * @throws IOException
	 */
	public static boolean cutImage(String srcImg, String destImg, int left, int top, Integer width, Integer height)
			throws IOException {
		if (destImg == null || destImg.trim().length() == 0) {
			if (IS_DEBUG) {
				System.err.println("ͼƬ�ؼ������ͼƬ·��[" + destImg + "]���󡣡���");
			}
			return false;
		}
		File file = new File(srcImg);
		if (file == null || file.exists() == false || file.isFile() == false) {
			if (IS_DEBUG) {
				System.err.println("ͼƬ�ؼ���[" + srcImg + "]�ļ������ڡ�����");
			}
			return false;
		}
		return cutImage(javax.imageio.ImageIO.read(file), destImg, left, top, width, height);
	}

	/**
	 * 
	 * @param input   ԭͼƬ������
	 * @param destImg ���ͼƬ·��
	 * @param left    ��߾�
	 * @param top     �ϱ߾�
	 * @param width   �ؼ����
	 * @param height  �ؼ��߶�
	 * @return
	 * @throws IOException
	 */
	public static boolean cutImage(InputStream input, String destImg, int left, int top, Integer width, Integer height)
			throws IOException {
		if (destImg == null || destImg.trim().length() == 0) {
			if (IS_DEBUG) {
				System.err.println("ͼƬ�ؼ������ͼƬ·��[" + destImg + "]���󡣡���");
			}
			return false;
		}
		if (input == null) {
			if (IS_DEBUG) {
				System.err.println("ͼƬ�ؼ���������Ϊ�ա�����");
			}
			return false;
		}
		return cutImage(javax.imageio.ImageIO.read(input), destImg, left, top, width, height);
	}

	/**
	 * 
	 * @param imginput ԭͼƬ������
	 * @param destImg  ���ͼƬ·��
	 * @param left     ��߾�
	 * @param top      �ϱ߾�
	 * @param width    �ؼ����
	 * @param height   �ؼ��߶�
	 * @return
	 * @throws IOException
	 */
	public static boolean cutImage(ImageInputStream imginput, String destImg, int left, int top, Integer width,
			Integer height) throws IOException {
		if (destImg == null || destImg.trim().length() == 0) {
			if (IS_DEBUG) {
				System.err.println("ͼƬ�ؼ������ͼƬ·��[" + destImg + "]���󡣡���");
			}
			return false;
		}
		if (imginput == null) {
			if (IS_DEBUG) {
				System.err.println("ͼƬ�ؼ���ͼƬ������Ϊ�ա�����");
			}
			return false;
		}
		return cutImage(javax.imageio.ImageIO.read(imginput), destImg, left, top, width, height);
	}

	public static boolean cutImage(Image srcImage, String destImg, int left, int top, Integer width, Integer height)
			throws IOException {
		if (destImg == null || destImg.trim().length() == 0) {
			if (IS_DEBUG) {
				System.err.println("ͼƬ�ؼ������ͼƬ·��[" + destImg + "]���󡣡���");
			}
			return false;
		}
		if (srcImage == null) {
			if (IS_DEBUG) {
				System.err.println("ͼƬ�ؼ���Դͼ������Ч��ͼƬ������");
			}
			return false;
		}
		StringBuffer sb = null;
		boolean params_error = false;
		if (IS_DEBUG) {
			sb = new StringBuffer("ͼƬ�ؼ���");
		}
		int src_w = srcImage.getWidth(null); // Դͼ��
		int src_h = srcImage.getHeight(null);// Դͼ��

		if (left < 0 || left >= src_w) {
			if (IS_DEBUG) {
				sb.append("��߾೬��ԭͼ��Ч��ȣ� ");
			}
			params_error = true;
		}
		if (top < 0 || top >= src_h) {
			if (IS_DEBUG) {
				sb.append("�ϱ߾೬��ԭͼ��Ч�߶ȣ� ");
			}
			params_error = true;
		}
		if (width != null && width <= 0) {
			if (IS_DEBUG) {
				sb.append("�ؼ���Ȳ���С�ڻ���� 0 �� ");
			}
			params_error = true;
		}
		if (height != null && height <= 0) {
			if (IS_DEBUG) {
				sb.append("�ؼ��߶Ȳ���С�ڻ���� 0 �� ");
			}
			params_error = true;
		}
		if (params_error) {
			if (IS_DEBUG) {
				System.err.println(sb.toString());
			}
			return false;
		}

		// Ŀ��ͼƬ��
		if (width == null || width > src_w || width + left > src_w) {
			width = src_w - left;
		}
		// Ŀ��ͼƬ��
		if (height == null || height > src_h || height + top > src_h) {
			height = src_h - top;
		}
		// Ŀ��ͼƬ
		ImageFilter cropFilter = new CropImageFilter(left, top, width, height);
		Image cutImage = Toolkit.getDefaultToolkit()
				.createImage(new FilteredImageSource(srcImage.getSource(), cropFilter));
		// �ػ�ͼƬ
		BufferedImage tag = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
		Graphics g = tag.getGraphics();
		g.drawImage(cutImage, 0, 0, null); // ������С���ͼ
		g.dispose();
		// ���Ϊ�ļ�
		FileOutputStream out = null;
		try {
			out = new FileOutputStream(destImg);
			JPEGImageEncoder encoder = JPEGCodec.createJPEGEncoder(out);
			encoder.encode(tag);

			if (IS_DEBUG) {
				System.out.println(
						"ͼƬ�ؼ���ԭͼƬ���Ϊ[" + src_w + " ����" + src_h + "]�����ͼƬ�Ŀ��Ϊ[" + width + " ����" + height + "].");
			}

		} catch (Exception e) {
			System.out.println(e.getMessage());
		} finally {
			if (out != null) {
				out.flush();
				out.close();
			}

		}
		return true;
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		try {
			cutImage("D:/image/7.jpg", "D:/image/9.jpg", 37, 67, 928, 1297);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
