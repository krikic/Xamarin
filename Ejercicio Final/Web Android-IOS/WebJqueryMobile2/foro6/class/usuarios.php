<?php

 require_once 'conexion.php';

class Usuarios  {

    public $mysql;
   


    public function __construct() {
        
        $this->mysql = new DB_Class();
     
        }
    
    

    //*****************************************************************
    // LISTAMOS USUARIOS
    //*****************************************************************
    public function usuarios() {

        $texto= "SELECT * FROM users";
      
        $resultado = mysql_query($texto);

        $data= array();
      
        while ($fila = mysql_fetch_assoc($resultado) )  {
           array_push($data, $fila);
        }
        return $data;
    }
    //*****************************************************************
    // TEMAS POR USUARIO USUARIOS
    //*****************************************************************
    public function usuariotemas($id) {
         $texto="SELECT
            foro_temas.id_tema,
            foro_temas.id_foro,
            foro_temas.id_subforo,
            foro_temas.titulo,
            foro_temas.contenido,
            foro_temas.fecha,
            foro_temas.id_usuario,
            foro_temas.activo,
            foro_temas.hits
            FROM
            foro_temas
            WHERE
            foro_temas.id_usuario = $id";
           $data= array();
           
        $resultado = mysql_query($texto);
        if($resultado!==false){
        while ($fila = mysql_fetch_assoc($resultado) )  {
           array_push($data, $fila);
        }
        }
        
        return $data;
    }

    //*****************************************************************
    // USUARIO POR ID
    //*****************************************************************
    public function usuariosid($id) {
        
        $texto="select * from users where uid = $id";
        $resultado=mysql_query($texto);
        $data= array();
        while ($fila = mysql_fetch_assoc($resultado) )  {
           array_push($data, $fila);
        }
        
        return $data;
    }

    //*****************************************************************
    // NUEVO USUARIO
    //*****************************************************************
    public function nuevousuario() {
        

        $pass = sha1($_POST["password"]);
        $tipo = 2;
        $address1 = $_POST['address1'];
        $address2 = $_POST['address2'];
        $activo = 1;
        $avatar = "default.jpg";
        $mobile = $_POST['mobile'];

        $username = $_POST['username'];

        $numtarjeta = $_POST['numtarjeta'];

        $cv = $_POST['CV'];
        $fechacaducidad = $_POST['fechacaducidad'];

        $name = $_POST['name'];
        $email = $_POST['email'];
        $fechaderegistro = $_POST['fechaderegistro'];
        // VALIDAMOS SI EXISTE EL NICK
        
        $texto="select username from users where username = '$username'";
        
        $resultado = mysql_query($texto);
          $registros = mysql_num_rows($resultado);

        

        if ($registros == 0) {
            $texto2="INSERT INTO users(username, password, name, email, tipo,fechaderegistro, 
            ultimoacceso, activo, avatar, mobile,address1,address2,numtarjeta,CV,fechacaducidad) 
                VALUES('$username','$pass', '$name', '$email', $tipo, '$fechaderegistro',now(), now(), $activo, '$avatar',
                 '$mobile',  '$address1','$address2','$numtarjeta','$cv','$fechacaducidad')";
            $resultado2 = mysql_query($texto2) or die(mysql_error());
          
            // creamos las sesiones para que automaticamente puedas comentar o publicar
            $_SESSION["uid"] = $id;
            $_SESSION["name"] = $name;
            $_SESSION["tipo"] = $tipo;

            echo "<script type='text/javascript'>
            window.location='index.php';
            </script>";
        } else {
            echo 'El usuario no existe';
        }
        return $resultado2;
        
    }

    //*****************************************************************
    // LISTAMOS LOS TEMAS PAGINADOS DEL FORO
    //*****************************************************************
    public function update($id,$name,$email) {
        
        
    $texto = "UPDATE users SET name='$name',email='$email' WHERE uid = '$id'";
        

      
          $resultado = mysql_query($texto) or die(mysql_error());
        
        
        echo "<script type='text/javascript'>
			window.location='perfil.php';
			</script>";
        return $resultado;
    }
	//*****************************************************************
    // ELIMINAR - DE UN USUARIO DESTRUYE TODA LA SESSION AL ELIMINARSE
    //*****************************************************************
	public function del($id, $dir) {
            $texto= "DELETE FROM users WHERE uid = $id";
         $resultado = mysql_query($texto) or die(mysql_error());
        
        if($dir == 0){
            echo "<script type='text/javascript'>window.location='usuarios.php';</script>";
        }else{
            echo "<script type='text/javascript'>window.location='logout.php';</script>";
        }
        return $resultado;
    }
    //*****************************************************************
    // LISTAMOS LOS TEMAS PAGINADOS DEL FORO
    //*****************************************************************
    public function updateavatar($id) {
        
        $texto = "SELECT username FROM users WHERE uid='".$_SESSION["uid"]."'";
        $resultado = mysql_query($texto) or die(mysql_error());
        $fila = mysql_fetch_assoc($resultado);
        $dir_subida = '../img/avatares/';
        $fichero_subido = $dir_subida . basename($fila['username'].".jpeg");
        
        $result1 = unlink($fichero_subido);
        $result2 = move_uploaded_file($_FILES['foto']['tmp_name'], $fichero_subido);
        //$texto="UPDATE users SET avatar = '$imagen' WHERE uid = $id";
        // $resultado = mysql_query($texto) or die(mysql_error());
        
        
        // Cambaimos el tamañao de todos los avatares subidos
        /*
        include_once('class/thumb.php');
        $mythumb = new thumb();
        $mythumb->loadImage('../img/avatares/'.$imagen);
        $mythumb->resize(70, 'width');
        $mythumb->save('../img/avatares/'.$imagen, 100);
        */
        
        echo "<script type='text/javascript'>
			window.location='perfil.php?uid=$id';
			</script>";
        
         return $resultado;
    }
    
   
}