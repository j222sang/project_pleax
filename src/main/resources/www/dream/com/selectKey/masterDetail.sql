create table s_master(
	id				number(9)			primary key, --Sequence Key 사용할것
	name			varchar2(100)		not null,
	reg_dt			timestamp			default sysdate not null
);
--↑몸체가 되는 정보

create table s_detail(
	m_id				number(9),			--master id로 가기위한 경로			
	id					number(9)			primary key,
	info				varchar2(50)		--연락처 정보
);
--↑그 몸체의 부가적인 정보
drop table s_master
drop table s_detail
drop SEQUENCE seq_master

CREATE SEQUENCE seq_master; -- 언제 만든건지 모르겠는데 이미 만들어져 있었네요.
	