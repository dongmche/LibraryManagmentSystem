<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<title>Login Page</title>
<link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@4.3.1/dist/css/bootstrap.min.css" integrity="sha384-ggOyR0iXCbMQv3Xipma34MD+dH/1fQ784/j6cY/iJTQUOhcWr7x9JvoRxT2MZw1T" crossorigin="anonymous">
<link rel="stylesheet" href="/css/style.css">
</head>
<body>
<form method="POST" action="/root/create/user">
   ${message}
  <div class="form-group">
    <label for="exampleInputEmail1">Email address</label>
    <input type="email" name="gmail" class="form-control" id="exampleInputEmail1" aria-describedby="emailHelp" placeholder="gmail" required>
    <small id="emailHelp" class="form-text text-muted">We'll never share this email with anyone else.</small>
  </div>
   <div class="form-group">
      <label for="exampleInputEmail1">Username</label>
      <input type="text" name="username" class="form-control" id="exampleInputEmail1" aria-describedby="emailHelp" placeholder="username" required>
   </div>
  <div class="form-group">
    <label for="exampleInputPassword1">Password</label>
    <input type="password" name="password" class="form-control" id="exampleInputPassword1" placeholder="Password" required>
  </div>
  <button type="submit" class="btn btn-primary">Submit</button>
  <a href="/root/users" class="btn btn-primary">Go back</a>
</form>

</div>
</body>
</html>