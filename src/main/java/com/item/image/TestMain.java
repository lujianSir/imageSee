package com.item.image;

import java.io.IOException;

import com.item.tool.FingerPrint;
import com.item.tool.GetImage;

public class TestMain {

	public static void main(String[] args) throws IOException {

		String startpath1 = "D:/image/xuexiao/4.png";
		String endpath1 = "D:/image/xuexiao/p.jpg";
		GetImage.getImage(startpath1, endpath1);

		String startpath2 = "D:/image/xuexiao/15.png";
		String endpath2 = "D:/image/xuexiao/g.jpg";
		GetImage.getImage(startpath2, endpath2);

		String message = FingerPrint.compare(endpath1, endpath2);
		System.out.println(message);
	}
}
