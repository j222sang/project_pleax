drop table s_id_seed; -- oracle에서 단순 table만 drop
truncate table s_id_seed; --컴퓨터 용량까지 비워버리는 코드 

create table s_id_seed(
	seq_id		number(19)		primary key,
	seed		char(5)
);

CREATE OR REPLACE FUNCTION get_id(intSeed number) RETURN char
is
	ret char(5);
BEGIN
	select seed into ret from s_id_seed where seq_id = intSeed;
	RETURN ret;
END; 

select get_id(15) from dual;