package com.geekbrains.server;

import com.geekbrains.CommonConstants;
import com.geekbrains.server.authorization.AuthService;
import com.geekbrains.server.authorization.InMemoryAuthServiceImpl;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class Server {
    private final AuthService authService;

    private List<ClientHandler> connectedUsers;

    public Server() {
        authService = new InMemoryAuthServiceImpl();
        try (ServerSocket server = new ServerSocket(CommonConstants.SERVER_PORT)) {
            authService.start();
            connectedUsers = new ArrayList<>();
            while (true) {
                System.out.println("Сервер ожидает подключения");
                Socket socket = server.accept();
                System.out.println("Клиент подключился");
                new ClientHandler(this, socket);
            }
        } catch (IOException exception) {
            System.out.println("Ошибка в работе сервера");
            exception.printStackTrace();
        } finally {
            if (authService != null) {
                authService.end();
            }
        }
    }

    public AuthService getAuthService() {
        return authService;
    }

    public boolean isNickNameBusy(String nickName) {
        for(ClientHandler handler: connectedUsers) {
            if (handler.getNickName().equals(nickName)) {
                return true;
            }
        }

        return false;
    }

    public synchronized void broadcastMessage(String message) {
        if (message.contains("/w first_user")){
            String arr[] = message.split(" ");
            String res[] = new String[arr.length - 1];
            for (int i = 1; i < arr.length; i++){
                res[i - 1] = arr[i];
            }
            for (ClientHandler handler1: connectedUsers){
                if (handler1.getNickName().equals(res[1])){
                    handler1.sendMessage(arr[0] + " " + res[2]);
                }
            }
        } else if (message.contains("/w second_user")){
            String arr[] = message.split(" ");
            String res[] = new String[arr.length - 1];
            for (int i = 1; i < arr.length; i++){
                res[i - 1] = arr[i];
            }
            for (ClientHandler handler1: connectedUsers){
                if (handler1.getNickName().equals(res[1])){
                    handler1.sendMessage(arr[0] + " " + res[2]);
                }
            }
        } else if (message.contains("/w third_user")){
            String arr[] = message.split(" ");
            String res[] = new String[arr.length - 1];
            for (int i = 1; i < arr.length; i++){
                res[i - 1] = arr[i];
            }
            for (ClientHandler handler1: connectedUsers){
                if (handler1.getNickName().equals(res[1])){
                    handler1.sendMessage(arr[0] + " " + res[2]);
                }
            }
        } else {
            for(ClientHandler handler: connectedUsers) {
                handler.sendMessage(message);
            }
        }
    }

    public synchronized void addConnectedUser(ClientHandler handler) {
        connectedUsers.add(handler);
    }

    public synchronized void disconnectUser(ClientHandler handler) {
        connectedUsers.remove(handler);
    }

}
