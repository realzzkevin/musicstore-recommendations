package com.project2.musicstorerecommendations.controller;

import com.project2.musicstorerecommendations.model.AlbumRecommendation;
import com.project2.musicstorerecommendations.model.TrackRecommendation;
import com.project2.musicstorerecommendations.repository.TrackRecommendationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/trackrecommendation")
public class TrackRecommendationController {

    @Autowired
    TrackRecommendationRepository repo;

    @GetMapping()
    @ResponseStatus(HttpStatus.OK)
    public List<TrackRecommendation> getAllTrackRecommendation() {
        return repo.findAll();
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public TrackRecommendation getTrackRecommendationById(@PathVariable Long id) {
        Optional<TrackRecommendation> returnVal = repo.findById(id);
        return returnVal.isPresent() ? returnVal.get() : null;
    }

    @PostMapping()
    @ResponseStatus(HttpStatus.CREATED)
    public TrackRecommendation addTrackRecommendation(@RequestBody @Valid TrackRecommendation trackRecomm) {
        return repo.save(trackRecomm);
    }

    @PutMapping()
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void updateTrackRecommendation(@RequestBody @Valid TrackRecommendation trackRecomm) {
        if (trackRecomm.getId() != null) {
            repo.save(trackRecomm);
        } else {
            throw new IllegalArgumentException("track recommendation id not present");
        }
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteTrackRecommendation(@PathVariable Long id) {
        repo.deleteById(id);
    }
}
