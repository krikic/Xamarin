<?php
// require_once 'conexion.php';

class Foros  {

    public $mysql;
    


    public function __construct() {
        
        $this->mysql = new DB_Class();
     
        
       
    }
    //*****************************************************************
	// LISTAMOS LOS FOROS POR CATEGORIAS
	//*****************************************************************
	public function getForo($categoria){
            $texto ="SELECT
			id_foro,
			id_forocategoria,
			foro,
			descripcion
			FROM
			foro_foro
			WHERE
			id_forocategoria = $categoria";
            $sql = mysql_query($texto);
            $data = array();
            while($row = mysql_fetch_assoc($sql))
            {
                array_push($data, $row);
            }	

            return $data;
	}
    //*****************************************************************
    // LISTAMOS LOS FOROS POR ID
    //*****************************************************************
	public function foroporid($id){
            $texto ="SELECT
			foro_foro.id_foro,
			foro_foro.foro,
			foro_foro.descripcion,
			foro_categoria.categoria
			FROM
			foro_foro
			INNER JOIN foro_categoria ON foro_foro.id_forocategoria = foro_categoria.id_forocategoria
			WHERE
			foro_foro.id_foro = $id";
	$sql = mysql_query($texto);

       $data= array();
        
        while ($fila = mysql_fetch_assoc($sql) )  {
           array_push($data, $fila);
        
        }

        return $data;
	}
    //*****************************************************************
    // AGREGAMOS UN NUEVO FORO
    //*****************************************************************
	public function add() {
		$categoria = htmlentities($_POST['categoria']);
		$titulo = htmlentities($_POST['titulo']);
                $texto="INSERT INTO foro_foro(id_forocategoria, foro, descripcion) VALUES($categoria, '$titulo', '$titulo')";
		$result = mysql_query($texto) or die(mysql_error());
        
                echo "<script type='text/javascript'>window.location='panel.php';</script>";
                return $result;
	}
    //*****************************************************************
    // ELIMINA UN FORO
    //*****************************************************************
	public function del($id) {
		header("Location: panel.php");
                $texto="DELETE FROM foro_foro WHERE id_foro = $id";
		$result = mysql_query($texto) or die(mysql_error());
		
        header("Location: panel.php");
                return $result;
	}
}
?>