<?php /** @noinspection PhpUnused */

namespace controllers;

use model\TagTypeModel;
use model\types\TagType;

require_once(__DIR__ . '/Request.php');
require_once(__DIR__ . '/../models/Database.php');
require_once(__DIR__ . '/Response.php');
require_once(__DIR__ . '/../models/types/TagType.php');
require_once(__DIR__ . '/../models/TagTypeModel.php');

class TagTypeController
{
    static public function post(Request $req): Response
    {
        $tagType = new TagType($req->data);
        $tagType->userId = $req->userId;
        $success = TagTypeModel::createTagType($tagType);
        $status = $success ? 0 : 1;
        return new Response(null, null, $status);
    }

    static public function get(Request $req): Response
    {
        $array = TagTypeModel::getTagType($req->userId, $req->id);
        return new Response($array, null, $array == null ? 1 : 0);
    }

    static public function delete(Request $req): Response
    {
        $success = TagTypeModel::deleteTagType($req->userId, $req->id);
        return new Response(null, null, $success ? 0 : 1);
    }

    static public function put(Request $req): Response
    {
        $tagType = new TagType($req->data);
        $tagType->userId = $req->userId;
        $tagType->id = $req->id;
        $success = TagTypeModel::updateTagType($tagType);
        return new Response(null, null, $success ? 0 : 1);
    }
}