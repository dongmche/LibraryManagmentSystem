<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<title>Login Page</title>
<link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@4.3.1/dist/css/bootstrap.min.css" integrity="sha384-ggOyR0iXCbMQv3Xipma34MD+dH/1fQ784/j6cY/iJTQUOhcWr7x9JvoRxT2MZw1T" crossorigin="anonymous">
<link rel="stylesheet" href="/css/style.css">
</head>
<body>
<form method="POST" action="/root/create/book">
   ${message}
  <div class="form-group">
    <label for="exampleInputEmail1">Book title</label>
    <input type="text" name="title" class="form-control" required>
  </div>
   <div class="form-group">
      <label for="exampleInputEmail1">author</label>
      <input type="text" name="author" class="form-control" required>
   </div>
  <div class="form-group">
    <label for="exampleInputPassword1">IBSN</label>
    <input type="text" name="IBSN" class="form-control" required>
  </div>
  <div class="form-group">
      <label for="exampleInputPassword1">Genre</label>
      <input type="text" name="genre" class="form-control" required>
    </div>
  <button type="submit" class="btn btn-primary">Submit</button>
  <a href="/root/users" class="btn btn-primary">Go back</a>
</form>

</div>
</body>
</html>