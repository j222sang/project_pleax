package www.dream.com.party.model;

import lombok.Data;
import www.dream.com.common.model.CommonMngVO;
import www.dream.com.framework.lengPosAnalyzer.HashTarget;

/**
 * 연락처 정보
 * @author Park
 *
 */
@Data
public class ContactPoint extends CommonMngVO { // 8. contact_point 클래스 생성 ,
	// 10 extends CommonMngVO 선언, 이유는 초기 설계에서 오류가 있었음, ContactPoint가 CommonMngVO상속을 받아야함. registrationDate, updateDate 를 이용하기 위해. 
	// 9. Java형에 맞게끔 자료형, 변수형 선언
	// 11. party.ddl 에서 contact_point_type 부분에 user_id 라는 info가 있는데 여기에는 사용하지 않았다. 이 친구는 어디에서 처리 해야하는가. 1:m 의 구조로 list, Master_Detail 부분 p.183 과정
	private String contactPointType; // 연락처 종류
	@HashTarget
	private String info; //연락처 정보

}
