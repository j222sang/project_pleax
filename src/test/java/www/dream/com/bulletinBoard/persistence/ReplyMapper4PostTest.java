package www.dream.com.bulletinBoard.persistence;

import static org.junit.Assert.assertNotNull;

import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import www.dream.com.bulletinBoard.model.BoardVO;
import www.dream.com.bulletinBoard.model.PostVO;
import www.dream.com.bulletinBoard.model.ReplyVO;
import www.dream.com.common.dto.Criteria;
import www.dream.com.party.model.Admin;

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
public class ReplyMapper4PostTest { 
	
	@Autowired
	private ReplyMapper postMapper;
	
	//@Test
	public void test000InsertPost() {
		try {
			BoardVO commNotice = new BoardVO(1);
			PostVO post = new PostVO("테스트", "게시글 테스트", new Admin("admin"));
			postMapper.insert(commNotice, post);
			//insert()문에서는 추가된 데이터의 PK값을 알 수 없다. -> 그래서 ->insertSelectKey()를 사용한다. p191참조
			System.out.println("지금 만든 객체의 ID는 " + post.getId()); 
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	//@Test
	public void test010DeleteById() {
		try {
			System.out.println(postMapper.deleteReplyById("00003"));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	//@Test
	public void test040GetList() {
		assertNotNull(postMapper);
		try {
			postMapper.getList(1, new Criteria()).forEach(post->{System.out.println(post);});	
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	//@Test
	public void test020UpdateById() {
		try {
			PostVO post = (PostVO) postMapper.findReplyById("00005");
			if(post != null) {
			post.setTitle(post.getTitle() + "업데이트 v1");
			postMapper.updatePost(post);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@Test
	public void test030FindById() {
		try {
			ReplyVO post = postMapper.findReplyById("00003");
			System.out.println(post);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
	
