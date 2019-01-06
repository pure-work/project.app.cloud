package com.gozi.core.base.util;

import com.google.gson.Gson;
import com.qiniu.common.QiniuException;
import com.qiniu.common.Zone;
import com.qiniu.http.Response;
import com.qiniu.storage.BucketManager;
import com.qiniu.storage.Configuration;
import com.qiniu.storage.UploadManager;
import com.qiniu.storage.model.DefaultPutRet;
import com.qiniu.storage.model.FileInfo;
import com.qiniu.util.Auth;

import java.io.File;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

/**
 * Created by piaoyizhe on 2018/3/25/0025.
 */

public class Qiniu {
    //...生成上传凭证，然后准备上传
    private static String accessKey = "_pY63L3rnVMQYW6sypSQpctNogNGik79rFWvwF32";
    private static String secretKey = "VFpXcVikpCKVw_qFznnCy7xQuygYzhxhFedG26n9";
    private static String bucket = "prettymom";
    private static String domainOfBucket = "http://image.topyuezi.cn";//"http://p655k0tfe.bkt.clouddn.com";
    private static Zone mZone = Zone.zone2();

    public static String uploadFile(String fileName,File file) {
        String hash;
        //默认不指定key的情况下，以文件内容的hash值作为文件名
        String upToken = getUpToken();
        DefaultPutRet ret = uploadAndGetResponse(file, fileName, upToken);
        hash=ret.hash;
        return hash;
    }
    public static DefaultPutRet uploadAndGetResponse(File file, String key, String upToken){
        Configuration cfg = new Configuration(mZone);
        UploadManager uploadManager = new UploadManager(cfg);
        try {
            Response response = uploadManager.put(file, key, upToken);
            //解析上传成功的结果
            DefaultPutRet ret = new Gson().fromJson(response.bodyString(), DefaultPutRet.class);
            return ret;
        } catch (QiniuException ex) {
            Response r = ex.response;
            System.err.println(r.toString());
            try {
                System.err.println(r.bodyString());
            } catch (QiniuException ex2) {
                //ignore
            }
        }
        return null;
    }
    /**
     * 下载公开空间文件
     * @param fileName
     * @return
     */
    public static String downloadPath(String fileName) {
        String encodedFileName = null;
        try {
            encodedFileName = URLEncoder.encode(fileName, "utf-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        String finalUrl = String.format("%s/%s", domainOfBucket, encodedFileName);
        System.out.println(finalUrl);
        return finalUrl;
    }
    
    public static String getDomainOfBucket() {
    	return domainOfBucket;
    }

    public static String getUpToken() {
        Auth auth = Auth.create(accessKey, secretKey);
        String upToken = auth.uploadToken(bucket);
        return upToken;
    }

    public static void main(String[] args) {
        //uploadFile(null,"C:\\Users\\piaoy\\Desktop\\mm.jpg");
    }

    public static FileInfo[] getFileList(){
        Configuration cfg = new Configuration(mZone);
        Auth auth = Auth.create(accessKey, secretKey);
        BucketManager bucketManager = new BucketManager(auth, cfg);
        //文件名前缀
        String prefix = "";
        //每次迭代的长度限制，最大1000，推荐值 1000
        int limit = 1000;
        //指定目录分隔符，列出所有公共前缀（模拟列出目录效果）。缺省值为空字符串
        String delimiter = "";
        //列举空间文件列表
        BucketManager.FileListIterator fileListIterator = bucketManager.createFileListIterator(bucket, prefix, limit, delimiter);
        FileInfo[] items=null;
        while (fileListIterator.hasNext()) {
            //处理获取的file list结果
            items = fileListIterator.next();
            for (FileInfo item : items) {
                System.out.println(item.key);
                System.out.println(item.hash);
                System.out.println(item.fsize);
                System.out.println(item.mimeType);
                System.out.println(item.putTime);
                System.out.println(item.endUser);
            }
        }
        return items;
    }
}