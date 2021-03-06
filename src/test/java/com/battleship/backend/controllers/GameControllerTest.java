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
import org.springframework.boot.test.mock.mockito.SpyBean;
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

    @MockBean
    private ComputerPlayer computerPlayer;

    @SpyBean
    private Game game;

    @Test
    public void getBoardsReturnsBothBoards() throws Exception {

        Mockito.when(game.getBoards()).thenReturn(boards);

        this.mockMvc.perform(get("/boards")).andDo(print()).andExpect(status().isOk()).andExpect(content().string(containsString("test")));
    }


    @Test
    public void placeShipPatchesShipOntoPlayerBoard() throws Exception {
        Mockito.when(game.getPlayerBoard()).thenReturn(new TestClasses.TestBoard());
        Mockito.when(placeValidator.isValid(Mockito.isA(Boardable.class), Mockito.isA(Request.class))).thenReturn(true);
        Mockito.when(game.getIsGameStarted()).thenReturn(false);
        Ship testShip = new Ship("test", 2);

        MockHttpServletRequestBuilder builder =
                MockMvcRequestBuilders.patch("/board/place")
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .accept(MediaType.APPLICATION_JSON)
                        .characterEncoding("UTF-8")
                        .content(getPlaceRequestInJSON(testShip, 0, 0));

        String testBoardJSON = "{\"name\":\"testBoard\",\"grid\":[[{\"isShip\":true,\"isHit\":false,\"shipName\":\"test\"},{\"isShip\":true,\"isHit\":false,\"shipName\":\"test\"},{\"isHit\":false,\"isShip\":false,\"shipName\":null}],[{\"isHit\":false,\"isShip\":false,\"shipName\":null},{\"isHit\":false,\"isShip\":false,\"shipName\":null},{\"isHit\":false,\"isShip\":false,\"shipName\":null}],[{\"isHit\":false,\"isShip\":false,\"shipName\":null},{\"isHit\":false,\"isShip\":false,\"shipName\":null},{\"isHit\":false,\"isShip\":false,\"shipName\":null}]],\"shipList\":[{\"width\":2,\"height\":1,\"name\":\"test\",\"isSunk\":false,\"shipSections\":[{\"isShip\":true,\"isHit\":false,\"shipName\":\"test\"},{\"isShip\":true,\"isHit\":false,\"shipName\":\"test\"}]}],\"size\":3,\"everyShipSunk\":false}";

        this.mockMvc.perform(builder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().string(testBoardJSON))
                .andDo(MockMvcResultHandlers.print());
    }

    @Test
    public void testPlaceShipThrowsExceptionIfInvalid() throws Exception{
        Mockito.when(game.getPlayerBoard()).thenReturn(new TestClasses.TestBoard());
        Mockito.when(placeValidator.isValid(Mockito.isA(Boardable.class), Mockito.isA(Request.class))).thenReturn(false);
        Mockito.when(game.getIsGameStarted()).thenReturn(false);
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
    void testPlaceShipThrowsExceptionIfGameHasAlreadyStarted() throws Exception{
        Mockito.when(game.getIsGameStarted()).thenReturn(true);

        Ship testShip = new Ship("test", 2);

        MockHttpServletRequestBuilder builder =
                MockMvcRequestBuilders.patch("/board/place")
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .accept(MediaType.APPLICATION_JSON)
                        .characterEncoding("UTF-8")
                        .content(getPlaceRequestInJSON(testShip, 2, 0));

        this.mockMvc.perform(builder)
                .andExpect(MockMvcResultMatchers.status().isBadRequest())
                .andExpect(MockMvcResultMatchers.content().string(containsString("The game has already started!")));
    }

    @Test
    void testPlaceShipRemovesShipFromOldSpotIfItsAlreadyOnBoard() throws Exception{
        Boardable testBoard = new TestClasses.TestBoard();
        Ship testShip = new Ship("test", 2);
        testBoard.addShip(testShip, 2, 0);

        Mockito.when(game.getIsGameStarted()).thenReturn(false);
        Mockito.when(game.getPlayerBoard()).thenReturn(testBoard);
        Mockito.when(placeValidator.isValid(Mockito.isA(Boardable.class), Mockito.isA(Request.class))).thenReturn(true);



        MockHttpServletRequestBuilder builder =
                MockMvcRequestBuilders.patch("/board/place")
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .accept(MediaType.APPLICATION_JSON)
                        .characterEncoding("UTF-8")
                        .content(getPlaceRequestInJSON(testShip, 0, 0));

        String testBoardJSON = "{\"name\":\"testBoard\",\"grid\":[[{\"isShip\":true,\"isHit\":false,\"shipName\":\"test\"},{\"isShip\":true,\"isHit\":false,\"shipName\":\"test\"},{\"isHit\":false,\"isShip\":false,\"shipName\":null}],[{\"isHit\":false,\"isShip\":false,\"shipName\":null},{\"isHit\":false,\"isShip\":false,\"shipName\":null},{\"isHit\":false,\"isShip\":false,\"shipName\":null}],[{\"isHit\":false,\"isShip\":false,\"shipName\":null},{\"isHit\":false,\"isShip\":false,\"shipName\":null},{\"isHit\":false,\"isShip\":false,\"shipName\":null}]],\"shipList\":[{\"width\":2,\"height\":1,\"name\":\"test\",\"isSunk\":false,\"shipSections\":[{\"isShip\":true,\"isHit\":false,\"shipName\":\"test\"},{\"isShip\":true,\"isHit\":false,\"shipName\":\"test\"}]}],\"size\":3,\"everyShipSunk\":false}";

        this.mockMvc.perform(builder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().string(testBoardJSON))
                .andDo(MockMvcResultHandlers.print());

    }

    @Test
    void testHitShipReturnsMessageWithHitShipName() throws Exception {
        Boardable testBoard = new TestClasses.TestBoard();
        Ship testShip = new Ship("test ship", 2);
        testBoard.addShip(testShip, 0, 0);

        Mockito.when(game.getComputerBoard()).thenReturn(testBoard);
        Mockito.when(hitValidator.isValid(Mockito.isA(Boardable.class), Mockito.isA(Request.class))).thenReturn(true);
        Mockito.when(game.getIsGameStarted()).thenReturn(true);
        Mockito.doReturn(new Boardable[] {testBoard}).when(game).getBoards();
        Mockito.doReturn(false).when(game).isGameOver();


        MockHttpServletRequestBuilder builder =
                MockMvcRequestBuilders.patch("/board/hit")
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .accept(MediaType.APPLICATION_JSON)
                        .characterEncoding("UTF-8")
                        .content(getHitRequestInJSON(0,0));

        this.mockMvc.perform(builder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().string("The testBoard's test ship has been hit!"))
                .andDo(MockMvcResultHandlers.print());
    }

    @Test
    void testHitShipReturnsWinnerMessageIfGameHasBeenWon() throws Exception {
        Boardable testBoard = new TestClasses.TestBoard();
        testBoard.addShip(new Ship("testShip", 2), 0, 0);



        Mockito.when(game.getComputerBoard()).thenReturn(testBoard);
        Mockito.when(hitValidator.isValid(Mockito.isA(Boardable.class), Mockito.isA(Request.class))).thenReturn(true);
        Mockito.when(game.getIsGameStarted()).thenReturn(true);
        Mockito.doReturn(true).when(game).isGameOver();
        Mockito.doReturn("Human Player is the Winner!").when(game).getWinner();
        Mockito.doReturn(new Boardable[] {testBoard}).when(game).getBoards();


        MockHttpServletRequestBuilder builder =
                MockMvcRequestBuilders.patch("/board/hit")
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .accept(MediaType.APPLICATION_JSON)
                        .characterEncoding("UTF-8")
                        .content(getHitRequestInJSON(0,0));

        this.mockMvc.perform(builder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().string("The testBoard's testShip has been hit! The Game is Over! Human Player is the Winner!"))
                .andDo(MockMvcResultHandlers.print());
    }

    @Test
    void testHitShipReturnsMissedMessageIfOnlyOceanIsHit() throws Exception{
        Boardable testBoard = new TestClasses.TestBoard();

        Mockito.when(game.getComputerBoard()).thenReturn(testBoard);
        Mockito.when(hitValidator.isValid(Mockito.isA(Boardable.class), Mockito.isA(Request.class))).thenReturn(true);
        Mockito.when(game.getIsGameStarted()).thenReturn(true);
        Mockito.doReturn(false).when(game).isGameOver();
        Mockito.doReturn(new Boardable[] {testBoard}).when(game).getBoards();


        MockHttpServletRequestBuilder builder =
                MockMvcRequestBuilders.patch("/board/hit")
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .accept(MediaType.APPLICATION_JSON)
                        .characterEncoding("UTF-8")
                        .content(getHitRequestInJSON(0,0));

        this.mockMvc.perform(builder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().string("Missed!"))
                .andDo(MockMvcResultHandlers.print());
    }

    @Test
    void testHitShipThrowsExceptionIfSpaceHasAlreadyBeenHit() throws Exception{
        Boardable testBoard = new TestClasses.TestBoard();

        Mockito.when(boardRepository.getComputerBoard()).thenReturn(testBoard);
        Mockito.when(hitValidator.isValid(Mockito.isA(Boardable.class), Mockito.isA(Request.class))).thenReturn(false);
        Mockito.when(game.getIsGameStarted()).thenReturn(true);
        Mockito.doReturn(false).when(game).isGameOver();



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

    @Test
    void testHitShipThrowsExceptionIfGameHasNotStartedYet() throws Exception {
        Mockito.when(game.getIsGameStarted()).thenReturn(false);
        MockHttpServletRequestBuilder builder =
                MockMvcRequestBuilders.patch("/board/hit")
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .accept(MediaType.APPLICATION_JSON)
                        .characterEncoding("UTF-8")
                        .content(getHitRequestInJSON(0,0));

        this.mockMvc.perform(builder)
                .andExpect(MockMvcResultMatchers.status().isBadRequest())
                .andExpect(MockMvcResultMatchers.content().string(containsString("The game cannot be started:")));
    }

    @Test
    void testComputerTurnReturnsMissedMessageIfOceanIsHit() throws Exception{
        Boardable testBoard = new TestClasses.TestBoard();
        game.setComputerPlayer(computerPlayer);

        Mockito.when(game.getPlayerBoard()).thenReturn(testBoard);
        Mockito.when(computerPlayer.generateValidHitRequest()).thenReturn(new Request(null, 0, 0));
        Mockito.when(hitValidator.isValid(Mockito.isA(Boardable.class), Mockito.isA(Request.class))).thenReturn(true);
        Mockito.when(game.getIsGameStarted()).thenReturn(true);

        Mockito.doReturn(false).when(game).isGameOver();
        Mockito.doReturn(new Boardable[] {testBoard}).when(game).getBoards();


        MockHttpServletRequestBuilder builder =
                MockMvcRequestBuilders.post("/board/computer_turn")
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .accept(MediaType.APPLICATION_JSON)
                        .characterEncoding("UTF-8");


        this.mockMvc.perform(builder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().string("Missed!"))
                .andDo(MockMvcResultHandlers.print());
    }

    @Test
    void testComputerTurnReturnsHitShipMessageIfShipIsHit() throws Exception{
        Boardable testBoard = new TestClasses.TestBoard();
        Ship testShip = new Ship("testShip", 2);
        testBoard.addShip(testShip, 0, 0);
        game.setComputerPlayer(computerPlayer);

        Mockito.when(game.getPlayerBoard()).thenReturn(testBoard);
        Mockito.when(computerPlayer.generateValidHitRequest()).thenReturn(new Request(null, 0, 0));
        Mockito.when(hitValidator.isValid(Mockito.isA(Boardable.class), Mockito.isA(Request.class))).thenReturn(true);
        Mockito.when(game.getIsGameStarted()).thenReturn(true);

        Mockito.doReturn(false).when(game).isGameOver();
        Mockito.doReturn(new Boardable[] {testBoard}).when(game).getBoards();


        MockHttpServletRequestBuilder builder =
                MockMvcRequestBuilders.post("/board/computer_turn")
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .accept(MediaType.APPLICATION_JSON)
                        .characterEncoding("UTF-8");


        this.mockMvc.perform(builder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().string("The testBoard's testShip has been hit!"))
                .andDo(MockMvcResultHandlers.print());
    }

    @Test
    void testComputerTurnReturnsWinnerMessageIfGameIsOver() throws Exception{
        Boardable testBoard = new TestClasses.TestBoard();
        Ship testShip = new Ship("testShip", 2);
        testBoard.addShip(testShip, 0, 0);

        game.setComputerPlayer(computerPlayer);

        Mockito.when(game.getPlayerBoard()).thenReturn(testBoard);
        Mockito.when(game.getComputerBoard()).thenReturn(testBoard);
        Mockito.when(computerPlayer.generateValidHitRequest()).thenReturn(new Request(null, 0, 0));
        Mockito.when(hitValidator.isValid(Mockito.isA(Boardable.class), Mockito.isA(Request.class))).thenReturn(true);
        Mockito.doReturn("Computer Player is the Winner!").when(game).getWinner();
        Mockito.when(game.getIsGameStarted()).thenReturn(true);

        Mockito.doReturn(true).when(game).isGameOver();
        Mockito.doReturn(new Boardable[] {testBoard}).when(game).getBoards();


        MockHttpServletRequestBuilder builder =
                MockMvcRequestBuilders.post("/board/computer_turn")
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .accept(MediaType.APPLICATION_JSON)
                        .characterEncoding("UTF-8");


        this.mockMvc.perform(builder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().string("The testBoard's testShip has been hit! The Game is Over! Computer Player is the Winner!"))
                .andDo(MockMvcResultHandlers.print());
    }

    @Test
    void testStartGameStartsTheGameIfGameIsReadyToStart() throws Exception{
        Mockito.doReturn(true).when(game).isPlayerReadyToStart();
        Mockito.doNothing().when(game).start();

        MockHttpServletRequestBuilder builder =
                MockMvcRequestBuilders.post("/game/start")
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .accept(MediaType.APPLICATION_JSON)
                        .characterEncoding("UTF-8");


        this.mockMvc.perform(builder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().string(containsString("Yay game has started")));
    }

    @Test
    void testStartGameThrowsExceptionIfGameIsNotReadyToStart() throws Exception{
        Mockito.doReturn(false).when(game).isPlayerReadyToStart();
        MockHttpServletRequestBuilder builder =
                MockMvcRequestBuilders.post("/game/start")
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .accept(MediaType.APPLICATION_JSON)
                        .characterEncoding("UTF-8");


        this.mockMvc.perform(builder)
                .andExpect(MockMvcResultMatchers.status().isBadRequest())
                .andExpect(MockMvcResultMatchers.content().string(containsString("The game cannot be started:")));
    }

    @Test
    void testStartGameThrowsExceptionIfGameHasAlreadyStarted() throws Exception{
        Mockito.doReturn(true).when(game).getIsGameStarted();

        MockHttpServletRequestBuilder builder =
                MockMvcRequestBuilders.post("/game/start")
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .accept(MediaType.APPLICATION_JSON)
                        .characterEncoding("UTF-8");


        this.mockMvc.perform(builder)
                .andExpect(MockMvcResultMatchers.status().isBadRequest())
                .andExpect(MockMvcResultMatchers.content().string(containsString("The game has already started!")));
    }


    private String getPlaceRequestInJSON(Ship ship, int row, int col){
        return "{\"ship\": { \"name\":\"" + ship.getName() + "\", \"width\": " + ship.getWidth() + ", \"height\": "+ ship.getHeight() + ", \"isSunk\": "+ ship.getIsSunk() + ", \"shipSections\": [{\"isHit\": false, \"isShip\": true, \"shipName\": \"test\"},{\"isHit\": false, \"isShip\": true, \"shipName\": \"test\"}]}, \"row\": " + row + " , \"col\": " + col + "}";

    }

    private String getHitRequestInJSON(int row, int col){
        return "{\"row\": " + row + ", \"col\": " + col +"}";
    }
}


