import java.util.Scanner;

/**
 * Created by Sam on 09/03/2016.
 */
public class CommandLineDomineering implements MoveChannel<DomineeringMove> {

    @Override
    public DomineeringMove getMove() {
//        return DomineeringMove.valueOf(System.console().readLine("Enter your move: "));
        System.out.println("Enter your move in the form \"x,y\": ");
        Scanner in = new Scanner(System.in);
        String ln = in.nextLine();
//        String ln = in.nextLine();
        assert (ln.length() == 3);
        assert (ln.charAt(1) == ',');
        int x = Integer.parseInt(ln.split(",")[0]);
        int y = Integer.parseInt(ln.split(",")[1]);
        assert(x >= 0);
        assert(y >= 0);
        return new DomineeringMove(x, y);
    }

    @Override
    public void giveMove(DomineeringMove move) {
        System.out.println("Computer plays " + move);
    }

    @Override
    public void end(int value) {
        System.out.println("Game over. The result is " + value);
    }

    @Override
    public void comment(String msg) {
        System.out.println(msg);
    }

    public static void main(String[] args) {
        DomineeringBoard board = new DomineeringBoard();
//        board.tree().firstPlayer(new CommandLineDomineering());
        board.tree().secondPlayer(new CommandLineDomineering());
    }

}
