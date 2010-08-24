/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.java.skripsi;

import java.awt.Color;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.xy.XYDataset;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;

public class MseChart {

    private XYDataset dataset;
    private JFreeChart chart;
    private ChartPanel chartPanel;
    private double[] datas;

    public MseChart() {
    }

    public ChartPanel getChartPanel(double[] datas) {
        this.datas = datas;
        dataset = createDataset();
        chart = createChart(dataset);
        chartPanel = new ChartPanel(chart);
        return chartPanel;
    }

    public XYDataset createDataset() {
        final XYSeries series2 = new XYSeries("MSE");
        for (int i = 0; i < datas.length; i++) {
            series2.add((i + 1), datas[i]);
        }

        XYSeriesCollection dataSetColl = new XYSeriesCollection();
        dataSetColl.addSeries(series2);

        return dataSetColl;
    }

    private JFreeChart createChart(final XYDataset dataset) {
        final JFreeChart chart = ChartFactory.createXYLineChart(
                "Mean Square Error", // chart title
                "epoch", // x axis label
                "mse", // y axis label
                dataset, // data
                PlotOrientation.VERTICAL,
                true, // include legend
                true, // tooltips
                false // urls
                );

        chart.setBackgroundPaint(Color.white);

        return chart;

    }
}
