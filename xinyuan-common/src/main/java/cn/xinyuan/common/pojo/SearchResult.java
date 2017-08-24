package cn.xinyuan.common.pojo;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

import java.io.Serializable;

/**
 * User:josli li
 * Date:17/8/24
 * Time:上午9:19
 * Mail:josli@kargocard.com
 */
@Getter
@Setter
public class SearchResult implements Serializable {
    //总记录
    private long recordTotal;
    //共有几页数据
    private int totalPages;
    //数据
    private List<SearchItem> itemList = new ArrayList<>();

}
