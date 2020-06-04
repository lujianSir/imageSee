package com.item.image;

/**
 * 偏移数据
 * 
 * @author Administrator
 *
 */
public class Entity {

	private int left;// 左上角的点距离左边的数据

	private int top;// 左上角的点距离顶点的的数据

	private Integer width;// 图片截取的宽度

	private Integer height;// 图片截取的高度

	public int getLeft() {
		return left;
	}

	public void setLeft(int left) {
		this.left = left;
	}

	public int getTop() {
		return top;
	}

	public void setTop(int top) {
		this.top = top;
	}

	public Integer getWidth() {
		return width;
	}

	public void setWidth(Integer width) {
		this.width = width;
	}

	public Integer getHeight() {
		return height;
	}

	public void setHeight(Integer height) {
		this.height = height;
	}

}
