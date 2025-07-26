package org.Scsp.com.service;

import org.Scsp.com.dto.FeedbackDTO;
import java.util.List;

public interface FeedbackService {
    FeedbackDTO createFeedback(FeedbackDTO feedbackDTO);
    FeedbackDTO getFeedbackById(Long id);
    List<FeedbackDTO> getAllFeedbacks();
    List<FeedbackDTO> getFeedbacksByUserId(Long userId);
    FeedbackDTO updateFeedback(Long id, FeedbackDTO feedbackDTO);
    void deleteFeedback(Long id);
}
