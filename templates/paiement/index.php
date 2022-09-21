<?php
\Stripe\Stripe::setApiKey('pk_test_pKoLAPoBFwVaritcQtcrrqqT00GS9O2drI');

$intent = \Stripe\PaymentIntent::create([
    'amount' => 1099,
    'currency' => 'usd',
// Verify your integration in this guide by including this parameter
    'metadata' => ['integration_check' => 'accept_a_payment'],
]);
?>