package www.dream.com.bulletinBoard.persistence;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import www.dream.com.bulletinBoard.model.BoardVO;
import www.dream.com.bulletinBoard.model.PostVO;
import www.dream.com.bulletinBoard.model.ReplyVO;
import www.dream.com.common.dto.Criteria;
import www.dream.com.party.model.Party;

// PostVO -> PostMapper 작성
public interface ReplyMapper { // 추후 Data를 가져오기 위해서 Interface -> Mapper 생성
	//LRCUD Data건수는 많으니까 long으로 int로 return하면 안됨
	/**--------------------------- 게시글 처리 함수 정의 영역------------------------- */
	/** public long  getTotalCount(@Param("boardId") int boardId, @Param("descrim") String descrim); */
	public long  getTotalCount(@Param("boardId") int boardId);
	
	public long  getSearchTotalCount(@Param("boardId") int boardId, @Param("cri") Criteria cri);
	
	/* Mapper에 들어가는 인자의 개수가 여러 개 일때는 필수적으로 @Param을 넣어줘야 합니다.
	 * 이를 실수하지 않기위하여 변수가 한 개여도 그냥 명시적으로 넣을 것 */
	public List<PostVO> getList(@Param("boardId") int boardId, @Param("cri") Criteria cri); // 1. 새로운 함수 하나 만들어주기, PostMapper.xml 에서 Data 전달을 표현하기위한
	// xml에서 쓰이기 위해 @Param도 사용
	
	// 초기 화면 띄울때 활용 
	public List<PostVO> getListByHashTag(@Param("boardId") int boardId, @Param("cri") Criteria cri);
	
	// id 값으로 Post및 Reply, Reply of Reply 객체 조회
	public ReplyVO findReplyById(String id); // 조회
	
	/** 게시글 등록 */
	public int insert(@Param("board") BoardVO board, @Param("post") PostVO post);
	// PostVO를 객체로 받는 insert 함수 객체 이름은 post @Param의 이름도 "post" 그리고 BoardVO에서 board id를 가져온다. 

	public PostVO findPostById(String id);
	
	/** 게시글 수정 처리 */
	public int updatePost(PostVO post);
	
	/** id 값으로 Post 객체 삭제*/
	public int deleteReplyById(String id);
	
	/**--------------------------- 댓글 처리 함수 정의 영역 06.10------------------------- */
	
	public int getAllReplyCount(@Param("replyId") String replyId,
			@Param("idLength") int idLength);
	
	public int getReplyCount(@Param("originalId") String originalId,
			@Param("idLength") int idLength);
	
	
	public List<ReplyVO> getReplyListWithPaging(@Param("originalId") String originalId,
			@Param("idLength") int idLength, @Param("cri") Criteria cri);
	
	/** 특정 댓글의 모든 후손 대댓글을 작성 순서에 따라 조회 해줍니다. */
	public List<ReplyVO> getReplyListOfReply(@Param("originalId")String originalId, @Param("idLength")int idLength);
	
	/* Id값으로 Post객체 조회*/
	public int insertReply(@Param("originalId") String originalId,@Param("reply") ReplyVO reply); // js에서 original이였음
	/* 댓글 수정 처리 */
	public int updateReply(ReplyVO reply);

	public List<PostVO> getFavorite(@Param("boardId")int boardId, @Param("curUser") Party curUser);
	
}
