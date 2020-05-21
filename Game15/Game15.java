package com.javarush.games.Game15;

import com.javarush.engine.cell.*;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Game15 extends Game {
    private static final int SIDE = 4;
    private boolean isGameStopped = false;
    private int zeroPos;
    private List<Integer> sortedList;
    private List<Integer> gameList;

    @Override
    public void initialize() {
        sortedList = new ArrayList<>();
        for (int i = 1; i < SIDE*SIDE; i++) {
            sortedList.add(i);
        }
        sortedList.add(0);
        setScreenSize(SIDE, SIDE);
        createGame();
    }

    private void createGame() {
        gameList = new ArrayList<>(sortedList);
        Collections.shuffle(gameList);
        zeroPos = gameList.indexOf(0);
        if (isWinlessPosition()) {
            int temp = gameList.get(0);
            gameList.set(0, gameList.get(1));
            gameList.set(1, temp);
        }
        drawScene();
    }

    private void drawScene() {
        for (int i = 0; i < SIDE*SIDE; i++) {
            int x = i % SIDE;
            int y = i / SIDE;
            setCellColor(x, y, Color.LEMONCHIFFON);
            if (gameList.get(i) != 0) {
                setCellNumber(x, y, gameList.get(i));
            }
            else {
                setCellValue(x, y, "");
            }
        }
    }

    private boolean isSorted (List<Integer> list) {
        return list.equals(sortedList);
    }

    private boolean isWinlessPosition() {
        int check = 0;
        for (int i = 0; i < SIDE*SIDE; i++) {
            for (int j = i + 1; j < SIDE*SIDE; j++) {
                if ((gameList.get(j) < gameList.get(i)) && (gameList.get(j) !=0)) {
                    check++;
                }
            }
        }
        check += (zeroPos / SIDE + 1);
        return (check % 2 == 1);
    }

    private void moveLeft() {
        if (zeroPos % SIDE == 0) {
            return;
        }
        gameList.set(zeroPos, gameList.get(zeroPos - 1));
        gameList.set(zeroPos - 1, 0);
        zeroPos--;
    }

    private void moveRight() {
        if (zeroPos % SIDE == SIDE - 1) {
            return;
        }
        gameList.set(zeroPos, gameList.get(zeroPos + 1));
        gameList.set(zeroPos + 1, 0);
        zeroPos++;
    }

    private void moveUp() {
        if (zeroPos / SIDE == 0) {
            return;
        }
        gameList.set(zeroPos, gameList.get(zeroPos - SIDE));
        gameList.set(zeroPos - SIDE, 0);
        zeroPos -= SIDE;
    }

    private void moveDown() {
        if (zeroPos / SIDE == SIDE - 1) {
            return;
        }
        gameList.set(zeroPos, gameList.get(zeroPos + SIDE));
        gameList.set(zeroPos + SIDE, 0);
        zeroPos += SIDE;
    }

    @Override
    public void onKeyPress(Key key) {
        if ((isGameStopped) && (key != Key.SPACE)) {
            return;
        }
        if ((isGameStopped) && (key == Key.SPACE)) {
            isGameStopped = false;
            createGame();
            return;
        }
        switch (key) {
            case LEFT:
                moveLeft();
                break;
            case RIGHT:
                moveRight();
                break;
            case UP:
                moveUp();
                break;
            case DOWN:
                moveDown();
                break;
            case SPACE:
                isGameStopped = false;
                createGame();
            default:
        }
        drawScene();
        if (isSorted(gameList)) {
            isGameStopped = true;
            win();
        }
    }

    private void win() {
        isGameStopped = true;
        showMessageDialog(Color.LIGHTGREY, "YOU WON!!!", Color.DARKGREEN, 75);
    }
}
