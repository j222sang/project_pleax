package www.dream.com.party.persistence;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import www.dream.com.party.model.ContactPointTypeVO;
import www.dream.com.party.model.Party;
import www.dream.com.party.model.partyOfAuthVO;
import www.dream.com.party.model.Member;

/**
 * Mybatis를 활용하여 Party 종류의 객체를 관리하는 인터페이스
 * @author Park
 *
 */
public interface PartyMapper { // 13. persistence package에 PartyMapper interface 작성
	// 함수 정의 순서 LRCUD
	// 목록 조회
	//@Select("select * from s_party") // 15. @Select를 사용하여, sql에 만들어놓은 s_party Table을 불러내볼것
	public List<Party> getList(); //14. List Type으로 Return 받는 목록조회 함수 getList 생성 , Table에 있는 User 정보를 읽어보자.
	// public List<Admin> getList(); // 22. Admin에다가 변수 넣어서 Test 해보는 것, 
	// 개별 객체 조회
	// Insert
	// Update
	// Delete
	
	public Party findPartyByUserId(String userId);
	//partyMapperTest.java에 있는 Code. 함수를 만들어 줄 것
	public void insert(@Param("user") Member newBie); // insert(newBie)에 대한 함수를 만들어볼건데 @Param 넣는건 늘 잘해줘야 한다.

	public String findByName(@Param("name") String name);

	// 자동으로 추가가 되지 않을때 어떻게 해야하는지
	
	public int setPwd(Party p);

	
	/** 연락처 관련 정의 영역 **/
	public List<ContactPointTypeVO> getCPTypeList();
	
	public void joinMember(@Param("party") Member member);
	
	public int IDDuplicateCheck(@Param("userId") String userId);
	
	/**	권한 처리 관련 영역	*/
	public partyOfAuthVO getMemberType();
}
