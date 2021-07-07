create table s_post(
	id				varchar2(4000)			primary key,
	board_id		number(9)				REFERENCES s_board(id),
	writer_id		varchar2(10)			REFERENCES s_party(user_id),
	title			varchar2(100),
	content			varchar2(4000),
	read_cnt		number(9)				default 0,
	like_cnt		number(9)				default 0,
	dislike_cnt		number(9)				default 0,
	reg_dt			timestamp				default sysdate not null,
	upt_dt			timestamp				default sysdate not null 
);

create table s_hashtag( -- 단어집
	id				number(9)		primary key,
	super_id		number(9)		references s_hashtag(id),
	hashtag			varchar2(100),				
	description		varchar2(1000)		
);

create table sm_ht2post( -- Mapping 정보
	hashtag_id		number(9),
	post_id			varchar2(4000),
	occur_cnt		number(9),
	primary key(hashtag_id, post_id)
);

 create table s_party(
	user_id			varchar2(10)		primary key,
	user_pwd		varchar2(100)		not null,	-- 100 : 추후 암호화 된 결과물 까지 고려함
	name			varchar2(100)		not null,	-- 100 : 전 지구적인 사람의 이름 길이까지 고려함
	birth_dt 		Date,							-- 생일은 년-월-일  
	sex				char(1)				default 1 not null, -- true : male, false : female	
	enabled			char(1)				default 1,
	reg_dt			timestamp			default sysdate not null,	--등록 시점
	upt_dt			timestamp			default sysdate not null,	--수정 시점
	descrim			varchar2(10)			not null	-- admin, manager를 넣기 위함
	--Admin용 속성 정의함
	--Manager용 속성 정의함
	--User용 속성 정의함
);


--검색어 기반 게시글 조회
-- 검색어 예시 : 리그 , 재계약
select id
  from s_hashtag
 where hashtag in ('리그', '재계약')

 -- 검색어 리그 , 재계약으로 이 단어가 적용된 게시글 id를 찾으시오
select post_id
  from sm_ht2post
 where hashtag_id in ( select id
 from s_hashtag
 where hashtag in('리그')	
 )
 
 
 select post_id
  from sm_ht2post
 where post_id in ('005sZ')
 
 

-- 상기 문제를 join으로 만들기
select distinct m.post_id
from sm_ht2post m, s_hashtag w 
where w.hashtag in ('리그', '재계약')
  and w.id = m.hashtag_id 

--검색어 "리그" , "재계약" 으로 이단어가 적용된 게시글id 와 작성자 정보를 찾으시오
-- from 절을 시작으로 꺼내는 쪽으로 → 계속 걸쳐가는 table 순서
select distinct p.*, w.*
  from  s_hashtag h, sm_ht2post m, s_post p, s_party w
  where h.hashtag in ('리그', '재계약')
    and h.id = m.hashtag_id
    and m.post_id = p.id
    and p.writer_id = w.user_id

    
-- 검색어 "리그" , "재계약"으로 이 단어가 적용된 게시글과 작성자 정보를 찾아서
-- 해당 페이지의 내용을 최신 작성글 순서로 출력 하시오
-- Query는 복잡한걸 단계적으로 만들어야 안정적으로 동작 한다. 

select distinct p.*, w.*
  from  s_hashtag h, sm_ht2post m, s_post p, s_party w
  where h.hashtag in ('리그', '재계약')
    and h.id = m.hashtag_id
    and m.post_id = p.id
    and p.writer_id = w.user_id
    ORDER BY p.id DESC
	   OFFSET 0 ROWS FETCH FIRST 10 ROWS ONLY
	   
-- 단어 사이의 상속 구조를 이용한 강력 검색 기능

insert into s_hashtag(id, hashtag)
	values (-1001, 'element');
	
insert into s_hashtag(id, hashtag)
	values (-1000, 'feature');
	
	
-- feature의 상단을 1001번으로 
update s_hashtag
   set super_id = -1001
 where id = -1000;
 
--48, 46 에 들어있는 정보를 update
update s_hashtag
   set super_id = -1000
 where id in (46, 48);

--단어 'element'를 s_hashtag에서 검색하면 그 결과는 ('element' , 'feature', '변수', '메소드')이다.

SELECT hashtag, id
   FROM s_hashtag
   START with hashtag = 'element'
   CONNECT BY PRIOR id = super_id;
   
SELECT hashtag, id
   FROM s_hashtag
   START with hashtag in 'element'
   CONNECT BY PRIOR id = super_id;

   
-- 검색어 "리그" , "재계약"으로 이 단어가 계층적으로 적용된 게시글과 작성자 정보를 찾아서
-- 해당 페이지의 내용을 최신 작성글 순서로 출력 하시오
select distinct p.*, w.*
  from  s_hashtag h, sm_ht2post m, s_post p, s_party w
  where h.id in (
  SELECT id
   FROM s_hashtag
   START with hashtag in ('element','리그','쟤계약')
   CONNECT BY PRIOR id = super_id)	   
   and h.id = m.hashtag_id
    and m.post_id = p.id
    and p.board_id = 3
    and p.writer_id = w.user_id
    ORDER BY p.id DESC
	   OFFSET 0 ROWS FETCH FIRST 10 ROWS ONLY  

	   
select count(distinct p.id)
      from  s_hashtag h, sm_ht2post m, s_post p
     where  h.id in (
       SELECT id
   	    FROM s_hashtag
     START with hashtag in ('element','리그','쟤계약')
   	 CONNECT BY PRIOR id = super_id)	   
  	and h.id = m.hashtag_id
  	and m.post_id = p.id
 	and p.board_id = 3
	   