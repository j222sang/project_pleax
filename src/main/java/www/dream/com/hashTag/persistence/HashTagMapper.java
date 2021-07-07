package www.dream.com.hashTag.persistence;

import java.util.Set;

import org.apache.ibatis.annotations.Param;

import www.dream.com.hashTag.model.HashtagVO;
import www.dream.com.hashTag.model.IHashTagOpponent;

//필요한 기능을 이곳에 정의해줄건데..
public interface HashTagMapper {
	/**
	 * setHashTag로 받은 단어중에 기존에 관리하고 있는 단어 집합 찾기
	 * setHashtag isEmpty() false 일때만 호출해주세요
	 * @param Park
	 * @return
	 */
	// Identifier가 필요해서 HashtagVO를 사용
	public Set<HashtagVO> findExisting(@Param("setHashTag") Set<String> setHashTag);
	
	public Set<HashtagVO> findprevUsedHashTag(@Param("opponent") IHashTagOpponent hashTagOpponent, 
			@Param("curSearch")Set<String> curSearch);
	
	/**
	 * HashTagVO의 id를 지정한 개수만큼 Sequence를 통하여 한번에 왕창 만들기. 성능
	 * @param cnt
	 * @return
	 */
	public String getIds(int cnt);
	
	/**
	 * HashTag와 Post사이의 관계,정보 다중 입력하기(고성능)
	 * @param setExisting Table에 존재하는 단어의 ID
	 * @param opponentId 상대방 Id
	 * @param opponentType 상대방 Type
	 * @return
	 */
	public int createHashTag(@Param("setNewHashTag") Set<HashtagVO> newHashtag);
	
	public int insertMapBetweenStringId(@Param("setExisting")Set<HashtagVO> setExisting,
			@Param("opponent") IHashTagOpponent opponent);
	// void -> int로 바꾸어서 몇 건 처리했는지 수식으로도 볼 수 있음
	
	//MyBatis는 Operator Overloading 지원을 안함
	public void deleteMapBetweenStringId(@Param("opponent") IHashTagOpponent hashTagOpponent);
	
	public void deleteMapBetweenOpponentStringId(@Param("setExisting") Set<HashtagVO> setExisting, @Param("opponent") IHashTagOpponent hashTagOpponent);
	
	//public int createHashTag(Set<HashtagVO> newHashtag); // 추후에 있는 affected Count에서 확인하려고
	

}
