--Oracle 자료형 선택시.. 고민 거리..
-- int , long -> number(9), 19로 사용
--date -> 년-월-일(date)사용, 일시까지 사용(timestamp)
--boolean -> char(1)로 사용
drop table s_contact_point;
drop table s_party;
drop table s_contact_point_type;
--Sequence Java-for문 같은 느낌
drop   SEQUENCE seq_party_id;

CREATE SEQUENCE seq_party_id START WITH -990000000 MINVALUE -990000000;

--party_type, description
create table s_party_auth(
	party_auth			varchar2(10),
	description			varchar2(100)
);

insert into s_party_auth(party_auth, description)
	values('Admin', '관리자');
    
insert into s_party_auth(party_auth, description)
	values('User', '사용자');	
	
	
	
	--user_id, user_pwd, name, birth_dt, sex, enabled, reg_dt, upt_dt, descrim	
	
create table s_party(
	user_id			varchar2(10)		primary key,
	user_pwd		varchar2(100)		not null,	-- 100 : 추후 암호화 된 결과물 까지 고려함
	name			varchar2(100)		not null,	-- 100 : 전 지구적인 사람의 이름 길이까지 고려함
	birth_dt 		Date,							-- 생일은 년-월-일  
	sex				char(1)				default 1 not null, -- true : male, false : female	
	enabled			char(1)				default 1,
	reg_dt			timestamp			default sysdate not null,	--등록 시점
	upt_dt			timestamp			default sysdate not null,	--수정 시점
	descrim			varchar2(10)		not null	-- admin, manager를 넣기 위함
	--Admin용 속성 정의함
	--Manager용 속성 정의함
	--User용 속성 정의함
);

create table s_party(
	user_id			varchar2(10)		primary key,
	user_pwd		varchar2(100)		not null,	-- 100 : 추후 암호화 된 결과물 까지 고려함
	name			varchar2(100)		not null,	-- 100 : 전 지구적인 사람의 이름 길이까지 고려함
	birth_dt 		Date,							-- 생일은 년-월-일  
	sex				char(1)				default 1 not null, -- true : male, false : female	
	enabled			char(1)				default 1,
);

insert into s_party(user_id, user_pwd, name, birth_dt, sex,enabled, descrim)
	values('admin', '1234', '김이박', '1999-01-30', '1', '1', 'Admin');
insert into s_party(user_id, user_pwd, name, birth_dt, sex,enabled, descrim)
	values('mana1', '1234', '김이홍', '2010-01-30', '0', '1', 'Manager');
insert into s_party(user_id, user_pwd, name, birth_dt, sex,enabled, descrim)
	values('mana2', '1234', '김이정', '2005-01-30', '1', '1', 'Manager');

insert into s_party(user_id, user_pwd, name, birth_dt, sex,enabled, descrim)
	values('hong', '1234', '홍길동', '2005-01-30', '1', '1', 'User');
insert into s_party(user_id, user_pwd, name, birth_dt, sex,enabled, descrim)
	values('lee', '1234', '이순신', '2005-01-30', '0', '1', 'User');	
insert into s_party(user_id, user_pwd, name, birth_dt, sex,enabled, descrim)
	values('ghost', '1234', '고스트', '2005-01-30', '0', '0', 'User');	
--각 행위자별 여러 연락처 정보 관리
--party_tpye, description

create table s_contact_point_type(
	contact_point_type	char(10),
	description			varchar2(100)
);

insert into s_contact_point_type(contact_point_type, description)
	values('address', '주소지');
insert into s_contact_point_type(contact_point_type, description)
	values('phoneNum', '주소지에 있는 전화 번호');
insert into s_contact_point_type(contact_point_type, description)
	values('mobileNum', '핸드폰번호');
	
--연락처 유형을 만들기 위한 Code Table. 상속, 연관 구조를 따져서.. 만든것
-- user_id, contact_point_type, info
create table s_contact_point (
	user_id			varchar2(10),
	contact_point_type	char(10),
	info		varchar2(50),	--연락처 정보
	reg_dt			timestamp			default sysdate not null,	--등록 시점
	upt_dt			timestamp			default sysdate not null,	--수정 시점
	primary key (user_id, contact_point_type),
	CONSTRAINT fk_cp_party FOREIGN KEY (user_id) REFERENCES s_party(user_id) --참조할떄의 문법
);

create table f_contact_point (
	user_id			varchar2(10),
	contact_point_type	char(10),
	info		varchar2(50),	--연락처 정보
	reg_dt			timestamp			default sysdate not null,	--등록 시점
	upt_dt			timestamp			default sysdate not null,
	primary key (user_id, contact_point_type)
w2
);

insert into s_contact_point(user_id, contact_point_type, info)
	values('admin', 'address', '서울시 금천구 가산동 312호');
insert into s_contact_point(user_id, contact_point_type, info)
	values('admin', 'phoneNum', '02-123-4567');
insert into s_contact_point(user_id, contact_point_type, info)
	values('admin', 'mobileNum', '010-2232-2212');

insert into s_contact_point(user_id, contact_point_type, info)
	values('mana1', 'address', '서울시 금천구 가산동 314호');
insert into s_contact_point(user_id, contact_point_type, info)
	values('mana1', 'phoneNum', '02-123-3568');
insert into s_contact_point(user_id, contact_point_type, info)
	values('mana1', 'mobileNum', '010-3357-4575');

insert into s_contact_point(user_id, contact_point_type, info)
	values('mana2', 'address', '서울시 금천구 가산동 314호');
insert into s_contact_point(user_id, contact_point_type, info)
	values('mana2', 'phoneNum', '02-423-3528');
insert into s_contact_point(user_id, contact_point_type, info)
	values('mana2', 'mobileNum', '010-1157-8495');
	
insert into s_contact_point(user_id, contact_point_type, info)
	values('hong', 'address', '한양 남산동 333번지');
insert into s_contact_point(user_id, contact_point_type, info)
	values('lee', 'address', '조선 충청도 아산 충무공');
	