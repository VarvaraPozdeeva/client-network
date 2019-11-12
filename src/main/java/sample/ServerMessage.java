package sample;

import com.fasterxml.jackson.annotation.JsonIgnore;

import java.util.Date;

public class ServerMessage
{
    private String from;
    private int message;
    private String topic;
    @JsonIgnore
    private Date time = new Date();

    public ServerMessage() {}

    public ServerMessage(String from, int message, String topic)
    {
	this.from = from;
	this.message = message;
	this.topic = topic;
    }

    public String getFrom()
    {
        return from;
    }

    public void setFrom(String from)
    {
        this.from = from;
    }

    public int getMessage()
    {
        return message;
    }

    public void setMessage(int message)
    {
        this.message = message;
    }

    public String getTopic()
    {
        return topic;
    }

    public void setTopic(String topic)
    {
        this.topic = topic;
    }

    public Date getTime()
    {
        return time;
    }

    public String toString()
    {
        return "{\"from\":\""+ from +"\",\"topic\": \""+topic+"\", \"message\": "+message+"}";
    }
}
