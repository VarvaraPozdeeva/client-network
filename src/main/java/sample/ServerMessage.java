package sample;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import java.util.Date;
@Data
public class ServerMessage
{
    private String from;
    private int message;
    private String topic;
    @JsonIgnore
    private Date time = new Date();

    public String toString()
    {
        return "{\"from\":\""+ from +"\",\"topic\": \""+topic+"\", \"message\": "+message+"}";
    }
}
