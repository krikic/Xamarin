<?php
// require_once 'conexion.php';
class Subforos{
	 public $mysql;
    


    public function __construct() {
        
        $this->mysql = new DB_Class();
     
        
       
    }
	//*****************************************************************
	// LISTAMOS LOS SUBFOROS POR FORO
	//*****************************************************************
	public function getSubforo($foro){
            $texto="SELECT
			id_subforo,
			id_foro,
			subforo,
			descripcion
			FROM
			foro_subforos
			WHERE
			id_foro = $foro";
		$sql = mysql_query($texto);

            $data = array();
            while($row = mysql_fetch_assoc($sql))
            {
                array_push($data, $row);
            }	
        
        return $data;
	}
    //*****************************************************************
	// LISTAMOS LOS SUBFOROS POR ID
	//*****************************************************************
	public function subforoporid($id){
            
            $texto="SELECT * FROM foro_subforos WHERE id_subforo = $id";

		$resultado=mysql_query($texto);

        $data= array();
        
        while ($fila = mysql_fetch_assoc($resultado) )  {
           array_push($data, $fila);
        
        }

        return $data;
	}

    //*****************************************************************
    // AGREGAR SUBFOROS
    //*****************************************************************
	public function add() {
		$foro = htmlentities($_POST['foro_parent']);
		$titulo = htmlentities($_POST['titulo']);
                
                $texto="INSERT INTO foro_subforos(id_foro, subforo, descripcion) VALUES($foro, '$titulo', '$titulo')";
		$result = mysql_query($texto) or die(mysql_error());

        echo "<script type='text/javascript'>window.location='panel.php';</script>";
        return $result;
	}

    //*****************************************************************
    // ELIMINAMOS UN SUBFORO
    //*****************************************************************
	public function del($id) {
            $texto="DELETE FROM foro_subforos WHERE id_subforo = $id";
		$result = mysql_query($texto) or die(mysql_error());
		
        header("Location: panel.php");
        return $result;
	}
}
?>