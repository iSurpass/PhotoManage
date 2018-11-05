package tech.bmatch.photo.model;

import java.util.Date;

/**
 * 图片的POJO类
 *
 * @author juniors
 * @date 2018/11/4
 */
public class Photo {
    /**
     * 图片的唯一标识
     */
    private String id;
    /**
     * 图片名称
     */
    private String name;
    /**
     * 图片大小(Byte)
     */
    private int size;
    /**
     * 图片高度
     */
    private int height;
    /**
     * 图片宽度
     */
    private int width;
    /**
     * 图片类型
     */
    private String type;
    /**
     * 图片存储路径
     */
    private String savePath;
    /**
     * 图片上传时间
     */
    private Date uploadDate;
    /**
     * 图片归属用户的id
     */
    private String userId;
    /**
     * 图片是否有水印
     */
    private boolean Watermark;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public String getSavePath() {
        return savePath;
    }

    public void setSavePath(String savePath) {
        this.savePath = savePath;
    }

    public Date getUploadDate() {
        return uploadDate;
    }

    public void setUploadDate(Date uploadDate) {
        this.uploadDate = uploadDate;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public boolean getWatermark() {
        return Watermark;
    }

    public void setWatermark(boolean watermark) {
        Watermark = watermark;
    }
    public String toString(){
        return "Photo:{" +
                "id="+ id +
                ", name='" + name + '\'' +
                ", type='" + type + '\'' +
                ", size=" + size +
                ", height=" + height +
                ", width=" + width +
                ", storePath='" + savePath + '\'' +
                ", uploadDate=" + uploadDate +
                ", userId='" + userId + '\'' +
                '}';
    }
}