package com.stczwd.HelloMessage;

import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.xml.XmlBeanFactory;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;

@SuppressWarnings("deprecation")
public class Main {

	public static void main(String[] args) {

		//导入xml配置文件
		Resource resource = new FileSystemResource("XML/helloMessage.xml");
		//加载配置文件，启动IOC容器
		BeanFactory beanFactory = new XmlBeanFactory(resource);
		//从IOC容器中获取Person类
		Person person = (Person) beanFactory.getBean("person");
		//打印输出信息
		System.out.println("输出结果："+person.sayHello());
	}

}
