package tech.bmatch.photo.service.Impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import tech.bmatch.photo.model.Photo;
import tech.bmatch.photo.util.IdUtils;
import tech.bmatch.photo.util.ImageUtils;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.*;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;

import static tech.bmatch.photo.util.IdUtils.getUUID;

public class PhotoServiceImpl extends FilePhotoStoreServiceImpl {
    /**
     * 日志Logger
     */
    public static final Logger logger = LoggerFactory.getLogger(PhotoServiceImpl.class);
    /**
     * 每个用户最大可存储空间
     */
    public static int userMAXSIZE = 1024000;
    /**
     * 每页的大小
     */
    public static int pageSize = 10;
    /**
     * 水印图片路径
     */
    public static final String logoPath = "./images/mark.png";

    /**
     * @param data   已转化过的Byte类型的图片数据
     * @param name   图片名称
     * @param userId 由其可以查询到指定用户的存储空间
     */
    @Override
    public void upload(byte[] data, String name, String userId) {
        Set<Map.Entry<String, List<Photo>>> entrySet = getPhotoMap().entrySet();
        Iterator<Map.Entry<String, List<Photo>>> iterator = entrySet.iterator();
        long sizeSum = 0;
        if (!getPhotoMap().containsKey(userId)) {
            getPhotoMap().put(userId, new ArrayList<Photo>());
        }
        while (iterator.hasNext()) {
            Map.Entry<String, List<Photo>> entry = iterator.next();
            if (entry.getKey().equals(userId)) {
                List<Photo> photoList = getPhotoMap().get(userId);
                for (Photo photo : photoList) {
                    sizeSum += photo.getSize();
                }
            }
            if ((data.length + sizeSum) > userMAXSIZE) {
                System.out.println("温馨提示:你的百度云空间已用尽!");
                return;
            }
            Photo photo = savePhoto(data, name, userId);
            mapToFile();
        }
    }

    /**
     * 由时间算法生成到
     * 服务器随机存储路径并
     * 利用字节流进行存储
     * 获得图片信息
     * 实例化photo对象
     *
     * @param data
     * @param name
     * @param userId
     * @return
     */
    public Photo savePhoto(byte[] data,String name,String userId){

        //通过上传时间来创建目录
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy/mm/dd/hh/mm/ss");
        String strTime = dateFormat.format(System.currentTimeMillis());

        //处理存储路径
        int pathIndex = getSeverIndex(getSeverList().size());
        String severPath = getSeverList().get(pathIndex).getSeverPath();
        String storePath = severPath + "/" + strTime + "/." + name;

        //创建层次性目录
        File storeFile = new File(storePath);
        storeFile.getParentFile().mkdirs();

        //通过字节流进行存储
        FileOutputStream fileOutputStream = null;
        try {
            fileOutputStream = new FileOutputStream(storeFile);
            fileOutputStream.write(data);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            logger.error("写入文件错误:"+storePath);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try{
                fileOutputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        //为实例化对象的数据进行准备
        BufferedImage imageSource = null;
        try {
            imageSource = ImageIO.read(new FileInputStream(storeFile));
        } catch (IOException e) {
            e.printStackTrace();
        }

        //准备数据
        Photo photo = new Photo();
        int height = imageSource.getHeight();
        int width = imageSource.getWidth();
        int size = data.length;
        String type = storePath.substring(name.lastIndexOf(".")+1);
        Date date = new Date();
        String id = IdUtils.getUUID();

        //进行实例化对象
        photo.setId(getUUID());
        photo.setName(name);
        photo.setHeight(height);
        photo.setSize(size);
        photo.setUserId(userId);
        photo.setType(type);
        photo.setUploadDate(date);
        photo.setWatermark(false);
        photo.setId(id);

        return photo;
    }

    /**
     * 根据服务器数量，获得随机的服务器索引
     *
     * @param serverCount
     * @return 服务器索引
     */
    public static int getSeverIndex(int serverCount) {
        Random rand = new Random();
        int randomNumber = rand.nextInt();
        if (randomNumber < 0) {
            randomNumber = -randomNumber;
        }
        //通过求余数，分散文件到服务器，达到平衡
        int serverIndex = randomNumber % serverCount;

        return serverIndex;
    }


    /**
     * 查询对应图片
     *
     * @param page 当前页码
     * @param userId 用户ID
     * @return
     */

    @Override
    public List<Photo> search(int page, String userId) {
        Set<Map.Entry<String, List<Photo>>> entrySet = getPhotoMap().entrySet();
        Iterator<Map.Entry<String, List<Photo>>> iterator = entrySet.iterator();
        Map.Entry<String, List<Photo>> entry;
        List<Photo> userPhotoList = null;
        int photoSum = 0;
        while (iterator.hasNext()) {
            entry = iterator.next();
            if (entry.getKey().equals(userId)) {
                userPhotoList = entry.getValue();
                photoSum = userPhotoList.size();
                break;
            }
        }
        if (page > 1 && (page - 1) * pageSize > photoSum) {
            System.out.println("已经超出范围");
            return null;
        }
        return null;
    }

    /**
     *
     * @param photoId  图片ID
     * @param position 指定位置
     * @param userId 用户ID
     */
    @Override
    public void doAddWatermark(String photoId, int position, String userId){
        if(!getPhotoMap().containsKey(userId)){
            System.out.println("没有该用户,请重新输入！");
            return;
        }

        List<Photo> photoList = getPhotoMap().get(userId);
        for(Photo photo:photoList){
            if(photo.getUserId().equals(userId)){
                if (photo.getWatermark()==true){
                    System.out.println("本照片已添加水印!");
                }
                String imagePath = photo.getSavePath();
                photo.setSavePath(ImageUtils.mergeWaterMark(imagePath,logoPath,position));
                photo.setWatermark(true);
                System.out.println("成功打上水印");
                //保存到文件中
                mapToFile();
            }
        }


    }

    /**
     *
     * @param photoId 图片ID
     * @param userId 用户ID
     */
    @Override
    public void doClearWatermark(String photoId, String userId) {
        if (!getPhotoMap().containsKey(userId)) {
            System.out.println("没有该用户的图片");
            return;
        }
        List<Photo> userPhotoList = getPhotoMap().get(userId);
        for (Photo photo : userPhotoList) {
            if (photo.getId().equals(photoId)) {
                if(photo.getWatermark()==false){
                    System.out.println("该图片还没有打上水印");
                    return;
                }
                String imagePath = photo.getSavePath();
                photo.setSavePath(ImageUtils.clearWaterMark(imagePath));
                photo.setWatermark(false);
                System.out.println("成功去除水印");
                //保存到文件中
                mapToFile();
            }
        }
    }

}