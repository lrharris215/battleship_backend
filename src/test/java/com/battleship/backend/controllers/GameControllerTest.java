package com.battleship.backend.controllers;

import com.battleship.backend.BoardRepository;
import com.battleship.backend.TestClasses;
import com.battleship.backend.models.*;
import com.battleship.backend.validators.HitRequestValidator;
import com.battleship.backend.validators.PlaceShipsValidator;
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

@WebMvcTest(GameController.class)
class GameControllerTest {

    Board[] boards = new Board[] {new Board("test")};

    @Autowired
    private MockMvc mockMvc;
    private WebApplicationContext wac;

    @MockBean
    private BoardRepository boardRepository;
    @MockBean
    private PlaceShipsValidator placeValidator;
    @MockBean
    private HitRequestValidator hitValidator;

    @Test
    public void getBoardsReturnsBothBoards() throws Exception {
        Mockito.when(boardRepository.getBoards()).thenReturn(boards);

        this.mockMvc.perform(get("/boards")).andDo(print()).andExpect(status().isOk()).andExpect(content().string(containsString("test")));
    }


    @Test
    public void placeShipsPatchesShipOntoPlayerBoard() throws Exception {
        Mockito.when(boardRepository.getPlayerBoard()).thenReturn(new TestClasses.TestBoard());
        Mockito.when(placeValidator.isValid(Mockito.isA(Boardable.class), Mockito.isA(Request.class))).thenReturn(true);
        Ship testShip = new Ship("test", 2);

        MockHttpServletRequestBuilder builder =
                MockMvcRequestBuilders.patch("/board/place")
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .accept(MediaType.APPLICATION_JSON)
                        .characterEncoding("UTF-8")
                        .content(getPlaceRequestInJSON(testShip, 0, 0));

        String testBoardJSON = "{\"name\":\"testBoard\",\"grid\":[[{\"isShip\":true,\"isHit\":false,\"shipName\":\"test\"},{\"isShip\":true,\"isHit\":false,\"shipName\":\"test\"},{\"isHit\":false,\"isShip\":false}],[{\"isHit\":false,\"isShip\":false},{\"isHit\":false,\"isShip\":false},{\"isHit\":false,\"isShip\":false}],[{\"isHit\":false,\"isShip\":false},{\"isHit\":false,\"isShip\":false},{\"isHit\":false,\"isShip\":false}]],\"shipList\":[{\"width\":2,\"height\":1,\"name\":\"test\",\"isSunk\":false,\"shipSections\":[{\"isShip\":true,\"isHit\":false,\"shipName\":\"test\"},{\"isShip\":true,\"isHit\":false,\"shipName\":\"test\"}]}],\"size\":3}";

        this.mockMvc.perform(builder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().string(testBoardJSON))
                .andDo(MockMvcResultHandlers.print());
    }

    @Test
    public void testPlaceShipsThrowsExceptionIfInvalid() throws Exception{
        Mockito.when(boardRepository.getPlayerBoard()).thenReturn(new TestClasses.TestBoard());
        Mockito.when(placeValidator.isValid(Mockito.isA(Boardable.class), Mockito.isA(Request.class))).thenReturn(false);
        Ship testShip = new Ship("test", 2);

        MockHttpServletRequestBuilder builder =
                MockMvcRequestBuilders.patch("/board/place")
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .accept(MediaType.APPLICATION_JSON)
                        .characterEncoding("UTF-8")
                        .content(getPlaceRequestInJSON(testShip, 2, 0));

        this.mockMvc.perform(builder)
                .andExpect(MockMvcResultMatchers.status().isBadRequest())
                .andExpect(MockMvcResultMatchers.content().string(containsString("That is not a valid placement!")));
    }

