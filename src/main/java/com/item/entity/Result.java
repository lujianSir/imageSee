package com.item.entity;

public class Result<T> {

	private String msg;

	private int code;

	private T data;

	private Result(T data) {
		this.code = 200;
		this.msg = "成功";
		this.data = data;
	}

	private Result(int code, String msg) {
		this.code = code;
		this.msg = msg;
	}

	/**
	 * 成功时候的调用
	 * 
	 * @return
	 */
	public static <T> Result<T> success(T data) {
		return new Result<T>(data);
	}

	/**
	 * 成功，不需要传入参数
	 * 
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static <T> Result<T> success() {
		return (Result<T>) success("");
	}

	/**
	 * 失败时候的调用
	 * 
	 * @return
	 */
	public static <T> Result<T> error(int code, String msg) {
		return new Result<T>(code, msg);
	}

	/**
	 * 失败时候的调用,扩展消息参数
	 * 
	 * @param cm
	 * @param msg
	 * @return
	 */
	public T getData() {
		return data;
	}

	public String getMsg() {
		return msg;
	}

	public int getCode() {
		return code;
	}
}