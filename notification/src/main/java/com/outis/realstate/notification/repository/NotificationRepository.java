package com.outis.realstate.notification.repository;

import com.outis.realstate.notification.domain.Notification;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

/**
 * Spring Data MongoDB repository for the Notification entity.
 */
@Repository
public interface NotificationRepository extends MongoRepository<Notification, String> {}
