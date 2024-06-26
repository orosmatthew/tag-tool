<?php

namespace controllers;

use model\types\User;
use model\UserModel;

require_once(__DIR__ . '/Request.php');
require_once(__DIR__ . '/Response.php');
require_once(__DIR__ . '/../../tag-tool/models/UserModel.php');

class UserController
{
    static public function post(Request $req): Response
    {
        $user = new User($req->data);
        if (UserModel::exists($user->username)) {
            return new Response(null, null, 1);
        }
        $success = UserModel::createUser($user);
        return new Response(null, null, $success ? 0 : 1);
    }

    static public function delete(Request $req): Response
    {
        $success = UserModel::deleteUser($req->userId);
        return new Response(null, null, $success ? 0 : 1);
    }
}