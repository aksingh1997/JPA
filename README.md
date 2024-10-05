#### To start/stop mysql server -
* Windows+R -> services.msc -> MYSQL80 -> RightClick -> start/stop

#### Sql commnads (can be run sequentially to see the output)
``` mysql
-- create database
create database college

-- use a database.
use college;

-- create table
select 'Hello';
create table Student(
	id int primary key,
    name varchar(20),
    age int
);

create table Laptop(
	id int primary key, -- adding primary key constraint
    name varchar(20),
    studentId int,
    foreign key (studentId) references Student (id) -- adding foreign key constraint
);

-- insert in table
insert into Student values(1, 'Abhi', 26);
insert into Student values(2, 'Manisha', 29);
insert into Student values(3, 'Rashmi', 28);
insert into Student values(4, 'Abhijeet', 25);
insert into Student values(5, 'Vaibhav', null);
select * from Student;

insert into Laptop values(1, 'Dell', 2);
insert into Laptop values(2, 'lenovo', 2);
insert into Laptop values(3, 'lenovo', 1);
insert into Laptop values(4, 'Dell', 3);
insert into Laptop values(5, 'Asus', 3);
insert into Laptop values(6, null, 1);
select * from Laptop;

-- sql wildcards
select * from Student where name like '%sh%'; -- name containing sh 
select * from Student where name like 'Ab%'; -- name starting with Ab
select * from Student where name like '%mi'; -- name ending with mi
select * from Student where name like 'Man___a'; -- each underscore represents a letter, can be any

-- between / and
select * from Student where age between 26 and 28;

-- in / not in
select * from Student where age in (25, 26, 27);
select * from Student where age not in (25, 26, 27);

-- sql aggregators
select max(age) from Student;
select min(age) from Student;
select avg(age) from Student;
select sum(age) from Student;

-- maths operation are possible too
select avg(age) + 5 from Student;
select age - 2 from Student;
select age * 2 from Student;
select age / 2 from Student;

-- sorting asc and desc , limit, offset
select * from Student order by age;
select * from Student order by age desc;
select * from Student order by age desc limit 3;
select * from Student order by age desc limit 1 offset 2; -- offset means it will ignore first two rows, prints the third highest age here

-- alias -as
select id as StudentId, name as StudentName from Student;

-- distinct / count
select distinct(name) from Laptop;
select count(distinct(name)) as laptopCount from Laptop;

-- is null / is not null / <> (not equal to)
select name from Student where age is null;
select name from Student where age is not null;
select * from Student where age <> 28;

-- better practice to give tables a short name like u and v, easier to write sql
-- upper / lower
select u.name as studentNaeme, v.name as laptopName from Student u, Laptop v where u.id = v.id;
select upper(u.name) as studentName, lower(v.name) as laptopName from Student u, Laptop v where u.id = v.id; 

-- group by / having 
select name, count(*) from Laptop group by name having name in ('Dell', 'Asus');

-- joins
select u.name as studentName, v.name as laptop from Student u
join Laptop v
on v.studentId = u.id where v.name <> 'Dell';

select u.name as studentName, v.name as laptop from Student u
inner join Laptop v
on v.studentId = u.id;

select u.name as studentName, v.name as laptop from Student u
left join Laptop v
on v.studentId = u.id;

select u.name as studentName, v.name as laptop from Student u
right join Laptop v
on v.studentId = u.id;

-- subquery
select * from Student where id = (select id from laptop where name = 'Dell');

-- null handling
select COALESCE(name, 'unknown laptop') from laptop; -- mySql dont support nvl() , oracle supports both. nvl = COALESCE

-- modify column, adding not null constraint and removing
alter table Student modify column name varchar(20) not null;
alter table Student modify column name varchar(20);

-- used to find out constraint name, I want to drop the foreign key constraint , but don't know its name, so ran the below query
select column_name, constraint_name from information_schema.KEY_COLUMN_USAGE 
where table_name = 'Laptop';

-- drop the foregin key constraint (mySql), took foreign key constraint name from previous query. better to give name to each constraint
alter table Laptop
drop FOREIGN KEY laptop_ibfk_1;

-- drop foriegn key constraint (oracle)
alter table Laptop
drop constraint laptop_ibfk_1;

-- add foreign key constraint again
alter table Laptop
add constraint studentId_fk -- providing a constraint name
foreign key(studentId) references Student(id);

-- add unique constraint
alter table Student
add constraint name_uc --its alsways better to give your constraints a name
unique(name); -- we can have more than one feild, comma seperated. It will constrain the entries to be unique for the combination of params provided here. 

-- remove unique constraint
alter table Student
drop constraint name_uc;

-- creating views in sql
-- views are virtual tables over real tables and we can apply sql over it like any other normal table
create view student_laptop as
select u.id, u.name as studentName, v.name as laptop from student u
join laptop v on u.id = v.studentId order by u.id;

select * from student_laptop;

drop view student_laptop;

```
