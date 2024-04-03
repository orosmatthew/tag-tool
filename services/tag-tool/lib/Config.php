<?php

namespace lib;

class Config
{
    private static function load()
    {
        $path = "/home/stu/moros20/tag-tool/db.cfg";
        return parse_ini_file($path, true);
    }


    public static function getValue($section, $key)
    {
        $config = self::load();
        return $config[$section][$key];
    }
}