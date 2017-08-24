package cn.xinyuan.common.pojo;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import org.apache.solr.client.solrj.beans.Field;
/**
 * User:josli li
 * Date:17/8/24
 * Time:上午9:09
 * Mail:josli@kargocard.com
 */
@Getter
@Setter
public class SearchItem implements Serializable {
    @Field
    private String id;
    @Field
    private String item_title;
    @Field
    private String item_sell_point;
    @Field
    private long item_price;
    @Field
    private String item_image;
    @Field
    private String item_category_name;
}
