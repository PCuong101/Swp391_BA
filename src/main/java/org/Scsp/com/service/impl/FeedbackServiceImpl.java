package org.Scsp.com.service.impl;

import lombok.AllArgsConstructor;
import org.Scsp.com.dto.FeedbackDTO;
import org.Scsp.com.exception.ResourceNotFoundException;
import org.Scsp.com.model.Feedback;
import org.Scsp.com.model.User;
import org.Scsp.com.repository.FeedbackRepository;
import org.Scsp.com.repository.UsersRepository;
import org.Scsp.com.service.FeedbackService;
import org.springframework.stereotype.Service;
import java.util.List;


@Service
@AllArgsConstructor
public class FeedbackServiceImpl implements FeedbackService {

    private FeedbackRepository feedbackRepository;


    private UsersRepository usersRepository;

    private FeedbackDTO convertToDTO(Feedback feedback) {
        return FeedbackDTO.builder()
                .id(feedback.getFeedbackID())
                .userId(feedback.getUser().getUserId())
                .content(feedback.getComments())
                .rating(feedback.getRating())
                .createdAt(feedback.getCreatedAt())
                .updatedAt(feedback.getUpdatedAt() != null ? feedback.getUpdatedAt() : feedback.getCreatedAt())
                .build();
    }

    private Feedback convertToEntity(FeedbackDTO feedbackDTO) {
        User user = usersRepository.findById(feedbackDTO.getUserId())
                .orElseThrow(() -> new ResourceNotFoundException("User not found with id: " + feedbackDTO.getUserId()));

        return Feedback.builder()
                .feedbackID(feedbackDTO.getId())
                .user(user)
                .comments(feedbackDTO.getContent())
                .rating(feedbackDTO.getRating())
                .build();
    }

    @Override
    public FeedbackDTO createFeedback(FeedbackDTO feedbackDTO) {
        Feedback feedback = convertToEntity(feedbackDTO);
        Feedback savedFeedback = feedbackRepository.save(feedback);
        return convertToDTO(savedFeedback);
    }

    @Override
    public FeedbackDTO getFeedbackById(Long id) {
        Feedback feedback = feedbackRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Feedback not found with id: " + id));
        return convertToDTO(feedback);
    }

    @Override
    public List<FeedbackDTO> getAllFeedbacks() {
        return feedbackRepository.findAll()
                .stream()
                .map(this::convertToDTO)
                .toList();
    }

    @Override
    public List<FeedbackDTO> getFeedbacksByUserId(Long userId) {
        return feedbackRepository.findByUser_UserId(userId)
                .stream()
                .map(this::convertToDTO)
                .toList();
    }

    @Override
    public FeedbackDTO updateFeedback(Long id, FeedbackDTO feedbackDTO) {
        Feedback existingFeedback = feedbackRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Feedback not found with id: " + id));

        existingFeedback.setComments(feedbackDTO.getContent());
        existingFeedback.setRating(feedbackDTO.getRating());

        Feedback updatedFeedback = feedbackRepository.save(existingFeedback);
        return convertToDTO(updatedFeedback);
    }

    @Override
    public void deleteFeedback(Long id) {
        if (!feedbackRepository.existsById(id)) {
            throw new ResourceNotFoundException("Feedback not found with id: " + id);
        }
        feedbackRepository.deleteById(id);
    }
}
