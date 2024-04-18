<?php

namespace model\types;

require_once(__DIR__ . "/Base.php");

class User extends Base
{
    public ?int $id;
    public string $username;
    public string $password;

    public function __construct($sourceObject)
    {
        parent::__construct($sourceObject);
    }
}