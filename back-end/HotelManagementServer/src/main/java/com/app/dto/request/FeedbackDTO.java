package com.app.dto.request;

import java.util.List;

public class FeedbackDTO {
    private Integer bookingId;
    private String userName; 
    private RatingsDTO ratings;
    private List<String> tags;
    private String comment;

    public Integer getBookingId() { return bookingId; }
    public void setBookingId(Integer bookingId) { this.bookingId = bookingId; }
    public String getUserName() { return userName; }
    public void setUserName(String userName) { this.userName = userName; }
    public RatingsDTO getRatings() { return ratings; }
    public void setRatings(RatingsDTO ratings) { this.ratings = ratings; }
    public List<String> getTags() { return tags; }
    public void setTags(List<String> tags) { this.tags = tags; }
    public String getComment() { return comment; }
    public void setComment(String comment) { this.comment = comment; }

    public static class RatingsDTO {
        private Integer reception;
        private Integer cleanliness;
        private Integer foodAndDrink;
        private Integer comfort;

        public Integer getReception() { return reception; }
        public void setReception(Integer reception) { this.reception = reception; }
        public Integer getCleanliness() { return cleanliness; }
        public void setCleanliness(Integer cleanliness) { this.cleanliness = cleanliness; }
        public Integer getFoodAndDrink() { return foodAndDrink; }
        public void setFoodAndDrink(Integer foodAndDrink) { this.foodAndDrink = foodAndDrink; }
        public Integer getComfort() { return comfort; }
        public void setComfort(Integer comfort) { this.comfort = comfort; }
    }
}