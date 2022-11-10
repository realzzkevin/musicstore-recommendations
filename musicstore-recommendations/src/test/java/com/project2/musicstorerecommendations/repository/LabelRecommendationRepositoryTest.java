package com.project2.musicstorerecommendations.repository;

import com.project2.musicstorerecommendations.model.LabelRecommendation;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class LabelRecommendationRepositoryTest {

    @Autowired
    LabelRecommendationRepository labelRecommendationRepository;

    private LabelRecommendation labelRecomm1;

    private LabelRecommendation labelRecomm2;

    @Before
    public void setUp() throws Exception {
        labelRecommendationRepository.deleteAll();

        labelRecomm1 = new LabelRecommendation(10L,  20L, true);
        labelRecomm2 = new LabelRecommendation(20L, 30L, false);
    }

    @Test
    public void shouldCreateFindDeleteArtist() {
        LabelRecommendation savedRecomm = labelRecommendationRepository.save(labelRecomm1);

        Optional<LabelRecommendation> foundRecomm = labelRecommendationRepository.findById(savedRecomm.getId());

        assertTrue(foundRecomm.isPresent());
        assertEquals(savedRecomm, foundRecomm.get());

        labelRecommendationRepository.deleteById(savedRecomm.getId());

        foundRecomm = labelRecommendationRepository.findById(savedRecomm.getId());
        assertFalse(foundRecomm.isPresent());

    }

    @Test
    public void shouldFindAllArtists() {

        LabelRecommendation savedRecomm1 = labelRecommendationRepository.save(labelRecomm1);
        LabelRecommendation savedRecomm2 = labelRecommendationRepository.save(labelRecomm2);

        Set<LabelRecommendation> savedRecommSet = new HashSet<>();

        savedRecommSet.add(savedRecomm1);
        savedRecommSet.add(savedRecomm2);

        Set<LabelRecommendation> outputSet = new HashSet<>(labelRecommendationRepository.findAll());
        assertEquals(savedRecommSet.size(), outputSet.size());
        assertEquals(savedRecommSet, outputSet);

    }

    @Test
    public void shouldUpdateArtistById() {
        LabelRecommendation savedRecomm = labelRecommendationRepository.save(labelRecomm1);

        savedRecomm.setLabelId(42L);
        savedRecomm.setUserId(42L);
        savedRecomm.setLiked(false);

        labelRecommendationRepository.save(savedRecomm);

        Optional<LabelRecommendation> updateRecomm = labelRecommendationRepository.findById(savedRecomm.getId());
        assertEquals(savedRecomm, updateRecomm.get());
    }
}