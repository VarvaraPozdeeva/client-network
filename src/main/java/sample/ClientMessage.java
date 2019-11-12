package sample;

public class ClientMessage
{
    private String from;
    private int text;

    public ClientMessage() {}

    public ClientMessage(String from, int text)
    {
	this.from = from;
	this.text = text;
    }

    public String getFrom()
    {
        return from;
    }

    public void setFrom(String from)
    {
        this.from = from;
    }

    public int getText()
    {
        return text;
    }

    public void setText(int text)
    {
        this.text = text;
    }
}
