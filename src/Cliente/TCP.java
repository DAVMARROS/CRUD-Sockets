/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Cliente;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ConnectException;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author david
 */
public class TCP {
    public static Vector<Vector<String>> Peticion(Vector data)
    {
        Vector<Vector<String>> receivedData = new Vector<Vector<String>>();
        Socket clientSocket;
        try {
            clientSocket = new Socket("192.168.43.16", 6789);
            ObjectOutputStream oos = new ObjectOutputStream(clientSocket.getOutputStream());
            oos.writeObject(data);
            ObjectInputStream ois = new ObjectInputStream(clientSocket.getInputStream());
            receivedData = (Vector) ois.readObject();
            ois.close();
            oos.close();
            clientSocket.close();
            return receivedData;
        } catch (IOException ex) {
            Logger.getLogger(TCP.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(TCP.class.getName()).log(Level.SEVERE, null, ex);
        } 
        return null;
    } 
}
