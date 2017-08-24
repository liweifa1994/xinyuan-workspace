package cn.xinyuan.search.dao.mapper;

import cn.xinyuan.common.pojo.SearchItem;
import org.apache.ibatis.annotations.Param;

import java.util.*;

/**
 * User:josli li
 * Date:17/8/24
 * Time:上午9:27
 * Mail:josli@kargocard.com
 */
public interface SearchItemMapperCustomer {

    List<SearchItem> getItemList(@Param("itemId") Long itemId);
}
