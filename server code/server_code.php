<?php

    require_once('vendor/stripe/stripe-php/init.php');
    createPaymentIntent();
    function createPaymentIntent()
    {
        try {
            $stripe=new \Stripe\StripeClient('sk_test_51LOOhzSC2k8ef4XuWUL86OQmC6JGvMZ5N5uLSjkO4mAr1ApNZwI1yd23Dw3uamuXdpGZQeRGsoXsl2qothrn8n8A00HbXWsJFW');
            $customer=createCustomer($stripe);
            $customer_id=$customer->id;
            $subscription=createSubscription($stripe,$customer_id);
            $data['client_secret']=$subscription->latest_invoice->payment_intent->client_secret;
            if($data['client_secret']==null){
            // if payment not required then latest_invoice's payment_intent will be null then user setup intent created by stripe in pending_setup_intent
              $pendingintent=$stripe->setupIntents->retrieve($subscription->pending_setup_intent);
              $data['client_secret']=$pendingintent->client_secret;
            }
            $data['subscription']=$subscription;

        } catch (\Throwable $th) {
            $data['error']=$th->getMessage();
        }
        header('Content-Type: application/json; charset=utf-8');
        echo (json_encode($data));
    }

    function createCustomer($stripe){
         $customer=$stripe->customers->create(
            [
              'email' => 'ajayasija23@gmail.com',
              'name' => 'Ajay Asija',
              'shipping' => [
                'address' => [
                  'city' => 'Brothers',
                  'country' => 'IN',
                  'line1' => '27 Fredrick Ave',
                  'postal_code' => '97712',
                  'state' => 'HR',
                ],
                'name' => 'Ajay Asija',
              ],
              'address' => [
                'city' => 'Brothers',
                'country' => 'IN',
                'line1' => '27 Fredrick Ave',
                'postal_code' => '97712',
                'state' => 'HR',
              ]
            ]
          );
          return $customer;

    }

    function createSubscription($stripe,$customer_id){
        $price_id="price_1LPq86SC2k8ef4XuOFOv9xWu";
     //  $coupon= $stripe->coupons->retrieve('SXjaeeaD');
        $subscription = $stripe->subscriptions->create([
            'customer' => $customer_id,
            'items' => [[
                'price' => $price_id,
            ]],
            'promotion_code'=>'promo_1LPqQbSC2k8ef4XuzwwIc8W7',//add promo code to subscription object
            'payment_behavior' => 'default_incomplete',
            'off_session'=>true,
            'payment_settings' => ['save_default_payment_method' => 'on_subscription'],
            'expand' => ['latest_invoice.payment_intent'],
        ]);
         return $subscription;
   }

