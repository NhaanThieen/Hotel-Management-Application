package com.app.services.servicesImpl;

import com.app.dto.request.FeedbackDTO;
import com.app.pojo.Feedback;
import com.app.pojo.Roombooking;
import com.app.pojo.User;
import com.app.repositories.FeedbackRepository;
import com.app.repositories.UserRepository;
import com.app.services.FeedbackService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

@Service
public class FeedbackServiceImpl implements FeedbackService {

    @Autowired
    private FeedbackRepository feedbackRepository;

    @Autowired
    private UserRepository userRepository;

    @Override
    @Transactional
    public void createFeedback(FeedbackDTO request) {
        if (request.getBookingId() == null) {
            throw new IllegalArgumentException("Mã đơn đặt phòng không hợp lệ.");
        }
        if (this.feedbackRepository.isFeedbackExist(request.getBookingId())) {
            throw new IllegalArgumentException("Bạn đã đánh giá đơn đặt phòng này rồi. Xin cảm ơn!");
        }
        Feedback feedback = new Feedback();

        Roombooking booking = new Roombooking();
        booking.setRoomBookingId(request.getBookingId());
        feedback.setRoomBookingId(booking);

        User user = this.userRepository.getUserByUsername(request.getUserName());
        if (user == null) {
            throw new IllegalArgumentException("Không tìm thấy thông tin người dùng hợp lệ.");
        }
        feedback.setUserId(user);

        int avgRating = 5;
        if (request.getRatings() != null) {
            int sum = request.getRatings().getReception()
                    + request.getRatings().getCleanliness()
                    + request.getRatings().getFoodAndDrink()
                    + request.getRatings().getComfort();
            avgRating = Math.round((float) sum / 4);
        }
        feedback.setRating(avgRating);

        String finalComment = request.getComment() != null ? request.getComment() : "";
        if (request.getTags() != null && !request.getTags().isEmpty()) {
            String tagsString = "[Điểm nhấn: " + String.join(", ", request.getTags()) + "]";
            finalComment = tagsString + " - " + finalComment;
        }
        feedback.setComment(finalComment);
        feedback.setCreateAt(new Date());

        this.feedbackRepository.saveFeedback(feedback);
    }
}
