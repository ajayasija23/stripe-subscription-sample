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
              ],
            ]
          );
          return $customer;

    }

    function createSubscription($stripe,$customer_id){
        $price_id="price_1LOdATSC2k8ef4Xu2jxK43Ed";
        $subscription = $stripe->subscriptions->create([
            'customer' => $customer_id,
            'items' => [[
                'price' => $price_id,
            ]],
            'payment_behavior' => 'default_incomplete',
            'payment_settings' => ['save_default_payment_method' => 'on_subscription'],
            'expand' => ['latest_invoice.payment_intent'],
        ]);
         return $subscription;
   }
