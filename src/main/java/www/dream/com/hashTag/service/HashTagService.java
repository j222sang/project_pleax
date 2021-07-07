package www.dream.com.hashTag.service;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import www.dream.com.framework.util.StringUtil;
import www.dream.com.hashTag.model.HashtagVO;
import www.dream.com.hashTag.model.IHashTagOpponent;
import www.dream.com.hashTag.persistence.HashTagMapper;
import www.dream.com.party.model.Party;

//JDBC를 활용해볼 것

@Service
public class HashTagService {
	@Autowired
	private  HashTagMapper hashTagMapper;
	/***
	 * 
	 * @param hashTagOpponent 상대
	 * @param mapOccur 단어와 출현 횟수
	 */
	public  void CreateHashTagAndMapping(IHashTagOpponent hashTagOpponent,
		Map<String, Integer> mapOccur) {
		Set<String> setHashTag =  mapOccur.keySet();
		
		if (setHashTag.isEmpty()) {
			//게시글에서 단어가 나타나지 않으면 처리할 것이 없음
			return;
		}

		Set<HashtagVO> setExisting = hashTagMapper.findExisting(setHashTag); 
		// 기존 단어집에서 찾아내어 Match 해줍니다.
		for(HashtagVO hashtag : setExisting) {
			hashtag.setOccurCnt(mapOccur.get(hashtag.getHashtag())); //총 3번 감싸주었다.
		}

		for(HashtagVO hashtag : setExisting) {  
			setHashTag.remove(hashtag.getHashtag());
			// ↑에 들어있는게 신규 단어, 그것들을 위한 New Id가 필요한데, 어떻게 개발을 해야할까?
			// HashtagVO에 있는 hashtag 객체에서 저장되어있던 정보를 꺼내어, setHashTag에서 제거할 것(신규 단어)
		}
		
		Set<String> setNewHashTag = setHashTag; //제거하고 남은것들은 setNewHashTag 이름으로 선언 새롭게 출현할 단어들
		if (!setNewHashTag.isEmpty()) { 
			//새로운 단어가 있으면 HashTag Table에 등록해줍니다. ↓ 
			int[] ids = StringUtil.convertCommnaSepString2IntArr(hashTagMapper.getIds(setNewHashTag.size()));
			int idx = 0;
			Set<HashtagVO> setHT = new HashSet<>(); //List 구조체
			
			for (String hashTag : setNewHashTag) {
				HashtagVO newHashtag = new HashtagVO(ids[idx++], hashTag); //HashTagVO에 관한 객체는 하나 만들어줬고
				newHashtag.setOccurCnt(mapOccur.get(hashTag));
				setHT.add(newHashtag);
			}
			hashTagMapper.createHashTag(setHT); // HashTag단어집에 이제, 신규 단어집이 들어간 것
			//새 단어를 단어집에 넣었으니 기존단어가 됨
			setExisting.addAll(setHT); 
			}
		
			//기존 단어와 신규단어와 매칭
			hashTagMapper.insertMapBetweenStringId(setExisting, hashTagOpponent); 
		}

			/* hashTagMapper.insertMapBetweenPost(setHT, post.getId()) 64번째 줄
			 * hashTagMapper.insertMapBetweenPost(setExisting, post.getId()) 86번째 줄
			 * 구조가 동일하다. setHT, setExisting의 차이였는데, 저 둘의 자료형도
			 * Set<HashtagVO>로 동일하였다. 수정전 코드상에는 같은 함수를 두 번 출력하였기에
			 * setHT를 setExisting에 담아줌으로써, 해결
			 * 새 단어를 단어집에 넣었으니 기존 단어가 되어버린것  */
	public void deleteMap(IHashTagOpponent hashTagOpponent) {
		hashTagMapper.deleteMapBetweenStringId(hashTagOpponent);
	}
	
	/**
	 * 기존의 검색한 단어는 활용 횟수 올려주기
	 * 신규 단어는 단어 새롭게 만들고 횟수는 1로 넣어줌
	 * @param curUser
	 * @param mapSearchWord
	 */
public void mngHashTagAndMapping(Party curUser, Map<String, Integer> mapSearchWord) {
	Set<String> setSearchWord =  mapSearchWord.keySet();
	
	if (setSearchWord.isEmpty()) {
		//게시글에서 단어가 나타나지 않으면 처리할 것이 없음
		return;
	}
	
	//기존에 검색에서 활용되었고 활용 횟수 1 증가 시킬 대상 : setPrevUsed
	Set<HashtagVO> setPrevUsed = hashTagMapper.findprevUsedHashTag(curUser ,setSearchWord); // 한 번이라도 쓰인 단어
	Set<HashtagVO> setExisting = hashTagMapper.findExisting(setSearchWord); // 완전 신규로 쓰이는 단어
	
	//기존의 있는 것들은 사용 횟수 올리기
	for (HashtagVO hashtag : setPrevUsed) {
		hashtag.setOccurCnt(mapSearchWord.get(hashtag.getHashtag())); //총 3번 감싸주었다.
	}

	for(HashtagVO hashtag : setExisting) {  
		setSearchWord.remove(hashtag.getHashtag());
		// ↑에 들어있는게 신규 단어, 그것들을 위한 New Id가 필요한데, 어떻게 개발을 해야할까?
		// HashtagVO에 있는 hashtag 객체에서 저장되어있던 정보를 꺼내어, setHashTag에서 제거할 것(신규 단어)
	}
	//검색에서 새롭게 나타난 단어. 새 단어 등록해주는 이유는 게시글이나 상품이 새롭게 등장하면 즉시 검색되도록 함.
	//setNewHashTag 변수명은 가독성을 위하여 setSearchWord를 대체하는 것 입니다. 이 아래에서 setSearchWord 변수 사용 금지 
	Set<String> setNewHashTag = setSearchWord;
	if (! setNewHashTag.isEmpty()) { 
		//새로운 단어가 있으면 HashTag Table에 등록해줍니다. ↓ 
		int[] ids = StringUtil.convertCommnaSepString2IntArr(hashTagMapper.getIds(setNewHashTag.size()));
		int idx = 0;
		Set<HashtagVO> setHT = new HashSet<>(); //List 구조체
		for (String hashTag : setNewHashTag) {
			HashtagVO newHashtag = new HashtagVO(ids[idx++], hashTag); //HashTagVO에 관한 객체는 하나 만들어줬고
			newHashtag.setOccurCnt(mapSearchWord.get(hashTag));
			setHT.add(newHashtag);
		}
		hashTagMapper.createHashTag(setHT); // HashTag단어집에 이제, 신규 단어집이 들어간 것
			//새 단어를 단어집에 넣었으니 기존단어가 됨
			setPrevUsed.addAll(setHT); 
		}
		//관리되던 단어가 검색에서 새롭게 나타남  
		setPrevUsed.addAll(setExisting);
	
		//기존 활용 횟수 정보 없애기
		hashTagMapper.deleteMapBetweenOpponentStringId(setPrevUsed, curUser);
		//기존 단어와 신규단어와 매칭
		hashTagMapper.insertMapBetweenStringId(setPrevUsed, curUser); 
	}
}
