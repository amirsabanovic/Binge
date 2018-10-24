package mobile.binge;

import java.util.List;

public class MovieModelResults {

    private List<MovieModel> results;

    public MovieModelResults(List<MovieModel> results) {
        this.results = results;
    }

    public List<MovieModel> getResults() {
        return results;
    }

    public void setResults(List<MovieModel> results) {
        this.results = results;
    }
}
