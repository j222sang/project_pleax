package www.dream.com.party.control;

import java.util.List;
import java.util.Map;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.servlet.ModelAndView;

@RunWith(SpringJUnit4ClassRunner.class) // 18. JUnit으로 Test 하겠다는 @
@WebAppConfiguration // 20. Browser 역할을 대신하는 @
@ContextConfiguration({"file:src\\main\\webapp\\WEB-INF\\spring\\root-context.xml",
	"file:src/main/webapp/WEB-INF/spring/appServlet/servlet-context.xml"}) // 19. 여러개의 파일 경로 설정 {중괄호}를 통해서

public class PartyControllerTest { // 17. Control.Class를 Test하기위한 새로운 Class 경로 잘 봐두고 , src/test/java에서 만들어야함
	@Autowired // 22. @연결하겠다는거죠?
	private WebApplicationContext ctx; // 21.Configuration을 통해서 내용물이 채워지면 이를 주입받음
	private MockMvc mockMvc; // 26. mockMvc라는 변수 선언해주고
	
	@Before// 23. 준비단계
	public void setup() { // 24. 뭔가를 setup 하겠다는 것. 그 내용은 이제 아래로.
		// Mock : 가상화 , 가짜, 대행자
		mockMvc = MockMvcBuilders.webAppContextSetup(ctx).build(); // 25. Java내에서 지원하는 lib
		
	}
	
	/**
	 * mockMvc를 통하여 Test 함으로써 back-end 개발자의 할일은 끝남
	 * front-end 개발자는 EL이나 JSTL을 활용하여 listParty에 들어 있는 정보를 HTML로 나타내 준다.
	 */
	@Test // 26.mockMvc를 Test 하기위한 @
	public void testPartyList() { // 27. testPartyList함수 선언
		
		try {
			ResultActions ra = mockMvc.perform(MockMvcRequestBuilders.get("/party/list"));
			// 28. ResultActions library 을 통해서 try-catch문 선언
			MvcResult mvcResult = ra.andReturn(); // 29. andReturn이라는 함수도 있다.
			ModelAndView modelAndView= mvcResult.getModelAndView(); // 30. 이부분도 마찬가지 getModelAndView
			Map model = modelAndView.getModel(); // 31. getModel이라는 함수도 있다.
			((List)(model.get("listParty"))).forEach(p ->{System.out.println(p);}); // 32. forEach가 안되어서 List넣고 돌리기.
			// 33. party에 list를 호출하여 () Mapper에서 호출을 받으면 model에 넣고 model에서 꺼낸다.
			/* Total: get 방법으로 Web server에게 /party/list 이 url로 요청을 보낸다.
			 * andReturn이라서 결과를 받고
			 * ModelAndview를 받은다음에, 이 부분이 PartyController의 GetList(Model model)에서 model.addAttribute("listParty", ~에 담은
			 * 정보를 꺼낸다(model.get("listParty). 그리고 꺼내서 for-each 돌린다.
			 * Return Type을 보고 변수명으로 잡으면 된다. 
			 */
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
	}
}
