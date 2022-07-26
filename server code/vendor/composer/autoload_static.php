<?php

// autoload_static.php @generated by Composer

namespace Composer\Autoload;

class ComposerStaticInita648e75ac2c31fad46d0a7190efd3b2c
{
    public static $prefixLengthsPsr4 = array (
        'S' => 
        array (
            'Stripe\\' => 7,
        ),
    );

    public static $prefixDirsPsr4 = array (
        'Stripe\\' => 
        array (
            0 => __DIR__ . '/..' . '/stripe/stripe-php/lib',
        ),
    );

    public static $classMap = array (
        'Composer\\InstalledVersions' => __DIR__ . '/..' . '/composer/InstalledVersions.php',
    );

    public static function getInitializer(ClassLoader $loader)
    {
        return \Closure::bind(function () use ($loader) {
            $loader->prefixLengthsPsr4 = ComposerStaticInita648e75ac2c31fad46d0a7190efd3b2c::$prefixLengthsPsr4;
            $loader->prefixDirsPsr4 = ComposerStaticInita648e75ac2c31fad46d0a7190efd3b2c::$prefixDirsPsr4;
            $loader->classMap = ComposerStaticInita648e75ac2c31fad46d0a7190efd3b2c::$classMap;

        }, null, ClassLoader::class);
    }
}
