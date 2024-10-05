* To start/stop mysql server - Windows+R -> services.msc -> MYSQL80 -> RightClick -> start/stop

``` mysql
use college;

select 'Hello';
create table Student(
	id int primary key,
    name varchar(20),
    age int
);

create table Laptop(
	id int primary key,
    name varchar(20),
    studentId int,
    foreign key (studentId) references Student (id)
);

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

select * from Student where name like '%sh%';
select * from Student where name like 'Ab%';
select * from Student where name like '%mi';
select * from Student where name like 'Man___a';

select * from Student where age between 26 and 28;

select * from Student where age in (25, 26, 27);
select * from Student where age not in (25, 26, 27);


select max(age) from Student;
select min(age) from Student;
select avg(age) from Student;
select sum(age) from Student;

select avg(age) + 5 from Student;
select age - 2 from Student;
select age * 2 from Student;
select age / 2 from Student;

select * from Student order by age;
select * from Student order by age desc;
select * from Student order by age desc limit 3;
select * from Student order by age desc limit 1 offset 2;

select id as StudentId, name as StudentName from Student;  

select distinct(name) from Laptop;
select count(distinct(name)) as laptopCount from Laptop;

select name from Student where age is null;
select name from Student where age is not null;
select * from Student where age <> 28;

select u.name as studentNaeme, v.name as laptopName from Student u, Laptop v where u.id = v.id;
select upper(u.name) as studentName, lower(v.name) as laptopName from Student u, Laptop v where u.id = v.id;

select name, count(*) from Laptop group by name having name in ('Dell', 'Asus');

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

select * from Student where id = (select id from laptop where name = 'Dell');

select COALESCE(name, 'unknown laptop') from laptop; -- mySql dont support nvl() , oracle supports both. nvl = COALESCE


```
