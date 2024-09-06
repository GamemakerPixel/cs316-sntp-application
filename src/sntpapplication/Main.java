package sntpapplication;

import java.io.IOException;
import java.net.*;
import java.nio.ByteBuffer;
import java.time.Instant;
import java.time.ZoneId;
import java.time.ZonedDateTime;

public class Main {
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

        try (DatagramSocket socket = new DatagramSocket()){
            byte[] sntp_request = new byte[] {0x01, 0x23, 0x45, 0x67};

            DatagramPacket request_packet = new DatagramPacket(
                    sntp_request,
                    sntp_request.length,
                    server_ip,
                    server_port
            );
            socket.send(request_packet);

            DatagramPacket reply_packet = new DatagramPacket(new byte[4], 4);
            socket.receive(reply_packet);

            int secondsSinceEpochSigned = ByteBuffer.wrap(reply_packet.getData()).getInt();
            long secondsSinceEpoch = Integer.toUnsignedLong(secondsSinceEpochSigned);

            System.out.println(secondsSinceEpoch);

            Instant instant = Instant.ofEpochSecond(secondsSinceEpoch);
            ZoneId timezone = ZoneId.of("America/Indianapolis");
            ZonedDateTime time = ZonedDateTime.ofInstant(instant, timezone);
            time = time.minusYears(70);

            System.out.println(time);
        } catch (IOException exception) {
            System.out.println("Could not allocate socket.");
        }
    }

    private static void process_args(String[] args) throws IllegalArgumentException, UnknownHostException{
        if (args.length != 2) {
            throw new IllegalArgumentException();
        }

        server_ip = InetAddress.getByName(args[0]);
        server_port = Integer.parseInt(args[1]);
    }
}
