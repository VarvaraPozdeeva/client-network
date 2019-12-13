package sample;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import java.util.Date;
@Data
public class ServerMessage
{
    private String from;
    private int status;
    private String idNeA;
    private String idNeZ ;
    private String topic;
    @JsonIgnore
    private Date time = new Date();

    public String toString()
    {
        return "{\"from\":\""+ from +"\",\"topic\": \""+topic+"\", \"status\": "+ status +"}";
    }
}
