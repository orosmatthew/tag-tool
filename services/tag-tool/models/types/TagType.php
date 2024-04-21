<?php

namespace model\types;

require_once(__DIR__ . "/Base.php");

class TagType extends Base
{
    public ?int $id;
    public ?string $createdAt;
    public ?string $name;

    public function __construct(object $sourceObject)
    {
        parent::__construct($sourceObject);
    }
}