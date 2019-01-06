package com.gozi.core.base.util;

public class CollectionUtil {
	/**
	 * 数组是否为空
	 * @param array
	 *            数组
	 * @return 是否为空
	 */
	public static <T> boolean isEmpty(T[] array) {
		return array == null || array.length == 0;
	}
	/**
	 * 数组是否为非空
	 * @param array
	 *            数组
	 * @return 是否为非空
	 */
	public static <T> boolean isNotEmpty(T[] array) {
		return false == isEmpty(array);
	}
	/**
	 * 以 conjunction 为分隔符将集合转换为字符串
	 * @param <T>
	 *            被处理的集合
	 * @param collection
	 *            集合
	 * @param conjunction
	 *            分隔符
	 * @return 连接后的字符串
	 */
	public static <T> String join(Iterable<T> collection, String conjunction) {
		StringBuilder sb = new StringBuilder();
		boolean isFirst = true;
		for (T item : collection) {
			if (isFirst) {
				isFirst = false;
			} else {
				sb.append(conjunction);
			}
			sb.append(item);
		}
		return sb.toString();
	}
	/**
	 * 以 conjunction 为分隔符将数组转换为字符串
	 * @param <T>
	 *            被处理的集合
	 * @param array
	 *            数组
	 * @param conjunction
	 *            分隔符
	 * @return 连接后的字符串
	 */
	public static <T> String join(T[] array, String conjunction) {
		StringBuilder sb = new StringBuilder();
		boolean isFirst = true;
		for (T item : array) {
			if (isFirst) {
				isFirst = false;
			} else {
				sb.append(conjunction);
			}
			sb.append(item);
		}
		return sb.toString();
	}
}
