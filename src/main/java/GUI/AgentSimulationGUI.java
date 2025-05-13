package GUI;

import Enums.PresetSimulationValues;
import Enums.SimulationSpeedLimitValues;
import GUI.Models.*;
import OSPABA.ISimDelegate;
import Observer.Subject;
import delegates.*;
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

public class AgentSimulationGUI extends AbstractSimulationGUI {
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
    private final JCheckBox animationCheckBox;
    private final JLabel simulationSpeedLabel;
    private JSpinner workerASpinner;
    private JSpinner workerBSpinner;
    private JSpinner workerCSpinner;
    private JSpinner workPlaceSpinner;
    private JLabel countALabel;
    private JLabel countBLabel;
    private boolean paused = false;
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
    private JLabel queueLengthLabel1;
    private JLabel queueLengthLabel2;
    private JLabel queueLengthLabel3;
    private JLabel queueLengthLabel4;
    private JLabel queueLengthLabel5;
    private SimulationTimeDelegate simulationTimeDelegate;
    private TableDelegate tableDelegate;
    private final LabelDelegate slowSpeedLabelDelegate;
    private GraphDelegate graphDelegate;
    private JLabel workerACountValue;
    private JLabel workerBCountValue;
    private JLabel workerCCountValue;
    private JLabel workplaceCountValue;
    private JLabel replicationsCountValue;
    private JLabel burnInValue;
    private FastMLabelDelegate fastMLabelDelegate;
    private final WorkerAverageUtilisationTableModel workerAverageUtilisationTableModel;
    private JTable workerUtilisationTable;
    public AgentSimulationGUI() {
        super("Event Simulation");
        this.simulationSpeedLabel = new JLabel("Simulation Speed: ");
        label = new JLabel("Simulation Time : 0");
        this.replicationCountLabel = new JLabel("Replication Count : 0");
        replicationCountLabel.setVisible(true);
        //replicationsInput.setVisible(true);


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

        replicationLabel.setVisible(true);
        core = new MySimulation();
        dayCountLabel = new JLabel("Day : 0");
        //callback UI refresh

        core.onSimulationDidFinish(sim -> {
            System.out.println("Simulácia skončila!");
        });


        JButton pauseButton = new JButton("Pause Simulation");
        this.controlPanel.add(pauseButton);
        pauseButton.addActionListener(e -> {
            if(paused) {
                core.resumeSimulation();
                paused = false;
            } else {
                core.pauseSimulation();
                paused = true;
            }

        });
        core.setSlowMode(true);


        //tables
        ordersTableModel = new OrdersTableModel(new ArrayList<>());
        workersTableModel = new WorkersTableModel(new ArrayList<>());
        workPlacesTableModel = new WorkPlacesTableModel(new ArrayList<>());
        furnitureTableModel = new FurnitureTableModel(new ArrayList<>());

        JTable ordersTable = new JTable(ordersTableModel);
        ordersTable.setPreferredScrollableViewportSize(new Dimension(600, 600));
        JScrollPane ordersScroll = new JScrollPane(ordersTable);

        JTable workersTable = new JTable(workersTableModel);
        workersTable.setPreferredScrollableViewportSize(new Dimension(600, 600));
        JScrollPane workersScroll = new JScrollPane(workersTable);

        JTable workPlaceTable = new JTable(workPlacesTableModel);
        workPlaceTable.setPreferredScrollableViewportSize(new Dimension(600, 600));
        JScrollPane workPlaceSroll = new JScrollPane(workPlaceTable);

        JTable furnitureTable = new JTable(furnitureTableModel);
        furnitureTable.setPreferredScrollableViewportSize(new Dimension(1500, 600)); // napr. viac ako 1000
        JScrollPane furnitureSroll = new JScrollPane(furnitureTable);
        furnitureSroll.setPreferredSize(new Dimension(1500, 300)); // rovnaká alebo väčšia šírka


        utilisationTableModel = new UtilisationTableModel(
                new ArrayList<>(), new ArrayList<>(), new ArrayList<>()
        );
        utilisationTable = new JTable(utilisationTableModel);
        utilisationTable.setPreferredScrollableViewportSize(new Dimension(400, 200));
        JScrollPane utilisationScroll = new JScrollPane(utilisationTable);
        utilisationScroll.setVisible(false);

        workerAverageUtilisationTableModel = new WorkerAverageUtilisationTableModel(
                core.getWorkersAUtilisationAverage(),
                core.getWorkersBUtilisationAverage(),
                core.getWorkersCUtilisationAverage());

        workerUtilisationTable = new JTable(workerAverageUtilisationTableModel);
        workerUtilisationTable.setPreferredScrollableViewportSize(new Dimension(600, 300));
        JScrollPane workerUtilisationScroll = new JScrollPane(workerUtilisationTable);
        workerUtilisationScroll.setPreferredSize(new Dimension(600, 300));


        JPanel tablePanel = new JPanel();
        tablePanel.setLayout(new BoxLayout(tablePanel, BoxLayout.X_AXIS));
        tablePanel.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
        tablePanel.add(Box.createHorizontalStrut(10));


        Dimension scrollSize = new Dimension(500, 300);
        ordersScroll.setPreferredSize(scrollSize);
        workersScroll.setPreferredSize(scrollSize);
        workPlaceSroll.setPreferredSize(scrollSize);
        utilisationScroll.setPreferredSize(scrollSize);
        furnitureSroll.setPreferredSize(scrollSize);

        tablePanel.add(ordersScroll);
        tablePanel.add(Box.createHorizontalStrut(10));
        tablePanel.add(workersScroll);
        tablePanel.add(Box.createHorizontalStrut(10));
        tablePanel.add(workPlaceSroll);
        tablePanel.add(Box.createHorizontalStrut(10));
        tablePanel.add(utilisationScroll);
        tablePanel.add(Box.createHorizontalStrut(10));
        tablePanel.add(furnitureSroll);

        JScrollPane scrollPane = new JScrollPane(tablePanel);
        scrollPane.setPreferredSize(new Dimension(1800, 600));
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_NEVER);

