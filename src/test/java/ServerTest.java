import org.junit.Test;

import java.io.IOException;

import static org.junit.Assert.*;

public class ServerTest {

    @Test
    public void main() {

        String[] serverArgs = {"ab, cd"};
        String[] clientArgs = {"localhost"};
        try {
            Server.main(serverArgs);
            Client.main(clientArgs);
        }
        catch (IOException e) {
            System.out.println("IOException!");
        }
    }
}