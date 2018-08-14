package bingosoft.hrhelper.mapper;

import java.sql.SQLException;
import java.util.List;

import bingosoft.hrhelper.model.Rule;

public interface RuleMapper {
    int deleteByPrimaryKey(String id) throws SQLException;

    int insert(Rule record);

    int insertSelective(Rule record);

    Rule selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(Rule record);

    int updateByPrimaryKey(Rule record);

	List<Rule> listAllRule();
}