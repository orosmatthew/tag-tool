<?php

namespace controllers;

class Request
{
    public ?object $data;
    public ?int $id;
    public ?int $userId;

    public function __construct($data = null, int $id = null, int $userId = null)
    {
        $this->data = $data;
        $this->id = $id;
        $this->userId = $userId;
    }
}