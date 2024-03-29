package cn.xinyuan.common.util;

import java.io.Serializable;

/**
 * User:josli li
 * Date:17/8/15
 * Time:下午3:16
 * Mail:josli@kargocard.com
 */
public class EasyUITreeNode implements Serializable{
    private long id;
    private String text;
    private String state;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    @Override
    public String toString() {
        return "EasyUITreeNode{" +
                "id=" + id +
                ", text='" + text + '\'' +
                ", state='" + state + '\'' +
                '}';
    }
}
