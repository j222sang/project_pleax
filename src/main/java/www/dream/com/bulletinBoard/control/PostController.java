package www.dream.com.bulletinBoard.control;

import java.security.Principal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.util.UriComponentsBuilder;

import www.dream.com.bulletinBoard.model.BoardVO;
import www.dream.com.bulletinBoard.model.PostVO;
import www.dream.com.bulletinBoard.service.BoardService;
import www.dream.com.bulletinBoard.service.PostService;
import www.dream.com.common.dto.Criteria;
import www.dream.com.framework.springSecurityAdapter.CustomUser;
import www.dream.com.party.model.Party;

//Post에 관한 Controller Class를 만들어 낼것. 0524 과정
@Controller
@RequestMapping("/post/*") 
public class PostController {
	@Autowired
	private PostService postService;

	@Autowired
	private BoardService boardService;

		
	/* 특정 게시판에 등록되어 있는 게시글을 목록으로 조회하기 P.212 void :/post/list.jsp로 변함 */
	//@GetMapping(value = "list") // LCRUD 에서 L:list
	//public void listPost(@RequestParam("boardId") int boardId,
			//@ModelAttribute("pagination")  Criteria fromUser ,Model model) {
		// Model 객체를 사용해서 jsp로 정보를 넘김
		//model.addAttribute("listPost", postService.getList(boardId, fromUser));
		/*
		 * boardId를 집어넣으면 목록 정보가 나오겠지 보여줄 게시글 목록은 여기에서 완료
		 */
		//model.addAttribute("boardId", boardId);
		/* 밑에 redirect할때 특정 게시판이 살아있어야 removePost 운용이 가능해진다.
		 boardId라는 속성값이 계속해서 따라다닌다. 막상 찾아보려고 하니, PostVO에는 정보값이 없고,
		 readPost.jsp에도 값이 없다.
		 그래서 목록조회를 할때 boardId를 넣어서 list.jsp에서 어떤 자리에서 boardId라는 정보를 넣어서 다음(readPost)으로
		 전달이 될 수 있게끔*/
		//model.addAttribute("boardName", boardService.getBoard(boardId).getName());
		// Home 화면에서 board 공지사항으로 바로 가기 위한 하나의 방법. 경로를 만들어 주기위한 기능을 정의하는 것이다.
		//Test를 위해 Criteria Class에 만들어놨던 생성자들을 최종적으로 제거 
		//fromUser.setTotal(postService.getTotalCount(boardId)); 
		//model.addAttribute("pagination", fromUser);
		// ↑06.07 부로 list 부분은 더이상 사용하지 않음 
		
		
		/* 밑에 게시글 목록을 만들어낼 요소. 여기서 다시 계산을 해주는 부분이, 정말로 중요하다. 이 요소를 추가해줌으로써
		 * 많은 오류들을 잘 처리하고있다.
		 */

		/*
		 * 05.28 현상황에서의 문제는 startPage와 endPage가 10Page이후로 계산이 안되었다. Pagination을 하기위해서
		 * 새롭게 객체 만든것을 활용(fromUser)
		 */
//	}
	
	/*
	 * 06.04 作
	 */
	@GetMapping(value = "listBySearch") // LCRUD 에서 L:list
	public void listBySearch(@RequestParam("boardId") int boardId,
			@ModelAttribute("pagination") Criteria userCriteria,
			@AuthenticationPrincipal Principal principal, Model model) {
		Party curUser = null;
		if (principal != null) {
			UsernamePasswordAuthenticationToken upat = (UsernamePasswordAuthenticationToken) principal;
			CustomUser cu = (CustomUser) upat.getPrincipal();
			curUser = cu.getCurUser();
		}
		
		model.addAttribute("listPost", postService.getListByHashTag(curUser,boardId, userCriteria));
		model.addAttribute("boardId", boardId);
		model.addAttribute("boardName", boardService.getBoard(boardId).getName());
		userCriteria.setTotal(postService.getSearchTotalCount(boardId, userCriteria));
		//model.addAttribute("pagination", fromUser);
		// return 구문은 이제 없어졌다. void 형식으로 바뀌었기에 06.07
	}

	@GetMapping(value = { "readPost", "modifyPost" }) // (value="readPost")가 바뀌었다. 여러개 호출하겠다고 value값을 조정
	// 밑에는 없어도 된다. 사실상 구조는 동일하다.
	public void findPostById(@RequestParam("boardId") int boardId,
			@RequestParam("postId") String id, Model model, 
			@ModelAttribute("pagination") Criteria fromUser) { // 게시글 조회 후 다시 돌아오기 위해 Criteria fromUser 추가
		model.addAttribute("post", postService.findPostById(id));
		model.addAttribute("boardId", boardId);// 그래서 findPostById 함수에도 boardId를 하나더 추가 시켜 주자
		// 그렇게 해야 remove쪽으로 던져줄 값이 하나 생긴다.
	}

