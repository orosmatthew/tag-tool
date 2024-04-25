<?php

namespace model\types;

require_once(__DIR__ . "/Base.php");

class Item extends Base
{
    public ?int $id;
    public string $name;
    public ?string $description;
    public ?string $createdAt;
    public ?string $codeData;
    public int $userId;

    public function __construct(object $sourceObject)
    {
        parent::__construct($sourceObject);
    }
}