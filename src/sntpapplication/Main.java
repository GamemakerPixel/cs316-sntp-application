package sntpapplication;

import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;

public class Main {
    private static final byte CLIENT_FIRST_BYTE = 0b00100011;

    private static InetAddress server_ip;
    private static int server_port;
    public static void main(String[] args) {
        try{
            process_args(args);
        }
        catch (IllegalArgumentException exception){
            System.out.println("Usage: sntpapplication <ip_address> <port>");
            return;
        }
        catch (UnknownHostException exception) {
            System.out.println("Unknown IP: " + args[0]);
            return;
        }

        try (Socket socket = new Socket()){
            byte[] sntp_request = create_sntp_request();
        } catch (IOException exception) {
            System.out.println("Could not allocate socket.");
            return;
        }
    }

    private static void process_args(String[] args) throws IllegalArgumentException, UnknownHostException{
        if (args.length != 2) {
            throw new IllegalArgumentException();
        }

        server_ip = InetAddress.getByName(args[0]);
        server_port = Integer.parseInt(args[1]);
    }

    private static byte[] create_sntp_request() {
        byte header_first_
    }
}
