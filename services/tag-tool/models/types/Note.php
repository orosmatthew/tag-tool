<?php

namespace model\types;

require_once(__DIR__ . "/Base.php");

class Note extends Base
{
    public ?int $id;
    public ?int $itemId;
    public ?string $content;
    public ?string $createdAt;

    public function __construct(object $sourceObject)
    {
        parent::__construct($sourceObject);
    }
}