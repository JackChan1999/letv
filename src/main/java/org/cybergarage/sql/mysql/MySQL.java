package org.cybergarage.sql.mysql;

import java.sql.DriverManager;
import org.cybergarage.sql.Database;
import org.cybergarage.util.Debug;

public class MySQL extends Database {
    public MySQL() {
        try {
            Class.forName("com.mysql.jdbc.Driver").newInstance();
        } catch (Exception e) {
            Debug.warning(e);
        }
    }

    public boolean open(String host, String dbname, String user, String passwd) {
        try {
            setConnection(DriverManager.getConnection("jdbc:mysql://" + host + "/" + dbname, user, passwd));
            return true;
        } catch (Exception e) {
            Debug.warning(e);
            return false;
        }
    }
}
