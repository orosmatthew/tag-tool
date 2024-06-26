<?php /** @noinspection PhpUnused */

namespace controllers;

use model\types\User;
use model\UserModel;

require_once(__DIR__ . '/Request.php');
require_once(__DIR__ . '/Response.php');
require_once(__DIR__ . '/../../tag-tool/models/UserModel.php');


class AuthController
{
    static public function post(Request $req): Response
    {
        $user = new User($req->data);
        if (!UserModel::exists($user->username)) {
            $data = array("userId" => null);
            return new Response($data, null, 0);
        }
        $result = UserModel::authenticate($user);
        $data = array("userId" => $result->id);
        return new Response($data, null, 0);
    }
}