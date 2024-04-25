<?php


use controllers\Request;
use lib\Security;

require_once(__DIR__ . '/../../tag-tool/controllers/Request.php');
require_once(__DIR__ . '/../../tag-tool/lib/Security.php');

require_once(__DIR__ . '/../../tag-tool/controllers/ItemController.php');
require_once(__DIR__ . '/../../tag-tool/controllers/UserController.php');
require_once(__DIR__ . '/../../tag-tool/controllers/AuthController.php');
require_once(__DIR__ . '/../../tag-tool/controllers/ItemsController.php');
require_once(__DIR__ . '/../../tag-tool/controllers/NoteController.php');
require_once(__DIR__ . '/../../tag-tool/controllers/NotesController.php');
require_once(__DIR__ . '/../../tag-tool/controllers/TagController.php');
require_once(__DIR__ . '/../../tag-tool/controllers/TagsController.php');
require_once(__DIR__ . '/../../tag-tool/controllers/TagTypeController.php');
require_once(__DIR__ . '/../../tag-tool/controllers/TagTypesController.php');

header('Content-Type: application/json');
date_default_timezone_set('America/New_York');

$method = strtolower($_SERVER["REQUEST_METHOD"]);
$path = explode("/", $_SERVER["PATH_INFO"]);

$controller = "controllers\\" . ucfirst($path[1]) . "Controller";
$data = json_decode(file_get_contents("php://input"));
$req = new Request();

$user = null;
if (!($controller == "controllers\\UserController" && $method == "post")
    && !($controller == "controllers\\AuthController" && $method == "post")) {
    $username = $_SERVER["PHP_AUTH_USER"];
    $password = $_SERVER["PHP_AUTH_PW"];
    $user = Security::authenticate($username, $password);
    if ($user === null) {
        http_response_code(401);
        die();
    }
}
if (method_exists($controller, $method)) {
    $req->userId = $user->id;
    if ($method == "get" || $method == "put" || $method == "delete") {
        $req->id = $path[2];
    }
    if ($method == "post" || $method == "put") {
        $req->data = $data;
    }
    $res = call_user_func(array($controller, $method), $req);
    echo json_encode($res);
} else {
    http_response_code(405);
    die();
}