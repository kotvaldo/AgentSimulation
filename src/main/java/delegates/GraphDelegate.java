package delegates;

import OSPABA.ISimDelegate;
import OSPABA.SimState;
import OSPABA.Simulation;
import Statistics.Average;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.data.xy.XYSeries;
import simulation.MySimulation;

import javax.swing.*;
import java.sql.Time;

public class GraphDelegate implements ISimDelegate {

    private final XYSeries orderTimeSeries;
    private final XYSeries intervalLower;
    private final XYSeries intervalUpper;
    private final JFreeChart chart;

    public GraphDelegate(XYSeries orderTimeSeries, XYSeries intervalLower, XYSeries intervalUpper, JFreeChart chart) {
        this.orderTimeSeries = orderTimeSeries;
        this.intervalLower = intervalLower;
        this.intervalUpper = intervalUpper;
        this.chart = chart;
    }

    @Override
    public void simStateChanged(Simulation simulation, SimState simState) {

    }

    @Override
    public void refresh(Simulation simulation) {
        MySimulation mySim = (MySimulation) simulation;
        Average timeAverage = mySim.getTimeOfWorkAverage();
        timeAverage.confidenceInterval();
        if(mySim.getActualRepCount() > mySim.getBurnInCount()) {
            if (!mySim.isSlowMode()) {
                SwingUtilities.invokeLater(() -> {
                    int rep = mySim.getActualRepCount();
                    double mean = timeAverage.mean();
                    orderTimeSeries.add(rep, mean);
                    intervalLower.add(rep, mySim.getTimeOfWorkAverage().getLowerBound());
                    intervalUpper.add(rep, mySim.getTimeOfWorkAverage().getUpperBound());
                });
                setRangeAxis(intervalLower.getMinY(), intervalUpper.getMaxY());
            }


        }

    }

    private void setRangeAxis(double minY, double maxY) {
        SwingUtilities.invokeLater(() -> {
            chart.getXYPlot().getRangeAxis().setRange(minY - 1, maxY + 1);
        });
    }

}
