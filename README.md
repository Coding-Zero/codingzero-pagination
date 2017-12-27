The only thing utilities-pagination try to do is that provide your functions a way to return values, which can be accessed by client page by page, without adding new arguments.

utilities-pagination supports two types of paging fashion -- offset based, and Cursor based, but, you can also create your own paging fashion by implement *ResultPage\<S\>* interface.

#Getting started

##Adding pagination to a function

You only need to do two things:

1. Makes the return value as PaginatedResult\<T\> type.

    ```java
    public interface StudentDAO {
        
        PaginatedResult<List<Student>> selectBySchoolName(String name);
        
    }
    ```

2. Provides a delegate to the returned value.

    **Offset based paging fashion**
    ```java
    public class StudentDAOImpl {
        
        public PaginatedResult<List<Student>> selectBySchoolId(String schoolId) {
            return new PaginatedResult<>(new PaginatedResultDelegate<List<Student>>() {
                
                @Override
                public List<Student> fetchResult(ResultFetchRequest resultFetchRequest) {
                    String schoolId = resultFetchRequest.getArguments()[0];
                    OffsetBasedResultPage page = (OffsetBasedResultPage) resultFetchRequest.getPage();
                    
                    StringBuilder sql = new StringBuilder();
                    sql.append("SELECT * FROM students WHERE school_id = ? ");                    
                    sql.append("LIMIT " + (page.getStart -1) + ", " + page.getSize());
                                        
                    Connection conn = getConnection();
                    PreparedStatement stmt = null;
                    ResultSet rs = null;
                    try {                    
                        stmt = conn.prepareCall(sql.toString());
                        stmt.setString(1, schoolId);
                        rs = stmt.executeQuery();
                        return toStudentList(rs, request.getPage());
                    } catch (SQLException | IOException e) {
                        throw new RuntimeException(e);
                    } finally {
                        closeResultSet(rs);
                        closePreparedStatement(stmt);
                        closeConnection(conn);
                    }
                }
            },
            schoolId); //adding school id as an argument.
        }
        
    }
    ```
 
    **Cursor based paging fashion**
    ```java
    public class StudentDAOImpl {
        
        public PaginatedResult<List<Student>> selectBySchoolId(String schoolId) {
            return new PaginatedResult<>(new PaginatedResultDelegate<List<Student>>() {
             
                @Override
                public ResultPage nextPage(ResultFetchRequest currentRequest, List<Student> currentResult) {
                    CursorBasedResultPage currentPage = (CursorBasedResultPage) currentRequest.getPage();
                    String nextCursor = currentResult.get(currentResult.size() - 1).getId();  
                    return new CursorBasedResultPage(nextCursor, currentPage.getSize());    
                }
                
                @Override
                public List<Student> fetchResult(ResultFetchRequest resultFetchRequest) {
                    String schoolId = resultFetchRequest.getArguments()[0];
                    CursorBasedResultPage page = (CursorBasedResultPage) resultFetchRequest.getPage();
                    
                    StringBuilder sql = new StringBuilder();
                    sql.append("SELECT * FROM students WHERE school_id = ? AND key > ? ORDER BY key ASC");                    
                    sql.append("LIMIT " + page.getSize());
                                        
                    Connection conn = getConnection();
                    PreparedStatement stmt;
                    ResultSet rs;
                    try {                    
                        stmt = conn.prepareCall(sql.toString());
                        stmt.setString(1, schoolId);
                        stmt.setString(2, page.getStart());
                        rs = stmt.executeQuery();
                        return toStudentList(rs, request.getPage());
                    } catch (SQLException | IOException e) {
                        throw new RuntimeException(e);
                    } finally {
                        closeResultSet(rs);
                        closePreparedStatement(stmt);
                        closeConnection(conn);
                    }
                }
            },
            schoolId); //adding school id as an argument.
        }
        
    }
    ```


##Mapping returned values

Sometime, we need to convert the values returned from one layer into another structure for another layer, for example, we need to convert returned values from access layer into DTO (Data transfer object) for UI layer.

The easy way to do the conversion is that implements the *PaginatedResultMapper\<T, S\>* abstract class. The following example shows you that how to convert *Student* into *StudentDTO* for UI layer:

```java
public class StudentRepository {
    
    PaginatedResult<List<StudentDTO>> findBySchool(School school) {
        PaginatedResult<List<Student>> result = studentDAO.selectBySchoolId(school.getId());
        return new PaginatedResult<>(new PaginatedResultMapper<List<StudentDTO>, List<Student>>() {
            
            @Override
            protected List<StudentDTO> toResult(List<Student> source, Object[] arguments) {
                List<StudentDTO> entities = new ArrayList<>(source.size());
                for (Student student: source) {
                    entities.add(convertStudentToStudentDTO(student));
                }
                return Collections.unmodifiableList(entities);
            }
            
        }, 
        result); //must be the first argument.
    }
    
} 
```

##Accessing values

```java
public class Client {
    
    public static void main(String[] args) {        
        PaginatedResult<List<StudentDTO>> result = studentRepository.findBySchool(new School("1"));
        
        //offset based paging.
        result = result.start(new OffsetBasedResultPage(1, 25)); 
        
        //cursor based paging.
        result = result.start(new CursorsetBasedResultPage(null, 25)); 
        
        //loop all student
        List<StudentDTO> students = result.start();
        do  {
            print(students);
            result = result.next(); //next page.
        } while (students.size() > 0);
    }
    
}
```



