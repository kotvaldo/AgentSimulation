package GUI;

import Enums.PresetSimulationValues;
import Enums.SimulationSpeedLimitValues;
import GUI.Models.*;
import Observer.Subject;
import delegates.SimulationTimeDelegate;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.XYPlot;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;
import simulation.MySimulation;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.Dictionary;
import java.util.Hashtable;

public class EventSimulationGUI extends AbstractSimulationGUI {
    private JLabel label;
    private final MySimulation core;
    private AgentSimulationWorker worker;
    private final JSlider speedSlider;
    private final OrdersTableModel ordersTableModel;
    private final WorkersTableModel workersTableModel;
    private final WorkPlacesTableModel workPlacesTableModel;
    private final FurnitureTableModel furnitureTableModel;
    private final JLabel dayCountLabel;
    private final JLabel replicationCountLabel;
    private final JCheckBox slowDownCheckBox;
    private final JLabel simulationSpeedLabel;
    private JSpinner workerASpinner;
    private JSpinner workerBSpinner;
    private JSpinner workerCSpinner;
    private JLabel countALabel;
    private JLabel countBLabel;
    private JLabel countCLabel;
    private final JLabel newOrdersAfterSimulationLabel;
    private final JLabel timeOfWorkLabel;
    private JLabel utilisationALabel;
    private JLabel utilisationBLabel;
    private JLabel utilisationCLabel;
    private JLabel utilisationAllLabel;
    private JLabel utilisationAIntervalLabel;
    private JLabel utilisationBIntervalLabel;
    private JLabel utilisationCIntervalLabel;
    private JLabel utilisationAllIntervalLabel;
    private UtilisationTableModel utilisationTableModel;
    private JTable utilisationTable;
    private Subject subject;
    private JFreeChart chart;
    private ChartPanel chartPanel;
    XYSeriesCollection dataset;
    XYSeries orderTimeSeries;
    XYSeries intervalLower;
    XYSeries intervalUpper;
    private final JLabel newOrdersIntervalLabel;
    private final JLabel timeOfWorkIntervalLabel;
    private JLabel countOfFinishedOrdersLabel;
    private JLabel countOfAllOrdersLabel;
    private JPanel statisticsForSimPanel;
    JLabel queueLengthLabel1;
    JLabel queueLengthLabel2;
    JLabel queueLengthLabel3;
    JLabel queueLengthLabel4;


