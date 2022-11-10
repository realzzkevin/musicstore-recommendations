package com.project2.musicstorerecommendations.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.project2.musicstorerecommendations.model.TrackRecommendation;
import com.project2.musicstorerecommendations.repository.TrackRecommendationRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.doNothing;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(TrackRecommendationController.class)
public class TrackRecommendationControllerTest {


    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper mapper;

    @MockBean
    private TrackRecommendationRepository trackRecommRepo;

    private TrackRecommendation inputRecomm1;

    private TrackRecommendation inputRecomm2;

    private TrackRecommendation outputRecomm1;

    private TrackRecommendation outputRecomm2;

    private List<TrackRecommendation> trackRecommList;

    @Before
    public void setUp() throws Exception {
        mapper = new ObjectMapper();

        inputRecomm1= new TrackRecommendation(10L,  20L, true);
        inputRecomm2 = new TrackRecommendation(20L, 30L, false);

        outputRecomm1= new TrackRecommendation(10L,  20L, true);
        outputRecomm1.setId(1L);

        outputRecomm2 = new TrackRecommendation(20L, 30L, false);
        outputRecomm2.setId(2L);

        trackRecommList = new ArrayList<>();

        trackRecommList.add(outputRecomm1);
        trackRecommList.add(outputRecomm2);
    }

    @Test
    public void shouldReturnAllTrackRecomms() throws Exception {

        doReturn(trackRecommList).when(trackRecommRepo).findAll();

        String outJson = mapper.writeValueAsString(trackRecommList);

        mockMvc.perform(
                        get("/trackrecommendation")
                )
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().json(outJson));

    }

    @Test
    public void shouldReturnTrackRecommById() throws Exception {
        when(trackRecommRepo.findById((long)1)).thenReturn(Optional.of(outputRecomm1));
        String outJson = mapper.writeValueAsString(outputRecomm1);

        mockMvc.perform(
                        get("/trackrecommendation/{id}", (long)1)
                )
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().json(outJson));
    }

    @Test
    public void shouldReturnNewTrackRecomm() throws Exception {

        when(trackRecommRepo.save(inputRecomm1)).thenReturn(outputRecomm1);

        String inJson = mapper.writeValueAsString(inputRecomm1);
        String outJson = mapper.writeValueAsString(outputRecomm1);

        mockMvc.perform(
                        post("/trackrecommendation")
                                .content(inJson)
                                .contentType(MediaType.APPLICATION_JSON)
                )
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(content().json(outJson));

    }

    @Test
    public void shouldReturn204StatusWithUpdate() throws Exception {
        when(trackRecommRepo.save(inputRecomm2)).thenReturn(null);
        String inJson = mapper.writeValueAsString(inputRecomm2);
        mockMvc.perform(
                        put("/trackrecommendation")
                                .content(inJson)
                                .contentType(MediaType.APPLICATION_JSON)
                )
                .andDo(print())
                .andExpect(status().isNoContent());
    }

    @Test
    public void shouldFailWhenUpdateWithBadRequest() throws Exception {
        doThrow(new IllegalArgumentException("No id found, unable to update")).when(trackRecommRepo).save(inputRecomm1);
        String inJson = mapper.writeValueAsString(inputRecomm1);
        mockMvc.perform(
                        put("/trackrecommendation")
                                .content(inJson)
                                .contentType(MediaType.APPLICATION_JSON)
                )
                .andDo(print())
                .andExpect(status().isUnprocessableEntity());
    }

    @Test
    public void shouldReturnNoContentWithDelete() throws Exception {
        doNothing().when(trackRecommRepo).deleteById((long)1);

        mockMvc.perform(
                        delete("/trackrecommendation/{id}", (long)1)
                )
                .andDo(print())
                .andExpect(status().isNoContent());
    }
}