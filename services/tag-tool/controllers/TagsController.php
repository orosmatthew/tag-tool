<?php /** @noinspection PhpUnused */

namespace controllers;

require_once(__DIR__ . '/Request.php');
require_once(__DIR__ . '/../models/Database.php');
require_once(__DIR__ . '/Response.php');
require_once(__DIR__ . '/../models/types/Tag.php');
require_once(__DIR__ . '/../models/TagModel.php');

use model\TagModel;

class TagsController
{
    static public function get(Request $req): Response
    {
        $array = TagModel::getTags();
        return new Response($array, null, $array == null ? 1 : 0);
    }
}