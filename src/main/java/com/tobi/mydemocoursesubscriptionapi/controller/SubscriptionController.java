package com.tobi.mydemocoursesubscriptionapi.controller;

import com.tobi.mydemocoursesubscriptionapi.cervice.SubscriptionService;
import com.tobi.mydemocoursesubscriptionapi.dto.SubscriptionRequestDto;
import com.tobi.mydemocoursesubscriptionapi.model.Subscription;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/subscriptions")
public class SubscriptionController {
    private final SubscriptionService subscriptionService;

    public SubscriptionController(SubscriptionService subscriptionService) {
        this.subscriptionService = subscriptionService;
    }

    @GetMapping
    public List<Subscription> getAllSubscriptions() {
        return subscriptionService.getAllSubscriptions();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Subscription> getSubscriptionById(@PathVariable Long id) {
        return subscriptionService.getSubscriptionById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<Subscription> subscribeUserToCourse(
            @RequestBody SubscriptionRequestDto subscriptionRequestDto) {
        Subscription createdSubscription = subscriptionService.subscribeUserToCourse(
                subscriptionRequestDto.getUserId(), subscriptionRequestDto.getCourseId());
        return ResponseEntity.status(HttpStatus.CREATED).body(createdSubscription);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Void> deleteSubscription(@PathVariable Long id) {
        boolean isDeleted = subscriptionService.deleteSubscription(id);
        if (isDeleted) {
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.notFound().build();
    }

}
