package www.dream.com.bulletinBoard.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import lombok.Data;
import lombok.NoArgsConstructor;
import www.dream.com.common.model.CommonMngVO;
import www.dream.com.framework.lengPosAnalyzer.HashTarget;
import www.dream.com.framework.printer.AnchorTarget;
import www.dream.com.framework.util.ToStringSuperHelp;
import www.dream.com.party.model.Party;

@Data
@NoArgsConstructor
public class ReplyVO extends CommonMngVO {
	public static final String DESCRIM4REPLY = "reply";
	
	public static final int ID_LENGTH = 5;
	@AnchorTarget
	private String id;
	
	@HashTarget
	private String content;
	
	@HashTarget
	private Party writer;
	
	private List<ReplyVO> listReply = new ArrayList<>();
	
	protected int replyCnt = 0; // 대댓글의 개수를 셀 변수
	
	public ReplyVO(String parentId, String content, Party writer) {
		this.content = content;
		this.writer = writer;
	}
	
	public ReplyVO(String content, Party writer) {
		this.content = content;
		this.writer = writer;
	}
	
	public int getDepth() { // 댓글, 대댓글, 대대댓글의 반복을 구현하기 위한 재귀함수
		return id.length() / ID_LENGTH;
	}
	
	public String getOriginalId() { // 댓글, 대댓글, 대대댓글의 반복을 구현하기 위한 재귀함수
		return id.substring(0, id.length() - ID_LENGTH);
	}
	
	@Override
	public String toString() {
		return "ReplyVO [id=" + id + ", content=" + content
				+ ", writer=" + writer
				+ ", " + ToStringSuperHelp.trimSuperString(super.toString()) + "]";
	}
	
	/**
	 * Query를 통하여 정보를 읽을 때는 목록으로만 조회가 가능하다.  
	 *  */ 
	public static List<ReplyVO>  buildCompositeHierachy(List<ReplyVO> listFromDB) {
		List<ReplyVO> ret = new ArrayList<>();
		//  ↓ID     ↓Object
		Map<String, ReplyVO> map = new HashMap<>();
		for (ReplyVO reply : listFromDB) {
			if (reply.getDepth() == 3) // 3부터가 대댓글인데, 전에 만들어 둔거에서 5자리씩 생성되게 되어있음 그래서 5로 나누면 떨어짐
				ret.add(reply);  
			
			map.put(reply.getId(),reply);
			if (map.containsKey(reply.getOriginalId())) {
				map.get(reply.getOriginalId()).getListReply().add(reply);
			}
		}
		return ret;		
	}
	
}
