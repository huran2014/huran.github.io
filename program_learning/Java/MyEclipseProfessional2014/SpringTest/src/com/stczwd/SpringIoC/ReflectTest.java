package com.stczwd.SpringIoC;

import java.lang.reflect.Constructor;
import java.lang.reflect.Method;


public class ReflectTest {
	public static Car initByDefaultConst() throws Throwable {
		//1.通过类装载器获取Car类对象
		ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
		Class clazz = classLoader.loadClass("com.stczwd.SpringIoC.Car");
		
		//2.获取类的默认构造器对象并实例化Car，效果类似于new Car()
		Constructor constructor = clazz.getDeclaredConstructor((Class[])null);
		Car car = (Car) constructor.newInstance();
		
		//3.通过反射方法设置属性
		Method setBrand = clazz.getMethod("setBrand", String.class);
		setBrand.invoke(car, "奔驰");
		Method setColor = clazz.getMethod("setColor", String.class);
		setColor.invoke(car, "银色");
		Method setMaxSpeed = clazz.getMethod("setMaxSpeed", String.class);
		setMaxSpeed.invoke(car, "260");
		return car;
	}
	
	public static Car initByParamConst() throws Throwable {
		//1.通过类装载器获取Car类对象
		ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
		Class clazz = classLoader.loadClass("com.stczwd.SpringIoC.Car");
		
		//2.获取类的带有参数的构造器对象，效果类似于new Car(String,String,String)
		Constructor constructor = clazz.getDeclaredConstructor(new Class[]{String.class,String.class,String.class});
		
		//3.使参数的构造器对象实例化Car
		Car car = (Car) constructor.newInstance(new Object[]{"宝马","黑色","200"});
		return car;
	}
	
	public static void main(String[] args) throws Throwable {
		Car car1 = initByDefaultConst();
		Car car2 = initByParamConst();
		car1.introduce();
		car2.introduce();
	}
}