        scrollPane.setPreferredSize(new Dimension(1000, 600));


        // === SLOW MODE PANEL ===
        // === SLOW MODE PANEL ===
        JPanel slowModePanel = new JPanel(new BorderLayout());
        JPanel slowContent = new JPanel(new BorderLayout());
        slowContent.add(scrollPane, BorderLayout.CENTER);
        slowModePanel.add(slowContent, BorderLayout.CENTER);

// === FAST MODE PANEL ===
        JPanel fastModePanel = new JPanel(new BorderLayout());
        JPanel fastContent = new JPanel(new BorderLayout());
        fastContent.add(workerUtilisationScroll, BorderLayout.CENTER);

// Graf do FAST MODE
        ChartPanel fastChartPanel = new ChartPanel(chart);
        fastChartPanel.setPreferredSize(new Dimension(800, 400));
        fastContent.add(fastChartPanel, BorderLayout.SOUTH);

        fastModePanel.add(fastContent, BorderLayout.CENTER);

        centerPanel.addTab("Slow Mode", slowModePanel);
        centerPanel.addTab("Fast Mode", fastModePanel);
        centerPanel.setSelectedIndex(0);
        JPanel animationPanel = new JPanel(new BorderLayout());
        animationPanel.add(new JLabel("Sem príde animácia..."), BorderLayout.CENTER); // dočasný obsah
        centerPanel.addTab("Animácia", animationPanel);



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
            core.setSimSpeed(speed.getValue() / PresetSimulationValues.UPDATES_PER_SECOND.asDouble(), 1.0 / PresetSimulationValues.UPDATES_PER_SECOND.asDouble());
            core.onReplicationWillStart(_ -> {
                core.setSimSpeed(speed.getValue() / PresetSimulationValues.UPDATES_PER_SECOND.asDouble(), 1.0 / PresetSimulationValues.UPDATES_PER_SECOND.asDouble());
            });
        });

        slowDownCheckBox = new JCheckBox("Slow Down", true);
        animationCheckBox = new JCheckBox("Animation", false);

        //stats
        this.statsPanel.add(slowDownCheckBox);
        this.statsPanel.add(animationCheckBox);
        this.statsPanel.add(label);
        this.statsPanel.add(dayCountLabel);
        this.statsPanel.add(Box.createHorizontalStrut(5));
        this.statsPanel.add(newOrdersPanel);
        this.statsPanel.add(timeOfWorkPanel);

        //input
        this.inputPanel.add(simulationSpeedLabel);
        this.inputPanel.add(speedSlider);
        this.inputPanel.add(replicationCountLabel);

        tableDelegate = new TableDelegate(ordersTableModel, furnitureTableModel, workPlacesTableModel, workersTableModel);
        simulationTimeDelegate = new SimulationTimeDelegate(label, dayCountLabel);
        slowSpeedLabelDelegate = new LabelDelegate(
                queueLengthLabel1, // Cutting
                queueLengthLabel2, // Staining
                queueLengthLabel3, // Painting
                queueLengthLabel4, // Assembly
                queueLengthLabel5, // Montage,
                countOfAllOrdersLabel,
                countOfFinishedOrdersLabel,
                replicationCountLabel
        );

        graphDelegate = new GraphDelegate(orderTimeSeries, intervalLower, intervalUpper, chart);
        fastMLabelDelegate = new FastMLabelDelegate(
                utilisationALabel,
                utilisationBLabel,
                utilisationCLabel,
                utilisationAllLabel,
                utilisationAIntervalLabel,
                utilisationBIntervalLabel,
                utilisationCIntervalLabel,
                utilisationAllIntervalLabel,
                countOfFinishedOrdersLabel,
                countOfAllOrdersLabel,
                queueLengthLabel1,
                queueLengthLabel2,
                queueLengthLabel4,
                queueLengthLabel5,
                workerAverageUtilisationTableModel,
                timeOfWorkLabel,
                timeOfWorkIntervalLabel

        );


        ArrayList<ISimDelegate> delegates = new ArrayList<>();
        delegates.add(graphDelegate);
        delegates.add(simulationTimeDelegate);
        delegates.add(slowSpeedLabelDelegate);
        delegates.add(tableDelegate);
        delegates.add(fastMLabelDelegate);

        core.registerDelegates(delegates);

        slowDownCheckBox.addActionListener(e -> {
            if (slowDownCheckBox.isSelected()) {
                core.setSlowMode(true);
                SimulationSpeedLimitValues speed = SimulationSpeedLimitValues.fromSliderIndex(5);
                core.setSimSpeed(
                        speed.getValue() / PresetSimulationValues.UPDATES_PER_SECOND.asDouble(),
                        1.0 / PresetSimulationValues.UPDATES_PER_SECOND.asDouble()
                );
                core.onReplicationWillStart(_ -> core.setSimSpeed(
                        speed.getValue() / PresetSimulationValues.UPDATES_PER_SECOND.asDouble(),
                        1.0 / PresetSimulationValues.UPDATES_PER_SECOND.asDouble()
                ));
            } else {
                core.setSlowMode(false);
                core.setMaxSimSpeed();
                core.onReplicationWillStart(_ -> core.setMaxSimSpeed());
            }

            // Reset tabuliek
            ordersTableModel.setOrders(new ArrayList<>());
            workersTableModel.setWorkers(new ArrayList<>());
            workPlacesTableModel.setWorkPlaces(new ArrayList<>());
            furnitureTableModel.setFurniture(new ArrayList<>());
            label.setVisible(slowDownCheckBox.isSelected());
            dayCountLabel.setVisible(slowDownCheckBox.isSelected());
            utilisationALabel.setVisible(!slowDownCheckBox.isSelected());
            utilisationBLabel.setVisible(!slowDownCheckBox.isSelected());
            utilisationCLabel.setVisible(!slowDownCheckBox.isSelected());
            utilisationAllLabel.setVisible(!slowDownCheckBox.isSelected());
            utilisationAIntervalLabel.setVisible(!slowDownCheckBox.isSelected());
            utilisationBIntervalLabel.setVisible(!slowDownCheckBox.isSelected());
            utilisationCIntervalLabel.setVisible(!slowDownCheckBox.isSelected());
            utilisationAllIntervalLabel.setVisible(!slowDownCheckBox.isSelected());
            //statisticsForSimPanel.setVisible(slowDownCheckBox.isSelected());
            //newOrdersPanel.setVisible(!slowDownCheckBox.isSelected());
            timeOfWorkPanel.setVisible(!slowDownCheckBox.isSelected());
        });

        animationCheckBox.addActionListener(e -> {
            if (animationCheckBox.isSelected()) {
                System.out.println("Animation button pressed");
                for (ISimDelegate delegate : delegates) {
                    core.removeDelegate(delegate);
                }

            } else {
                System.out.println("Animation button unpressed");
                core.registerDelegates(delegates);
            }
        });

    }


    @Override
    protected void initializeChart() {
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
    }



    @Override
    protected void setupCustomInput() {


    }


    @Override
    protected void setupCustomPanel() {
        customPanel = new JPanel();
        customPanel.setLayout(new GridBagLayout());
        customPanel.setBorder(BorderFactory.createTitledBorder("Statistics"));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.anchor = GridBagConstraints.WEST;
        gbc.gridwidth = GridBagConstraints.REMAINDER;

       /* // ==== KONFIGURÁCIA Z PRESET SIMULATION VALUES ====
        workerACountValue = new JLabel("Workers A: ");
        workerBCountValue = new JLabel("Workers B: ");
        workerCCountValue = new JLabel("Workers C: ");
        workplaceCountValue = new JLabel("Workplaces: ");
        replicationsCountValue = new JLabel("Replications: ");
        burnInValue = new JLabel("Burn-in count: ");

        customPanel.add(workerACountValue, gbc);
        customPanel.add(workerBCountValue, gbc);
        customPanel.add(workerCCountValue, gbc);
        customPanel.add(workplaceCountValue, gbc);
        customPanel.add(replicationsCountValue, gbc);
        customPanel.add(burnInValue, gbc);
*/
        // Oddeľovač
        customPanel.add(new JSeparator(SwingConstants.HORIZONTAL), gbc);

        // ==== SPINNERY PRE NASTAVENIE ====
        workerASpinner = new JSpinner(new SpinnerNumberModel(6, 0, 100, 1));
        workerBSpinner = new JSpinner(new SpinnerNumberModel(5, 0, 100, 1));
        workerCSpinner = new JSpinner(new SpinnerNumberModel(38, 0, 100, 1));
        workPlaceSpinner = new JSpinner(new SpinnerNumberModel(58, 0, 100, 1));

        JPanel spinnerPanel = new JPanel(new GridLayout(4, 2, 5, 5));
        spinnerPanel.add(new JLabel("Set Workers A:"));
        spinnerPanel.add(workerASpinner);
        spinnerPanel.add(new JLabel("Set Workers B:"));
        spinnerPanel.add(workerBSpinner);
        spinnerPanel.add(new JLabel("Set Workers C:"));
        spinnerPanel.add(workerCSpinner);
        spinnerPanel.add(new JLabel("Set Workplaces:"));
        spinnerPanel.add(workPlaceSpinner);

        customPanel.add(spinnerPanel, gbc);

        // Oddeľovač
        customPanel.add(new JSeparator(SwingConstants.HORIZONTAL), gbc);

        // ==== FRONTY ====
        queueLengthLabel1 = new JLabel("Cutting QL : ");
        queueLengthLabel2 = new JLabel("Staining QL : ");
        queueLengthLabel3 = new JLabel("Painting QL : ");
        queueLengthLabel4 = new JLabel("Assembly QL : ");
        queueLengthLabel5 = new JLabel("Montage QL : ");

        customPanel.add(queueLengthLabel1, gbc);
        customPanel.add(queueLengthLabel2, gbc);
        customPanel.add(queueLengthLabel3, gbc);
        customPanel.add(queueLengthLabel4, gbc);
        customPanel.add(queueLengthLabel5, gbc);

        // Oddeľovač
        customPanel.add(new JSeparator(SwingConstants.HORIZONTAL), gbc);

        // ==== OBJEDNÁVKY ====
        countOfAllOrdersLabel = new JLabel("All Orders : ");
        countOfFinishedOrdersLabel = new JLabel("Finished Orders : ");

        customPanel.add(countOfAllOrdersLabel, gbc);
        customPanel.add(countOfFinishedOrdersLabel, gbc);

        // Oddeľovač
        customPanel.add(new JSeparator(SwingConstants.HORIZONTAL), gbc);

        // ==== VYUŽITIE ====
        utilisationALabel = new JLabel("Utilisation A: ");
        utilisationAIntervalLabel = new JLabel("CI: ");
        utilisationALabel.setVisible(false);
        utilisationAIntervalLabel.setVisible(false);

        utilisationBLabel = new JLabel("Utilisation B: ");
        utilisationBIntervalLabel = new JLabel("CI: ");
        utilisationBLabel.setVisible(false);
        utilisationBIntervalLabel.setVisible(false);

        utilisationCLabel = new JLabel("Utilisation C: ");
        utilisationCIntervalLabel = new JLabel("CI: ");
        utilisationCLabel.setVisible(false);
        utilisationCIntervalLabel.setVisible(false);

        utilisationAllLabel = new JLabel("Utilisation All: ");
        utilisationAllIntervalLabel = new JLabel("CI: ");
        utilisationAllLabel.setVisible(false);
        utilisationAllIntervalLabel.setVisible(false);

        customPanel.add(utilisationALabel, gbc);
        customPanel.add(utilisationAIntervalLabel, gbc);
        customPanel.add(utilisationBLabel, gbc);
        customPanel.add(utilisationBIntervalLabel, gbc);
        customPanel.add(utilisationCLabel, gbc);
        customPanel.add(utilisationCIntervalLabel, gbc);
        customPanel.add(utilisationAllLabel, gbc);
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

                // === ZÍSKANIE HODNÔT Z GUI ===
                int workersA = (int) workerASpinner.getValue();
                int workersB = (int) workerBSpinner.getValue();
                int workersC = (int) workerCSpinner.getValue();
                int workplaces = (int) workPlaceSpinner.getValue();
                int replications = Integer.parseInt(replicationsInput.getText());
                int burnIn = Integer.parseInt(burnInInput.getText());

                // === NASTAVENIE DO SIMULÁCIE ===
                core.setCountWorkerA(workersA);
                core.setCountWorkerB(workersB);
                core.setCountWorkerC(workersC);
                core.setWorkPlacesCount(workplaces);
                core.setReplicationsCount(replications);
                core.setBurnInCount(burnIn);

                // === SLOWMODE / FASTMODE ===
                if (slowDownCheckBox.isSelected()) {
                    core.setSlowMode(true);
                    SimulationSpeedLimitValues speed = SimulationSpeedLimitValues.fromSliderIndex(speedSlider.getValue());
                    core.setSimSpeed(
                            speed.getValue() / PresetSimulationValues.UPDATES_PER_SECOND.asDouble(),
                            1.0 / PresetSimulationValues.UPDATES_PER_SECOND.asDouble()
                    );
                } else {
                    core.setSlowMode(false);
                    core.setMaxSimSpeed();
                }

                // === SPUSTENIE ===
                worker = new AgentSimulationWorker();
                worker.execute();

                startButton.setEnabled(false);
                stopButton.setEnabled(true);

            } catch (Exception e) {
                e.printStackTrace(); // pre debug, odporúčam logovať chyby
            }
        }
    }

    @Override
    protected void stopSimulation() {
        core.stopSimulation();
        worker.cancel(true);

    }

    public JSpinner getWorkPlaceSpinner() {
        return workPlaceSpinner;
    }

    public void setWorkPlaceSpinner(JSpinner workPlaceSpinner) {
        this.workPlaceSpinner = workPlaceSpinner;
    }


    private class AgentSimulationWorker extends SwingWorker<Void, Void> {
        @Override
        protected Void doInBackground() {
            core.simulate(core.getReplicationsCount(), PresetSimulationValues.END_OF_SIMULATION.asDouble());
            return null;
        }

        @Override
        protected void done() {
            startButton.setEnabled(true);
            stopButton.setEnabled(false);
        }
    }
}
