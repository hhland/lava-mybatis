package lava.mybatis.plugin;

import java.util.List;

import org.mybatis.generator.api.IntrospectedColumn;
import org.mybatis.generator.api.IntrospectedTable;
import org.mybatis.generator.api.Plugin;
import org.mybatis.generator.api.PluginAdapter;
import org.mybatis.generator.api.dom.java.Field;
import org.mybatis.generator.api.dom.java.FullyQualifiedJavaType;
import org.mybatis.generator.api.dom.java.Interface;
import org.mybatis.generator.api.dom.java.Method;
import org.mybatis.generator.api.dom.java.TopLevelClass;

import lava.mybatis.api.CurdMapper;

/**
 * Mybatis Gen 插件-实体bean增加表名，字段名的自定义注解
 */
public class MgBeanAnnoPlugin extends PluginAdapter {

	/**
	 * This plugin is always valid - no properties are required
	 */
	public boolean validate(List<String> warnings) {
		return true;
	}

	/**
	 * 添加自定义注解引用 给对象增加自定义注解，以对象对应表名为注解值
	 */
	@Override
	public boolean modelBaseRecordClassGenerated(TopLevelClass topLevelClass, IntrospectedTable introspectedTable) {
		//entity序列号
		topLevelClass.addSuperInterface(new FullyQualifiedJavaType("java.io.Serializable"));
		topLevelClass.addImportedType("java.io.Serializable");
		Field field = new Field("serialVersionUID", new FullyQualifiedJavaType("long"));
		field.setStatic(true);
		field.setFinal(true);
		field.setInitializationString("1L");
		topLevelClass.addField(field);
		return true;
	}

	@Override
	public boolean clientGenerated(Interface interfaze, TopLevelClass topLevelClass, IntrospectedTable introspectedTable) {
		//dao注入@Repository
		interfaze.addImportedType(new FullyQualifiedJavaType("org.springframework.stereotype.Repository"));
		interfaze.addImportedType(new FullyQualifiedJavaType(CurdMapper.class.getName()));
		interfaze.addAnnotation("@Repository");
		List<Method> list = interfaze.getMethods();
		String m = "";
		for (Method method : list) {
			   ;
			if ("insertSelective".equals(method.getName())) {
				FullyQualifiedJavaType t = (FullyQualifiedJavaType) method.getParameters().get(0).getType();
				
				m = t.getShortName();
				break;
			}
		}
		//extends CurdMapper
		interfaze.addSuperInterface(new FullyQualifiedJavaType(CurdMapper.class.getSimpleName() + "<" + m + "," + m + "Criteria>"));
		interfaze.getMethods().clear();
		return true;
	}

	/**
	 * 创建实体对象字段时，增加自定义注解，以对应数据库列名为注解值
	 */
	@Override
	public boolean modelFieldGenerated(Field field, TopLevelClass topLevelClass, IntrospectedColumn introspectedColumn, IntrospectedTable introspectedTable, Plugin.ModelClassType modelClassType) {
		// field.addAnnotation("@ColumnName(\"" +
		// introspectedColumn.getActualColumnName() + "\")");
		//    ;
		// field.addJavaDocLine("/** "+ introspectedColumn.getRemarks()+" */");
		return true;
	}
}
