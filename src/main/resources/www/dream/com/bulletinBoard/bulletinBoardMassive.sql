select count(*) from
	s_post;
	
select * from
	s_post;
			
insert into s_post(id, board_id, writer_id, title, content)
select get_id(seq_post_id.nextval),board_id, writer_id, title, content
  from s_post;
  --계속해서 객체를 만들어 내는 코드
  
  --인덱스가 있는 컬럼을 그대로 사용하면 DB가 인덱스 활용 해주는데
  -- 그 값을 강제적으로 바꾸면 Index 활용하지 못하여서 내부적으로
  -- 모든 Data를 읽고 DB안의 메모리에서 정렬을 완료한다.
  -- 이때, 엄청난 시간이 걸린다.
  
  select *
    from s_post order by id;
    
    --값을 강제적으로 바꾼다.
  select *
    from s_post order by id || 'rrr';
    
a
b
c
가 있다. 여기에서 보면 a1 b2 c3
a를 첫번째 b를 두 번째 c를 세 번째로 읽었다.

역순으로 보면
c1 b2 a3
뭐 이렇게 ROWNUM이라는게 이런식으로 numberring 해주는 느낌

select p.*, ROWNUM
from s_post p order by id;

실행결과 읽은 다음에 만들어 지는것이 ROWNUM

실제 Data가 아니라서 Table에는 없다.
sql 맨뒤에 보면 ROWNUM 이라는 column이 있다.

select * 
from (
    select p.*, ROWNUM rn --44~47줄이 inLine 으로 볼 수있다.
      from s_post p
     where rownum <= 20
     order by id desc) p 
where rn > 10;

-- () 감싸는것 자체가 하나의 name space이기 때문에 p는 겹치지 않는다.
-- 근데 저 문제가 특정 수 대의 data를 찾고 싶으면, 그 수식을 직접 넣어야 한다.
-- 당연히 뒤로 갈 수록 속도는 좀 느려지기 마련

insert into sm_ht2post(hashtag_id, post_id, occur_cnt)
select h.id, p.id, 1
from s_hashtag h, s_post p
where hashtag = '재계약'; -- 55번

delete from sm_ht2post
where hashtag_id = 55;

select h.id, p.id, 1
from s_hashtag h, s_post p
where hashtag = '재계약';


select id
 from s_post;