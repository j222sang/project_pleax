package www.dream.com.framework.lengPosAnalyzer;

import java.lang.reflect.AccessibleObject;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import kr.co.shineware.nlp.komoran.constant.DEFAULT_MODEL;
import kr.co.shineware.nlp.komoran.core.Komoran;
import kr.co.shineware.nlp.komoran.model.KomoranResult;
import kr.co.shineware.nlp.komoran.model.Token;
import www.dream.com.framework.classAnalyzer.ClassAnalyzer;

/**
 * 품사 분석기가 정의한 Annotation을 달아 놓은 객체의 속성에 들어있는 정보를 KOMORAN을 활용하여 품사 분석하고 hashTag로
 * 활용할만한 단어가 몇 번 사용되었는지 까지를 Pair의 List로 변화할 것입니다.
 */

public class PosAnalyzer {

	private static Komoran komoran = new Komoran(DEFAULT_MODEL.FULL);

	/**
	 * 주어진 객체에서 각종 속성및 함수위에 @HashTarget을 달아서 노출시킨 정보를 바탕으로 단어 분석기를 통하여 나온 것들을 출현
	 * 횟수까지 찾아 반환
	 */
	public static Map<String, Integer> getHashTags(Object obj) {
		Map<String, Integer> ret = new HashMap<>();
		getHashTags(obj, ret); // 주어진 Object가 게시글 정보
		return ret;
	}

	private static void getHashTags(Object obj, Map<String, Integer> map) {
		if (obj == null) {
			return;
		}
		else if (obj instanceof String) {
			analyzeHashTag((String) obj, map);
		} else if (obj instanceof Iterable) {
			((Iterable) obj).forEach(ele -> getHashTags(ele, map)); // obj를 Interable 형변환 후 forEach 돌리고 lamda식 사용,
			// getHashTags에 던지고 Map형태로 저장

		} else if (obj instanceof Map) {
			((Map) obj).entrySet().forEach(ele -> getHashTags(ele, map));
		} else {
			List<AccessibleObject> listFeature = ClassAnalyzer.findFeatureByAnnotation(obj.getClass(),
					HashTarget.class);

			for (AccessibleObject ao : listFeature) {
				if (ao instanceof Field) {
					Field field = (Field) ao;
					try {
						field.setAccessible(true);
						Object fieldValue = field.get(obj);
						getHashTags(fieldValue, map);
						// Field에 저장되었는 값을 읽어내겠다. 그리고 품사 분석을 할 것

					} catch (IllegalArgumentException | IllegalAccessException e) {
					}
				} else if (ao instanceof Method) { 
					Method method = (Method) ao;
					try {
						Object returnValue = method.invoke(obj, null);
						getHashTags(returnValue, map);
					} catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
						e.printStackTrace();
						// 길었던 코드들을 많이 줄여버렸다.
					}

				}
			}

		}
	}


/**
 * 품사 분석기 KOMORAN을 활용하여 지정한 품사들을 대상으로 몇 번 나타났는지 까지 정보를 모아준다.
 * 
 * @param analysisTargetString
 * @param ret
 */

private static void analyzeHashTag(String analysisTargetString, Map<String, Integer> ret) {
	KomoranResult analyzeResultList = komoran.analyze(analysisTargetString);
	List<Token> tokenList = analyzeResultList.getTokenList();

	for (Token token : tokenList) {
		TargetPos pos = null; // 품사
		try {
			pos = TargetPos.valueOf(token.getPos());
		} catch (Exception e) {
		}
		if (pos != null) {
			String hashTag = token.getMorph();
			if (ret.containsKey(hashTag)) {
				ret.put(hashTag, (ret.get(hashTag) + 1));
			} else {
				ret.put(hashTag, 1);
			}

		}
	}
}
}