package tech.bmatch.photo.model;
/**
 * 存储图片的唯一服务器的POJO类
 *
 * @author juniors
 * @date 2018/11/4
 */
public class Sever {
    /**
     * 服务器的唯一标识
     */
    private int severId;
    /**
     * 模拟服务器的路径
     */
    private String severPath;

    public int getSeverId() {
        return severId;
    }

    public void setSeverId(int severId) {
        this.severId = severId;
    }

    public String getSeverPath() {
        return severPath;
    }

    public void setSeverPath(String severPath) {
        this.severPath = severPath;
    }

    public Sever(int severId, String severPath){
        this.severId = severId;
        this.severPath = severPath;
    }
}
