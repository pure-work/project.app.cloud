package com.gozi.core.base.util;

import com.gozi.core.base.enums.BusinessExceptionCode;
import com.gozi.core.base.exception.BusinessException;
import org.apache.log4j.Logger;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

public class FileUtil {

	private static Logger log = Logger.getLogger(FileUtil.class);
	
	/**
	 * NIO way
	 */
	public static byte[] toByteArray(String filename) {

		File f = new File(filename);
		if (!f.exists()) {
			log.error("文件未找到！" + filename);
			throw new BusinessException(BusinessExceptionCode.FILE_NOT_FOUND);
		}
		FileChannel channel = null;
		FileInputStream fs = null;
		try {
			fs = new FileInputStream(f);
			channel = fs.getChannel();
			ByteBuffer byteBuffer = ByteBuffer.allocate((int) channel.size());
			while ((channel.read(byteBuffer)) > 0) {
				// do nothing
				// System.out.println("reading");
			}
			return byteBuffer.array();
		} catch (IOException e) {
			throw new BusinessException(BusinessExceptionCode.FILE_READING_ERROR);
		} finally {
			try {
				channel.close();
			} catch (IOException e) {
				throw new BusinessException(BusinessExceptionCode.FILE_READING_ERROR);
			}
			try {
				fs.close();
			} catch (IOException e) {
				throw new BusinessException(BusinessExceptionCode.FILE_READING_ERROR);
			}
		}
	}
	
	/**
	 * 将multfile 转换成file
	 * @param multfile
	 * @return
	 * @throws IOException
	 */
	public static File multipartToFile(MultipartFile multfile) throws IOException {
		String tmpdir=System.getProperty("java.io.tmpdir");
		String fileName =multfile.getOriginalFilename();
		File targetFile = new File(tmpdir + fileName);
		if (!targetFile.exists()) {
			targetFile.getParentFile().mkdir();
			targetFile.createNewFile();
		}
		multfile.transferTo(targetFile);
		return targetFile;
	}
	
	public static void copyFile(File fromFile,File toFile) throws IOException{
        FileInputStream ins = new FileInputStream(fromFile);
        File filedir =new File(toFile.getParentFile().getAbsolutePath());    
		//如果文件夹不存在则创建    
		if  (!filedir.exists()  && !filedir.isDirectory())      
		{       
		    filedir.mkdirs();
		}
        FileOutputStream out = new FileOutputStream(toFile);
        byte[] b = new byte[1024];
        int n=0;
        while((n=ins.read(b))!=-1){
            out.write(b, 0, n);
        }
        ins.close();
        out.close();
    }
	
}
