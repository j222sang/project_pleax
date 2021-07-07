package www.dream.com.hashTag.persistence;

import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import www.dream.com.framework.util.StringUtil;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("file:src\\main\\webapp\\WEB-INF\\spring\\root-context.xml")
@FixMethodOrder(MethodSorters.NAME_ASCENDING)

public class HashTagMapperTest {

	@Autowired
	private HashTagMapper hashTagMapper;

	@Test
	public void test001SelectMultipleId() {
		try {
			// 입력은 28,29,30 -> 출력은 INT 배열로 변환시켜서 출력 각각의 객체에 Id를 주기위해서
			
			System.out.println("↓↓↓↓↓결과를 확인하세요↓↓↓↓↓");
			System.out.println(StringUtil.convertCommnaSepString2IntArr(hashTagMapper.getIds(3)));
			System.out.println("↑↑↑↑↑결과를 확인하세요↑↑↑↑↑");

		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
