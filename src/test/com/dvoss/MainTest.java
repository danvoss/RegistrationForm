package com.dvoss;

import org.junit.Test;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;

import static org.junit.Assert.*;

/**
 * Created by Dan on 6/15/16.
 */
public class MainTest {

    public Connection startConnection() throws SQLException {
        Connection conn = DriverManager.getConnection("jdbc:h2:mem:test");
        Main.createTable(conn);
        return conn;
    }

    @Test
    public void testInsert() throws SQLException {
        Connection conn = startConnection();
        User user = new User(1, "Bob", "Address", "Email");
        Main.insertUser(conn, user);
        ArrayList<User> users = Main.selectUsers(conn);
        conn.close();
        assertTrue(users.size() == 1);
        assertTrue(user.username.equals("Bob"));
    }

    @Test
    public void testSelect() throws SQLException {
        Connection conn = startConnection();
        User alice = new User(1, "Alice", "", "");
        User bob = new User(2, "Bob", "", "");
        User charlie = new User(3, "Charlie", "", "");
        Main.insertUser(conn, alice);
        Main.insertUser(conn, bob);
        Main.insertUser(conn, charlie);
        ArrayList<User> users = Main.selectUsers(conn);
        conn.close();
        assertTrue(users.size() == 3);
        assertTrue(users.get(0).username.equals("Alice"));
        assertTrue(users.get(1).username.equals("Bob"));
        assertTrue(users.get(2).username.equals("Charlie"));

    }

    @Test
    public void testUpdate() throws SQLException {
        Connection conn = startConnection();
        User user = new User(1, "Alice", "", "");
        Main.insertUser(conn, user);
        User updatedUser = new User(1, "Albert", "", "");
        Main.updateUser(conn, updatedUser);
        ArrayList<User> users = Main.selectUsers(conn);
        conn.close();
        assertTrue(users.size() == 1);
        assertTrue(user.id == updatedUser.id);
        assertTrue(users.get(0).username.equals("Albert"));
    }

    @Test
    public void testDelete() throws SQLException {
        Connection conn = startConnection();
        User user = new User(1, "Bob", "", "");
        Main.insertUser(conn, user);
        Main.deleteUser(conn, 1);
        ArrayList<User> users = Main.selectUsers(conn);
        conn.close();
        assertTrue(users.size() == 0);
    }
}