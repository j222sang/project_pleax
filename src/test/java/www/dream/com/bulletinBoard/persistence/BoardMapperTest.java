package www.dream.com.bulletinBoard.persistence;

import static org.junit.Assert.assertNotNull;

import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import www.dream.com.party.persistence.PartyMapper;

// 17. Test를 하기위한 @의 모임. 
@RunWith(SpringJUnit4ClassRunner.class) // 5. test 하기위해서 아까했던 TestDI를 가져오고
@ContextConfiguration("file:src\\main\\webapp\\WEB-INF\\spring\\root-context.xml")
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
//0521 Board의 속성 Data 값을 확인하기 위한 Junit Test

/**
 * JUnit Test를 할때, 함수의 선언 결과가 오름차순 순으로 정렬이 되어 출력이 된다.
 * 그래서 밑에 testgetB , testgetL B가 더 높기때문에 B쪽 함수가 먼저 출력됨.
 * @author Park
 */
public class BoardMapperTest { 
	
	@Autowired // . 
	private BoardMapper boardMapper;  // 

	@Test
	public void testgetList() {
		assertNotNull(boardMapper); // 
		try {
			boardMapper.getList().forEach(p ->{System.out.println(p);}); // 
		} catch(Exception e) {
			e.printStackTrace(); // 
		}
	}
	
	@Test
	public void testgetBoard() { 
		assertNotNull(boardMapper); // 
		try {
			System.out.println(boardMapper.getBoard(1));// 일단 이곳에 들어갈 수 있는 수는 1,2,3 만들어놓은 Data가 3개뿐
		} catch(Exception e) {
			e.printStackTrace(); // 
		}
	}

}
