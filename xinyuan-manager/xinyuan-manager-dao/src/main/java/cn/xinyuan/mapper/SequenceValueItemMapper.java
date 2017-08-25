package cn.xinyuan.mapper;

import cn.xinyuan.pojo.SequenceValueItem;
import cn.xinyuan.pojo.SequenceValueItemExample;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface SequenceValueItemMapper {
    int countByExample(SequenceValueItemExample example);

    int deleteByExample(SequenceValueItemExample example);

    int deleteByPrimaryKey(String seqName);

    int insert(SequenceValueItem record);

    int insertSelective(SequenceValueItem record);

    List<SequenceValueItem> selectByExample(SequenceValueItemExample example);

    SequenceValueItem selectByPrimaryKey(String seqName);

    int updateByExampleSelective(@Param("record") SequenceValueItem record, @Param("example") SequenceValueItemExample example);

    int updateByExample(@Param("record") SequenceValueItem record, @Param("example") SequenceValueItemExample example);

    int updateByPrimaryKeySelective(SequenceValueItem record);

    int updateByPrimaryKey(SequenceValueItem record);
}