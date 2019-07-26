package lava.mybatis.api;

import java.util.List;



public interface CurdMapper<M, E> {

	/**新增*/
	int insert(M record);
	/**新增(去空)*/
	int insertSelective(M record);

	/**根据主键进行修改*/
	int updateByPrimaryKey(M record);
	/**根据主键进行修改(去空)*/
	int updateByPrimaryKeySelective(M record);
	/**根据具体条件进行修改*/
	

	/**根据主键进行删除*/
	int deleteByExample(E example);
	/**根据条件进行删除*/
	int deleteByPrimaryKey(String id);
	
	/**根据主键唯一查询*/
	M selectByPrimaryKey(String id);
	/**条件查询*/
	List<M> selectByExample(E example);
	
	/**获取记录数*/
	int countByExample(E example);
	
}
