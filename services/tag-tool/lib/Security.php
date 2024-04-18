<?php

namespace lib;

use model\types\User;
use model\UserModel;

require_once(__DIR__ . '/../../tag-tool/models/types/User.php');
require_once(__DIR__ . '/../../tag-tool/models/UserModel.php');

class Security
{
    static public function authenticate($username, $password): ?User
    {
        $user = new User(null);
        $user->username = $username;
        $user->password = $password;
        return UserModel::authenticate($user);
    }
}