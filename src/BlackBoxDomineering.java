import java.util.Scanner;

/**
 * Created by Sam on 20/03/2016.
 */
public class BlackBoxDomineering implements MoveChannel<DomineeringMove> {

    @Override
    public DomineeringMove getMove() {
        Scanner in = new Scanner(System.in);
        String ln = in.nextLine();

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
        System.out.println(move);
        System.out.flush();
    }

    @Override
    public void comment(String msg) {
        System.err.println(msg);
    }

    @Override
    public void end(int value) {
        System.err.println("Game over. The result is " + value);
    }

    public static void main(String[] args) {
        assert (args.length == 4);
        assert (args[0].equals("first") || args[0].equals("second"));
        assert (args[1].equals("horizontal") || args[1].equals("vertical"));
        assert (Integer.parseInt(args[2]) > 0 && Integer.parseInt(args[3]) > 0);

        DomineeringBoard b = new DomineeringBoard(Integer.parseInt(args[2]), Integer.parseInt(args[3]));
        if (args[0].equals("first") && args[1].equals("horizontal"))
            b.tree().firstPlayer(new BlackBoxDomineering());
        else if (args[0].equals("second") && args[1].equals("vertical"))
            b.tree().secondPlayer(new BlackBoxDomineering());
        else
            System.exit(0);
    }

}
