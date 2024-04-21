<?php /** @noinspection PhpUnused */

namespace controllers;

use model\TagModel;
use model\types\Tag;

require_once(__DIR__ . '/Request.php');
require_once(__DIR__ . '/../models/Database.php');
require_once(__DIR__ . '/Response.php');
require_once(__DIR__ . '/../models/types/Tag.php');
require_once(__DIR__ . '/../models/TagModel.php');

class TagController
{
    static public function post(Request $req): Response
    {
        $tag = new Tag($req->data);
        $success = TagModel::createTag($tag);
        $status = $success ? 0 : 1;
        return new Response(null, null, $status);
    }

    static public function get(Request $req): Response
    {
        $array = TagModel::getTag($req->id);
        return new Response($array, null, $array == null ? 1 : 0);
    }

    static public function delete(Request $req): Response
    {
        $success = TagModel::deleteTag($req->id);
        return new Response(null, null, $success ? 0 : 1);
    }

    static public function put(Request $req): Response
    {
        $tag = new Tag($req->data);
        $tag->id = $req->id;
        $success = TagModel::updateTag($tag);
        return new Response(null, null, $success ? 0 : 1);
    }
}