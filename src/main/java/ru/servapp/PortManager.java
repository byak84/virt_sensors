package ru.servapp;

import jssc.SerialPort;
import jssc.SerialPortEvent;
import jssc.SerialPortEventListener;
import jssc.SerialPortException;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Component
public class PortManager {

    //    @Value("/home/byak/pipe1")
    private String comPort = "/home/byak/pipe1";

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

            serialPort.addEventListener(new SerialPortEventListener() {
                @Override
                public void serialEvent(SerialPortEvent serialPortEvent) {
                    try {

                        byte[] buffer = serialPort.readBytes();
//                        String str = serialPort.readHexString();
//                        System.out.println(buffer.length);
                        System.out.println("Данные из порта: " + Integer.toString(buffer[0] & 0xFF));

                    } catch (SerialPortException e) {
                        System.out.println("Лажа с портом");
                    }

                }
            }, SerialPort.MASK_RXCHAR);
        } catch (SerialPortException e) {
            e.printStackTrace();
        }

    }
}