package www.dream.com.framework.classAnalyzer;

import java.lang.reflect.AccessibleObject;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class ClassAnalyzer {
	
	/**
	 * 주어진 Class에서 정의되어 있는 모든 속성과 함수(기능)를 상속 구조까지 분석하여 모아서 반환한다
	 * 
	 * @param targetClass
	 * @return
	 */

	public static List<AccessibleObject> findAllFeature(Class targetClass) {
		List<AccessibleObject> ret = new ArrayList<>();
		findAllFeatureByRecursion(targetClass, ret);
		return ret;
	}
	

	/**
	 * 주어진 클래스에서 정의되어 이는 몯느 속성과 함수에 지정된 Annotation을 붙혀놓은 것들을 상속구조까지 분석하여 모아서 반환한다.
	 * 
	 * @param targetClass
	 * @param ret
	 */

	public static List<AccessibleObject> findFeatureByAnnotation(Class targetClass, Class targetAnno) {
		return findAllFeature(targetClass).stream().filter(ao -> ao.getAnnotation(targetAnno) != null)
									.collect(Collectors.toList());
		/* ↑위처럼 Refactoring이 가능하다.
		List<AccessibleObject> ret = new ArrayList<>();
		findAllFeature(targetClass.getSuperclass(), ret);
		return ret.stream().filter(ao -> ao.getAnnotation(targetAnno) != null).collect(Collectors.toList());
		*/
	}
	
	//Annotation 없이 모아서 출력해주는 함수
	private static void findAllFeatureByRecursion(Class targetClass, List<AccessibleObject> ret) {
		if (targetClass == null)
			return;

		try {
			Field[] fields = targetClass.getDeclaredFields();
			for (Field field : fields) {
				ret.add(field);
			}

			Method[] methods = targetClass.getDeclaredMethods();
			for (Method method : methods) {
				ret.add(method);
			}
		} catch (Exception e) {
		}
		findAllFeatureByRecursion(targetClass.getSuperclass(), ret);
	}
}
