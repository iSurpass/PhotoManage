package tech.bmatch.photo.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

public class FileUtils {

    public static final Logger logger = LoggerFactory.getLogger(FileUtils.class);

    /**
     * 由文件数据转换为文件流
     *
     * @return byte[]
     */
    public static byte[] getData(String filePath){

        byte[] data = null;
        File file = new File(filePath);

        try {
            FileInputStream fileInputStream = new FileInputStream(file);
            data = new byte[fileInputStream.available()];
            fileInputStream.read(data);
            return data;
        } catch (java.io.IOException e) {
            logger.error("文件输出流初始化失败!");
            e.printStackTrace();
        }

        return null;
    }
}
