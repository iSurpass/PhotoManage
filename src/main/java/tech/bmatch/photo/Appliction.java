package tech.bmatch.photo;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import tech.bmatch.photo.model.Photo;
import tech.bmatch.photo.service.Impl.PhotoServiceImpl;
import tech.bmatch.photo.service.PhotoService;
import tech.bmatch.photo.util.FileUtils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;

public class Appliction {

    /**
     * 日志Logger
     */
    public static final Logger logger = LoggerFactory.getLogger(Appliction.class);

    /**
     * 创建图片处理服务
     */
    public static final PhotoService photoService = new PhotoServiceImpl();

    /**
     * 用户ID
     */
    public static String userId = null;

    public static void main(String[] args) {
        /**
         * 服务开启
         * 开启字符串缓冲输入流
         */
        logger.info("Start Application:");
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));

        /**
         * 进行初始化用户ID操作
         */
        System.out.println("请输入用户ID:");
        try {
            userId = bufferedReader.readLine();
        } catch (IOException e) {
            logger.error("用户初始化失败");
            e.printStackTrace();
        }

        /**
         * 进行解析指令并分配方法
         */
        while (true) {
            String commend = null;
            try {
                commend = bufferedReader.readLine();
            } catch (IOException e) {
                logger.error("指令输入失败!");
                e.printStackTrace();
            }

            if (commend == "upload ") {
                System.out.println("执行命令:" + commend);
                String filePath = commend.replaceAll("upload ", "");
                uploadPhoto(filePath);
            }
            if (commend == "list ") {
                System.out.println("执行命令:" + commend);
                int pageNum = Integer.parseInt(commend.replaceAll("list ", ""));
                query(pageNum);
            }
            if (commend == "waterwork ") {
                System.out.println("执行命令:" + commend);
                commend = commend.replaceAll("waterwork ", "");
                String[] splited = commend.split("\\+s");
                String photoId = splited[0];
                int position = Integer.parseInt(splited[1]);
                addWatermark(photoId, position);
            }
            if (commend == "clear ") {
                System.out.println("执行命令:" + commend);
                String photoId = commend.replace("clear ", "");
                clearWatermark(photoId);
            }
            if (commend == "exit ") {
                System.out.println("执行命令:" + commend);
                commend.replace("exit ", "");
                break;
            }
        }

    }

        public static void uploadPhoto(String filePath) {
            byte[] data = FileUtils.getData(filePath);
            //文件名称 带后缀
            String fileName = filePath.substring(filePath.lastIndexOf("/") + 1);
            photoService.upload(data, fileName, userId);
        }

        /**
         * 查询图片
         *
         * @param pageNum 要获取的页码
         */
        public static void query(int pageNum) {
            List<Photo> photoList = photoService.search(pageNum, userId);
            if (photoList != null) {
                System.out.println("第" + pageNum + "页：");
                for (Photo photo : photoList) {
                    System.out.println(photo.getId() + " " + photo.getName() + " " + photo.getSavePath());
                }
            }
        }

        /**
         * 添加水印
         * @param photoId 图片ID
         * @param position
         */
        public static void addWatermark(String photoId, int position) {
            photoService.doAddWatermark(photoId, position, userId);
        }

        /**
         * 清除水印
         * @param photoId 图片ID
         */
        public static void clearWatermark(String photoId) {
            photoService.doClearWatermark(photoId, userId);
        }

    }

