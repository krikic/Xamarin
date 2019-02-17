<?php

include_once 'include/functions.php';
$user = new User();
// Checking for user logged in or not
/*
if ($user->get_session())
{
   header("location:index.php");
}
*/
if ($_SERVER["REQUEST_METHOD"] == "POST") 
{

    
     
  
$currentDate = date("Y-m-d H:i:s");
                      $_POST['fechaderegistro']=$currentDate;
                      $_POST['ultimoacceso']=$currentDate;
                      $_POST['activo']='1';

    $register = $user->register_user( $_POST['username'], $_POST['password'],$_POST['name'],
     $_POST['email'],$_POST['fechaderegistro'],$_POST['ultimoacceso'],
     $_POST['activo'],$_POST['username'],$_POST['mobile'],$_POST['address1'],$_POST['address2'],
     $_POST['numtarjeta'],$_POST['CV'],$_POST['fechacaducidad']);
    
    if ($register) {
        $dir_subida = './img/avatares/';
        $fichero_subido = $dir_subida . basename($_POST['username'].".jpeg");

        if (move_uploaded_file($_FILES['avatar']['tmp_name'], $fichero_subido)) {
            // die("El fichero es válido y se subió con éxito.\n");
        } else {
            die("¡Posible ataque de subida de ficheros!\n");
        }
        // Registration Success
        echo 'Registration  successful';
    } else {
        // Registration Failed
        echo 'Registration failed. Email or Username already exits please try again';
    }
}
?>
<!DOCTYPE html>
<html>
<head>
<title>Welcome</title>
<meta charset="UTF-8"> 
<meta name='viewport' content='width=device-width,
initial-scale=no'>
<link rel="stylesheet" href="js/jqm/jquery.mobile-1.4.5.css" />
<script src="js/jquery-1.8.3.min.js"></script>
<script src="js/jqm/jquery.mobile-1.4.5.js"></script>
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
        <div data-role="page">
  <div data-role="header">
    <h1>Register</h1>
  </div>
                <br/><br/>
                <form data-ajax="false" method="POST" action="register.php"  id="register_form" name="reg" enctype="multipart/form-data">
                    <div class="head">
                        <b> I am new user !</b><br/><br/>
                    </div>

                    <label>Full Name</label><br/>
                    <input type="text" name="name"  required="true"/><br/><br/>
                    <label>Username</label><br/>
                    <input type="text" name="username"  required="true"/><br/><br/>
                    <label>Password</label><br/>
                    <input type="password" name="password" required="true"/><br/><br/>
                    <label>Email</label><br/>
                    <input type="text" name="email" id="email"  required="true"/><br/><br/>
                   
                       <!-- //fecha de registro,ultimo acceso y activo son campos automáticos//-->
                           <!-- //Avatar es cargar una imagen//-->
                       <label>Avatar </label><br/>
                    <input type="file" name="avatar" id="avatar"  required="true"/><br/><br/>
                    <label>Mobile </label><br/>
                    <input type="text" name="mobile" id="mobile"  required="true"/><br/><br/>
                    <label>Address 1 </label><br/>
                    <input type="text" name="address1" id="address1"  required="true"/><br/><br/>
                    <label>Address 2 </label><br/>
                    <input type="text" name="address2" id="address2"  required="true"/><br/><br/>
                    <label>Numero tarjeta </label><br/>
                    <input type="text" name="numtarjeta" id="numtarjeta"  required="true"/><br/><br/>
                    <label>CV </label><br/>
                    <input type="text" name="CV" id="CV"  required="true"/><br/><br/>
                     <!-- //fecha caducidad debe ser un select combobox con opciones 01/2017 hasta 01/2018 // -->
                     <label>Fecha Caducidad</label><br/>
                     <select name="fechacaducidad">
                    <option value="01/2017">01/2017</option>
                    <option value="02/2017">02/2017</option>
                    <option value="03/2017">03/2017</option>
                    <option value="04/2017">04/2017</option>
                    <option value="05/2017">05/2017</option>
                    <option value="06/2017">06/2017</option>
                    <option value="07/2017">07/2017</option>
                    <option value="08/2017">08/2017</option>
                    <option value="09/2017">09/2017</option>
                    <option value="10/2017">10/2017</option>
                    <option value="11/2017">11/2017</option>
                    <option value="12/2017">12/2017</option>
                    <option value="01/2018">01/2018</option>
                    </select>
                    </br></br>
                    <input type="submit" name="register_btn" onclick="return( submitregistration());" value="Register"/><br/><br/>
                    <label><a href="index.php">I already Registered. Login here</a></label>                    
                </form>
				
		 <div data-role="footer">
    <h1>Footer Text</h1>
  </div>
</div> 
    </body>
</html>