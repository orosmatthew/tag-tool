<?php

namespace model;

require_once(__DIR__ . "/types/User.php");
require_once(__DIR__ . "/Database.php");

use model\types\User;

class UserModel
{
    static public function createUser(User $user): bool
    {
        $hash = password_hash($user->password, PASSWORD_DEFAULT);
        $id = Database::executeSql("INSERT INTO User (username, password) VALUES (?, ?)",
            "ss", array($user->username, $hash));
        return isset($id);
    }

    static function authenticate(User $user)
    {
        $sql = "SELECT id, username, password FROM User where username = ?";
        $result = Database::executeSql($sql, "s", array($user->username));
        $password = $user->password;
        if (is_array($result)) {
            $user = new User($result[0]);
            if (password_verify($password, $user->password)) {
                return $user;
            }
        }
        return null;
    }
}