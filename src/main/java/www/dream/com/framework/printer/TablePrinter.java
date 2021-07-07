package www.dream.com.framework.printer;

import java.lang.reflect.AccessibleObject;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.TreeSet;
import java.util.stream.Collectors;

import www.dream.com.framework.classAnalyzer.ClassAnalyzer;
import www.dream.com.framework.util.ComparablePair;
import www.dream.com.framework.util.Penta;
import www.dream.com.framework.util.StringUtil;

public class TablePrinter {
	/**
	 * 받은 객체의 속성 및 메소드에 추가된 @PrintTarget 에노테이션을 기반으로
	 * <Table> <tr> 한줄을 만들어 줍니다.
	 * @param obj
	 * @return
	 */
	public static String printTableRow(Object obj, String tagClass) {
		//받은 객체에서 @PrintTarget이 달린 Feature의 값을 읽고 이를 순서에 따라 저장한다. <순서, featureName, Type, 값>
		TreeSet<Penta<Integer, String, Class, Object, Object>> ordered = new TreeSet<>();
		collectValues(obj, PrintTarget.class, ordered);
		
		StringBuilder sb = new StringBuilder();
		
		for (Penta<Integer, String, Class, Object, Object> penta : ordered) {
			PrintTarget printTarget = (PrintTarget) penta.getD();
			if (printTarget.withAnchor()) {
				List<AccessibleObject> listAO = ClassAnalyzer.findFeatureByAnnotation(obj.getClass(), AnchorTarget.class);
				try {
					Field field = ((Field) listAO.get(0));
					field.setAccessible(true);
					Object id = field.get(obj);
					AnchorTarget anchorAnno = field.getAnnotation(AnchorTarget.class);
					sb.append("<td><a class='"+ tagClass + "' href=" + id + ">" + penta.getE() + "</a></td>");
				} catch (IllegalArgumentException | IllegalAccessException e) {
				}
			} else if (! StringUtil.hasInfo(printTarget.pattern())) {
				sb.append("<td>" + penta.getE() + "</td>");	
			} else {
				SimpleDateFormat  fmt = new SimpleDateFormat(printTarget.pattern());
				sb.append("<td>" + fmt.format((Date) penta.getE()) + "</td>");
			}
		}
		return sb.toString();
	}
	
	private static void collectValues(Object obj, Class<PrintTarget> anno,
			TreeSet<Penta<Integer, String, Class, Object, Object>> ordered) {
		if (obj == null)
			return; //NullpointException 방지
		
		List<AccessibleObject> listAO = ClassAnalyzer.findAllFeature(obj.getClass());
		// 상속 구조에 의하여 PrintTarget이 중복되는 현상에 대하여 처음 불려진 객체의 상위 속성 출력을 우선시 하겠다.
		List<AccessibleObject> listAnnotatedAO = listAO.stream().filter(ao-> ao.getAnnotation(anno) != null)
					.collect(Collectors.toList());
		listAO.removeAll(listAnnotatedAO);
		
		for (AccessibleObject ao : listAnnotatedAO) {
			PrintTarget printTarget = ao.getAnnotation(anno);
			//PrintTarget Annotation이 추가되어있는 속성 또는 메소드라면..
			int order = printTarget.order();
			String featureName = null;
			Class type = null;
			Object value = null;
			if (ao instanceof Field) {
				Field field = (Field) ao;
				//ex)private int readCnt;에 대하여 "readCnt" 찾기 
				featureName = field.getName();
				type = field.getType(); //int 가져오기
				try {
					field.setAccessible(true);
					value = field.get(obj);
				} catch (IllegalArgumentException | IllegalAccessException e) {
				}
			} else if (ao instanceof Method) {
				Method method = (Method) ao;
				//ex)public String toString(){} "toString" 가져오기
				featureName = method.getName();
				type = method.getReturnType(); //String 가져오기
				try {
					value = method.invoke(obj, new Object[] {});
				} catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
				}
			}
			ordered.add(new Penta<>(order, featureName, type, printTarget, value));	
		} 
		
		/*  PrintTarget Annotation이 추가되어있지 않더라도 해당 객체 내부에 PrintTarget이 추가된
	    속성 또는 메소드에 대하여 재귀적으로 구성  */
		for (AccessibleObject ao : listAO) {
			PrintTarget printTarget = ao.getAnnotation(anno);
			if (ao instanceof Field) {
				Field field = (Field) ao;
				Class type = field.getType();
				ClassPrintTarget classPrintTarget = (ClassPrintTarget) type.getAnnotation(ClassPrintTarget.class);
				if (classPrintTarget != null) {
					try {
						field.setAccessible(true);
						Object value = field.get(obj);
						collectValues(value, PrintTarget.class, ordered);
					} catch (IllegalArgumentException | IllegalAccessException e) {
					}
				}
			} else if (ao instanceof Method) {
				Method method = (Method) ao;
				Class type = method.getReturnType();
				ClassPrintTarget classPrintTarget = (ClassPrintTarget) type.getAnnotation(ClassPrintTarget.class);
				if (classPrintTarget != null) {
					try {
						Object value = method.invoke(obj, new Object[] {});
						collectValues(value, PrintTarget.class, ordered);
					} catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
					}
				}
			}
		}
	}

	public static String printHeader(Class cls) {
		//HeaderTarget가 추가된 모든 AccessibleObject를 모집
		TreeSet<ComparablePair<Integer, String>> ordered = new TreeSet<>();
		
		collectHeaders(cls, PrintTarget.class, ordered); 
		
		//문자열 출력
		StringBuilder sb = new StringBuilder();
		for (ComparablePair<Integer, String> cp : ordered) {
			sb.append("<th>" + cp.getSecond() +  "</th>");
		}
		return sb.toString();
	}
	
	private static void collectHeaders(Class cls, Class anno, TreeSet<ComparablePair<Integer, String>> ordered) {
		if (cls.getAnnotation(ClassPrintTarget.class) == null)
			return;
		List<AccessibleObject> listFeature = ClassAnalyzer.findAllFeature(cls);
		for (AccessibleObject ao : listFeature) {
			PrintTarget ht = (PrintTarget) ao.getAnnotation(anno);
			if (ht != null) {
				ordered.add(new ComparablePair<>(ht.order(), ht.caption()));	
			} else { //annotation 못찾았으면
				if (ao instanceof Field) {
					collectHeaders(((Field) ao).getType(), anno, ordered);
				} else if (ao instanceof Method) {
					collectHeaders(((Method) ao).getReturnType(), anno, ordered);
				}
			}
		}
	}
}
