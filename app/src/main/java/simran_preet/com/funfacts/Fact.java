package simran_preet.com.funfacts;

/**
 * Created by jc on 3/22/15.
 */
public class Fact
{
    private long id;
    private String fact;

    public long getId()
    {
        return id;
    }

    public void setId(long id)
    {
        this.id = id;
    }

    public String getFact()
    {
        return fact;
    }

    public void setFact(String fact)
    {
        this.fact = fact;
    }

    @Override
    public String toString()
    {
        return String.format("%d: %s", id, fact);
    }
}
