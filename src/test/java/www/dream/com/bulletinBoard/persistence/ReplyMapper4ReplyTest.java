package www.dream.com.bulletinBoard.persistence;

import java.util.stream.IntStream;

import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import www.dream.com.bulletinBoard.model.ReplyVO;
import www.dream.com.common.dto.Criteria;
import www.dream.com.party.model.Member;

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
public class ReplyMapper4ReplyTest { 
	
	@Autowired
	private ReplyMapper replyMapper;
	
	@Autowired
	private ReplyMapper postMapper;
	
	
	@Test
		public void test040GetList() {
			try {
				replyMapper.getReplyListWithPaging("00003", "00003".length() + ReplyVO.ID_LENGTH , 
						new Criteria()).forEach(reply
						->{System.out.println(reply);
						});	
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	
	@Test
	public void test000InsertReply() {
		try {
			// 자유 게시판 최신글 찾기 original(원글)로 삼자
			ReplyVO original = postMapper.getList(3, new Criteria()).get(0); // 최신 글을 불러와주는 부분
			// 댓글 아무렇게나 만들어서 Insert할 것
			Member lee = new Member("lee");
			IntStream.rangeClosed(0, 1).forEach(i->{
			ReplyVO reply = new ReplyVO("폴란드전의 영웅" + i, lee);
			replyMapper.insertReply(original.getId(), reply);
			});
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@Test
	public void test030FindById() {
		try {
			ReplyVO post = postMapper.findReplyById("0000300005");
			System.out.println(post);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@Test
		public void test000InsertReplyOfReply() {
			try {
				// 자유 게시판 최신글 찾기 original(원글)로 삼자
				ReplyVO original = postMapper.findReplyById("0000300005"); // 최신 글을 불러와주는 부분
				// 댓글 아무렇게나 만들어서 Insert할 것
				Member lee = new Member("lee");
				IntStream.rangeClosed(0, 1).forEach(i->{
				ReplyVO reply = new ReplyVO("할 수있다 유상철 형!" + i, lee);
				replyMapper.insertReply("0000300005", reply);
				});
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
}
	
