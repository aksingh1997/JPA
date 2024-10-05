* To start/stop mysql server - Windows+R -> services.msc -> MYSQL80 -> RightClick -> start/stop

``` mysql
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


```
