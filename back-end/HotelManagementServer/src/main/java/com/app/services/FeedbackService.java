package com.app.services;
import com.app.dto.request.FeedbackDTO;

public interface FeedbackService {
    void createFeedback(FeedbackDTO request);
}