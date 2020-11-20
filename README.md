Having pagination arguments in your APIs make them looks very wordy and not friendly to use. So, I built utilities-pagination which provides a simple way to help you remove these arguments from you APIs.

So far, codingzero-pagination supports two paging styles -- offset based paging, and cursor based paging, but, you can also expend it to have your own paging fashion.

# Concept
utilities-pagination is built based on the idea to find a way to move pagination from API's signature to its returning data. **Delegate design pattern** is the whole key concept applied to get this job done.

# Getting started

### Maven Configuration
Add the dependence to your pom.xml between \<dependences\>\</dependences\>.
```
<dependency>
    <groupId>com.codingzero.utilities</groupId>
    <artifactId>codingzero-pagination</artifactId>
    <version>CHANGE_TO_THE_LATEST_VERSION</version>
</dependency>
```
### Basic Usage
Let's use an example to explain how to declare a method and implement it.

Firstly, declare a method with wrapping up the returning data List\<Student\> with PaginatedResult.

```java
PaginatedResult<List<Student>, OffsetPaging> getStudentsByAge(int age);
```

Then, implement this method.
```java
public List<Student> getStudentsByAge(int age) {
    return new OffsetPaginatedResult<>(
            request -> {
                int age = request.getArgument(0);
                OffsetPaging paging = request.getPage();
                List<Student> students = new ArrayList<>(paging.getSize());
                //JDB query with paging args and convert result set to Student list
                 return students;
            },
            age //<-- pass the age parameter
    );
}
```
Now, we can access these students page by page.

```java
OffsetPaginatedResult<List<Student>> result = getStudentsByAge(16);

        //initial paging, first page
        result = result.start(new OffsetPaging(1, 25));

        //loop all student
        List<StudentDTO> students;
        do  {
            students = result.getData();
            //do something...
            result = result.next(); //next page.
        } while (students.size() > 0);
```

You can also have another way to flip pages, if PaginatedResult#isTotalCountAvailable() returns 'true'.

```java
OffsetPaginatedResult<List<Student>> result = getStudentsByAge(16);

        //get total number of data.
        long total = result.getTotalCount();

        //initial paging, first page
        result = result.start(new OffsetPaging(1, 25));
        while (result.getCurrentPage().getStart() <= total) {
            students = result.getData();
            //do something...
            result = result.next();
        }
```

### PaginatedResult
**PaginatedResult\<T, P\>**
T - return data type
P - paging type. OffsetPaging and CursorPaging are provided.

PaginatedResult is composited with **PaginatedResultDelegate**, **ResultCountDelegate**, **PagingDelegate** and method arguments (eg: 'age' in above example).

### OffsetPaginatedResult & CursorPaginatedResult
There are two sub types of PaginatedResult, which will help you make the API more clean.

For example, switch to OffsetPaginatedResult at the above example:
```java
OffsetPaginatedResult<List<Student>> getStudentsByAge(int age);
```

### PaginatedResultDelegate
OffsetPaginatedResult is part of PaginatedResult to be returned. It's used for fetching data based on method's arguments and paging arguments.

T - return data type, List<Student> at above example
P - paging type, OffsetPaging at above example.
ResultFetchRequest - contains paging arguments, query parameters, 'age' at above example.

### ResultCountDelegate (Optional)
For counting the total number of the data can be returned eventually. If you don't provided this as create PaginatedResult object, then, PaginatedResult#getTotalCount() will throw UnsupportedOperationException.

### PagingDelegate
So far, this interface is only used for calculating next page.