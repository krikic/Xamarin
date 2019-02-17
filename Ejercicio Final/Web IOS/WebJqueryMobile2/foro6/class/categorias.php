<?php
require_once 'class/conexion.php';

class Categorias {

    public $mysql;
 

    public function __construct() {
        
        $this->mysql=new DB_Class();
        
    }
    //*****************************************************************
    // LISTAMOS TODAS LAS CATEGORIAS
    //*****************************************************************
    public function getCategorias() {
        $texto = "Select id_forocategoria, categoria from foro_categoria";
        $data = []; 
        $sql = mysql_query($texto);
        
        $data = array();
      
        $no_rows = mysql_num_rows($sql);
        
        if ($no_rows != 0) 
        {
            while($row = mysql_fetch_assoc($sql))
            {
                array_push($data, $row);
            }	
        }
        
        return $data;
    }

    //*****************************************************************
    // AGREGAMOS UNA CATEGORIA
    //*****************************************************************
    public function add() {
        $categoria = htmlentities($_POST['titulo']);
        $texto2="INSERT INTO foro_categoria(categoria) VALUES('$categoria')";
        $resultado = mysql_query($texto2) or die(mysql_error());
        echo "<script type='text/javascript'>window.location='panel.php';</script>";
        return $resultado;
    }
    
    //*****************************************************************
    // ELIMINAMOS UNA CATEGORIA
    //*****************************************************************
    public function del($id) {
        $texto2="DELETE FROM foro_categoria WHERE id_forocategoria = $id";
        $resultado = mysql_query($texto2) or die(mysql_error());
        header("Location: panel.php");
          return $resultado;
    }

}