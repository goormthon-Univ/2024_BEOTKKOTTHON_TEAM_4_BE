package com.vacgom.backend.notification.domain

import org.springframework.data.jpa.repository.JpaRepository
import java.util.*

interface NotificationRepository : JpaRepository<Notification, Long> {
    fun findAllByMemberIdOrderByCreatedAt(memberId: UUID): List<Notification>
}
