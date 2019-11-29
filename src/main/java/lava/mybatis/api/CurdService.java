package lava.mybatis.api;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import lava.mybatis.vo.PagingVO;







public interface CurdService {

	
	
	
	   CurdMapper getMapper(Class cls) throws Exception;
	
	 
	 /**新增*/
		int insert(Object... record);
		/**新增(去空)*/
		int insertSelective(Object... record);

		/**根据主键进行修改*/
		int updateByPrimaryKey(Object... record);
		
		/**根据具体条件进行修改*/
		int updateByExample(Object record, Object example);
		
		
		/**根据主键进行删除*/
	    int deleteByPrimaryKey(String id, Class cls);
		/**根据条件进行删除*/
		int deleteByExample(Object example);
		
		/**根据主键唯一查询*/
		<M> M selectByPrimaryKey(String id, Class<M> cls);
		/**条件查询*/
		<M> List<M> selectByExample(Object example);

		/**获取记录数*/
		int countByExample(Object example, Class cls);
		
		 <M> PagingVO<M> selectPagging(Object example, int pageNum, int pageSize);
	 
}
