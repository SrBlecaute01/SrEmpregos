package br.com.blecaute.jobs.model.interfaces;

public interface Hook {

    void enable();

    boolean hasSupport();

    String getPluginName();
}