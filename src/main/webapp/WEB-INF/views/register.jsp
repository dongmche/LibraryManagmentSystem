<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<title>Registration Page</title>
<link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@4.3.1/dist/css/bootstrap.min.css" integrity="sha384-ggOyR0iXCbMQv3Xipma34MD+dH/1fQ784/j6cY/iJTQUOhcWr7x9JvoRxT2MZw1T" crossorigin="anonymous">
<link rel="stylesheet" href="css/style.css">
</head>
<body>

<div class="login-container">
    <h2>Register</h2>
    <form action="/register" method="post" id="registrationForm">
            <c:if test="${not empty message}">
                       <div class="alert-message">${message}</div>
               </c:if>
        <div class="form-group">
            <label for="username">Username:</label>
            <input type="text" id="username" name="username" required>
        </div>
        <div class="form-group">
            <label for="email">Email:</label>
            <input type="email" id="email" name="email" required>
        </div>
        <div class="form-group">
            <label for="password">Password:</label>
            <input type="password" id="password" name="password" required>
        </div>
        <div class="form-group">
            <label for="confirm-password">Confirm Password:</label>
            <input type="password" id="confirm-password" name="confirm-password" required>
        </div>
        <div class="form-group">
            <button type="submit">Register</button>
        </div>
    </form>
    <div class="registration-link">
        Already have an account? <a href="/login">Login here!</a>
    </div>
</div>


    <script>
        document.addEventListener("DOMContentLoaded", function() {
            // When the DOM is fully loaded, add an event listener to the form
            document.getElementById("registrationForm").addEventListener("submit", function(event) {
                var password = document.getElementById("password").value;
                var confirmPassword = document.getElementById("confirm-password").value;

                // Check if passwords match
                if (password !== confirmPassword) {
                    // If they don't match, prevent the form from being submitted
                    event.preventDefault();
                    // Alert the user or display the error message in another way
                    alert("Passwords do not match.");
                }
            });
        });
    </script>
</body>
</html>
