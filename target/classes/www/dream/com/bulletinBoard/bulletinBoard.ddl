drop SEQUENCE seq_post_id;
drop table s_post;
drop table s_board;

create table s_board(
	id			number(9)		primary key,
	name		varchar2(100),
	description varchar2(1000),
	reg_dt		timestamp		default sysdate not null,
	upt_dt		timestamp		default sysdate not null
);

	insert into s_board(id, name, description)
		values(1, '공지사항', '드림 회사에서 드리는 공지사항');
	
	insert into s_board(id, name, description)
		values(2, 'FAQ', '자주 문의되는 사항에 대한 답변들');
	
	insert into s_board(id, name, description)
		values(3, '자유 게시판', '회원이면 누구나 자유롭게 쓰세요');
	
	
create SEQUENCE seq_reply_id;
	
-- id, writer_id, content, reg_dt, upt_dt, descrim, board_id, title, read_cnt, like_cnt, dislike_cnt
create table s_reply(
	id				varchar2(4000)			primary key,
	writer_id		varchar2(10)			REFERENCES s_party(user_id),
	content			varchar2(4000),
	reg_dt			timestamp				default sysdate not null,
	upt_dt			timestamp				default sysdate not null,
	descrim			varchar2(10)			not null, --reply, post 두 개의 String으로 구분할 것
	--descrim이 post일 경우 사용되는 정보들
	board_id		number(9)				REFERENCES s_board(id),
	title			varchar2(100),
	read_cnt		number(9)				default 0,
	like_cnt		number(9)				default 0,
	dislike_cnt		number(9)				default 0
);

create index idx_Reply_board_id on s_reply(board_id, id);
drop index idx_reply_board_id

--상속구조일때 Top, Bottom, Hybrid 전략이 있는데, Bottom은 좋지않음, Top은 너무 부피가 커지고 그래서 Hybrid전략으로