package tech.bmatch.photo.service.Impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.core.type.TypeReference;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import tech.bmatch.photo.model.Photo;
import tech.bmatch.photo.model.Sever;
import tech.bmatch.photo.service.PhotoService;

import java.io.File;
import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public abstract class FilePhotoStoreServiceImpl implements PhotoService {
    /**
     * 创建日志Logger
     */
    public static final Logger logger = LoggerFactory.getLogger(FileInputStream.class);
    /**
     * 创建json对象
     * Map映射
     */
    private static ObjectMapper objectMapper = new ObjectMapper();
    /**
     * 模拟服务器数据库的存储数据的json文件
     */
    private static File jsonFile = new File("./photos.json");
    /**
     * 创建服务器列表
     */
    private static List<Sever> severList = new ArrayList<Sever>();
    /**
     * 将所有的图片数据存储在HashMap
     */
    private static Map<String,List<Photo>> photoMap;
    /**
     * 进行初始化操作
     * 读取json文件的数据到HashMap
     */
    static {
        photoMap = new HashMap();
        try {
            initMapFormFile();
        }catch (Exception e){
            e.printStackTrace();
        }
        if(severList==null){
            severList = new ArrayList<Sever>();
        }
        severList.add(new Sever(1,"./sever1"));
        severList.add(new Sever(2,"./sever2"));
        severList.add(new Sever(3,"./sever3"));
    }

    /**
     * 将文件中数据内容读取到HMap
     * static 静态方法
     */
    public static void initMapFormFile(){
        try {
            photoMap = objectMapper.readValue(jsonFile, new TypeReference<Map<String,List<Photo>>>(){});
        }catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 将map中数据储存到json文件
     */
    public static void mapToFile(){
        if(photoMap==null){
            return;
        }
        try{
            objectMapper.writeValue(jsonFile,photoMap);
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    /**
     * 允许外界访问其私有成员
     * @return 服务器列表
     */
    public static List<Sever> getSeverList() {
        return severList;
    }
    /**
     * 允许外界访问其私有成员
     * @return photoMap
     */
    public static Map<String, List<Photo>> getPhotoMap() {
        return photoMap;
    }
}