    public EventSimulationGUI() {
        super("Event Simulation");
        this.simulationSpeedLabel = new JLabel("Simulation Speed: ");
        label = new JLabel("Simulation Time : 0");
        this.replicationCountLabel = new JLabel("Replication Count : 0");
        replicationCountLabel.setVisible(false);
        replicationsInput.setVisible(false);


        JPanel newOrdersPanel = new JPanel(new GridLayout(2, 1));
        newOrdersAfterSimulationLabel = new JLabel("Average Non Started Orders : ");
        newOrdersIntervalLabel = new JLabel("CI: ");
        newOrdersPanel.add(newOrdersAfterSimulationLabel);
        newOrdersPanel.add(newOrdersIntervalLabel);
        newOrdersPanel.setVisible(false);

        JPanel timeOfWorkPanel = new JPanel(new GridLayout(2, 1));
        timeOfWorkLabel = new JLabel("Average time of Work : ");
        timeOfWorkIntervalLabel = new JLabel("CI: ");
        timeOfWorkPanel.add(timeOfWorkLabel);
        timeOfWorkPanel.add(timeOfWorkIntervalLabel);
        timeOfWorkPanel.setVisible(false);
        Subject subject = new Subject();

        replicationLabel.setVisible(false);
        core = new MySimulation();
        dayCountLabel = new JLabel("Day : 0");
        core.registerDelegate(new SimulationTimeDelegate());
        //callback UI refresh
        core.onRefreshUI(core -> {
            System.out.println("🔁 refreshUI called at sim time: " + core.currentTime());
            System.out.println("📦 počet objednávok: " + ((MySimulation) core).getOrderArrayList().size());
            System.out.println("👷‍♀️ pracovníci A: " + ((MySimulation) core).getWorkersAArrayList().size());
            subject.notifyObservers();
        });


        JButton pauseButton = new JButton("Pause Simulation");
        this.controlPanel.add(pauseButton);
        pauseButton.addActionListener(e -> core.pauseSimulation());
        core.setSlowMode(true);


        //tables
        ordersTableModel = new OrdersTableModel(new ArrayList<>());
        workersTableModel = new WorkersTableModel(new ArrayList<>());
        workPlacesTableModel = new WorkPlacesTableModel(new ArrayList<>());
        furnitureTableModel = new FurnitureTableModel(new ArrayList<>());

        JTable ordersTable = new JTable(ordersTableModel);
        ordersTable.setPreferredScrollableViewportSize(new Dimension(400, 600));
        JScrollPane ordersScroll = new JScrollPane(ordersTable);

        JTable workersTable = new JTable(workersTableModel);
        workersTable.setPreferredScrollableViewportSize(new Dimension(400, 600));
        JScrollPane workersScroll = new JScrollPane(workersTable);

        JTable workPlaceTable = new JTable(workPlacesTableModel);
        workPlaceTable.setPreferredScrollableViewportSize(new Dimension(400, 600));
        JScrollPane workPlaceSroll = new JScrollPane(workPlaceTable);

        JTable furnitureTable = new JTable(furnitureTableModel);
        furnitureTable.setPreferredScrollableViewportSize(new Dimension(400, 600));
        JScrollPane furnitureSroll = new JScrollPane(furnitureTable);


        utilisationTableModel = new UtilisationTableModel(
                new ArrayList<>(), new ArrayList<>(), new ArrayList<>()
        );
        utilisationTable = new JTable(utilisationTableModel);
        utilisationTable.setPreferredScrollableViewportSize(new Dimension(400, 200));
        JScrollPane utilisationScroll = new JScrollPane(utilisationTable);
        utilisationScroll.setVisible(false);

        JPanel tablePanel = new JPanel();
        tablePanel.setLayout(new BoxLayout(tablePanel, BoxLayout.X_AXIS));
        tablePanel.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));

        tablePanel.add(ordersScroll);
        tablePanel.add(Box.createHorizontalStrut(10));
        tablePanel.add(workersScroll);
        tablePanel.add(Box.createHorizontalStrut(10));
        tablePanel.add(workPlaceSroll);
        tablePanel.add(utilisationScroll);
        tablePanel.add(Box.createHorizontalStrut(10));
        tablePanel.add(furnitureSroll);
        JScrollPane scrollPane = new JScrollPane(tablePanel);
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_NEVER); // ak nechceš vertikálny scroll

        this.centerPanel.add(scrollPane);


        //statistic for simulation run


        speedSlider = new JSlider(1, 9, 1);
        speedSlider.setPaintTicks(true);
        speedSlider.setPaintLabels(true);
        speedSlider.setMajorTickSpacing(1);
        speedSlider.setMinorTickSpacing(1);
        speedSlider.setPreferredSize(new Dimension(250, 50));

        Dictionary<Integer, JLabel> labelTable = new Hashtable<>();
        labelTable.put(1, new JLabel("1"));
        labelTable.put(2, new JLabel("10"));
        labelTable.put(3, new JLabel("100"));
        labelTable.put(4, new JLabel("500"));
        labelTable.put(5, new JLabel("1K"));
        labelTable.put(6, new JLabel("10K"));
        labelTable.put(7, new JLabel("36K"));
        labelTable.put(8, new JLabel("100K"));
        labelTable.put(9, new JLabel("300K"));
        speedSlider.setLabelTable(labelTable);

        speedSlider.addChangeListener(e -> {
            SimulationSpeedLimitValues speed = SimulationSpeedLimitValues.fromSliderIndex(speedSlider.getValue());
            core.setSimSpeed(speed.getValue() / PresetSimulationValues.UPDATES_PER_SECOND.getValue(), 1.0 / PresetSimulationValues.UPDATES_PER_SECOND.getValue());
        });


        slowDownCheckBox = new JCheckBox("Slow Down", true);
        slowDownCheckBox.addActionListener(e -> {
            if (slowDownCheckBox.isSelected()) {
                speedSlider.setValue(1);
                SimulationSpeedLimitValues speed = SimulationSpeedLimitValues.fromSliderIndex(5);
                core.setSimSpeed(
                        speed.getValue() / PresetSimulationValues.UPDATES_PER_SECOND.getValue(),
                        1.0 / PresetSimulationValues.UPDATES_PER_SECOND.getValue()
                );
            }
            ordersScroll.setVisible(slowDownCheckBox.isSelected());
            workersScroll.setVisible(slowDownCheckBox.isSelected());
            workPlaceSroll.setVisible(slowDownCheckBox.isSelected());
            speedSlider.setVisible(slowDownCheckBox.isSelected());
            simulationSpeedLabel.setVisible(slowDownCheckBox.isSelected());
            replicationCountLabel.setVisible(!slowDownCheckBox.isSelected());
            label.setVisible(slowDownCheckBox.isSelected());
            dayCountLabel.setVisible(slowDownCheckBox.isSelected());
            replicationsInput.setVisible(!slowDownCheckBox.isSelected());
            replicationLabel.setVisible(!slowDownCheckBox.isSelected());
            burnInInput.setVisible(!slowDownCheckBox.isSelected());
            burnLabel.setVisible(!slowDownCheckBox.isSelected());
            utilisationALabel.setVisible(!slowDownCheckBox.isSelected());
            utilisationBLabel.setVisible(!slowDownCheckBox.isSelected());
            utilisationCLabel.setVisible(!slowDownCheckBox.isSelected());
            utilisationAllLabel.setVisible(!slowDownCheckBox.isSelected());
            utilisationAIntervalLabel.setVisible(!slowDownCheckBox.isSelected());
            utilisationBIntervalLabel.setVisible(!slowDownCheckBox.isSelected());
            utilisationCIntervalLabel.setVisible(!slowDownCheckBox.isSelected());
            utilisationAllIntervalLabel.setVisible(!slowDownCheckBox.isSelected());
            //statisticsForSimPanel.setVisible(slowDownCheckBox.isSelected());
            utilisationScroll.setVisible(!slowDownCheckBox.isSelected());
            if (chartPanel != null) {
                chartPanel.setVisible(!slowDownCheckBox.isSelected());
            }
            newOrdersPanel.setVisible(!slowDownCheckBox.isSelected());
            timeOfWorkPanel.setVisible(!slowDownCheckBox.isSelected());

        });

        //stats
        this.statsPanel.add(slowDownCheckBox);
        this.statsPanel.add(label);
        this.statsPanel.add(dayCountLabel);
        this.statsPanel.add(Box.createHorizontalStrut(5));
        this.statsPanel.add(newOrdersPanel);
        this.statsPanel.add(timeOfWorkPanel);


        //input
        this.inputPanel.add(simulationSpeedLabel);
        this.inputPanel.add(speedSlider);
        this.inputPanel.add(replicationCountLabel);

    }

    @Override
    protected void initializeChart() {
        // === GRAF ===
        orderTimeSeries = new XYSeries("Average compute time");
        intervalLower = new XYSeries("CI: Lower value");
        intervalUpper = new XYSeries("CI: Upper value");

        dataset = new XYSeriesCollection();
        dataset.addSeries(orderTimeSeries);
        dataset.addSeries(intervalLower);
        dataset.addSeries(intervalUpper);

        chart = ChartFactory.createXYLineChart(
                "Average working time",
                "Replication",
                "Time [sek]",
                dataset
        );

        XYPlot plot = chart.getXYPlot();

        plot.getRangeAxis().setAutoRange(true);
        plot.getDomainAxis().setAutoRange(true);

        chartPanel = new ChartPanel(chart);
        chartPanel.setPreferredSize(new Dimension(800, 400));
        centerPanel.add(chartPanel);
        chartPanel.setVisible(false);
    }


    @Override
    protected void setupCustomInput() {


    }


    @Override
    protected void setupCustomPanel() {
        customPanel = new JPanel();
        customPanel.setLayout(new GridBagLayout());
        customPanel.setBorder(BorderFactory.createTitledBorder("Statistics"));

        queueLengthLabel1 = new JLabel("Cutting QL : ");
        queueLengthLabel2 = new JLabel("Coloring QL : ");
        queueLengthLabel3 = new JLabel("Assembly QL : ");
        queueLengthLabel4 = new JLabel("Montage QL : ");

        workerASpinner = new JSpinner(new SpinnerNumberModel(2, 0, 1000, 1));
        workerBSpinner = new JSpinner(new SpinnerNumberModel(2, 0, 1000, 1));
        workerCSpinner = new JSpinner(new SpinnerNumberModel(18, 0, 1000, 1));

        countALabel = new JLabel("Count A: ");
        countBLabel = new JLabel("Count B: ");
        countCLabel = new JLabel("Count C: ");

        utilisationALabel = new JLabel("Utilisation A: ");
        utilisationAIntervalLabel = new JLabel("CI: ");
        utilisationAIntervalLabel.setVisible(false);
        utilisationALabel.setVisible(false);

        utilisationBLabel = new JLabel("Utilisation B: ");
        utilisationBIntervalLabel = new JLabel("CI: ");

        utilisationBIntervalLabel.setVisible(false);
        utilisationBLabel.setVisible(false);


        utilisationCLabel = new JLabel("Utilisation C: ");
        utilisationCIntervalLabel = new JLabel("CI: ");

        utilisationCIntervalLabel.setVisible(false);
        utilisationCLabel.setVisible(false);

        utilisationAllLabel = new JLabel("Utilisation All: ");
        utilisationAllIntervalLabel = new JLabel("CI: ");
        utilisationAllIntervalLabel.setVisible(false);
        utilisationAllLabel.setVisible(false);

        countOfFinishedOrdersLabel = new JLabel("Finished Orders : ");
        countOfAllOrdersLabel = new JLabel("All Orders : ");


        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.anchor = GridBagConstraints.WEST;

        // ---------- SPINNERS + LABELS ----------

        gbc.gridx = 0;
        gbc.gridy = 0;
        customPanel.add(countALabel, gbc);
        gbc.gridx = 1;
        customPanel.add(workerASpinner, gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        customPanel.add(countBLabel, gbc);
        gbc.gridx = 1;
        customPanel.add(workerBSpinner, gbc);

        gbc.gridx = 0;
        gbc.gridy = 2;
        customPanel.add(countCLabel, gbc);
        gbc.gridx = 1;
        customPanel.add(workerCSpinner, gbc);


        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.gridwidth = 2;
        customPanel.add(Box.createVerticalStrut(15), gbc);
        gbc.gridwidth = 1;

// ---------- STATS ----------

        gbc.gridx = 0;
        gbc.gridy = 4;
        customPanel.add(queueLengthLabel1, gbc);

        gbc.gridy = 5;
        customPanel.add(queueLengthLabel2, gbc);

        gbc.gridy = 6;
        customPanel.add(queueLengthLabel3, gbc);

        gbc.gridy = 7;
        customPanel.add(queueLengthLabel4, gbc);

        gbc.gridy = 8;
        customPanel.add(countOfAllOrdersLabel, gbc);

        gbc.gridy = 9;
        customPanel.add(countOfFinishedOrdersLabel, gbc);

// ---------- UTILISATIONS ----------

        gbc.gridx = 0;
        gbc.gridy = 10;
        customPanel.add(utilisationALabel, gbc);
        gbc.gridx = 0;
        gbc.gridy = 11;
        customPanel.add(utilisationAIntervalLabel, gbc);

        gbc.gridx = 0;
        gbc.gridy = 12;
        customPanel.add(utilisationBLabel, gbc);
        gbc.gridx = 0;
        gbc.gridy = 13;
        customPanel.add(utilisationBIntervalLabel, gbc);

        gbc.gridx = 0;
        gbc.gridy = 14;
        customPanel.add(utilisationCLabel, gbc);
        gbc.gridx = 0;
        gbc.gridy = 15;
        customPanel.add(utilisationCIntervalLabel, gbc);

        gbc.gridx = 0;
        gbc.gridy = 16;
        customPanel.add(utilisationAllLabel, gbc);
        gbc.gridx = 0;
        gbc.gridy = 17;
        customPanel.add(utilisationAllIntervalLabel, gbc);
        customPanel.add(utilisationAllIntervalLabel, gbc);
        customPanel.setVisible(true);
    }


    @Override
    protected void startSimulation() {
        if (worker == null || worker.isDone()) {
            try {
                orderTimeSeries.clear();
                intervalUpper.clear();
                intervalLower.clear();
                SwingUtilities.invokeLater(() -> {
                    dayCountLabel.setText("Day : 0");
                    label.setText("Simulation Time : 0");
                    ordersTableModel.setOrders(new ArrayList<>());
                    workersTableModel.setWorkers(new ArrayList<>());
                    workPlacesTableModel.setWorkPlaces(new ArrayList<>());
                    furnitureTableModel.setFurniture(new ArrayList<>());
                });

                int replicationCount = Integer.parseInt(replicationsInput.getText());
                System.out.println("replication count: " + replicationCount);
                core.onSimulationDidFinish(sim -> {
                    System.out.println("Simulácia skončila!");
                });

                int burnInCount = 0;
                core.setReplicationsCount(replicationCount);
                SimulationSpeedLimitValues speed = SimulationSpeedLimitValues.fromSliderIndex(speedSlider.getValue());
                core.setSimSpeed(
                        speed.getValue() / PresetSimulationValues.UPDATES_PER_SECOND.getValue(),
                        1.0 / PresetSimulationValues.UPDATES_PER_SECOND.getValue()
                );

                core.setCountWorkerA((Integer) workerASpinner.getValue());
                core.setCountWorkerB((Integer) workerBSpinner.getValue());
                core.setCountWorkerC((Integer) workerCSpinner.getValue());
                //core.setBurnInCount(burnInCount);
                core.setWorkPlacesCount(5);

                worker = new AgentSimulationWorker();
                worker.execute();

                startButton.setEnabled(false);
                stopButton.setEnabled(true);

            } catch (Exception e) {

            }
        }
    }

    @Override
    protected void stopSimulation() {
        core.stopSimulation();
        worker.cancel(true);

    }


    private class AgentSimulationWorker extends SwingWorker<Void, Void> {
        @Override
        protected Void doInBackground() {
            core.simulate(core.getReplicationsCount(), PresetSimulationValues.END_OF_SIMULATION.getValue());
            return null;
        }

        @Override
        protected void done() {
            startButton.setEnabled(true);
            stopButton.setEnabled(false);
        }
    }
}
