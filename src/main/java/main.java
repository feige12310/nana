import com.ksyun.campus.Game;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @Author: Lemon
 * @Date: 2024/7/9 21:35
 */
public class main {
    public static void main(String[] args) {
        Game game = null;
        try {
            game = new Game(2);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        game.startGame();
    }
}