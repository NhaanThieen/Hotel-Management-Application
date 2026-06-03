package com.app.repositories;

import com.app.pojo.Feedback;

public interface FeedbackRepository {
    void saveFeedback(Feedback feedback);
    boolean isFeedbackExist(Integer bookingId);
}