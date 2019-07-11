<!DOCTYPE html>
<html>
<head>
    <title>Login</title>
</head>

<body>
    <h1>Please input your login credentials</h1>

    <form name="loginForm" action="login.do" method="POST" onsubmit="return validateForm()">
        <!-- fields -->
		<input name="username" type="text" placeholder="Username" />
		<input name="password" type="password" placeholder="Password"/>
		
		<!-- button -->
		<input type="submit" value="SUBMIT" />
    </form>

    <script>
        function validateForm(){
            var username=document.loginForm.username.value;
            var password=document.loginForm.password.value;

            if(username==null||username==""){
                alert("Username field cannot be blank!");
                return false;
            }else if(password==null||password==""){
                alert("Password field cannot be blank!");
                return false;
            }
        }
    </script>
</body>
</html>