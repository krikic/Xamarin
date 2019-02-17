<?php

// require_once 'conexion.php';

class Comments  {

    public $mysql;
    private $tComentarios;


    public function __construct() {
        $this->mysql = new DB_Class();
     
        }
    
    //*****************************************************************
    // ULTIMO COMENTARIO
    //*****************************************************************
    public function ultimo_comentario($id) {
        $texto = "SELECT
            comentario_foro.id_comentario,
            comentario_foro.id_tema,
            comentario_foro.comentario,
            comentario_foro.fecha,
            comentario_foro.activo,
            foro_temas.titulo,
            foro_foro.id_foro,
            users.username
            FROM
            comentario_foro
            INNER JOIN foro_temas ON foro_temas.id_tema = comentario_foro.id_tema
            INNER JOIN foro_foro ON foro_foro.id_foro = foro_temas.id_foro
            INNER JOIN users ON comentario_foro.id_usuario = users.uid
            WHERE
            foro_foro.id_foro = $id
            ORDER BY
            comentario_foro.id_comentario DESC
            limit 1";
        
            $sql = mysql_query($texto);

            $data = array();
            
            if($sql !== false){
                while($row = mysql_fetch_assoc($sql))
                {
                    array_push($data, $row);
                }	
            }
        return $data;
    }

        
    //*****************************************************************
    // LISTA LOS COMENTARIOS
    //*****************************************************************
    public function comentarios($id) {
        $texto = "SELECT
            comentario_foro.id_comentario,
            comentario_foro.id_tema,
            comentario_foro.id_usuario,
            comentario_foro.comentario,
            comentario_foro.fecha,
            comentario_foro.activo,
            users.username,
            users.avatar,
            users.fechaderegistro
            FROM
            comentario_foro
            INNER JOIN users ON comentario_foro.id_usuario = users.uid 
            WHERE id_tema = $id";
        
             $sql = mysql_query($texto);

            $data = array();
            
            if($sql !== false){
                while($row = mysql_fetch_assoc($sql))
                {
                    array_push($data, $row);
                }	
            }
        return $data;
    }


        

    //*****************************************************************
    // TOTAL DE COMENTARIOS POR TEMA
    //*****************************************************************
    public function TotalComentarios($tema) {
        
        
         $texto = "select count(*) as total from comentario_foro where id_tema = '$tema'";
        $sql = mysql_query($texto);
      

       $data= array();
      
        while ($fila = mysql_fetch_assoc($sql) )  {
           array_push($data, $fila);
            $this->tComentarios = $fila["total"];
        }
        return $this->tComentarios;
    }
    //*****************************************************************
    // TOTAL DE COMENTARIOS POR TEMA
    //*****************************************************************
    public function TotalComentariosUsuario($id) {
        
        
         $texto = "select count(*) as total from comentario_foro where id_usuario = '$id'";
        $sql = mysql_query($texto);
      
        $data= array();
      
        while ($fila = mysql_fetch_assoc($sql) )  {
           array_push($data, $fila);

        

            $this->tComentarios = $fila["total"];
        }
        return $this->tComentarios;
    }
        
         
    //*****************************************************************
    // TOTAL DE COMENTARIOS POR FORO
    //*****************************************************************
    public function TotalComentariosForo($foro) {
         
            
         $texto = "SELECT count(*) as total
            FROM
            comentario_foro
            INNER JOIN foro_temas ON comentario_foro.id_tema = foro_temas.id_tema
            INNER JOIN foro_foro ON foro_temas.id_foro = foro_foro.id_foro
            WHERE
            foro_foro.id_foro = '$foro'";
        $sql = mysql_query($texto);
      

        while ( $fila = mysql_fetch_assoc($sql) ) {

            $this->tComentarios = $fila["total"];
        }
        return $this->tComentarios;
    }
        
    //*****************************************************************
    // AGREGAR COMENTARIO
    //*****************************************************************
    public function add($id, $foro, $sub) {

        $comentario = $_POST['comentario'];
        $activo = 0;
        $usuario = $_SESSION["uid"];
        $texto2="INSERT INTO comentario_foro(id_tema, id_usuario, comentario, fecha, activo) 
            VALUES($id, $usuario, '$comentario', now(), $activo)";
         $result = mysql_query($texto2) or die(mysql_error());

        
        
        if($sub == 0){
            echo "<script type='text/javascript'>window.location='tema.php?id=$id&foro=$foro';</script>";
        }else{
            echo "<script type='text/javascript'>window.location='tema.php?id=$id&foro=$foro&sub=$sub';</script>";
        }
        return $result;
    }

    //*****************************************************************
    // ULTIMO COMENTARIO
    //*****************************************************************
    public function ultmoComentario($id) {

        $texto = "SELECT
            comentario_foro.id_comentario,
            comentario_foro.id_tema,
            comentario_foro.comentario,
            comentario_foro.fecha,
            comentario_foro.activo,
            users.username,
            users.avatar,
            users.fechaderegistro
       
            FROM
            comentario_foro
            INNER JOIN users ON comentario_foro.id_usuario = users.uid
            where id_tema = $id
            ORDER BY
            comentario_foro.id_comentario DESC
            limit 1";
        $sql = mysql_query($texto);
      

       $data= array();
      if($sql!==false){
        while ($fila = mysql_fetch_assoc($sql) )  {
           array_push($data, $fila);
        }
      }
        
        return $data;
    }

    //*****************************************************************
    // ELIMINA COMENTARIO
    //*****************************************************************
    public function del($id, $tema, $foro, $sub) {
        
        $texto = "DELETE FROM comentario_foro WHERE id_comentario = $id";
        $resultado = mysql_query($texto) or die(mysql_error());
       

        if($sub == 0){
            header("Location: tema.php?id=$tema&foro=$foro");
        }else{
            header("Location: tema.php?id=$tema&foro=$foro&sub=$sub");
        }
        return $resultado;
        
    }
}
?>