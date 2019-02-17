<?php
/*
 * obtengo la opcion
 * 1 categoria - listo
 * 2 foro - listo
 * 3 subforo - listo
 * 4 tema - listo con redireccionador al tema correspondiente
 * 5 comentario - listo con redirecionador al tema correspondiente
 * obtengo el id
 */

require_once 'class/categorias.php';
require_once 'class/foros.php';
require_once 'class/subforos.php';
require_once 'class/temas.php';
require_once 'class/usuarios.php';
require_once 'class/comentarios.php';

switch ($_GET["opc"]) {
    case 1:
        $obj = new Categorias();
        $obj->del($_GET["id_forocategoria"]);
        break;
    case 2:
        $obj = new Foros();
        $obj->del($_GET["id_foro"]);
        break;
    case 3:
        $obj = new Subforos();
        $obj->del($_GET["id_subforo"]);
        break;
    case 4:
        $obj = new Temas();
        /* temas.php?foro=1&sub=3
         * $_GET["foro"] foro
         * $_GET["sub"] sub
         */
        $obj->del($_GET["id_tema"], $_GET["foro"], $_GET["sub"]);
        break;
    case 5:
        $obj = new Comments();
        /* tema.php?id=5&foro=1&sub=3
         * $_GET["id"] id del cometario
         * $_GET["tema"] tema
         * $_GET["foro"] foro
         * $_GET["sub"] sub
         */
        $obj->del($_GET["id_comentario"], $_GET["tema"], $_GET["foro"], $_GET["sub"]);
        // header("Location: tema.php?id=".$_GET["id_tema"]."&foro=".$_GET["foro"].".php");
        break;
    case 6:
        $obj = new Usuarios();
        /* temas.php?foro=1&sub=3
         * $_GET["foro"] foro
         * $_GET["sub"] sub
         */
        $obj->del($_GET["uid"], $_GET["dir"]);
        break;
}

