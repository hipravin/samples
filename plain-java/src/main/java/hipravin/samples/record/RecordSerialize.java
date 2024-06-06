package hipravin.samples.record;

import java.io.*;
import java.time.LocalDate;

public class RecordSerialize {
    record Employee(
            Long id,
            String firstName,
            String lastName,
            LocalDate birthDate) implements Serializable {
    }

    public static void main(String[] args) {
        Employee employee = new Employee(1L, "John", "Doe", LocalDate.of(2033, 1, 2));

        byte[] serializedBytes = serialize(employee).toByteArray();
        System.out.println(new String(serializedBytes));

        Employee deserialized = deserialize(serialize(employee));
        System.out.println(deserialized);
    }

    public static ByteArrayOutputStream serialize(Employee employee) {
        try (ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream(16);
             ObjectOutputStream out = new ObjectOutputStream(byteArrayOutputStream)) {

            out.writeObject(employee);
            return byteArrayOutputStream;
        } catch (IOException e) {
            throw new UncheckedIOException(e);
        }
    }

    public static Employee deserialize(ByteArrayOutputStream bos) {
        try (ByteArrayInputStream bis = new ByteArrayInputStream(bos.toByteArray());
             ObjectInputStream in = new ObjectInputStream(bis)) {
            return (Employee) in.readObject();
        } catch (IOException e) {
            throw new UncheckedIOException(e);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }
}
