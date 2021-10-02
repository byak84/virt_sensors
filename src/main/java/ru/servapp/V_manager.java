package ru.servapp;

import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;

@Component
public class V_manager {
    private final List<V_sensor> v_sens_array = new ArrayList<>();
    private boolean isBusInit = false;
    private V_sensor current_VSensor;

    @PostConstruct
    private void init() {
        current_VSensor = null;
        try {
            create_VSensors(80, 20, 30);
            create_VSensors(129, 20, 30);
            create_VSensors(128, 20, 30);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public void create_VSensors(int sensor_id, double min_temp, double max_temp) throws Exception {
        V_sensor v_sensor = getByID(sensor_id);
        if (v_sensor == null) v_sens_array.add(new V_sensor(sensor_id, min_temp, max_temp));
        else {
            throw new Exception("Такой датчик уже есть");
        }
    }

    public V_sensor getByID(int sensor_id) {
        V_sensor tmp = null;
        for (V_sensor v_sensor : v_sens_array) {
            if (v_sensor.getSensor_id() == sensor_id) {
                tmp = v_sensor;
                break;
            }
        }
        return tmp;
    }

    public char[] getVSensorInfo(int sensor_id) {
        V_sensor v_sensor = getByID(sensor_id);
        if (v_sensor != null) {
            System.out.println(v_sensor.getTemp());
        }
        return null;
    }

    public void GetData(int data) {
        if (data == 170) {
            System.out.println("Сброс шины");
            this.isBusInit = true;
        }
        else if (isBusInit && current_VSensor == null) {
            V_sensor v_sensor = getByID(data);
            if (v_sensor == null) {
                System.out.println("Нет датчика с id = " + data);
            }
            else {
                current_VSensor = v_sensor;
                System.out.println("Найден датчик с id "+ data);
            }
        }
        if (current_VSensor != null && isBusInit) {
            if (data == 240) {
                System.out.println("Режим чтения данных из датчика");
                isBusInit = false;
                current_VSensor = null;
            }
        }
    }
}
