package cn.xinyuan.content.service;

import cn.xinyuan.common.util.EasyUIDataGridResult;
import cn.xinyuan.common.util.XinYuanResult;
import cn.xinyuan.pojo.TbContent;
import java.util.List;

/**
 * User:josli li
 * Date:17/8/17
 * Time:上午10:03
 * Mail:josli@kargocard.com
 */
public interface ContentService {

    /**
     * 内容列表
     * @param contentCategoryId
     * @param page 显示第几页
     * @param rows 每一页显示几条数据
     * @return
     */
    EasyUIDataGridResult findContentList(Long contentCategoryId,int page,int rows);


    /**
     * 内容列表 不用分页
     * @param contentCategoryId
     * @return
     */
    List<TbContent> findContentList(Long contentCategoryId);

    List<TbContent> getList(Long contentCategoryId);

    /**
     * 更新或新增内容
     * @param content
     * @return
     */
    XinYuanResult insertOrUpdateContent(TbContent content);

    /**
     * 删除数据
     * @param contentId
     * @return
     */
    XinYuanResult deleteContent(Long contentId);
}
