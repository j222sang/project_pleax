package www.dream.com.bulletinBoard.persistence;

import java.util.List;

import www.dream.com.bulletinBoard.model.BoardVO;

public interface BoardMapper { // 추후 Data를 가져오기 위해서 Interface-Mapper 생성
	public List<BoardVO> getList();
	
	public BoardVO getBoard(int id);
}
