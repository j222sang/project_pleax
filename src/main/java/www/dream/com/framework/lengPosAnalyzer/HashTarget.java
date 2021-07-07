package www.dream.com.framework.lengPosAnalyzer;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

@Retention(RUNTIME)
@Target({FIELD, METHOD }) // TYPE이 있기때문에, Class위에 @를 달 수 있다. Class의 별칭이 Type이니까
public @interface HashTarget {

}
