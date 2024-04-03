<?php

namespace controllers;

class Request
{
    public ?object $data;
    public ?int $id;

    public function __construct($data = null, int $id = null)
    {
        $this->data = $data;
        $this->id = $id;
    }
}