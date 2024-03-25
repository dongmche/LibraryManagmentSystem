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
      <a class="navbar-brand" href="#">Admin</a>
      <button class="navbar-toggler" type="button" data-toggle="collapse" data-target="#navbarSupportedContent" aria-controls="navbarSupportedContent" aria-expanded="false" aria-label="Toggle navigation">
        <span class="navbar-toggler-icon"></span>
      </button>

      <div class="collapse navbar-collapse" id="navbarSupportedContent">
        <ul class="navbar-nav mr-auto">
          <li class="nav-item active">
            <a class="nav-link" href="/root/users">Home <span class="sr-only">(current)</span></a>
          </li>
          <li class="nav-item">
            <a class="nav-link" href="/root/create/user">Create user</a>
          </li>
          <li class="nav-item">
            <a class="nav-link" href="/root/create/book">add book</a>
          </li>
          <li class="nav-item active">
                <a class="nav-link" href="/root/overdue/books">overdue books <span class="sr-only">(current)</span></a>
          </li>
          <li class="nav-item active">
                                        <a class="nav-link" href="/logout">logout <span class="sr-only">(current)</span></a>
           </li>
        </ul>
        <form action="/root/search/user" method="get" class="form-inline my-2 my-lg-0" >
          <input class="form-control mr-sm-2" name="query" type="search" placeholder="Search user" aria-label="Search">
          <button class="btn btn-outline-success my-2 my-sm-0" type="submit">Search</button>
        </form>
      </div>
    </nav>



       <table class="table table-bordered table-dark">
               <thead>
                   <tr>
                       <th scope="col">title</th>
                       <th scope="col">author</th>
                       <th scope="col">dueDate</th>
                       <th scope="col">owner</th>
                   </tr>
               </thead>
               <tbody>
                   <c:forEach var="item" items="${items}" varStatus="status">
                       <tr>
                           <td>${item.title}</td>
                           <td>${item.author}</td>
                           <td>
                              <c:choose>
                                  <c:when test="${item.dueDate == null}">
                                      available
                                  </c:when>
                                  <c:otherwise>
                                      ${item.dueDate}
                                  </c:otherwise>
                              </c:choose>
                          </td>
                           <td>
                               <c:choose>
                                   <c:when test="${item.dueDate == null}">
                                        available
                                   </c:when>
                                   <c:otherwise>
                                       <a href="/root/notify/${item.owner}/${item.ISBN}">Notify</a>
                                   </c:otherwise>
                               </c:choose>
                           </td>
                       </tr>
                   </c:forEach>
               </tbody>
           </table>



</body>
</html>