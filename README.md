# JPA
* JPA provides ORM (Object relation management).
* Create a POJO class with @Entity. Give the table name (optional)
* Example -
  ``` java
  @Entity
  @Table(name = "Student")
  @Data
  public class Student {
      @Id
      private int id;
  
      @Column(name = "name", unique = true)
      private String name;
  
      // Learning - By default the columns are nullable, Here I have used int instead of Integer and got exception that
      // null cannot be mapped to int. Make sure to use Wrapper class for nullable fields.
      @Column(nullable = true)
      private Integer age;
  
      @Column(name = "subject_stream") // name of column can also be provided if it differs from the variable name used.
      private String subjectStream;

      @OneToMany(mappedBy = "student", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
      List<Book> bookList;
  
  }
  ```

* To use the above enity we need to create a interface let's say StudentRepo that will extend a JPARepository<ClassName, Primary Key Type>
* Jpa repository is and interface with predefined methods like findById, findByName, etc. and these all implementations are provided by JPA at compile time.
  ``` java
  public interface StudentRepo extends JpaRepository<Student, Integer> {

    //Useful methods provided by JPA - https://docs.spring.io/spring-data/jpa/reference/jpa/query-methods.html

    // following method's implementation are provided by JPA. From name itself it is intuitive what each method says.
    // I have provided useful ones, others can be found in the link above
    public List<Student> findByNameAndAge(String name, int age);
    public List<Student> findByAgeBetween(int from, int to);
    public List<Student> findByAgeLessThan(int age);
    public List<Student> findByAgeGreaterThan(int age);
    public List<Student> findByNameStartingWith(String match);
    public List<Student> findByNameEndingWith(String match);
    public List<Student> findByNameContaining(String match);
    public List<Student> findByNameNotLike(String match);
    public List<Student> findAllByOrderByAgeDesc();

    // derived query - It is default under @Query. class and feild names have to be used instead of table name and column name
    @Query(value = "select u from Student u where name like ?1% and age = ?2") // Note: don't put single inverted commas('') after like
    public List<Student> getStudentWithStartingNameAndAgeDerived(String match, int age);

    // native query - It has to be provided, it is like normal sql query, hence the name native
    @Query(value = "select * from student where subject_stream = ?1", nativeQuery = true)
    public List<Student> getStudentByStream(String subjectStream);



    @Modifying // required for modifying the data, or else will consider it as read data
    @Transactional // if we don't provide this and use @modifying alone , this will throw transaction required exception.
    @Query(value = "update student set age = ?1 where id = ?2", nativeQuery = true)
    public void updateAgeById(int age, int id);
  }
  ```
* Many useful implementations are already provided by JPA, we just need to use proper method name to utilize it. See the method name's pattern carefully.
* Besides these methods we have derived query and native query too. definations of them are provided in code's comment.
* @Modifying - This annotation is required if we are modifying the data. - Update / Delete
* @Transactional - This annotation is required if we want to persist the changes in database, other wise the data will only be persisted by persistent context and not get flushed into database.
* Just autowired this interface in your service layer and we are good to go for utilizing JPARepository's methods.
* To save an object(row) in database, use - jpaObject.save(object). If we want to bulk insert the rows (list of objects) use saveAll(objectList);
* @OneToMany relationship
  - Lets take an example of Student and Book class.
  - Each Student can have list of book, and one book can be owned by a student.
  - We want that in database, Book table should contain a column with student_id which is foreign key referencing Student table's id. Let see code - 
  - Student class @OneToMany
    ``` java
    @OneToMany(mappedBy = "student", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
      List<Book> bookList;
    ```
    We can see that each student can have list of books. Since this annotation contains mappedBy field, this is non-owning side of relationhip. This means
    student table will not contain any actual column corresponding to this feild. Lets see the params - 
    1. mappedBy states that there is a variable/object with name student in another class which is annotated with @ManyToOne 
    2. fetchType = Lazy => When Student object is loaded by JPA, lets say we did findById(), bookList will not be loaded unless we call getter method for
       this variable. fetchType = Eager => bookList gets loaded when we do findById() or similar query. Lazy approach is generally preferred and it is default
       on @OneToMany side. Behind the scenes, bookList is loaded by JPA using JOIN operation.
    3. cascadeType = All => It means if a student row is deleted, the book's row that refers to that studentId will also get delelted.
  - Book class @ManyToOne
    ``` java
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "studentId")
    Student student;
    ```
    Here we have declared student as a field with Join column name = studentId, this is owning side of relationship and a column with name student_id gets
    created in Book Table in database. Also this field student_id acts as foreign key which references the primary key of Student table, i.e. Id.
    By defailt the fetch type at @ManyToOne side is eager, since its just a single row of student to be mapped here.
