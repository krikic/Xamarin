<?php
//Include las clases
require_once 'class/foros.php';
require_once 'class/subforos.php';
require_once 'class/temas.php';
require_once 'class/comentarios.php';
require_once 'class/usuarios.php';


include 'header.php';

?>
<div class="caja">
    <div class="categorias">
        <div class="temas_titulo">Usuarios</div>
        <div class="temas_respuestas">Temas</div>
        <div class="temas_ultimo">Temas / Comentarios</div>
        <div style="clear:both; height:1px;font-size:0px; line-height: 0px;"></div>
    </div>
    <div class="foro">
        <?php
        // listo todos los usuarios cosa de niños
        $obju = new Usuarios();
        $usuarios = $obju->usuarios();
        foreach ($usuarios as $u) {
            ?>
            <div class="foro_icono">
                <img src="../img/note.png">
            </div>
            <div class="foro_titulo">
                <a href="perfil.php?id=<?php echo $u["uid"]; ?>"><?php echo $u["name"]; ?> (<?php echo $u["username"]; ?>)</a>
				<?php
				if ($u["username"] == "admin" or $u["uid"] == $_SESSION["uid"]) {
				?>
                                    <a class="btn btn-link" href="eliminar.php?opc=6&id=<?php
                                    include 'include/functions.php';
                                    echo $u["uid"]; ?>&dir=0">
                                        Eliminar</a>
                                        <?php
				}
				?>
            </div>
            <div class="temas_mensajes">
                <a href="temasusuario.php?id=<?php echo $u["uid"]; ?>">Ver temas creados por <?php echo $u["username"]; ?></a>
            </div>
            <div class="ultimocomentario">
                <?php
                $objTemas = new Temas();
                $temas = $objTemas->TotalTemasUsuarios($u["uid"]);
                if (sizeof($temas) > 0) {
                    echo "Temas: " . $temas . "<br/>";
                } else {
                    echo 'Temas: 0';
                }

                $objC = new Comments();
                $comentarios = $objC->TotalComentariosUsuario($u["uid"]);
                if (sizeof($temas) > 0) {
                    echo "Comentarios: " . $comentarios . "<br/>";
                } else {
                    echo 'Comentarios: 0';
                }
                ?>
            </div>
            <div style="clear:both; height:1px;font-size:0px; line-height: 0px;"></div>
            <?php
        }
        ?>
    </div>
</div>

<?php


include 'footer.php';
?>
