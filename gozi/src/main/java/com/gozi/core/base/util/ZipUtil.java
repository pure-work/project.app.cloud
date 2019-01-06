package com.gozi.core.base.util;

import java.io.*;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

/**
 * 压缩文件处理
 *
 */
public class ZipUtil {
	private static int BUFFER_SIZE = 1024;
	public final static String SP = File.separator;

	/**
	 * zip解压
	 * 
	 * @param srcFile
	 *            zip源文件
	 * @param destDirPath
	 *            解压后的目标文件夹
	 * @throws RuntimeException
	 *             解压失败会抛出运行时异常
	 */
	public static void unZip(File srcFile, String destDirPath) throws RuntimeException {
		long start = System.currentTimeMillis();
		// 判断源文件是否存在
		if (!srcFile.exists()) {
			throw new RuntimeException(srcFile.getPath() + "所指文件不存在");
		}
		// 开始解压
		ZipFile zipFile = null;
		try {
			Charset gbk = Charset.forName("GBK");
			zipFile = new ZipFile(srcFile, gbk);
			Enumeration<?> entries = zipFile.entries();
			while (entries.hasMoreElements()) {
				ZipEntry entry = (ZipEntry) entries.nextElement();
				System.out.println("解压" + entry.getName());
				// 如果是文件夹，就创建个文件夹
				if (entry.isDirectory()) {
					String dirPath = destDirPath + SP + entry.getName();
					File dir = new File(dirPath);
					dir.mkdirs();
				} else {
					// 如果是文件，就先创建一个文件，然后用io流把内容copy过去
					File targetFile = new File(destDirPath + SP + entry.getName());
					// 保证这个文件的父文件夹必须要存在
					if (!targetFile.getParentFile().exists()) {
						targetFile.getParentFile().mkdirs();
					}
					targetFile.createNewFile();
					// 将压缩文件内容写入到这个文件中
					InputStream is = zipFile.getInputStream(entry);
					FileOutputStream fos = new FileOutputStream(targetFile);
					int len;
					byte[] buf = new byte[BUFFER_SIZE];
					while ((len = is.read(buf)) != -1) {
						fos.write(buf, 0, len);
					}
					// 关流顺序，先打开的后关闭
					fos.close();
					is.close();
				}
			}
			long end = System.currentTimeMillis();
			System.out.println("解压完成，耗时：" + (end - start) + " ms");
		} catch (Exception e) {
			throw new RuntimeException("unzip error from ZipUtils", e);
		} finally {
			if (zipFile != null) {
				try {
					zipFile.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}

	/**
	 * 清空该目录下所有文件、文件夹
	 * 
	 * @param path
	 */
	public static void clearDirectory(String path) {
		if (StringUtil.isNotBlank(path)) {
			File file = new File(path);
			if (file.isDirectory()) {
				File[] files = file.listFiles();
				for (int i = 0; i < files.length; i++) {
					if (files[i].isDirectory()) {
						clearDirectory(files[i].getPath());
					} else {
						files[i].delete();
					}
				}
			}
			file.delete();
		}
	}

	public static void main(String[] args) {
		/*String fileUrl = "http://image.topyuezi.cn/kjfdjkldskldsfklfdskfdklkdsf.zip";// "http://image.topyuezi.cn/bigPhone.zip";
		String local = System.getProperty("user.dir");
		System.out.println("当前目录:" + local);
		String destDirPath = local + SP + "tempfile" + SP + "file";
		String localFilePath = local + SP + "tempfile" + SP + "zip";
		String zipFileName = "file.zip";
		String uzpDir = "bigPhone";
		try {
			//
			File dir1 = new File(destDirPath);
			if (!dir1.exists()) {
				dir1.mkdirs();
			}
			File dir2 = new File(localFilePath);
			if (!dir2.exists()) {
				dir2.mkdirs();
			}

			URL url = new URL(fileUrl);
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			// 设置超时间为3秒
			conn.setConnectTimeout(60 * 1000);
			// 防止屏蔽程序抓取而返回403错误
			conn.setRequestProperty("User-Agent", "Mozilla/4.0 (compatible; MSIE 5.0; Windows NT; DigExt)");
			conn.setDoOutput(true);// 使用 URL 连接进行输出
			conn.setDoInput(true);// 使用 URL 连接进行输入
			conn.setUseCaches(false);// 忽略缓存
			conn.setRequestMethod("GET");// 设置URL请求方法
			// 可设置请求头
			conn.setRequestProperty("Content-Type", "application/octet-stream");
			conn.setRequestProperty("Connection", "Keep-Alive");// 维持长连接
			conn.setRequestProperty("Charset", "UTF-8");

			byte[] file = input2byte(conn.getInputStream());
			String tempZipPath = localFilePath + SP + zipFileName;
			writeBytesToFile(file, tempZipPath);
			// System.out.println(file);

			File targetFile = new File(tempZipPath);

			ZipUtil.unZip(targetFile, destDirPath);

			targetFile.delete();

			String newDirPath = destDirPath + SP + uzpDir;
			List<String> list = readfile(newDirPath);
			if (list.size() > 0) {
				for (String key : list) {
					System.out.println(key);
					String image = key.substring(key.lastIndexOf(ZipUtil.SP) + 1);
					// System.out.println(image);
					image = image.substring(0, image.indexOf("."));
					// System.out.println(image);
					String ph = Qiniu.uploadFile(null, new File(key));
					// System.out.println(ph);
				}
			}

			// deletefile(newDirPath);
		} catch (Exception e) {
			e.printStackTrace();
		}*/
	}

	public static final byte[] input2byte(InputStream inStream) throws IOException {
		ByteArrayOutputStream swapStream = new ByteArrayOutputStream();
		byte[] buff = new byte[100];
		int rc = 0;
		while ((rc = inStream.read(buff, 0, 100)) > 0) {
			swapStream.write(buff, 0, rc);
		}
		byte[] in2b = swapStream.toByteArray();
		return in2b;
	}

	public static File writeBytesToFile(byte[] b, String outputFile) {
		File file = null;
		FileOutputStream os = null;
		try {
			file = new File(outputFile);
			os = new FileOutputStream(file);
			os.write(b);
		} catch (Exception var13) {
			var13.printStackTrace();
		} finally {
			try {
				if (os != null) {
					os.close();
				}
			} catch (IOException var12) {
				var12.printStackTrace();
			}
		}
		return file;
	}

	/**
	 * 获得目录下的所有文件
	 * 
	 * @param filepath
	 * @return
	 * @throws FileNotFoundException
	 * @throws IOException
	 */
	public static List<String> readfile(String filepath) throws FileNotFoundException, IOException {
		List<String> result = new ArrayList<String>();
		try {
			File file = new File(filepath);
			if (!file.isDirectory()) {
				result.add(file.getPath());
			} else if (file.isDirectory()) {
				String[] filelist = file.list();
				for (int i = 0; i < filelist.length; i++) {
					File readfile = new File(filepath + SP + filelist[i]);
					if (!readfile.isDirectory()) {
						// System.out.println("path=" + readfile.getPath());
						// System.out.println("absolutepath=" + readfile.getAbsolutePath());
						// System.out.println("name=" + readfile.getName());
						result.add(readfile.getPath());
					} else if (readfile.isDirectory()) {
						result.addAll(readfile(filepath + SP + filelist[i]));
					}
				}
			}
		} catch (FileNotFoundException e) {
			System.out.println("readfile()   Exception:" + e.getMessage());
		}
		return result;
	}

	/**
	 * 删除文件夹
	 * 
	 * @param delpath
	 * @return
	 * @throws FileNotFoundException
	 * @throws IOException
	 */
	public static boolean deletefile(String delpath) throws FileNotFoundException, IOException {
		try {
			File file = new File(delpath);
			if (!file.isDirectory()) {
				file.delete();
			} else if (file.isDirectory()) {
				String[] filelist = file.list();
				for (int i = 0; i < filelist.length; i++) {
					File delfile = new File(delpath + SP + filelist[i]);
					if (!delfile.isDirectory()) {
						delfile.delete();
					} else if (delfile.isDirectory()) {
						deletefile(delpath + SP + filelist[i]);
					}
				}
				file.delete();
			}
		} catch (FileNotFoundException e) {
			System.out.println("deletefile()   Exception:" + e.getMessage());
		}
		return true;
	}

}
