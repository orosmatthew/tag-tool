<?php

namespace controllers;

class Response
{
    public $data;
    public int $status;
    public ?string $error;

    public function __construct($data = null, $error = null, int $status = 0)
    {
        $this->data = $data;
        $this->error = $error;
        $this->status = $status;
    }
}