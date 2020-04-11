package it.polimi.ingsw.model;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;


import static org.junit.Assert.*;


public class PlayerTest {

    private Worker worker;
    private Worker worker2;
    private Player player;
    private Game game;
    private Board board;

    @Before
    public void setUp() {
        game = new Game(2);
        game.addPlayer("player");
        game.addPlayer("player2");
        board = game.getBoard();
        player = game.getPlayers().get(0);
        worker = player.getWorkers().get(0);
        worker2 = player.getWorkers().get(1);
    }

    @After
    public void tearDown() {
        game = null;
        board = null;
        player = null;
        worker = null;
    }

    @Test
    public void testGetNickname() {

        assertEquals("player", player.getNickname());
    }


    @Test
    public void testGetGod() {

        assertNull(player.getGod());


    }
/*
    @Test
    public void testChooseGod() {

        God apollo = new Apollo();
        God artemis = new Artemis();
        God athena = new Athena();
        God chosengod;


        ArrayList<God> gods = new ArrayList<God>(game.getNumberOfPlayers());
        gods.add(apollo);
        gods.add(artemis);
        gods.add(athena);




        int i=0, chosenOne;

        //dalla lista dei chosenGods del gioco sceglierne uno dei 3 e assegnarlo al God di questo player
        //todo:
        //dal controller ricevo l'input che la view deve dare per poter scegliere uno dei god tra cui scegliere
        //intanto lo faccio qui per semplicità

        System.out.println("Choose one God among the following.");
        Scanner input = new Scanner(System.in);
        chosenOne = input.nextInt();   //scelgo il god della lista con un numero da 1 a 3
        chosengod = gods.get(chosenOne-1);
        while(i<game.getNumberOfPlayers()){
            //Se il giocatore è diverso e il dio scelto è lo stesso allora faccio riscegliere il dio
            if(!this.equals(game.getPlayers().get(i)) && chosengod.equals(game.getPlayers().get(i).getGod())){
                System.out.println("This god has already been chosen. Pick another!\n");
                chosenOne = input.nextInt();   //scelgo il god della lista con un numero da 1 a 3
                chosengod = gods.get(chosenOne-1);
            }
            else{
                i++;
            }
        }








    }
*/
    @Test
    public void testChooseWorker() {


    }

    @Test
    public void testGetColor() {

        player.setColor(Color.BLUE);

        assertEquals(Color.BLUE, player.getColor());

        player.setColor(Color.WHITE);

        assertEquals(Color.WHITE, player.getColor());

        player.setColor(Color.BEIGE);

        assertEquals(Color.BEIGE, player.getColor());

    }

    @Test
    public void testSetColor() {

        player.setColor(Color.BLUE);

        assertEquals(Color.BLUE, player.getColor());

        player.setColor(Color.WHITE);

        assertEquals(Color.WHITE, player.getColor());

        player.setColor(Color.BEIGE);

        assertEquals(Color.BEIGE, player.getColor());
    }


    @Test
    public void testGetWorkers() {
        assertEquals(worker,player.getWorkers().get(0));
        assertEquals(worker2,player.getWorkers().get(1));
    }

    @Test
    public void testGetGame() {

        assertEquals(game, player.getGame());
    }
}