# Book Finder
![book_finder](https://user-images.githubusercontent.com/39121338/154841108-938480fe-3ad7-4676-bd0d-dec4aed65a85.gif)

# Architecture
MVVM (Mode-View-ViewModel)

# Libraries
`Okhttp`
`Retrofit`
`RxJava`
`Glide`

# How to use it?
Enter keywords for the book you are looking for.
And look result. 

If there were book what you are lookging for, just click it.
Then, you can see all information of book.

* NOT operator : if you search 'java-kotlin', search results are for 'java' excluding 'kotlin'.
* OR operator : if you search 'java|kotlin', search result is a sum of the results of 'java' and 'kotlin'.

Operator supports only 2 keywords.

# Data Source
[IT Book store API](https://api.itbook.store/)

# Problem of API
The API of "IT Book Store" had some problems.
That is, From page 101, the result of page 1 is displayed.
Let's see Json 

[GET] https://api.itbook.store/1.0/search/java/1
```
{"error":"0","total":"1232","page":"1","books":[{"title":"Effective JavaScript","subtitle":"68 Specific Ways to Harness the Power of JavaScript","isbn13":"9780321812186","price":"$21.99","image":"https://itbook.store/img/books/9780321812186.png","url":"https://itbook.store/books/9780321812186"},{"title":"Learning JavaScript","subtitle":"A Hands-On Guide to the Fundamentals of Modern JavaScript","isbn13":"9780321832740","price":"$8.99","image":"https://itbook.store/img/books/9780321832740.png","url":"https://itbook.store/books/9780321832740"},{"title":"Java Cookbook, 2nd Edition","subtitle":"Solutions and Examples for Java Developers","isbn13":"9780596007010","price":"$3.89","image":"https://itbook.store/img/books/9780596007010.png","url":"https://itbook.store/books/9780596007010"},{"title":"JavaScript: The Good Parts","subtitle":"Unearthing the Excellence in JavaScript","isbn13":"9780596517748","price":"$15.99","image":"https://itbook.store/img/books/9780596517748.png","url":"https://itbook.store/books/9780596517748"},{"title":"Head First JavaScript","subtitle":"A Learner's Companion to JavaScript","isbn13":"9780596527747","price":"$7.72","image":"https://itbook.store/img/books/9780596527747.png","url":"https://itbook.store/books/9780596527747"},{"title":"Sams Teach Yourself Java in 24 Hours, 6th Edition","subtitle":"Covering Java 7 and Android","isbn13":"9780672335754","price":"$5.71","image":"https://itbook.store/img/books/9780672335754.png","url":"https://itbook.store/books/9780672335754"},{"title":"Sams Teach Yourself Java in 24 Hours, 8th Edition","subtitle":"Covering Java 9","isbn13":"9780672337949","price":"$17.79","image":"https://itbook.store/img/books/9780672337949.png","url":"https://itbook.store/books/9780672337949"},{"title":"Pro Java ME Apps","subtitle":"Building Commercial Quality Java ME Apps","isbn13":"9781430233275","price":"$36.22","image":"https://itbook.store/img/books/9781430233275.png","url":"https://itbook.store/books/9781430233275"},{"title":"Expert Oracle and Java Security","subtitle":"Programming Secure Oracle Database Applications With Java","isbn13":"9781430238317","price":"$46.78","image":"https://itbook.store/img/books/9781430238317.png","url":"https://itbook.store/books/9781430238317"},{"title":"JavaScript Creativity","subtitle":"Exploring the Modern Capabilities of JavaScript and HTML5","isbn13":"9781430259442","price":"$30.64","image":"https://itbook.store/img/books/9781430259442.png","url":"https://itbook.store/books/97814
```

[GET] https://api.itbook.store/1.0/search/java/100
```
{"error":"0","total":"1232","page":"100","books":[{"title":"Seriously Good Software","subtitle":"Code that works, survives, and wins","isbn13":"9781617296291","price":"$31.99","image":"https://itbook.store/img/books/9781617296291.png","url":"https://itbook.store/books/9781617296291"},{"title":"Object Design Style Guide","subtitle":"","isbn13":"9781617296857","price":"$31.99","image":"https://itbook.store/img/books/9781617296857.png","url":"https://itbook.store/books/9781617296857"},{"title":"Spring Microservices in Action, 2nd Edition","subtitle":"","isbn13":"9781617296956","price":"$45.86","image":"https://itbook.store/img/books/9781617296956.png","url":"https://itbook.store/books/9781617296956"},{"title":"React Hooks in Action","subtitle":"With Suspense and Concurrent Mode","isbn13":"9781617297632","price":"$39.99","image":"https://itbook.store/img/books/9781617297632.png","url":"https://itbook.store/books/9781617297632"},{"title":"Svelte and Sapper in Action","subtitle":"","isbn13":"9781617297946","price":"$39.99","image":"https://itbook.store/img/books/9781617297946.png","url":"https://itbook.store/books/9781617297946"},{"title":"Code like a Pro in C#","subtitle":"","isbn13":"9781617298028","price":"$58.30","image":"https://itbook.store/img/books/9781617298028.png","url":"https://itbook.store/books/9781617298028"},{"title":"Spring Start Here","subtitle":"Learn what you need and learn it well","isbn13":"9781617298691","price":"$49.99","image":"https://itbook.store/img/books/9781617298691.png","url":"https://itbook.store/books/9781617298691"},{"title":"Ionic in Action","subtitle":"Hybrid Mobile Apps with Ionic and AngularJS","isbn13":"9781633430082","price":"$35.99","image":"https://itbook.store/img/books/9781633430082.png","url":"https://itbook.store/books/9781633430082"},{"title":"Reactive Web Applications","subtitle":"Covers Play, Akka, and Reactive Streams","isbn13":"9781633430099","price":"$26.23","image":"https://itbook.store/img/books/9781633430099.png","url":"https://itbook.store/books/9781633430099"},{"title":"Vue.js Succinctly","subtitle":"","isbn13":"9781642002003","price":"$0.00","image":"https://itbook.store/img/books/9781642002003.png","url":"https://itbook.store/books/9781642002003"}]}
```

[GET] https://api.itbook.store/1.0/search/java/101
```
{"error":"0","total":"1232","page":"1","books":[{"title":"Effective JavaScript","subtitle":"68 Specific Ways to Harness the Power of JavaScript","isbn13":"9780321812186","price":"$21.99","image":"https://itbook.store/img/books/9780321812186.png","url":"https://itbook.store/books/9780321812186"},{"title":"Learning JavaScript","subtitle":"A Hands-On Guide to the Fundamentals of Modern JavaScript","isbn13":"9780321832740","price":"$8.99","image":"https://itbook.store/img/books/9780321832740.png","url":"https://itbook.store/books/9780321832740"},{"title":"Java Cookbook, 2nd Edition","subtitle":"Solutions and Examples for Java Developers","isbn13":"9780596007010","price":"$3.89","image":"https://itbook.store/img/books/9780596007010.png","url":"https://itbook.store/books/9780596007010"},{"title":"JavaScript: The Good Parts","subtitle":"Unearthing the Excellence in JavaScript","isbn13":"9780596517748","price":"$15.99","image":"https://itbook.store/img/books/9780596517748.png","url":"https://itbook.store/books/9780596517748"},{"title":"Head First JavaScript","subtitle":"A Learner's Companion to JavaScript","isbn13":"9780596527747","price":"$7.72","image":"https://itbook.store/img/books/9780596527747.png","url":"https://itbook.store/books/9780596527747"},{"title":"Sams Teach Yourself Java in 24 Hours, 6th Edition","subtitle":"Covering Java 7 and Android","isbn13":"9780672335754","price":"$5.71","image":"https://itbook.store/img/books/9780672335754.png","url":"https://itbook.store/books/9780672335754"},{"title":"Sams Teach Yourself Java in 24 Hours, 8th Edition","subtitle":"Covering Java 9","isbn13":"9780672337949","price":"$17.79","image":"https://itbook.store/img/books/9780672337949.png","url":"https://itbook.store/books/9780672337949"},{"title":"Pro Java ME Apps","subtitle":"Building Commercial Quality Java ME Apps","isbn13":"9781430233275","price":"$36.22","image":"https://itbook.store/img/books/9781430233275.png","url":"https://itbook.store/books/9781430233275"},{"title":"Expert Oracle and Java Security","subtitle":"Programming Secure Oracle Database Applications With Java","isbn13":"9781430238317","price":"$46.78","image":"https://itbook.store/img/books/9781430238317.png","url":"https://itbook.store/books/9781430238317"},{"title":"JavaScript Creativity","subtitle":"Exploring the Modern Capabilities of JavaScript and HTML5","isbn13":"9781430259442","price":"$30.64","image":"https://itbook.store/img/books/9781430259442.png","url":"https://itbook.store/books/9781430259442"}]}
```

Because of the above problem, the client cannot know the end of the search result after page 101.
So, I add this code.

```
...
if (currentPage > 100) return 
...
```
