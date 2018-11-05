package tech.bmatch.photo.service;

import tech.bmatch.photo.model.Photo;

import java.util.List;

/**
 * 图片处理服务的接口
 *
 * @author juniors
 * @date 2018/11/4
 */
public interface PhotoService {
    /**
     * 检查用户存储空间
     * 上传图片并记录其数据
     * @param data 已转化过的Byte类型的图片数据
     * @param name 图片名称
     * @param userId 由其可以查询到指定用户的存储空间
     *               用于Photo实例
     */
    void upload(byte[] data,String name,String userId);
    /**
     * 查询用户图集功能
     * @param page 当前页码
     * @param userId 用户ID
     * @return 查询结果List
     */
    List<Photo> search(int page,String userId);
    /**
     * 用户指定位置打水印
     *
     * @param photoId  图片ID
     * @param position 指定位置
     * @param userId 用户ID
     */
    void doAddWatermark(String photoId, int position, String userId);

    /**
     * 去除水印
     * @param photoId 图片ID
     * @param userId 用户ID
     */
    void doClearWatermark(String photoId, String userId);
}
