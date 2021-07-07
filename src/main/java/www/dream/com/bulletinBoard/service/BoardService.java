package www.dream.com.bulletinBoard.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import www.dream.com.bulletinBoard.model.BoardVO;
import www.dream.com.bulletinBoard.persistence.BoardMapper;

// 1. 홈페이지에서 Service를 제공하는 부분

@Service // 2. 서비스 부분은 @Service 달기, 이건 Control또한 맟찬가지
// 3. 그리고 root-context에 scan 부분 추가해주고
// 4.  BoardVO에서 가져올 Board 목록을 보여주는getList 함수 작성
public class BoardService {
	@Autowired
	private BoardMapper boardMapper;
	
	public List<BoardVO> getList(){
		return boardMapper.getList();
	}
	
	//5. 함수 하나 더 만들기
	// 6. Board Table을 가지고 만들수 있는가를 확인
	public BoardVO getBoard(int id){
		return boardMapper.getBoard(id);
	}
}