    @Test
    void testHitShipUpdatesShipStatusOnComputerBoard() throws Exception {
        Boardable testBoard = new TestClasses.TestBoard();
        Ship testShip = new Ship("test", 2);
        testBoard.addShip(testShip, 0, 0);
        Mockito.when(boardRepository.getComputerBoard()).thenReturn(testBoard);
        Mockito.when(hitValidator.isValid(Mockito.isA(Boardable.class), Mockito.isA(Request.class))).thenReturn(true);


        MockHttpServletRequestBuilder builder =
                MockMvcRequestBuilders.patch("/board/hit")
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .accept(MediaType.APPLICATION_JSON)
                        .characterEncoding("UTF-8")
                        .content(getHitRequestInJSON(0,0));

        //Returned board with ship hit.
        String testBoardJSON = "{\"name\":\"testBoard\",\"grid\":[[{\"isShip\":true,\"isHit\":true,\"shipName\":\"test\"},{\"isShip\":true,\"isHit\":false,\"shipName\":\"test\"},{\"isHit\":false,\"isShip\":false}],[{\"isHit\":false,\"isShip\":false},{\"isHit\":false,\"isShip\":false},{\"isHit\":false,\"isShip\":false}],[{\"isHit\":false,\"isShip\":false},{\"isHit\":false,\"isShip\":false},{\"isHit\":false,\"isShip\":false}]],\"shipList\":[{\"width\":2,\"height\":1,\"name\":\"test\",\"isSunk\":false,\"shipSections\":[{\"isShip\":true,\"isHit\":true,\"shipName\":\"test\"},{\"isShip\":true,\"isHit\":false,\"shipName\":\"test\"}]}],\"size\":3}";

        this.mockMvc.perform(builder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().string(testBoardJSON))
                .andDo(MockMvcResultHandlers.print());
    }

    @Test
    void testHitShipUpdatesHitStatusOfOceanOnComputerBoard() throws Exception {
        Boardable testBoard = new TestClasses.TestBoard();
        Mockito.when(boardRepository.getComputerBoard()).thenReturn(testBoard);
        Mockito.when(hitValidator.isValid(Mockito.isA(Boardable.class), Mockito.isA(Request.class))).thenReturn(true);


        MockHttpServletRequestBuilder builder =
                MockMvcRequestBuilders.patch("/board/hit")
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .accept(MediaType.APPLICATION_JSON)
                        .characterEncoding("UTF-8")
                        .content(getHitRequestInJSON(0,0));

        //Returned board with ocean hit.
        String testBoardJSON = "{\"name\":\"testBoard\",\"grid\":[[{\"isHit\":true,\"isShip\":false},{\"isHit\":false,\"isShip\":false},{\"isHit\":false,\"isShip\":false}],[{\"isHit\":false,\"isShip\":false},{\"isHit\":false,\"isShip\":false},{\"isHit\":false,\"isShip\":false}],[{\"isHit\":false,\"isShip\":false},{\"isHit\":false,\"isShip\":false},{\"isHit\":false,\"isShip\":false}]],\"shipList\":[],\"size\":3}";

        this.mockMvc.perform(builder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().string(testBoardJSON))
                .andDo(MockMvcResultHandlers.print());
    }

    @Test
    void testHitShipThrowsExceptionIfSpaceHasAlreadyBeenHit() throws Exception{
        Boardable testBoard = new TestClasses.TestBoard();

        Mockito.when(boardRepository.getComputerBoard()).thenReturn(testBoard);
        Mockito.when(hitValidator.isValid(Mockito.isA(Boardable.class), Mockito.isA(Request.class))).thenReturn(false);



        MockHttpServletRequestBuilder builder =
                MockMvcRequestBuilders.patch("/board/hit")
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .accept(MediaType.APPLICATION_JSON)
                        .characterEncoding("UTF-8")
                        .content(getHitRequestInJSON(0,0));

        this.mockMvc.perform(builder)
                .andExpect(MockMvcResultMatchers.status().isBadRequest())
                .andExpect(MockMvcResultMatchers.content().string(containsString("That is not a valid target!")));
    }

    private String getPlaceRequestInJSON(Ship ship, int row, int col){
        return "{\"ship\": { \"name\":\"" + ship.getName() + "\", \"width\": " + ship.getWidth() + ", \"height\": "+ ship.getHeight() + ", \"isSunk\": "+ ship.getIsSunk() + ", \"shipSections\": [{\"isHit\": false, \"isShip\": true, \"shipName\": \"test\"},{\"isHit\": false, \"isShip\": true, \"shipName\": \"test\"}]}, \"row\": " + row + " , \"col\": " + col + "}";

    }

    private String getHitRequestInJSON(int row, int col){
        return "{\"row\": " + row + ", \"col\": " + col +"}";
    }
}

