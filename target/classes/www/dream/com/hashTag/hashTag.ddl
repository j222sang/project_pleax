drop table sm_ht2post
drop table s_hashtag;
truncate table sm_ht2post
drop SEQUENCE seq_hashtag_id;


CREATE SEQUENCE seq_hashtag_id; -- id값을 자동으로 만들어낼 것

-- id ,super_id ,hashtag  ,description		


create table s_hashtag(
	id				number(9)		primary key,
	super_id		number(9)		references s_hashtag(id),
	hashtag			varchar2(100),				-- 단어의 중복성을 방지하기위한 index
	description		varchar2(1000)		
);
-- 사랑, 우정이라는 단어가 기록되어 있다. 어떤 게시글을 작성하였는데 행복 이라는 단어가 추가되었는데
-- 행복이 s_hashtag table에 들어있냐고 물어보면 당연히 없기때문에, 행복이라는 단어는 hashtag상에 있어야 하기에
-- hashtag가 primary key가 되어야 한다. // 근데 또 바뀌었음

create unique index uidx_hashtag on s_hashtag(hashtag);
--↑ Drop하면 같이 사라짐 , 단어 중복 방지 용도

-- unique index = primary key 개념적으로는 봐도 동일.
 
-- 여기서 m은 n:m관계
-- hashtag_id, post_id, occur_cnt
create table sm_ht2StringId(
	hashtag_id		number(9),
	opponent_type	varchar2(50),
	opponent_id		varchar2(4000),
	occur_cnt		number(9),
	primary key(hashtag_id, opponent_type, opponent_id)
);

DB에 Query를 여러번 날리는것이 아닌 좋은 "성능"과 관계가 있다.

create unique index idx_fromOpponent on sm_ht2StringId(opponent_type, opponent_id, hashtag_id);

create unique index idx_fromOpponent on sm_ht2StringId(opponent_type, opponent_id, hashtag_id);
 
--개인화 서비스, Personalization
create table sm_ht2NumberId(
	hashtag_id			number(9),
	opponent_type		varchar2(10),
	occur_id			number(9),
	occur_cnt			number(9),
	--최종 검색 활용 시점 ↓
	latest_use_time timestamp		default sysdate not null,
	primary key(occur_id, hashtag_id,opponent_type )
)

-- 배열 형 만들기
drop TYPE int_id_array
CREATE Or REPLACE TYPE int_id_array AS VARRAY(10000) OF NUMBER(9); 

CREATE Or REPLACE TYPE int_id_array AS table OF NUMBER(9); 

-- SEQUENCE를 활용하여 원하는 개수 만큼 숫자형 ID를 만들어 내기 
CREATE OR REPLACE FUNCTION genMultiId(cnt number) RETURN varchar2
IS
	seq_val number(9);
	strRet varchar2(4000) := ''; -- varchar2에 대한 변수 선언
BEGIN
	
	FOR i in 1..cnt LOOP
		select seq_hashtag_id.nextval into seq_val from dual;
		strRet := strRet || ',' || seq_val;
	END LOOP;
	
	RETURN ltrim(strRet, ',');
END; 

select genMultiId(3) from dual;
