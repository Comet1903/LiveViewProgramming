import static java.lang.StringTemplate.STR;

import java.util.Arrays;
import java.util.Optional;

class TicTacToe implements Clerk {
    final String ID;
    final int width, height;
    final String libPath = "clerks/TicTacToe/tictactoe.js";
    LiveView view;
    
    int[] fields = {0,0,0,0,0,0,0,0,0};
    int turn = 1;

    TicTacToe(LiveView view, int width, int height) {
        this.view = view;
        this.width  = Math.max(1, Math.abs(width));  // width is at least of size 1
        this.height = Math.max(1, Math.abs(height)); // height is at least of size 1
        Clerk.load(view, libPath);
        ID = Clerk.getHashID(this);

        Clerk.write(view, STR."""
            <canvas id="tttCanvas\{ID}" width="\{this.width}" height="\{this.height}" style="border:1px solid #000;">
            </canvas>
                """);
        Clerk.script(view, STR."const ttt\{ID} = new TicTacToe(document.getElementById('tttCanvas\{ID}'), 'ttt\{ID}');");
        
        this.view.createResponseContext(STR."/ttt\{ID}", response -> {
            int i = Integer.parseInt(response);
            if (i >= 0 && i < 9) {
                move(i);
                int[] winnerPos = getWinnerPos();
                if (winnerPos.length == 3) {
                    this.sendWinPosition(winnerPos[0], winnerPos[2]);
                }
            }
        });
    }

    TicTacToe(LiveView view) { this(view, 500, 500); }
    TicTacToe(int width, int height) { this(Clerk.view(), width, height); }
    TicTacToe() { this(Clerk.view());}

    int[] getWinnerPos() {
        /*
            code
         */
        return new int[0];
    }

    TicTacToe sendWinPosition(int start, int end) {
        Clerk.call(view, STR."ttt\{ID}.showWinner(\{start}, \{end})");
        return this;
    }

    TicTacToe move(int position) {
        if (fields[position] == 0) {
            fields[position] = turn;
            Clerk.call(view, STR."ttt\{ID}.drawToken(\{turn == 1}, \{position})");
            turn = -turn;            
        }
        return this;
    }
}