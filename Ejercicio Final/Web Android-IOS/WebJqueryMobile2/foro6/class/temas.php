<?php

// require_once 'conexion.php';

class Temas {

    public $mysql;
    private $tTemas;

  

    public function __construct() {
        
        $this->mysql=new DB_Class();
    }

    //*****************************************************************
    // LISTAMOS LOS TEMAS PAGINADOS DEL FORO
    //*****************************************************************
    public function getTemas($foro, $inicio, $cantTemas) {

        $texto="SELECT
            foro_temas.id_tema,
            foro_temas.id_foro,
            foro_temas.id_subforo,
            foro_temas.titulo,
            foro_temas.contenido,
            foro_temas.fecha,
            foro_temas.activo,
            foro_temas.hits,
            users.username,
            foro_temas.id_usuario
            FROM
            foro_temas
            INNER JOIN users ON foro_temas.id_usuario = users.uid
            where id_foro = $foro
            order by id_tema desc
            limit $inicio, $cantTemas";
        $sql = mysql_query($texto);

            $data = array();
            while($row = mysql_fetch_assoc($sql))
            {
                array_push($data, $row);
            }	
        
        return $data;
    }

    //*****************************************************************
    // TOTAL DE TEMAS DEL FORO
    //*****************************************************************
    public function TotalTemas($foro) {
        
        $texto="select count(*) as total from foro_temas where id_foro = '$foro'";
        $resultado=mysql_query($texto);
        $data= array();
        while ($fila = mysql_fetch_assoc($resultado))  {
            array_push($data, $fila);
            $this->tTemas = $fila["total"];
        }

        return $this->tTemas;
    }
    //*****************************************************************
    // TOTAL TEMAS POR USUARIO
    //*****************************************************************
    public function TotalTemasUsuarios($id) {
        
        $texto="select count(*) as total from foro_temas where id_usuario = '$id'";
        $resultado=mysql_query($texto);
        

        $data= array();
      
        while ($fila = mysql_fetch_assoc($resultado) )  {
           array_push($data, $fila);
        
            $this->tTemas = $fila["total"];
        }
        
        return $this->tTemas;
    }

    //*****************************************************************
    // LISTAMOS LOS TEMAS PAGINADOS DEL SUBFORO
    //*****************************************************************
    public function getsubTemas($sub, $inicio, $cantTemas) {
        $texto= "SELECT
            foro_temas.id_tema,
            foro_temas.id_foro,
            foro_temas.id_subforo,
            foro_temas.titulo,
            foro_temas.contenido,
            foro_temas.fecha,
            foro_temas.activo,
            foro_temas.hits,
            users.username,
            foro_temas.id_usuario
            FROM
            foro_temas
            INNER JOIN users ON foro_temas.id_usuario = users.uid
            where id_subforo = $sub
            order by id_tema desc
            limit $inicio, $cantTemas";
        $resultado=mysql_query($texto);
       

        $data= array();
       if($resultado!==false){
        while ($fila = mysql_fetch_assoc($resultado) )  {
           array_push($data, $fila);
        }
        }
        return $data;
    }

    //*****************************************************************
    // TOTAL DE TEMAS DEL SUBFORO
    //*****************************************************************
    public function TotalsubTemas($sub) {
        
        $texto="select count(*) as total from foro_temas where id_subforo = '$sub'";
        $resultado=mysql_query($texto);
        

      $data= array();
      
        while ($fila = mysql_fetch_assoc($resultado) )  {
           array_push($data, $fila);
        
        
        
            $this->tTemas = $fila["total"];
        }

        return $this->tTemas;
    }

    //*****************************************************************
    // LISTAMOS LOS SUBFOROS POR FORO
    //*****************************************************************
    
    public function tema($id) {
       
        $texto="SELECT foro_temas.id_tema,foro_temas.id_foro,
            foro_temas.id_subforo,foro_temas.titulo,foro_temas.contenido,
            foro_temas.fecha,foro_temas.activo,
            foro_temas.hits,
            users.username,
            foro_temas.id_usuario,
            users.name,
            users.address1,
            users.address2,
            users.mobile,
            users.avatar
            FROM foro_temas
            INNER JOIN users ON foro_temas.id_usuario = users.uid
            where id_tema = $id";
        $resultado=mysql_query($texto);
        


       $data= array();
      if($resultado!==false){
        while ($fila = mysql_fetch_assoc($resultado) )  {
           array_push($data, $fila);
        }
        
        }
        
        return $data;
    }

    //*****************************************************************
    // SUMA LAS VISITAS
    //*****************************************************************
    public function hits($id) {
        $texto="UPDATE foro_temas SET hits = hits+1 WHERE id_tema = $id";
         $result = mysql_query($texto) or die(mysql_error());
        return $result;
    }
    
    //*****************************************************************
    // LISTAMOS LOS SUBFOROS POR FORO
    //*****************************************************************
    public function buscar($buscar) {
          $texto="SELECT
            foro_temas.id_tema,
            foro_temas.id_foro,
            foro_temas.id_subforo,
            foro_temas.titulo,
            foro_temas.contenido,
            foro_temas.fecha,
            foro_temas.id_usuario,
            foro_temas.activo,
            foro_temas.hits,
            users.username
            FROM
            foro_temas
            INNER JOIN users ON foro_temas.id_usuario = users.uid where titulo LIKE '%$buscar%'";
         $resultado = mysql_query($texto);
        
             $data= array();
        if($resultado !== false){
        while ($fila = mysql_fetch_assoc($resultado) )  {
           array_push($data, $fila);
        
        }
             
       }
        
        return $data;
    }
    
    //*****************************************************************
    // LISTAMOS LOS SUBFOROS POR FORO
    //*****************************************************************
    public function add($foro, $sub) {

        $tema = htmlentities($_POST['titulo']);
        $contenido = $_POST['contenido'];
        $usuario = $_SESSION["uid"];
        $activo = 1;
        $hit = 0;
        $texto="INSERT INTO foro_temas(id_foro, id_subforo, titulo, contenido, fecha, id_usuario, activo, hits) 
            VALUES($foro, $sub, '$tema', '$contenido', now(), $usuario, $activo, $hit)";
         $result = mysql_query($texto) or die(mysql_error());
        
        if($sub == 0){
            echo "<script type='text/javascript'>window.location='temas.php?foro=$foro';</script>";
        }else{
            echo "<script type='text/javascript'>window.location='temas.php?foro=$foro&sub=$sub';</script>";
        }
        return $result;
    }
    //*****************************************************************
    // LISTAMOS LOS SUBFOROS POR FORO
    //*****************************************************************
    public function del($id, $foro, $sub) {
        $texto="DELETE FROM foro_temas WHERE id_tema = $id";
        $result = mysql_query($texto) or die(mysql_error());
        
        if($sub==0)
            header("Location: temas.php?foro=$foro");
        else
            header("Location: temas.php?foro=$foro&sub=$sub");
        return $result;
    }
}
?>