	/*
	 * @GetMapping(value="modifyPost") // 수정이라는 기능을 불러오는것을 만듬 0526 //readPost와 거의
	 * 동일한 구성이라고 봐도 무방하다. public void openModifyPost(@RequestParam("boardId") int
	 * boardId,
	 * 
	 * @RequestParam("postId") String id, Model model) { model.addAttribute("post",
	 * postService.findPostById(id)); model.addAttribute("boardId", boardId); }
	 */

	@GetMapping(value = "registerPost") // LCRUD 에서 Create 부분
	@PreAuthorize("isAuthenticated()") // 현재 사용자가 로그인 처리 했습니까?
	public void registerPost(
			@RequestParam("boardId") int boardId, Model model) {
		model.addAttribute("boardId", boardId);
	}

	@PostMapping(value = "registerPost") // LCRUD 에서 Update 부분
	@PreAuthorize("isAuthenticated()")
	public String registerPost(
			@AuthenticationPrincipal Principal principal,
			@RequestParam("boardId") int boardId,
		PostVO newPost, RedirectAttributes rttr) {
		
		newPost.parseAttachInfo();
		BoardVO board = new BoardVO(boardId);
		
		UsernamePasswordAuthenticationToken upat = (UsernamePasswordAuthenticationToken) principal;
		CustomUser cu = (CustomUser) upat.getPrincipal();
		Party writer = cu.getCurUser();
		
		newPost.setWriter(writer);
		postService.insert(board, newPost);

		// 신규 게시글이 만들어 질때 select key를 이용하여 id가 만들어진다. PostMapper.xml에 id = "insert" 부분
		rttr.addFlashAttribute("result", newPost.getId());

		return "redirect:/post/listBySearch?boardId=" + boardId; // Redirect때문에 필요하다 Return 값이

		// 새로운 글을 등록하였을때 저장이 되게끔
	}

	@PostMapping(value = "modifyPost") // 수정 처리 기능을 담당 0526
	// 이부분은 removPost와 동일하다고 봐도 무방.
	@PreAuthorize("principal.username == #writerId")
	public String openModifyPost(
			@RequestParam("boardId") int boardId, PostVO modifiedPost,
			RedirectAttributes rttr, Criteria fromUser, String writerId) {
		// 화면에서 정보가 들어온다고 하자 boardId는 가교 역할, 그리고 밑에 오는 객체들이 정보 덩어리이다.
		modifiedPost.parseAttachInfo(); // 수정했을시에 파일이 삭제 되는것을 방지
		if (postService.updatePost(modifiedPost)) {
			rttr.addFlashAttribute("result", "수정처리가 성공");
			}
		//수정 버튼을 눌렀을때, 수정한 게시글이 있던 곳으로 돌아옴, 1페이지로 초기화 안됨
		
			UriComponentsBuilder builder = UriComponentsBuilder.fromPath("");
			builder.queryParam("boardId", boardId);
			fromUser.appendQueryParam(builder);
			// 게시글의 전체 내용이 바뀌기 보다는 조금의 내용이 바뀌는 것이 수정 행위의 일반적인 경향
			return "redirect:/post/listBySearch" + builder.toUriString();
		// 목록으로 다시 돌아가게끔, redirect 하기위해서 함수의 형도 void -> String으로 바꿔줘야한다.
		// 그리고 수정처리 하는 기능은 modify.jsp 에서 만들어줘야 한다.
	}

	// RedirectAttributes를 이용한 삭제 방법(Post방식 이용) 0525 Start
	@PostMapping(value = "removePost") // 재요청을 할때 다시 속성을 주는 것 LCRUD : Delete
	@PreAuthorize("principal.username == #writerId") // #:내가 받은 인자
	public String removePost(@RequestParam("boardId") int boardId, @RequestParam("postId") String id,
			RedirectAttributes rttr, Criteria fromUser, String writerId) {
		if (postService.deletePostById(id)) { // postService Class를 호출 해줘야 한다.
			rttr.addFlashAttribute("result", "삭제처리가 성공");
			//삭제 버튼을 눌렀을때, 삭제한 게시글이 있던 곳으로 돌아옴, 1페이지로 초기화 안됨
		}
		UriComponentsBuilder builder = UriComponentsBuilder.fromPath("");
		builder.queryParam("boardId", boardId);
		fromUser.appendQueryParam(builder);
		return "redirect:/post/listBySearch" + builder.toUriString();
		// 내가 어떤 정보가 필요한데, 그 정보의 출발점은 어디며, 연동계통은 어디이며 그 정보를 살려내어서 이곳(removePost)까지 받아낼 것
	}
}

// boardId가 들어 오면 model 값에 boardId를 담고, readPost할때, 다시금 boardId로 받을 수 있다.
// 그리고 boardId값을 하나 더 던져줄 거고. 받은 boardId값을 다시 던져줄때 redirect 할 수 있도록