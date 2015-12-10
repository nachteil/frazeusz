package pl.edu.agh.ki.to2.plotter;

/**
 * Created by Mistrz on 2015-12-10.
 */
public class Main {
    public static void main(String [] args){
        Ploter ploter = new Ploter();
        ViewFrame viewFrame = new ViewFrame();
        ploter.setViewFrame(viewFrame);
        viewFrame.setPloter(ploter);
    }
}
