<?php


use controllers\Request;

require_once(__DIR__ . '/../../tag-tool/controllers/Request.php');

require_once(__DIR__ . '/../../tag-tool/controllers/ItemController.php');

header('Content-Type: application/json');
date_default_timezone_set('America/New_York');

$method = strtolower($_SERVER["REQUEST_METHOD"]);
$path = explode("/", $_SERVER["PATH_INFO"]);

$controller = ucfirst($path[1]) . "Controller";
$data = json_decode(file_get_contents("php://input"));
$req = new Request();

if (method_exists($controller, $method)) {
    if ($method == "get" || $method == "put" || $method == "delete") {
        $req->id = $path[2];
    }
    if ($method == "post" || $method == "put") {
        $req->data = $data;
    }
    $response = call_user_func(array($controller, $method), $req);
} else {
    http_response_code(405);
    die();
}