<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Book Details</title>
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@4.3.1/dist/css/bootstrap.min.css" integrity="sha384-ggOyR0iXCbMQv3Xipma34MD+dH/1fQ784/j6cY/iJTQUOhcWr7x9JvoRxT2MZw1T" crossorigin="anonymous">
</head>
<body>

    <div class="card d-flex justify-content-center" style="width: 18rem;">
      <div class="card-body">
        <h5 class="card-title">${book.title}</h5>
        <p class="card-text">written by ${book.author}</p>
        <a href="/books" class="btn btn-primary">Go back</a>
      </div>
    </div>
</body>
</html>
