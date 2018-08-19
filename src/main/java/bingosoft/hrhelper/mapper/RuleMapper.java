package bingosoft.hrhelper.mapper;

import java.sql.SQLException;
import java.util.List;

import bingosoft.hrhelper.form.RuleDetailForm;
import bingosoft.hrhelper.form.RuleListForm;
import bingosoft.hrhelper.model.Rule;
import org.apache.ibatis.annotations.Param;

public interface RuleMapper {
    int deleteByPrimaryKey(String id) throws SQLException;

    int insert(Rule record) throws SQLException;

    int insertSelective(Rule record);

    Rule selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(Rule record) throws SQLException;

    int updateByPrimaryKey(Rule record) throws SQLException;

	List<Rule> listAllRule();

    /**
     * 获取规则列表信息
     * @param operationId
     * @return 规则列表信息
     */
	List<RuleListForm> listRuleListForm(String operationId) throws SQLException;

    /**
     * 获取规则详情
     * @param ruleId
     * @return 规则详情
     */
	RuleDetailForm getRuleDetail(String ruleId) throws SQLException;

    /**
     * 根据业务ID删除规则
     * @param operationId
     * @return
     * @throws SQLException
     */
	int deleteByOperationId(String operationId) throws SQLException;


}