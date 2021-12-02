package com.battleship.backend.controllers;

import com.battleship.backend.BoardRepository;
import com.battleship.backend.TestClasses;
import com.battleship.backend.models.*;
import org.junit.jupiter.api.Test;

import static org.hamcrest.Matchers.containsString;

import org.mockito.Mockito;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.web.context.WebApplicationContext;

@WebMvcTest(BoardController.class)
class BoardControllerTest {

    Board[] boards = new Board[] {new Board("test")};

    @Autowired
    private MockMvc mockMvc;
    private WebApplicationContext wac;

    @MockBean
    private BoardRepository boardRepository;

    @Test
    public void getBoardsReturnsBothBoards() throws Exception {
        Mockito.when(boardRepository.getBoards()).thenReturn(boards);

        this.mockMvc.perform(get("/boards")).andDo(print()).andExpect(status().isOk()).andExpect(content().string(containsString("test")));
    }


    @Test
    public void placeShipsPatchesShipOntoPlayerBoard() throws Exception {
        Mockito.when(boardRepository.getPlayerBoard()).thenReturn(new TestClasses.TestBoard());
        Ship testShip = new Ship("test", 2);

        MockHttpServletRequestBuilder builder =
                MockMvcRequestBuilders.patch("/board/place")
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .accept(MediaType.APPLICATION_JSON)
                        .characterEncoding("UTF-8")
                        .content(getPlaceRequestInJSON(testShip, 0, 0));

        String testBoardJSON = "{\"name\":\"testBoard\",\"grid\":[[{\"isShip\":true,\"isHit\":false,\"shipName\":\"test\"},{\"isShip\":true,\"isHit\":false,\"shipName\":\"test\"},{\"isHit\":false,\"isShip\":false}],[{\"isHit\":false,\"isShip\":false},{\"isHit\":false,\"isShip\":false},{\"isHit\":false,\"isShip\":false}],[{\"isHit\":false,\"isShip\":false},{\"isHit\":false,\"isShip\":false},{\"isHit\":false,\"isShip\":false}]],\"size\":3}";

        this.mockMvc.perform(builder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().string(testBoardJSON))
                .andDo(MockMvcResultHandlers.print());
    }

    private String getPlaceRequestInJSON(Ship ship, int row, int col){
        return "{\"ship\": { \"name\":\"" + ship.getName() + "\", \"width\": " + ship.getWidth() + ", \"height\": "+ ship.getHeight() + ", \"isSunk\": "+ ship.getIsSunk() + ", \"shipSections\": [{\"isHit\": false, \"isShip\": true, \"shipName\": \"test\"},{\"isHit\": false, \"isShip\": true, \"shipName\": \"test\"}]}, \"row\": " + row + " , \"col\": " + col + "}";

    }
}


