package kr.re.keti.sc.dataservicebroker.entities.sqlprovider.hive;

import java.util.List;

import kr.re.keti.sc.dataservicebroker.common.vo.DbConditionVO;
import kr.re.keti.sc.dataservicebroker.common.vo.entities.DynamicEntityDaoVO;

import org.apache.ibatis.annotations.DeleteProvider;
import org.apache.ibatis.annotations.InsertProvider;
import org.apache.ibatis.annotations.SelectProvider; // Mapper 인터페이스로 만든다
import org.apache.ibatis.annotations.UpdateProvider;

import kr.re.keti.sc.dataservicebroker.common.vo.CommonEntityDaoVO;

public interface HiveEntitySqlProvider {

    @UpdateProvider(method = "executeDdl")
    void executeDdl(String ddl);

    @UpdateProvider(method = "refreshTable")
    void refreshTable(CommonEntityDaoVO entityDaoVO);

    @UpdateProvider(method = "refreshTableBulk")
    void refreshTableBulk(String tableName, List<DynamicEntityDaoVO> entityDaoVO);

    @InsertProvider(method = "bulkCreate")
    void bulkCreate(String tableName, List<DynamicEntityDaoVO> entityDaoVOList);

    @InsertProvider(method = "create")
    void create(CommonEntityDaoVO entityDaoVO);

    @UpdateProvider(method = "replaceAttr")
    int replaceAttr(CommonEntityDaoVO entityDaoVO);

    @UpdateProvider(method = "replaceAttrBulk")
    int replaceAttrBulk(List<DynamicEntityDaoVO> entityDaoVO);

    @UpdateProvider(method = "replaceAttrHBase")
    int replaceAttrHBase(CommonEntityDaoVO entityDaoVO);

    @UpdateProvider(method = "replaceAttrHBaseBulk")
    int replaceAttrHBaseBulk(List<DynamicEntityDaoVO> entityDaoVO);

    @UpdateProvider(method = "appendAttr")
    int appendAttr(CommonEntityDaoVO entityDaoVO);

    @UpdateProvider(method = "appendNoOverwriteAttr")
    int appendNoOverwriteAttr(CommonEntityDaoVO entityDaoVO);

    @UpdateProvider(method = "updateAttr")
    int updateAttr(CommonEntityDaoVO entityDaoVO);

    @UpdateProvider(method = "partialAttrUpdate")
    int partialAttrUpdate(CommonEntityDaoVO entityDaoVO);

    @DeleteProvider(method = "delete")
    int delete(CommonEntityDaoVO entityDaoVO);

    @DeleteProvider(method = "deleteHist")
    int deleteHist(CommonEntityDaoVO entityDaoVO);

    @DeleteProvider(method = "deleteFullHist")
    int deleteFullHist(CommonEntityDaoVO entityDaoVO);

    @DeleteProvider(method = "deleteBulk")
    int deleteBulk(String tableName, List<DynamicEntityDaoVO> entityDaoVO);

    @DeleteProvider(method = "deleteHistBulk")
    int deleteHistBulk(String tableName, List<DynamicEntityDaoVO> entityDaoVO);

    @DeleteProvider(method = "deleteFullHistBulk")
    int deleteFullHistBulk(String tableName, List<DynamicEntityDaoVO> entityDaoVO);

    @UpdateProvider(method = "deleteAttr")
    int deleteAttr(CommonEntityDaoVO entityDaoVO);

    @UpdateProvider(method = "createHist")
    int createHist(String tableName, List<DynamicEntityDaoVO> entityDaoVO);

    @UpdateProvider(method = "createFullHist")
    int createFullHist(String tableName, List<DynamicEntityDaoVO> entityDaoVO);

    @SelectProvider(method = "selectOne")
    DynamicEntityDaoVO selectOne(DbConditionVO dbConditionVO);

    @SelectProvider(method = "selectList")
    List<DynamicEntityDaoVO> selectList(DbConditionVO dbConditionVO);

    @SelectProvider(method = "selectHistList")
    List<DynamicEntityDaoVO> selectHistList(DbConditionVO dbConditionVO);

}