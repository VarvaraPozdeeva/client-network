package sample;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ClientMessage {
    private String from;
    private int text;
}
