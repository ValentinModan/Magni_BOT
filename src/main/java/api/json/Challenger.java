package api.json;

public class Challenger
{
    private String id;
    private String name;
    private String title;
    private Double rating;
    private Boolean provisional;

    public String getId()
    {
        return id;
    }

    public void setId(String id)
    {
        this.id = id;
    }

    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    public String getTitle()
    {
        return title;
    }

    public void setTitle(String title)
    {
        this.title = title;
    }

    public Double getRating()
    {
        return rating;
    }

    public void setRating(Double rating)
    {
        this.rating = rating;
    }

    public Boolean getProvisional()
    {
        return provisional;
    }

    public void setProvisional(Boolean provisional)
    {
        this.provisional = provisional;
    }

    @Override
    public String toString()
    {
        return "Challenger{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", title='" + title + '\'' +
                ", rating=" + rating +
                ", provisional=" + provisional +
                '}';
    }
}
