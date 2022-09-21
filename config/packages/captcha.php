<?php
// app/config/packages/captcha.php

if(!class_exists('CaptchaConfiguration')) {return;}

return[
    'ExempleCaptchaReservation' => [
        'ReservationInputID' => 'captchaCode',
        'ImageWidth' => 250,
        'ImageHeight' => 50,
    ],
];