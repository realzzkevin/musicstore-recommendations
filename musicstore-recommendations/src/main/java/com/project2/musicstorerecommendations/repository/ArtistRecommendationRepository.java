package com.project2.musicstorerecommendations.repository;

import com.project2.musicstorerecommendations.model.ArtistRecommendation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ArtistRecommendationRepository extends JpaRepository <ArtistRecommendation, Long>{
}
