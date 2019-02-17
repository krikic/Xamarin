

<?php

include_once 'config.php';


class User {

    // private $db;
    
public function __construct() 
{
        $db = new DB_Class();
        // var_dump($this->db->connection);die();
}


public function register_user($username, $password,$name, $email,$fechaderegistro,$ultimoacceso,$activo,$avatar,$mobile,$address1,$address2,$numtarjeta,$CV,$fechacaducidad) 
{
        $password = md5($password);
	
        $texto = "SELECT * from users WHERE username = '$username' or email = '$email'";
        $sql = mysql_query($texto);
        $no_rows = mysql_num_rows($sql);
		        // var_dump($no_rows);die();
                        //var_dump($_FILES);die();
        if ($no_rows == 0) 
        {
        $avatar = $username.".jpeg";
        $texto2 = "INSERT INTO users(username,password,name,email,
        fechaderegistro,ultimoacceso,activo,avatar,mobile,address1,address2,
        numtarjeta,CV,fechacaducidad) values ('".$username."', '".$password."','".$name."',
        '".$email."', '".$fechaderegistro."','".$ultimoacceso."','".$activo."',
          '".$avatar."','".$mobile."','".$address1."',
        '".$address2."','".$numtarjeta."','".$CV."','".$fechacaducidad."')";
        
        $result = mysql_query($texto2) or die(mysql_error());
        return $result;
		}
		else
		{
		return FALSE;
		}
		
    }

   public function check_login($nick, $password) 
	{
       session_start();
        $password = md5($password);
        $result = mysql_query("SELECT uid from users WHERE (username = '$nick' or email='$nick') and password = '$password'");
        $user_data = mysql_fetch_array($result);
        
        $no_rows = mysql_num_rows($result);
        
        if ($no_rows == 1) 
		{
			
            $_SESSION["uid"] = $user_data['uid'];
            return TRUE;
        }
        else
        {
            session_destroy();
            return FALSE;
        }
    }

    public function get_fullname($uid) 
	{
        $result = mysql_query("SELECT username FROM users WHERE uid = $uid");
        $user_data = mysql_fetch_array($result);
        echo $user_data['username'];
    }
    
    public function get_imagen_url($uid)
    {
        $imagenUrl = "./img/avatares/";
        $result = mysql_query("SELECT avatar FROM users WHERE uid = $uid");
        $user_data = mysql_fetch_array($result);
        echo $imagenUrl.$user_data['avatar'];
    }
  

    public function get_session() 
    {
        // session_start();
        // var_dump($_SESSION);die();
        return $_SESSION["uid"];
    }

    public function user_logout() {
        $_SESSION["uid"] = FALSE;
        session_destroy();
    }

}

?>
