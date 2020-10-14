package com.example.safetapp.Model;

public class CrimeCounter {
    int ChainSnatching;
    int Vandalism;
    int PickPocketing;
    int EveTeasing;

    public CrimeCounter(int kidnapping, int trafficking, int molestation, int rape) {
        this.ChainSnatching = kidnapping;
        this.Vandalism = trafficking;
        this.PickPocketing = molestation;
        this.EveTeasing = rape;
    }

    public CrimeCounter() {
    }

    public int getChainSnatching() {
        return ChainSnatching;
    }

    public void setChainSnatching(int chainSnatching) {
        ChainSnatching = chainSnatching;
    }

    public int getVandalism() {
        return Vandalism;
    }

    public void setVandalism(int vandalism) {
        Vandalism = vandalism;
    }

    public int getPickPocketing() {
        return PickPocketing;
    }

    public void setPickPocketing(int pickPocketing) {
        PickPocketing = pickPocketing;
    }

    public int getEveTeasing() {
        return EveTeasing;
    }

    public void setEveTeasing(int eveTeasing) {
        EveTeasing = eveTeasing;
    }
}
