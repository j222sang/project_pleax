package www.dream.com.testFrameWork;

import java.util.Map;

import org.junit.Test;

import www.dream.com.bulletinBoard.model.PostVO;
import www.dream.com.framework.lengPosAnalyzer.PosAnalyzer;
import www.dream.com.party.model.Admin;
import www.dream.com.party.model.ContactPoint;

public class TestPosAnalyzer {

	@Test
	public void test() {
		PostVO post = new PostVO();
		post.setTitle("질문 구조 그리고 밥");
		post.setContent("프로젝트의 전체구조 목표");
		
		Admin writer = new Admin();
		writer.setName("강아지");
		post.setWriter(writer);
		
		ContactPoint cp = new ContactPoint();
		cp.setInfo("서울 특별시 금천구 가산디지털");
		writer.addContactPoint(cp);
		
		cp = new ContactPoint();
		cp.setInfo("pretty_gm@naver.com");
		writer.addContactPoint(cp);
		
		Map<String, Integer> map = PosAnalyzer.getHashTags(post);
		for (String k : map.keySet()) {
			System.out.println(k + " : " + map.get(k));
		}
	}
}
// Map<String, Integer> hashList = PosAnalyzer.getHashTags(post); //
// PosAnalyzer에게 post 객체를 던져주면 String의 출현 횟수가 나올것.

/*
 * hashList = PosAnalyzer.getHashTags(post.getTitle()); hashList =
 * PosAnalyzer.getHashTags(post.getContent()); hashList =
 * PosAnalyzer.getHashTags(post.getWriter().getName());
 */

// 이렇게 개발을 하면, 있는 객체들마다 찾아야 한다. 총 4개 -> 코드가 길어지지
