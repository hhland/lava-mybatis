package lava.mybatis.base;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

import lava.mybatis.api.CurdMapper;
import lava.mybatis.api.CurdService;

public abstract class CurdServiceBase implements CurdService{

	
	
	
	
    private static final Map<Class, CurdMapper> CACHE_MAPPER = new HashMap<>();
	
	public synchronized CurdMapper getMapper(Class cls) throws Exception {
        CurdMapper re = null;
        if (CACHE_MAPPER.containsKey(cls)) {
            re = CACHE_MAPPER.get(cls);
        } else {
            for (Field field : this.getClass().getDeclaredFields()) {
                Class fieldType = field.getType();
                if (fieldType.getGenericInterfaces().length == 0)
                    continue;
                String typeName = fieldType.getGenericInterfaces()[0].getTypeName();
                String[] typeNames = typeName.substring(typeName.indexOf("<") + 1, typeName.length() - 1).split(",");
                for (String _typeName : typeNames) {
                    if (_typeName.trim().equals(cls.getName())) {
                        re = (CurdMapper) field.get(this);
                        break;
                    }
                }
                if (re != null) {
                    CACHE_MAPPER.put(cls, re);
                    break;
                }
            }
        }
        return re;
    }
	
}
