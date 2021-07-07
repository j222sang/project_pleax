package www.dream.com.hashTag.model;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class HashtagVO implements Comparable<HashtagVO>{ // 0602 HashTag 요소 추가
	
	private int id;
	private String hashtag;
	private String description;
	
	private int occurCnt;

	public HashtagVO(int id, String hashtag) { // hashtag 만 받아들이는 생성자를 만들어줍니다.
		this.id = id;
		this.hashtag = hashtag;
	}
	
	public void setOccurCnt(int cnt) {
		occurCnt += cnt;
	}
	
	@Override
	public int compareTo(HashtagVO o) {
		return this.id - o.id;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		HashtagVO other = (HashtagVO) obj;
		if (id != other.id)
			return false;
		return true;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + id;
		return result;
	}
	
	
}
