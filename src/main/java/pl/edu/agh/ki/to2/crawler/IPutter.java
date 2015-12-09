package pl.edu.agh.ki.to2.crawler;

import java.net.URL;

interface IPutter{
    void put(URL url, int depth);
}