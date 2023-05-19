package com.uca.controller;

import com.uca.core.PossessionCore;
import com.uca.core.SessionManager;
import com.uca.core.UserCore;
import org.eclipse.jetty.http.HttpStatus;
import org.junit.jupiter.api.BeforeEach;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

public class PexControllerTest {
    HttpStatus.Code snippet;
    @Mock
    private PossessionCore possessionCore;

    @Mock
    private UserCore userCore;

    @Mock
    private SessionManager sessionManager;

    private PexController pexController;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);

    }
    /*

    @Test
    public void testAddPexToPossession() throws IllegalRouteException, NotFoundException, NeedToConnectException {
        int possID = 1;
        UserEntity userEntity = new UserEntity();
        userEntity.setPoints(10);
        PossessionEntity possessionEntity = new PossessionEntity();
        possessionEntity.setId(possID);

        Mockito.when(possessionCore.getPossessionById(possID)).thenReturn(possessionEntity);
        Mockito.when(sessionManager.isConnected(Mockito.any(), Mockito.any())).thenReturn(true);
        Mockito.when(userCore.getConnectedUser(Mockito.any(), Mockito.any())).thenReturn(userEntity);

        pexController.addPexToPossession(Mockito.mock(Request.class), Mockito.mock(Response.class));

        Mockito.verify(possessionCore).pexPossession(possessionEntity);
        Mockito.verify(userCore).update(userEntity);
        assertEquals(9, userEntity.getPoints());
    }

    @Test
    public void testAddPexToPossessionWhenNotConnected() throws IllegalRouteException, NotFoundException {
        Mockito.when(sessionManager.isConnected(Mockito.any(), Mockito.any())).thenReturn(false);

        assertThrows(NeedToConnectException.class, () -> pexController.addPexToPossession(Mockito.mock(Request.class), Mockito.mock(Response.class)));
    }

    @Test
    public void testAddPexToPossessionWhenUserHasNoPoints() throws IllegalRouteException, NotFoundException {
        UserEntity userEntity = new UserEntity();
        userEntity.setPoints(0);

        Mockito.when(sessionManager.isConnected(Mockito.any(), Mockito.any())).thenReturn(true);
        Mockito.when(userCore.getConnectedUser(Mockito.any(), Mockito.any())).thenReturn(userEntity);

        assertThrows(NeedToConnectException.class, () -> pexController.addPexToPossession(Mockito.mock(Request.class), Mockito.mock(Response.class)));
    }

    @Test
    public void testAddPexToPossessionWhenPossessionNotFound() throws IllegalRouteException, NeedToConnectException {
        Mockito.when(possessionCore.getPossessionById(Mockito.anyInt())).thenReturn(null);

        assertThrows(NotFoundException.class, () -> pexController.addPexToPossession(Mockito.mock(Request.class), Mockito.mock(Response.class)));
    }

    @Test
    public void testAddPexToPossessionWhenIllegalRoute() throws IllegalRouteException, NotFoundException, NeedToConnectException {
        Mockito.when(possessionCore.getPossessionById(Mockito.anyInt())).thenReturn(null);

        assertThrows(IllegalRouteException.class, () -> pexController.addPexToPossession(Mockito.mock(Request.class), Mockito.mock(Response.class)));
    }

     */
}