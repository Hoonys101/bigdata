alter session set "_oracle_script"=true;
create user bigdata identified by java;
grant connect, resource, unlimited tablespace to bigdata;
conn bigdata/java;

DROP TABLE ServiceUsage;
DROP TABLE BranchHistory;
DROP TABLE ExclusionperiodHistory;
DROP TABLE ExcludedquarterHistory;
DROP TABLE ArchivedData;
DROP TABLE addition;
DROP TABLE AvailableData;
DROP TABLE ID;
DROP SEQUENCE  serviceusage_seq;
DROP SEQUENCE  BranchHistory_seq;
DROP SEQUENCE ExclusionperiodHistory_seq;
DROP SEQUENCE ExcludedquarterHistory_seq;
create table ID(
   ID varchar2(100) constraint PROJECT_PK primary key, 
   pwd varchar2(50), 
   user_name varchar2(50), 
   email varchar2(100), 
   birth_date date, 
   gender varchar2(20), 
   signup_date date
); 

insert into ID values('1', '11111', '홍길동', 'human@naver.com', '1980-01-01', '남성', '2024-04-01');
insert into ID values('2', '22222', '이순신', 'human1@naver.com', '1985-02-02', '남성', '2024-05-01');
insert into ID values('3', '33333', '강감찬', 'human2@naver.com', '1990-03-03', '남성', '2024-06-01');
insert into ID values('4', '44444', '유관순', 'human3@naver.com', '1995-04-04', '여성', '2024-07-01');


CREATE TABLE AvailableData (
   stock_code VARCHAR2(255) constraint AvailableData_PK primary key, 
   Nation VARCHAR2(255), 
   db_name VARCHAR2(255),
   Sector VARCHAR2(255),
   Name VARCHAR2(255)
   );

CREATE TABLE ArchivedData (
   data_date DATE, 
   stock_code VARCHAR2(255),
   db_name VARCHAR2(255), 
   open NUMBER, 
   high NUMBER, 
   low NUMBER, 
   close NUMBER, 
   volume NUMBER,
   FOREIGN KEY (stock_code) REFERENCES AvailableData(stock_code)
   );

CREATE TABLE addition (
   ID varchar2(100),
   stock_code VARCHAR2(255),
   FOREIGN KEY (ID) REFERENCES ID(ID),
   FOREIGN KEY (stock_code) REFERENCES AvailableData(stock_code)
);
CREATE SEQUENCE serviceusage_seq START WITH 1 INCREMENT BY 1;
CREATE TABLE ServiceUsage (
   serviceusage_seq NUMBER PRIMARY KEY,
   stock_code1 VARCHAR2(255),
   stock_code2 VARCHAR2(255),
   name1 VARCHAR2(255),
   name2 VARCHAR2(255),
   start_date VARCHAR2(30),
   end_date VARCHAR2(30),
   ID VARCHAR2(30),
   report VARCHAR2(255),
   FOREIGN KEY (ID) REFERENCES ID(ID),  -- ID를 참조하는 외래 키 제약 조건
   FOREIGN KEY (stock_code1) REFERENCES AvailableData(stock_code),
   FOREIGN KEY (stock_code2) REFERENCES AvailableData(stock_code)
);
CREATE SEQUENCE BranchHistory_seq START WITH 1 INCREMENT BY 1;
CREATE TABLE BranchHistory(
   branchHistory_seq NUMBER PRIMARY KEY,
   stock_code1 VARCHAR2(255),
   stock_code2 VARCHAR2(255),
   name1 VARCHAR2(255),
   name2 VARCHAR2(255),
   start_date VARCHAR2(30),
   end_date VARCHAR2(30),
   ID VARCHAR2(30),
   report VARCHAR2(255),
   resultcount NUMBER,
   FOREIGN KEY (ID) REFERENCES ID(ID),  -- ID를 참조하는 외래 키 제약 조건
   FOREIGN KEY (stock_code1) REFERENCES AvailableData(stock_code),
   FOREIGN KEY (stock_code2) REFERENCES AvailableData(stock_code)
);

CREATE SEQUENCE ExclusionperiodHistory_seq START WITH 1 INCREMENT BY 1;
CREATE TABLE ExclusionperiodHistory(
   exclusionperiodHistory_seq NUMBER PRIMARY KEY,
   stock_code1 VARCHAR2(255),
   stock_code2 VARCHAR2(255),
   stock_code3 VARCHAR2(255),
   name1 VARCHAR2(255),
   name2 VARCHAR2(255),
   name3 VARCHAR2(255),
   start_date VARCHAR2(30),
   end_date VARCHAR2(30),
   ID VARCHAR2(30),
   report VARCHAR2(255),
   FOREIGN KEY (ID) REFERENCES ID(ID),  -- ID를 참조하는 외래 키 제약 조건
   FOREIGN KEY (stock_code1) REFERENCES AvailableData(stock_code),
   FOREIGN KEY (stock_code2) REFERENCES AvailableData(stock_code),
   FOREIGN KEY (stock_code3) REFERENCES AvailableData(stock_code)
);
CREATE SEQUENCE ExcludedquarterHistory_seq START WITH 1 INCREMENT BY 1;
CREATE TABLE ExcludedquarterHistory (
   excludedquarterHistory_seq NUMBER PRIMARY KEY,
   stock_code1 VARCHAR2(255),
   stock_code2 VARCHAR2(255),
   stock_code3 VARCHAR2(255),
   name1 VARCHAR2(255),
   name2 VARCHAR2(255),
   name3 VARCHAR2(255),
   start_date VARCHAR2(30),
   end_date VARCHAR2(30),
   ID VARCHAR2(30),
   report VARCHAR2(255),
   resultcount NUMBER,
   FOREIGN KEY (ID) REFERENCES ID(ID),  -- ID를 참조하는 외래 키 제약 조건
   FOREIGN KEY (stock_code1) REFERENCES AvailableData(stock_code),
   FOREIGN KEY (stock_code2) REFERENCES AvailableData(stock_code),
   FOREIGN KEY (stock_code3) REFERENCES AvailableData(stock_code)
);
commit;

select CONSTRAINT_NAME, CONSTRAINT_TYPE from user_constraints where TABLE_NAME='ID';
select * from ID;
desc id;
desc addition;
desc availabledata;
desc archiveddata;
desc ServiceUsage;
desc ServiceUsage1;