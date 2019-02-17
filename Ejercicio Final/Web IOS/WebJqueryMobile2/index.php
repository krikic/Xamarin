<?php
session_start();
include_once 'include/functions.php';
$user = new User();
/*
if ($user->get_session())
{
   header("location:main.php");
}*/
$_SESSION["mensaje_login_incorrecto"] ="";

if ($_SERVER["REQUEST_METHOD"] == "POST") {    
    $login = $user->check_login($_POST['nick'], $_POST['password']);
    // var_dump($login);die();
    if ($login) {
        // Registration Success
       header("location:main.php");
       
    } else {
        // Registration Failed
       // ech$logino 'Username / password wrong';
$_SESSION["mensaje_login_incorrecto"] = 'Username / password wrong';
    }
}
?>
<!DOCTYPE html>
<html>
<head>
<title>Welcome</title>
<meta name='viewport' content='width=device-width' />
<meta name='viewport' content='width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=no' />
<link rel="stylesheet" href="js/jqm/jquery.mobile-1.4.5.css" />
<script src="js/jquery-1.8.3.min.js"></script>
<script src="js/jqm/jquery.mobile-1.4.5.js"></script>

  <link rel="stylesheet" href="css/style.css">
<script>

$(document).ready(function(){
  $(window).bind("orientationchange", function(event){    
    if (event.orientation)    
      $("#orientacion").html(event.orientation);
  });
});
$(window).trigger("orientationchange");
</script>
</head>
<body>
  <div data-role="page" id="pageindex">
    <div data-role="header" data-position="fixed">
      <h1>Login</h1>
    </div>
    <div data-role="content">
<?php
	echo $_SESSION["mensaje_login_incorrecto"];
	?>
        <form data-ajax="false" method="POST" action=""  id="login_form" name="login">
        <label>Username</label><br/>
        <input type="text" name="nick"  required="true"/><br/>         <br/>
        <label>Password</label><br/>
        <input type="password" name="password" id="password" required="true"/><br/><br/>
        <input type="hidden" name="flag" value="login"/>
        <input type="submit" name="login_btn" value="Sign In"/><br/><br/>
      </form>
			<label><a href="register.php">Register new user</a></label>
    </div>
  <div data-role="footer" data-position="fixed" >
    <h1>Footer Text</h1>
  </div>
</div> 
</body>
</html>
