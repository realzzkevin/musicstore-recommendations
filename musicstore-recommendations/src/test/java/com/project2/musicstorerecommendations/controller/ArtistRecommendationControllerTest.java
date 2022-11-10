package com.project2.musicstorerecommendations.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.project2.musicstorerecommendations.model.ArtistRecommendation;
import com.project2.musicstorerecommendations.repository.ArtistRecommendationRepository;
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
@WebMvcTest(ArtistRecommendationController.class)
public class ArtistRecommendationControllerTest {


    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper mapper;

    @MockBean
    private ArtistRecommendationRepository artistRecommRepo;

    private ArtistRecommendation inputRecomm1;

    private ArtistRecommendation inputRecomm2;

    private ArtistRecommendation outputRecomm1;

    private ArtistRecommendation outputRecomm2;

    private List<ArtistRecommendation> artistRecommList;

    @Before
    public void setUp() throws Exception {
        mapper = new ObjectMapper();

        inputRecomm1= new ArtistRecommendation(10L,  20L, true);
        inputRecomm2 = new ArtistRecommendation(20L, 30L, false);

        outputRecomm1= new ArtistRecommendation(10L,  20L, true);
        outputRecomm1.setId(1L);

        outputRecomm2 = new ArtistRecommendation(20L, 30L, false);
        outputRecomm2.setId(2L);

        artistRecommList = new ArrayList<>();

        artistRecommList.add(outputRecomm1);
        artistRecommList.add(outputRecomm2);
    }

    @Test
    public void shouldReturnAllArtistRecomms() throws Exception {

        doReturn(artistRecommList).when(artistRecommRepo).findAll();

        String outJson = mapper.writeValueAsString(artistRecommList);

        mockMvc.perform(
                        get("/artistrecommendation")
                )
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().json(outJson));

    }

    @Test
    public void shouldReturnArtistRecommById() throws Exception {
        when(artistRecommRepo.findById((long)1)).thenReturn(Optional.of(outputRecomm1));
        String outJson = mapper.writeValueAsString(outputRecomm1);

        mockMvc.perform(
                        get("/artistrecommendation/{id}", (long)1)
                )
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().json(outJson));
    }

    @Test
    public void shouldReturnNewArtistRecomm() throws Exception {

        when(artistRecommRepo.save(inputRecomm1)).thenReturn(outputRecomm1);

        String inJson = mapper.writeValueAsString(inputRecomm1);
        String outJson = mapper.writeValueAsString(outputRecomm1);

        mockMvc.perform(
                        post("/artistrecommendation")
                                .content(inJson)
                                .contentType(MediaType.APPLICATION_JSON)
                )
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(content().json(outJson));

    }

    @Test
    public void shouldReturn204StatusWithUpdate() throws Exception {
        when(artistRecommRepo.save(inputRecomm2)).thenReturn(null);
        String inJson = mapper.writeValueAsString(inputRecomm2);
        mockMvc.perform(
                        put("/artistrecommendation")
                                .content(inJson)
                                .contentType(MediaType.APPLICATION_JSON)
                )
                .andDo(print())
                .andExpect(status().isNoContent());
    }

    @Test
    public void shouldFailWhenUpdateWithBadRequest() throws Exception {
        doThrow(new IllegalArgumentException("No id found, unable to update")).when(artistRecommRepo).save(inputRecomm1);
        String inJson = mapper.writeValueAsString(inputRecomm1);
        mockMvc.perform(
                        put("/artistrecommendation")
                                .content(inJson)
                                .contentType(MediaType.APPLICATION_JSON)
                )
                .andDo(print())
                .andExpect(status().isUnprocessableEntity());
    }

    @Test
    public void shouldReturnNoContentWithDelete() throws Exception {
        doNothing().when(artistRecommRepo).deleteById((long)1);

        mockMvc.perform(
                        delete("/artistrecommendation/{id}", (long)1)
                )
                .andDo(print())
                .andExpect(status().isNoContent());
    }
}