package site.ownw.multpledatasource.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface TestMapper {
    @Select("select count(*) from extensions limit 1")
    Integer count();
}
