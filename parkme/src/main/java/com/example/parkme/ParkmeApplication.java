package com.example.parkme;

import com.stripe.Stripe;
import com.stripe.exception.StripeException;
import com.stripe.model.PaymentMethod;
import com.stripe.param.PaymentMethodAttachParams;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class PaymentController {

	public PaymentController() {
		// Initialize Stripe with your secret key
		Stripe.apiKey = "sk_test_...";
	}

	@PostMapping("/create-payment-method")
	public ResponseEntity<?> createPaymentMethod(@RequestBody PaymentMethodCreateParams params,
												 @RequestParam String customerId) {
		try {
			// Create a PaymentMethod using the card details received from the client
			PaymentMethod paymentMethod = PaymentMethod.create(params.toMap());

			// Attach the payment method to the customer
			PaymentMethodAttachParams attachParams = PaymentMethodAttachParams.builder()
					.setCustomer(customerId)
					.build();
			paymentMethod = paymentMethod.attach(attachParams);

			// TODO: Perform other server-side operations as needed

			return ResponseEntity.ok(paymentMethod);
		} catch (StripeException e) {
			return ResponseEntity.status(500).body(e.getMessage());
		}
	}
}
