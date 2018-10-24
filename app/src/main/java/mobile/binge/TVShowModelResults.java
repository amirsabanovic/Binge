package mobile.binge;

import java.util.List;

public class TVShowModelResults {

    private List<TVShowModel> results;

    public TVShowModelResults(List<TVShowModel> results) {
        this.results = results;
    }

    public List<TVShowModel> getResults() {
        return results;
    }

    public void setResults(List<TVShowModel> results) {
        this.results = results;
    }
}
