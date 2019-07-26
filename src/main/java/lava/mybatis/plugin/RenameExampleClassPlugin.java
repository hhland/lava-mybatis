/*
 *  Copyright 2008 The Apache Software Foundation
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */

package lava.mybatis.plugin;

import static org.mybatis.generator.internal.util.StringUtility.stringHasValue;
import static org.mybatis.generator.internal.util.messages.Messages.getString;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.mybatis.generator.api.IntrospectedTable;
import org.mybatis.generator.api.PluginAdapter;
import org.mybatis.generator.api.dom.java.Field;
import org.mybatis.generator.api.dom.java.FullyQualifiedJavaType;
import org.mybatis.generator.api.dom.java.InnerClass;
import org.mybatis.generator.api.dom.java.TopLevelClass;

public class RenameExampleClassPlugin extends PluginAdapter {

	private String searchString;
	private String replaceString;
	private Pattern pattern;
	private String packageName;

	public RenameExampleClassPlugin() {
	}

	public boolean validate(List<String> warnings) {
		packageName = properties.getProperty("packageName");
		searchString = properties.getProperty("searchString");
		replaceString = properties.getProperty("replaceString");
		boolean valid = stringHasValue(searchString) && stringHasValue(replaceString);
		if (valid) {
			pattern = Pattern.compile(searchString);
		} else {
			if (!stringHasValue(searchString)) {
				warnings.add(getString("ValidationError.18", "RenameExampleClassPlugin", "searchString"));
			}
			if (!stringHasValue(replaceString)) {
				warnings.add(getString("ValidationError.18", "RenameExampleClassPlugin", "replaceString"));
			}
		}
		return valid;
	}

	@Override
	public void initialized(IntrospectedTable introspectedTable) {
		String oldType = introspectedTable.getExampleType();
		Matcher matcher = pattern.matcher(oldType);
		oldType = matcher.replaceAll(replaceString);
		if (stringHasValue(packageName)) {
			oldType = packageName + oldType.substring(oldType.lastIndexOf("."));
		}

		introspectedTable.setExampleType(oldType);
	}

	@Override
	public boolean modelExampleClassGenerated(TopLevelClass topLevelClass, IntrospectedTable introspectedTable) {
		//xxxCriteria序列号
		topLevelClass.addSuperInterface(new FullyQualifiedJavaType("java.io.Serializable"));
		topLevelClass.addImportedType("java.io.Serializable");
		Field field = new Field("serialVersionUID", new FullyQualifiedJavaType("long"));
		field.setStatic(true);
		field.setFinal(true);
		field.setInitializationString("1L");
		topLevelClass.addField(field);
		
		//xxxCriteria内部类序列号
		List<InnerClass> list = topLevelClass.getInnerClasses();
		for (InnerClass c : list) {
			c.addSuperInterface(new FullyQualifiedJavaType("java.io.Serializable"));
			c.addField(field);
		}
		return super.modelExampleClassGenerated(topLevelClass, introspectedTable);
	}

}
