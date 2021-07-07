package www.dream.com.bulletinBoard.control;

import java.security.Principal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import www.dream.com.bulletinBoard.model.ReplyVO;
import www.dream.com.bulletinBoard.service.ReplyService;
import www.dream.com.common.dto.Criteria;
import www.dream.com.framework.springSecurityAdapter.CustomUser;
import www.dream.com.framework.util.ComparablePair;
import www.dream.com.party.model.Party;
import www.dream.com.party.model.Member;

@RestController
@RequestMapping("/replies/*")
public class ReplyController {

	@Autowired
	private ReplyService replyService;
	
	//댓글Count하기
	@GetMapping(value = "count/{replyId}") 
	public ResponseEntity<Integer> getCountOfReply(@PathVariable("replyId") String replyId) {
		
		return new ResponseEntity<>(replyService.getCountOfReply(replyId), HttpStatus.OK);
	}

	// LRCUD 순으로 배치
	@GetMapping(value = "pages/{originalId}/{page}")
	public ResponseEntity<ComparablePair<Criteria, List<ReplyVO>>> getReplyListWithPaging(
			@PathVariable("originalId") String originalId,
			@PathVariable("page") int page) {
		Criteria cri = new Criteria();
		cri.setPageNumber(page);
		
		ComparablePair<Criteria, List<ReplyVO>> listReplyWithCount 
		= replyService.getReplyListWithPaging(originalId, cri);
		return new ResponseEntity<>(listReplyWithCount, HttpStatus.OK);
	}
	
	@GetMapping(value = "pages/{replyId}")
	public ResponseEntity<List<ReplyVO>> getReplyListOfReply(
			@PathVariable("replyId") String replyId) {
		return new ResponseEntity<>(replyService.getReplyListOfReply(replyId), HttpStatus.OK);
	}

	@GetMapping(value = "/{id}")
	public ResponseEntity<ReplyVO> findReplyById(@PathVariable("id") String id) {
		return new ResponseEntity<>(replyService.findReplyById(id), HttpStatus.OK);
	}
	
	@PreAuthorize("isAuthenticated()")
	@PostMapping(value = "new/{originalId}",
			consumes = "application/json",
			produces = { MediaType.TEXT_PLAIN_VALUE })
	public ResponseEntity<String> insertReply(@PathVariable("originalId") String originalId,
			@RequestBody ReplyVO reply, @AuthenticationPrincipal Principal principal) {
		
		UsernamePasswordAuthenticationToken upat = (UsernamePasswordAuthenticationToken) principal;
		CustomUser cu = (CustomUser) upat.getPrincipal();
		Party writer = cu.getCurUser();
		
		reply.setWriter(writer);
		
		int insertCount = replyService.insertReply(originalId, reply);
		if (insertCount == 1) {
			return new ResponseEntity<>(reply.getId(), HttpStatus.OK);
		} else {
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@RequestMapping(method = {RequestMethod.PUT, RequestMethod.PATCH},
			consumes = "application/json",
			produces = { MediaType.TEXT_PLAIN_VALUE })
	public ResponseEntity<String> updateReply(@RequestBody ReplyVO reply) {
		int updateCount = replyService.updateReply(reply);
		if (updateCount == 1) {
			return new ResponseEntity<>("수정 처리가 성공", HttpStatus.OK);
		} else {
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
			
		}
	}
	
	@DeleteMapping(value = "{id}",
			produces = { MediaType.TEXT_PLAIN_VALUE })
	public ResponseEntity<String> deleteReplyById(@PathVariable("id") String id) {
		int deleteCount = replyService.deleteReplyById(id);
		if (deleteCount > 0) {
			return new ResponseEntity<>("삭제 처리가 성공", HttpStatus.OK);
		} else {
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
}
