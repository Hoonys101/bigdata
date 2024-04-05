alter session set "_oracle_script"=true;
create user bigdata identified by java;
grant connect, resource, unlimited tablespace to bigdata;
conn bigdata/java;

drop table ID;
drop sequence ID_SEQ;
drop table ArchivedData;
drop table AvailableData;


create table ID(
   ID number constraint PROJECT_PK primary key, 
   pwd varchar2(50), 
   user_name varchar2(50), 
   email varchar2(100), 
   birth_date date, 
   gender varchar2(20), 
   signup_date date
); 
create sequence ID_SEQ increment by 1 start with 1 nocache;

insert into ID values(ID_SEQ.nextval, '11111', 'ȫ�浿', 'human@naver.com', '1980-01-01', '����', '2024-04-01');
insert into ID values(ID_SEQ.nextval, '22222', '�̼���', 'human1@naver.com', '1985-02-02', '����', '2024-05-01');
insert into ID values(ID_SEQ.nextval, '33333', '������', 'human2@naver.com', '1990-03-03', '����', '2024-06-01');
insert into ID values(ID_SEQ.nextval, '44444', '������', 'human3@naver.com', '1995-04-04', '����', '2024-07-01');


CREATE TABLE AvailableData (
	"Nation" VARCHAR2(255), 
	"db_name" VARCHAR2(255), 
	"stock_code" VARCHAR2(255), 
	"Name" VARCHAR2(255), 
	"Sector" VARCHAR2(255)
	);
alter table availabledata add primary key ("stock_code");
CREATE TABLE ArchivedData (
	"Date" DATE, 
	"stock_code" VARCHAR2(255),
	"db_name" VARCHAR2(255), 
	"Open" NUMBER, 
	"High" NUMBER, 
	"Low" NUMBER, 
	"Close" NUMBER, 
	"Volume" NUMBER
	);
ALTER TABLE ArchivedData ADD CONSTRAINT fk_ArchivedData_AvailableData
	FOREIGN KEY ("stock_code") REFERENCES AvailableData("stock_code");
	
commit;

select CONSTRAINT_NAME, CONSTRAINT_TYPE from user_constraints where TABLE_NAME='ID';
select * from ID;
desc availabledata;
desc archiveddata;