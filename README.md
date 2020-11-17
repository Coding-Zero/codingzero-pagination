Having pagination arguments in your APIs make them looks very wordy and not friendly to use. So, I built utilities-pagination which provides a simple way to help you remove these arguments from you APIs.

So far, utilities-pagination supports two paging styles -- offset based paging, and cursor based paging, but, you can also expend it to have your own paging fashion.

# Concept
Let's step back a little bit to think about pagination. We want to do pagination is all about the returned data from API, but API self.

So, utilities-pagination is built based on the idea to find a way to move pagination from API to the returned data. **Delegate design pattern** is the whole key concept applied to get this job done.

# Getting started

### PaginatedResult Usage
**PaginatedResult\<T, P\>**
T - return data type
P - paging type. OffsetPaging and CursorPaging are provided.

Let's say you wanna to query students based on age, then you can declare the method as following:

```java
PaginatedResult<List<Student>, OffsetPaging> getStudentsByAge(int age);
```

PaginatedResult is composited with PaginatedResultDelegate, ResultCountDelegate, PagingDelegate and query arguments.

**PaginatedResultDelegate** - for fetching data based on query arguments and paging arguments.

**ResultCountDelegate (Optional)** - for count the total number of the data can be returned eventually. If you don't provide, then, PaginatedResult#getTotalCount() will throw UnsupportedOperationException.

**PagingDelegate** - for calculating next page.

**Query Arguments** - Arguments passed in from the API you declared, for the above example, it's "age"

### PaginatedResultDelegate Usage
OffsetPaginatedResult is part of PaginatedResult to be returned.

There's one method must to be implemented.

```java
T fetchResult(ResultFetchRequest<? extends P> request);
```

T - return data type, List<Student> at above example
P - paging type, OffsetPaging at above example.
ResultFetchRequest - contains paging arguments, query parameters, 'age' at above example.

The full example as following:
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

### OffsetPaginatedResult & CursorPaginatedResult
There are two sub types of PaginatedResult, which will help you make the API more clean.

For example, switch to OffsetPaginatedResult at the above example:
```java
OffsetPaginatedResult<List<Student>> getStudentsByAge(int age);
```

## Accessing data
Now, let's access all students with the given age page by page
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

If you implement PagingDelegate interface, then you can another option to access all Students page by page.
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
