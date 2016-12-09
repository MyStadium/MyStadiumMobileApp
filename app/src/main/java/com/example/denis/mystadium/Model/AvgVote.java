package com.example.denis.mystadium.Model;

/**
 * Created by denis on 09-12-16.
 */

public class AvgVote {
    private float ambiance;
    private float niveau;
    private float fairplay;

    public AvgVote() {
    }

    public AvgVote(float ambiance, float niveau, float fairplay) {
        this.ambiance = ambiance;
        this.niveau = niveau;
        this.fairplay = fairplay;
    }

    /**
     * @return the ambiance
     */
    public float getAmbiance() {
        return ambiance;
    }

    /**
     * @param ambiance the ambiance to set
     */
    public void setAmbiance(float ambiance) {
        this.ambiance = ambiance;
    }

    /**
     * @return the niveau
     */
    public float getNiveau() {
        return niveau;
    }

    /**
     * @param niveau the niveau to set
     */
    public void setNiveau(float niveau) {
        this.niveau = niveau;
    }

    /**
     * @return the fairplay
     */
    public float getFairplay() {
        return fairplay;
    }

    /**
     * @param fairplay the fairplay to set
     */
    public void setFairplay(float fairplay) {
        this.fairplay = fairplay;
    }

}
