import db.DBConnection;
import view.MainMenuView;

import javax.swing.*;
import java.sql.Connection;

public class AppDriver {
    public static void main(String[] args) {

        // invokeLater() invokes Java to run the GUI on the Event Dispatcher Thread and not on the main thread.
        // If everything was run on the main thread and not seperately on their own, bugs happen.

        SwingUtilities.invokeLater(() -> {                  
            Connection conn = DBConnection.getConnection();
            if (conn != null) {
                new MainMenuView(conn).setVisible(true);
            } else {
                System.out.println("ERROR: Failed to connect to database.");
            }
        });
    }
}
