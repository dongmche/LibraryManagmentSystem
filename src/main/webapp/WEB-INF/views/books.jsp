<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<title>books Page</title>
<link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@4.3.1/dist/css/bootstrap.min.css" integrity="sha384-ggOyR0iXCbMQv3Xipma34MD+dH/1fQ784/j6cY/iJTQUOhcWr7x9JvoRxT2MZw1T" crossorigin="anonymous">
<link rel="stylesheet" href="/css/books.css">
</head>
<body style="background-color: #333;">
    <nav class="navbar navbar-expand-lg navbar-light bg-light">
      <a class="navbar-brand" href="#">Navbar</a>
      <button class="navbar-toggler" type="button" data-toggle="collapse" data-target="#navbarSupportedContent" aria-controls="navbarSupportedContent" aria-expanded="false" aria-label="Toggle navigation">
        <span class="navbar-toggler-icon"></span>
      </button>

      <div class="collapse navbar-collapse" id="navbarSupportedContent">
        <ul class="navbar-nav mr-auto">
          <li class="nav-item active">
            <a class="nav-link" href="/books">Home <span class="sr-only">(current)</span></a>
          </li>
          <li class="nav-item">
            <a class="nav-link disabled" href="#">Disabled</a>
          </li>
          <li class="nav-item active">
                                        <a class="nav-link" href="/logout">logout <span class="sr-only">(current)</span></a>
           </li>
        </ul>
        <form action="/search" method="get" class="form-inline my-2 my-lg-0" >
          <input class="form-control mr-sm-2" name="query" type="search" placeholder="Search book or author" aria-label="Search">
          <button class="btn btn-outline-success my-2 my-sm-0" type="submit">Search</button>
        </form>
      </div>
    </nav>


    <table class="table table-bordered table-dark">
      <thead>
        <tr>
          <th scope="col">#</th>
          <th scope="col">Title</th>
          <th scope="col">Author</th>
          <th scope="col">ISBN</th>
          <th scope="col">availability</th>
        </tr>
      </thead>
      <tbody id="tableBody">
        <c:forEach var="book" items="${books}" varStatus="status">
          <tr>
            <th scope="row">${status.index + 1}</th>
            <td><a href="/book/${book.ISBN}"> ${book.title}</td>
            <td>${book.author}</td>
            <td>${book.ISBN}</td>
            <td>
            <c:choose>
                <c:when test="${book.owner == sessionScope.userId}">
                    <form action="/book/return/${book.ISBN}" method="POST">
                                                            <button type="submit" class="btn btn-link">return</button>
                                                        </form>
                </c:when>
                <c:when test="${book.dueDate == null and book.owner != sessionScope.userId}">
                    <form action="/book/borrow/${book.ISBN}" method="POST">
                        <button type="submit" class="btn btn-link">borrow</button>
                    </form>
                </c:when>
                <c:otherwise>
                    <p>not available until ${book.dueDate}</p>
                </c:otherwise>
            </c:choose>
           </td>
          </tr>
        </c:forEach>
      </tbody>
    </table>


</body>
</html>
