package simran_preet.com.funfacts;

/**
 * Created by jc on 3/22/15.
 */
public class Fact
{
    private String fact;
    private String objectId;
    public String getObjectId() {
        return objectId;
    }

    public void setObjectId(String objectId) {
        this.objectId = objectId;
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
        return String.format("%s", fact);
    }
}
