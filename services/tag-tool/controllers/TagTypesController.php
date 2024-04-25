<?php /** @noinspection PhpUnused */

namespace controllers;

require_once(__DIR__ . '/Request.php');
require_once(__DIR__ . '/../models/Database.php');
require_once(__DIR__ . '/Response.php');
require_once(__DIR__ . '/../models/types/TagType.php');
require_once(__DIR__ . '/../models/TagTypeModel.php');

use model\TagTypeModel;

class TagTypesController
{
    static public function get(Request $req): Response
    {
        $array = TagTypeModel::getTagTypes($req->userId);
        return new Response($array, null, $array == null ? 1 : 0);
    }
}