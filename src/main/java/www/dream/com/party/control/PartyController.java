package www.dream.com.party.control;

import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import www.dream.com.party.model.Member;
import www.dream.com.party.service.PartyService;

@Controller // 11. Controller @ 생성 , Controller @는 servelt.xml에 추가해줘야 한다.
@RequestMapping("/party/*") // @ 12. RequestMapping @ 생성
public class PartyController implements AuthenticationSuccessHandler, AccessDeniedHandler { // 8. PartyController class 생성
	
	@Autowired // 10. Autowired @ 생성
	private PartyService partyService; // 9. PartyClass와 이어줄거고
	
	@Autowired
	private PasswordEncoder pwEncoder;
	
	@GetMapping(value="list") // 14. GetMapping @도 가져오고.
	public void getList(Model model) { // 13.getList 함수 생성 , // 15. Model 이거 구글링해보고 
		model.addAttribute("listParty", partyService.getList()); // 16. getlist를 불러오기위한 동작을 model에 담을것
	}
	
	@GetMapping("customLogin")
	public void loginInput(String error, String logout, Model model) {
		if (error != null) {
			model.addAttribute("error", "계정을 다시 확인 하세요");
		}
		
		if (logout != null) {
			model.addAttribute("logout", "로그 아웃 성공!");
		}
	}
//	
//	@GetMapping("customLogout")
//	public void processLogout() {
//	}
	
	@PostMapping("customLogout")
	public void processLogoutPost() {
	}
	
	@GetMapping("showCurUser")
	public void showCurUser() {
	}
	
	@GetMapping("joinMember")
	public void joinMember(Model model) {
		model.addAttribute("listCPType", partyService.getCPTypeList());
		model.addAttribute("memberType", partyService.getMemberType());
	}
	
	@PostMapping("joinMember")
	public String joinMember(Member newBie, @DateTimeFormat(pattern = "yyyy-MM-dd") Date birthDate) {
		//회원가입시 비밀번호 암호화
		newBie.setUserPwd(pwEncoder.encode(newBie.getUserPwd()));
		partyService.joinMember(newBie);
		return "redirect:/";
	}
	
	@ResponseBody
	@PostMapping(value = "idCheck", produces="text/plane")
	public String ID_Check(@RequestBody String paramData) throws ParseException {
		//클라이언트가 보낸 ID값
		String ID = paramData.trim();
		int dto = partyService.IDDuplicateCheck(ID);
		
		if(dto > 0) {//결과 값이 있으면 아이디 존재	
			return "-1";
		} else {		//없으면 아이디 존재 X
			System.out.println("null");
			return "0";
		}
	}
	
	/**
	 * 로그인 성공 시 각 사용자의 권한 유형에 따라 개인화된 화면을 연동 시켜주기 위한 기능을 이곳에서 개발합니다.
	 */
	@Override
	public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
			Authentication authentication) throws IOException, ServletException {
		List<String> roleNames = new ArrayList<>();
		authentication.getAuthorities().forEach(authority->{
			roleNames.add(authority.getAuthority());
		});
		
		 if (roleNames.contains("manager")) {
		 response.sendRedirect("/party/showCurUser"); return; 
		 }
		
		if (roleNames.contains("admin")) {
			response.sendRedirect("/post/listBySearch?boardId=1");
			return;
		}
		
		if (roleNames.contains("manager")) {
			response.sendRedirect("/post/listBySearch?boardId=2");
			return;
		}
		
		response.sendRedirect("/post/listBySearch?boardId=3");
	}
	
	@Override
	public void handle(HttpServletRequest request, HttpServletResponse response,
			AccessDeniedException accessDeniedException) throws IOException, ServletException {
		response.sendRedirect("/party/accessError");
	}
}
