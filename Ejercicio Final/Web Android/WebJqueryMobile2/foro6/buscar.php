<?php
//Include las clases
require_once 'class/foros.php';
require_once 'class/subforos.php';
require_once 'class/temas.php';
require_once 'class/comentarios.php';
include 'header.php';

// muestro lo que busco, la busqueda solo sera en titulos
$cadena_a_buscar = "";
if(isset($_GET["busqueda"])) $cadena_a_buscar = $_GET["busqueda"];
?>
<h2>Busqueda: <?php echo $cadena_a_buscar; ?></h2>

<?php
$objbusqueda = new Temas();
$busqueda = htmlentities($cadena_a_buscar); 

$resultado = $objbusqueda->buscar($busqueda);
// sizeof valida si hay valores en el array 
if (sizeof($resultado) > 0) {
    ?>
    <div class="caja">
        <div class="categorias">
            <div class="temas_titulo">Titulo / Autor</div>
            <div class="temas_respuestas">Respuestas / Visitas</div>
            <div class="temas_ultimo">Último mensaje</div>
            <div style="clear:both; height:1px;font-size:0px; line-height: 0px;"></div>
        </div>
        <?php
        foreach ($resultado as $temas) {
            ?>
            <div class="foro">
                <div class="foro_icono">
                    <img src="../img/note.png">
                </div>
                <div class="foro_titulo">
                    <?php
                    // validamos si el subforo es cero para que concatene la ruta correcta
                        if($temas["id_subforo"] == 0){
                            $rsub = "";
                        }else{
                            $rsub = "&sub=".$temas["id_subforo"];
                        }
                            
                    ?>
                    <a href="tema.php?id=<?php echo $temas["id_tema"]; ?>&foro=<?php echo $temas["id_foro"].$rsub; ?>"><?php echo $temas["titulo"]; ?></a><br>
                    Iniciado por <a href="perfil.php?id=<?php echo $temas["id_usuario"]; ?>"><?php echo $temas["username"]; ?></a>, <?php echo $temas["fecha"]; ?>
                </div>
                <div class="temas_mensajes">
                    Respuestas:
                            <?php
                            $objCom = new Comments();
                            $com = $objCom->TotalComentarios($temas["id_tema"]);
                            echo $com;
                            ?>
                            <br>
                            Visitas: <?php echo $temas["hits"]; ?>
                </div>
                <div class="ultimocomentario">
                    <?php
                            $objMensajes = new Comments();
                            $mensaje = $objMensajes->ultmoComentario($temas["id_tema"]);
                            if (sizeof($mensaje) > 0) {
                                echo "Usuario: " . $mensaje[0]["username"] . "<br/>";
                                echo "Fecha: " . $mensaje[0]["fecha"];
                            } else {
                                echo 'No hay comentarios';
                            }
                            ?>
                </div>
                <div style="clear:both; height:1px;font-size:0px; line-height: 0px;"></div>
            </div>

            <?php
        }
        ?>
    </div>
    <?php
} else {
    echo "<h2>No se encontro ningun resultado relacionado con la busqueda... :(</h2>";
}
?>


<?php
include 'footer.php';
?>
