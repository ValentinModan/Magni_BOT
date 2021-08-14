package api.json;


import java.util.List;

public class ChallengeJson
{

    private List<Input> in;

    private List<Output> out;

    public List<Input> getIn()
    {
        return in;
    }

    public void setIn(List<Input> in)
    {
        this.in = in;
    }

    public List<Output> getOut()
    {
        return out;
    }

    public void setOut(List<Output> out)
    {
        this.out = out;
    }
}
