<?php

namespace model\types;

require_once(__DIR__ . "/Base.php");

class Tag extends Base
{
    public ?int $id;
    public ?int $tagTypeId;
    public ?string $createdAt;
    public ?int $itemId;

    public function __construct(object $sourceObject)
    {
        parent::__construct($sourceObject);
    }
}