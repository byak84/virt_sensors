package ru.servapp;

import jssc.SerialPort;
import jssc.SerialPortException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Component
public class PortManager {

    //    @Value("/home/byak/pipe1")
    private String comPort = "/home/byak/pipe1";

    @Autowired
    private V_manager v_manager;

    private final SerialPort serialPort = new SerialPort(comPort);

    @PostConstruct
    public void init() {
        try {
            serialPort.openPort();
            serialPort.setParams(SerialPort.BAUDRATE_9600,
                    SerialPort.DATABITS_8,
                    SerialPort.STOPBITS_1,
                    SerialPort.PARITY_NONE);
            serialPort.setFlowControlMode(SerialPort.FLOWCONTROL_RTSCTS_IN |
                    SerialPort.FLOWCONTROL_RTSCTS_OUT);

            serialPort.addEventListener(serialPortEvent -> {
                try {

                    byte[] buffer = serialPort.readBytes(1);

                    v_manager.GetData(buffer[0] & 0xFF);
//                    if ((buffer[0] & 0xFF) == 170) System.out.println("Сброс шины");
//                    System.out.println("Данные из порта: " + Integer.toString(buffer[0] & 0xFF));
//                    System.out.println("--------------");

                } catch (SerialPortException e) {
                    System.out.println("Лажа с портом");
                }

            }, SerialPort.MASK_RXCHAR);
        } catch (SerialPortException e) {
            e.printStackTrace();
        }

    }
}